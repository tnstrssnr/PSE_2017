package edu.kit.pse17.go_app.repositories;

import android.util.Log;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Singleton;

import edu.kit.pse17.go_app.model.Status;
import edu.kit.pse17.go_app.model.entities.Cluster;
import edu.kit.pse17.go_app.model.entities.Go;
import edu.kit.pse17.go_app.model.entities.UserLocation;
import edu.kit.pse17.go_app.serverCommunication.upstream.TomcatRestApi;
import edu.kit.pse17.go_app.serverCommunication.upstream.TomcatRestApiClient;
import edu.kit.pse17.go_app.viewModel.livedata.GoLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * The GO Repository is responsible for all operations of the GO Data
 * and is the simple interface for fetching, changing and deletion of data.
 * In the case of a request the Repository can fetch the data from server.
 *
 * The Repository operates as a mediator between the local database and data
 * that the app from server obtains.
 * This Repository is also a singleton.
 */
@Singleton
public class GoRepository extends Repository<List<Go>>{

    /**
     * Private attribute for GoRepository (see Singleton).
     */
    private static GoRepository goRepo;

    /**
     * The Reference to the REST-API, which TomcatServer provides, so that
     * communication with the server is possible.
     */
    private final TomcatRestApi apiService;

    /**
     * Local database for GO (not consistent).
     */
    private Go go;

    /**
     * LiveData of the GO (consistent).
     */
    private GoLiveData data;

    /**
     * HTTP status code of the response of the server (by the requests).
     * It is used for testing.
     */
    private int responseStatus;

    /**
     * Constructor for the GO repository.
     */
    private GoRepository() {
        this.apiService = TomcatRestApiClient.getClient().create(TomcatRestApi.class);
        if (data == null) {
            data = new GoLiveData();
        }
    }

    /**
     * Method that changes the status of the user in the current GO.
     *
     * @param userId: ID of the user
     * @param goId: ID of the GO
     * @param status: New status of the user
     */
    public void changeStatus(String userId, long goId, Status status) {
        final Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("userId", userId);
        parameters.put("status", status.name());
        Call<Void> call = apiService.changeStatus(parameters, goId);
        call.enqueue(new Callback<Void>() {

            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                 responseStatus = response.code();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("change_status", t.toString());
            }
        });
    }

    /**
     * Method that deletes GO by ID.
     *
     * @param goId: ID of the GO
     */
    public void deleteGo(long goId) {
        Call<Void> call = apiService.deleteGo(goId);
        call.enqueue(new Callback<Void>() {

            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                responseStatus = response.code();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("delete_go", t.toString());
            }
        });
    }

    /**
     * Method that edits current GO.
     *
     * @param goId: ID of the GO
     * @param groupId: ID of the group
     * @param userId: ID of the owner
     * @param name: New name of the GO
     * @param description: New description of the GO
     * @param start: New start time
     * @param end: New end time
     * @param latLon: New desired location (latitude + longitude)
     * @param threshold: New threshold
     */

    /* using array latLon instead, because it won't accept more that 7 parameters */
    public void editGo(long goId, long groupId, String userId, String name,
                       String description, String start, String end,
                       double[] latLon, int threshold) {
        final Map<String, String> parameters = new HashMap<String, String>();
        double lat = latLon[0];
        double lon = latLon[1];
        parameters.put("goId", Long.toString(goId));
        parameters.put("groupId", Long.toString(groupId));
        parameters.put("userId", userId);
        parameters.put("name", name);
        parameters.put("description", description);
        parameters.put("start", start);
        parameters.put("end", end);
        parameters.put("latitude", Double.toString(lat));
        parameters.put("longitude", Double.toString(lon));
        parameters.put("threshold", Integer.toString(threshold));
        Go go = new Go(goId, name, description, start, end, null, lat, lon, null, null, null, null);
        Call<Void> call = apiService.editGo(go, go.getId());
        call.enqueue(new Callback<Void>() {

            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                responseStatus = response.code();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("edit_go", t.toString());
            }
        });
    }

    /**
     * Method that is responsible for locations. This method sends current location
     * of the user to the server and gets the list of clusters back.
     *
     * @param userId: ID of the user
     * @param goId: ID of the GO
     * @param lat: Latitude
     * @param lon: Longitude
     */
    public void getLocation(final String userId, final long goId, final double lat, final double lon) {
        final Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("userId", userId);
        parameters.put("goId", Long.toString(goId));
        parameters.put("latitude", Double.toString(lat));
        parameters.put("longitude", Double.toString(lon));

        Call<Void> sendLocation = apiService.setLocation(goId, new UserLocation(userId,lat, lon));
        sendLocation.enqueue(new Callback<Void>() {

            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                responseStatus = response.code();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });

        Call<List<Cluster>> getLocation = apiService.getLocation(goId);
        getLocation.enqueue(new Callback<List<Cluster>>() {
            @Override
            public void onResponse(Call<List<Cluster>> call, Response<List<Cluster>> response) {
                List<Cluster> locations = response.body();

                GroupRepository groupRepo = GroupRepository.getInstance();
                groupRepo.onLocationsUpdated(goId, locations);

                responseStatus = response.code();
            }

            @Override
            public void onFailure(Call<List<Cluster>> call, Throwable t) {

            }
        });
    }

    /**
     * GetInstance method for GoRepository Singleton.
     *
     * @return GoRepository Singleton object
     */
    public static GoRepository getInstance(){
        if (goRepo == null) {
            goRepo = new GoRepository();
        }
        return goRepo;
    }

    /**
     * Getter for LiveData.
     *
     * @return GoLiveData
     */
    public GoLiveData getData() {
        return data;
    }

    /**
     * Setter for LiveData.
     *
     * @param data: GoLiveData
     */
    public void setData(GoLiveData data) {
        this.data = data;
    }

    /**
     * Getter for current GO.
     *
     * @return GO object
     */
    public Go getGo() {
        return go;
    }

    /**
     * Setter for GO.
     *
     * @param go: GO object
     */
    public void setGo(Go go) {
        this.go = go;
    }

    /**
     * Getter for the HTTP status code of the response of the server.
     * It is used only for testing.
     *
     * @return The HTTP status code
     */
    @Deprecated
    public int getResponseStatus() {
        return responseStatus;
    }

    /**
     * Setter for the HTTP status code of the response of the server.
     * It is used only for testing (to reset the status code).
     *
     * @param responseStatus: Status of the response (0)
     */
    @Deprecated
    public void setResponseStatus(int responseStatus) {
        this.responseStatus = responseStatus;
    }
}
