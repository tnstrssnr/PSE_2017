package edu.kit.pse17.go_app.repositories;

import android.arch.lifecycle.LiveData;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.StringDef;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;

import javax.inject.Inject;
import javax.inject.Singleton;

import edu.kit.pse17.go_app.model.Status;
import edu.kit.pse17.go_app.model.entities.Cluster;
import edu.kit.pse17.go_app.model.entities.Go;
import edu.kit.pse17.go_app.model.entities.Group;
import edu.kit.pse17.go_app.serverCommunication.upstream.TomcatRestApi;
import edu.kit.pse17.go_app.serverCommunication.upstream.TomcatRestApiClient;
import edu.kit.pse17.go_app.viewModel.livedata.GoLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

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

    private ArrayList<Go> list;
    private GoLiveData data;

    /**
     * Eine DAO zum komminizieren mit der lokalen go-Datenbankrelation
     */
    // private final GoDao goDao;

    /**
     * Ein executor-objekt, um Anfragen auf einem separaten Hintergrundthread ausführen zu können.
     */
    //private final Executor executor;

    @Inject
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

    public void editGo(long goId, long groupId, String userId, String name,
                       String description, String start, String end,
                       double lat, double lon, int threshold) {
        final Map<String, String> parameters = new HashMap<String, String>();
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
                for (Go go: list) {
                    if (go.getId() == goId) {
                        try {
                            go.setLocations(call.execute().body());
                            data.setValue(go);
                            break;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
        t.start();
    }

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

    public ArrayList<Go> getList() {
        return list;
    }

    public void setList(ArrayList<Go> list) {
        this.list = list;
    }

    public GoLiveData getData() {
        return data;
    }

    public void setData(GoLiveData data) {
        this.data = data;
    }
}
