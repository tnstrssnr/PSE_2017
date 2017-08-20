package edu.kit.pse17.go_app.repositories;

import android.util.Log;

import javax.inject.Singleton;

import edu.kit.pse17.go_app.model.entities.User;
import edu.kit.pse17.go_app.serverCommunication.upstream.TomcatRestApi;
import edu.kit.pse17.go_app.serverCommunication.upstream.TomcatRestApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * The User Repository manages and sends data requests and data changes, which
 * with the user account linked informations are.
 *
 * Unlike other Repositories, this class also addresses
 * the SharedPreferences of the system.
 * This Repository is also a singleton.
 */
@Singleton
public class UserRepository extends Repository<User> {

    /**
     * Private attribute for UserRepository (see Singleton).
     */
    private static UserRepository userRepo;

    /**
     * The Reference to the REST-API, which TomcatServer provides, so that
     * communication with the server is possible.
     */
    private final TomcatRestApi apiService;
    //private ArrayList<Group> groups;

    /**
     * Constructor for User Repository.
     */
    public UserRepository() {
        this.apiService = TomcatRestApiClient.getClient().create(TomcatRestApi.class);
        //groups = null;
    }

    //TODO parameter User user, then take all http parameters from the user object
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

    /**
     * This method deletes user.
     *
     * @param userId: ID of the user
     */
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

    public void registerDevice(String userId, String instanceId) {
        /*Call<Void> call = apiService.registerDevice(userId, instanceId);
        call.enqueue(new Callback<Void>() {

            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                String message = response.message();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("register_device", t.toString());
            }
        });*/
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

    /**
     * GetInstance method for UserRepository Singleton.
     *
     * @return UserRepository Singleton object
     */
    public static UserRepository getInstance() {
        if (userRepo == null) {
            userRepo = new UserRepository()/*, GroupListViewModel.getCurrentGroupListViewModel().getObserver()*/;
        }
        return userRepo;
    }
}
