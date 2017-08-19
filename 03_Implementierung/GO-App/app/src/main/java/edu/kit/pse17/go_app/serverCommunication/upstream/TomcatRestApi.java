package edu.kit.pse17.go_app.serverCommunication.upstream;

import java.util.List;
import java.util.Map;

import edu.kit.pse17.go_app.model.entities.Cluster;
import edu.kit.pse17.go_app.model.entities.Go;
import edu.kit.pse17.go_app.model.entities.Group;
import edu.kit.pse17.go_app.model.entities.User;
import edu.kit.pse17.go_app.model.entities.UserLocation;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

/**
 * This is the interface between the Client (App) to the REST API of Tomcat-Server.
 * The communication with the REST API of server is taken from the Framework Retrofit.
 *
 * In this Interface are all methods defined that are offered from the REST API
 * of server. The implementation is not necessary, cause that would be defined from Retrofit.
 *
 * The return types of the methods of the interface match with the return types
 * of REST-Methods on server. However they are wrapped in the Call-Object,
 * as it in the case of use of the Retrofit Framework usual is.
 */
public interface TomcatRestApi {

    /**
     * Method that returns all the data of the user (groups, GOs, etc.) from server
     *
     * @return Call to the server (with List of the groups of this user (incl. GOs) in Response)
     */
    @GET("user/{userid}/{email}/{userName}")
    public Call<List<Group>> getData(@Path("userid") String userId, @Path("email") String email, @Path("userName") String userName);

    /**
     * Method that creates and registers new user on the server
     *
     * @param userId: ID of the user
     * @return Call to the server (no additional information in Response)
     */
    @POST("user/{userId}")
    public Call<Void> createUser(@Path("userId") String userId);

    /**
     * Method that deletes user from server
     *
     * @param userId: ID of the user
     * @return Call to the server (no additional information in Response)
     */
    @DELETE("user/{userId}")
    public Call<Void> deleteUser(@Path("userId") String userId);

    /**
     * Method that registers a new device on the server
     *
     * @param instanceId: ID of the device
     * @return Call to the server (no additional information in Response)
     */
    @PUT("user/{userId}/device/{instanceId]")
    public Call<Void> registerDevice(@Path("userId") String userId, @Path("instanceId") String instanceId);

    @PUT("user/update")
    public Call<Void> updateUser(@Body User user);


    /**
     * Method that creates a new group on the server
     *
     * @return Call to the server (with ID of the group)
     */
    @POST("group/")
    public Call<Long> createGroup(@Body Group group);

    /**
     * Method that changes information about the group on the server
     *
     * @return Call to the server (no additional information in Response)
     */
    @PUT("group/")
    public Call<Void> editGroup(@Body Group group);

    /**
     * Method that deletes the group from the server
     *
     * @param groupId: ID of the group
     * @return Call to the server (no additional information in Response)
     */
    @DELETE("group/{group_id}")
    public Call<Void> deleteGroup(@Path("group_id")long groupId);

    /**
     * Method that puts the acceptance of the group request by the user
     * through the server
     *
     * @return Call to the server (no additional information in Response)
     */
    @PUT("group/members/{group_id}/{user_id}")
    public Call<Void> acceptRequest(@Path("group_id") long groupId, @Path("user_id") String userId);

    /**
     * Method that puts the denial of the group request by the user
     * through the server
     *
     * @return Call to the server (no additional information in Response)
     */
    @DELETE("group/requests/{group_id}/{user_id}")
    public Call<Void> denyRequest(@Path("group_id") long groupId, @Path("user_id") String userId);

    /**
     * Method that removes member of the group on server
     *
     * @return Call to the server (no additional information in Response)
     */
    @DELETE("group/members/{group_id}/{user_id}")
    public Call<Void> removeMember(@Path("group_id") long groupId, @Path("user_id") String userId);

    /**
     * Method that sends a group request to some user
     * @return Call to the server (no additional information in Response)
     */
    @POST("group/requests/{group_id}/{user_id}")
    public Call<Void> inviteMember(@Path("group_id") long groupId, @Path("user_id") String userId);

    /**
     * Method that adds new administrator to the group on server
     *
     * @return Call to the server (no additional information in Response)
     */
    @POST("group/admin/{group_id}/{user_id}")
    public Call<Void> addAdmin(@Path("group_id") long groupId, @Path("user_id") String userId);

    /**
     * Method that creates a new GO in the group on server
     *
     * @return Call to the server (with the ID of GO in Response)
     */
    @POST("gos/{groupid}/{userid}")
    public Call<Long> createGo(@Body Go go, @Path("groupid") long goid, @Path("userid") String userid);


    /**
     * Method that changes the status of the user in the GO on server
     *
     * @param parameters:
     *                  String userId: ID of the user
     *                  (String) long goId: ID of the GO
     *                  (String) Status status: New status of the user in this GO
     * @return Call to the server (no additional information in Response)
     */
    @PUT("gos/{goId}/status")
    public Call<Void> changeStatus(@QueryMap Map<String, String> parameters, @Path("goId") long goId);

    /**
     * Method that returns the list of positions (clusters) of the GO participants
     *
     * @return Call to the server (with List of Clusters for the current GO)
     */
    @GET("gos/location/{goId}")
    public Call<List<Cluster>> getLocation(@Path("goId") long goId);

    @PUT("gos/location/{goId}")
    public Call<Void> setLocation(@Path("goId") long goId, @Body UserLocation location);

    /**
     * Method that deletes current GO from server
     *
     * @param goId: ID of the GO to be deleted
     * @return Call to the server (no additional information in Response)
     */
    @DELETE("gos/{goId}")
    public Call<Void> deleteGo(@Path("goId") long goId);

    /**
     * Method that changes the information about the current GO on server
     *
     * @return Call to the server (no additional information in Response)
     */
    @PUT("gos/{goid}")
    public Call<Void> editGo(@Body Go go, @Path("goid") long goid);
}