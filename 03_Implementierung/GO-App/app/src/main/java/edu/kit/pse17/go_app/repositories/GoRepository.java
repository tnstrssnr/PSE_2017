package edu.kit.pse17.go_app.repositories;

import android.util.Log;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Singleton;

import edu.kit.pse17.go_app.model.Status;
import edu.kit.pse17.go_app.model.entities.Cluster;
import edu.kit.pse17.go_app.model.entities.Go;
import edu.kit.pse17.go_app.serverCommunication.upstream.TomcatRestApi;
import edu.kit.pse17.go_app.serverCommunication.upstream.TomcatRestApiClient;
import edu.kit.pse17.go_app.viewModel.livedata.GoLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Das Go-Repository ist verantwortlich für sämtliche Operationen auf den Go-Daten und stellt eine
 * einfache Schnittstelle zum Holen, Ändern und Löschen von Daten zur Verfügung.
 *
 * Bei einer Anfrage weiß das Repository, wo es die Daten holen muss (lokal oder vom remote Server).
 * Das Repository agiert als Vermittler zwischen der lokalen Datanbank und den Daten die die App vom Server erhält.
 */

@Singleton
public class GoRepository extends Repository<List<Go>>{

    private static GoRepository goRepo;
    /**
     * Eine Referenz auf das die Rest-Api, die der TomcatServer bereitstellt, um mit ihm kommunizieren zu können.
     */
    private final TomcatRestApi apiService;

    private Go go;
    private GoLiveData data;

    /**
     * Eine DAO zum komminizieren mit der lokalen go-Datenbankrelation
     */
    // private final GoDao goDao;

    /**
     * Ein executor-objekt, um Anfragen auf einem separaten Hintergrundthread ausführen zu können.
     */
    //private final Executor executor;

    //@Inject
    private GoRepository(/*GoDao godao Executor executor*/) {
        this.apiService = TomcatRestApiClient.getClient().create(TomcatRestApi.class);
        //this.executor = executor;
    }


    public void changeStatus(String userId, long goId, Status status) {
        final Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("userId", userId);
        parameters.put("goId", Long.toString(goId));
        parameters.put("status", status.name());

        Call<Void> call = apiService.changeStatus(parameters);
        call.enqueue(new Callback<Void>() {

            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("change_status", t.toString());
            }
        });
    }

    public void deleteGo(long goId) {
        Call<Void> call = apiService.deleteGo(goId);
        call.enqueue(new Callback<Void>() {

            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("delete_go", t.toString());
            }
        });
    }
    //using array latlon instead, because it won't accept more that 7 parameters... -_- wtf
    public void editGo(long goId, long groupId, String userId, String name,
                       String description, String start, String end,
                       double[] latlon, int threshold) {
        final Map<String, String> parameters = new HashMap<String, String>();
        double lat = latlon[0];
        double lon = latlon[1];
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

        Call<Void> call = apiService.editGo(parameters);
        call.enqueue(new Callback<Void>() {

            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("edit_go", t.toString());
            }
        });
    }

    public void getLocation(String userId, final long goId, double lat, double lon) {
        final Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("userId", userId);
        parameters.put("goId", Long.toString(goId));
        parameters.put("latitude", Double.toString(lat));
        parameters.put("longitude", Double.toString(lon));

        final Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                Call<List<Cluster>> call = apiService.getLocation(parameters);
                try {
                    go.setLocations(call.execute().body());
                    data.setValue(go);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
    }

    //-------------------------------------------------------------------


    @Override
    public List<Go> fetchData() {
        return null;
    }

    @Override
    public List<Go> getUpdatedData() {
        return null;
    }


    public static GoRepository getInstance(){
        if(goRepo == null){
            goRepo = new GoRepository(/*, GroupListViewModel.getCurrentGroupListViewModel().getObserver()*/);
        }
        return goRepo;
    }


    public GoLiveData getData() {
        return data;
    }

    public void setData(GoLiveData data) {
        this.data = data;
    }

    public Go getGo() {
        return go;
    }

    public void setGo(Go go) {
        this.go = go;
    }
}
