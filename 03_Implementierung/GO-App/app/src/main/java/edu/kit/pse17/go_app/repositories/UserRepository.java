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
 * with the user account linked information are.
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

    /**
     * HTTP status code of the response of the server (by the requests).
     * It is used for testing.
     */
    private int responseStatus;

    /**
     * Constructor for User Repository.
     */
    private UserRepository() {
        this.apiService = TomcatRestApiClient.getClient().create(TomcatRestApi.class);
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
                responseStatus = response.code();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("delete_user", t.toString());
            }
        });
    }

    /**
     * GetInstance method for UserRepository Singleton.
     *
     * @return UserRepository Singleton object
     */
    public static UserRepository getInstance() {
        if (userRepo == null) {
            userRepo = new UserRepository();
        }
        return userRepo;
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