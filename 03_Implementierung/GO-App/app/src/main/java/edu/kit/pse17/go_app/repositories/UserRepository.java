package edu.kit.pse17.go_app.repositories;

import android.content.SharedPreferences;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;

import edu.kit.pse17.go_app.model.entities.Group;
import edu.kit.pse17.go_app.model.entities.User;
import edu.kit.pse17.go_app.serverCommunication.upstream.TomcatRestApi;
import edu.kit.pse17.go_app.serverCommunication.upstream.TomcatRestApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Dieses Repository verwaltet und vermittelt Datenanfragen und Datenänderungen, die mit dem Benutzerkonto
 * selbst verknüpfte Informationen betreffen.
 *
 * Im Gegensatz zu anderen Repositories spricht diese Klasse auch die SharedPreferences des Systems an.
 */

public class UserRepository extends Repository<User> {

    private final TomcatRestApi apiService;
    //private ArrayList<Group> groups;

    public UserRepository() {
        this.apiService = TomcatRestApiClient.getClient().create(TomcatRestApi.class);
        //groups = null;
    }

    public void createUser(String userId) {
        Call<Void> call = apiService.createUser(userId);
        call.enqueue(new Callback<Void>() {

            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("create_user", t.toString());
            }
        });
    }

    public void deleteUser(String userId) {
        Call<Void> call = apiService.deleteUser(userId);
        call.enqueue(new Callback<Void>() {

            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("delete_user", t.toString());
            }
        });
    }

    public void registerDevice(String instanceId) {
        Call<Void> call = apiService.registerDevice(instanceId);
        call.enqueue(new Callback<Void>() {

            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("register_device", t.toString());
            }
        });
    }


    @Override
    public User fetchData() {
        return null;
    }

    @Override
    public User getUpdatedData() {
        return null;
    }


    /*
    private final TomcatRestApi apiService;
    private final SharedPreferences sharedPrefManager;
    /*
    * Wird wie Thread benutzt, um die UI nicht anzuhalten
    * */
   /* private final Executor executor;

    public UserRepository(TomcatRestApi apiService, SharedPreferences sharedPrefManager, Executor executor) {
        this.apiService = apiService;
        this.sharedPrefManager = sharedPrefManager;
        this.executor = executor;
    } */

}
