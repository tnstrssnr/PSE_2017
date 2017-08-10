package edu.kit.pse17.go_app.serverCommunication.upstream;

import java.util.List;
import java.util.Map;

import edu.kit.pse17.go_app.model.entities.Cluster;
import edu.kit.pse17.go_app.model.entities.Group;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Query;
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
     * @param parameters:
     *                  String userId: ID of the user
     *                  String email: E-Mail address of the user
     *                  String instanceId: ID of the device
     * @return Call to the server (with List of the groups of this user (incl. GOs) in Response)
     */
    @GET("group/get")
    public Call<List<Group>> getData(@QueryMap Map<String, String> parameters);

    /**
     * Method that creates and registers new user on the server
     *
     * @param userId: ID of the user
     * @return Call to the server (no additional information in Response)
     */
    @PUT("user/create")
    public Call<Void> createUser(@Query("userId") String userId);

    /**
     * Method that deletes user from server
     *
     * @param userId: ID of the user
     * @return Call to the server (no additional information in Response)
     */
    @DELETE("user/delete")
    public Call<Void> deleteUser(@Query("userId") String userId);

    /**
     * Method that registers a new device on the server
     *
     * @param instanceId: ID of the device
     * @return Call to the server (no additional information in Response)
     */
    @PUT("user/register")
    public Call<Void> registerDevice(@Query("instanceId") String instanceId);


    /**
     * Method that creates a new group on the server
     *
     * @param parameters:
     *                  String name: Name of the group
     *                  String description: Description of the group
     *                  String userId: ID of administrator of the group
     * @return Call to the server (with ID of the group)
     */
    @PUT("group/createGroup")
    public Call<Long> createGroup(@QueryMap Map<String, String> parameters);

    /**
     * Method that changes information about the group on the server
     *
     * @param parameters:
     *                  (String) long groupId: ID of the group
     *                  String name: New name of the group
     *                  String description: New description of the group
     * @return Call to the server (no additional information in Response)
     */
    @PUT("group/edit")
    public Call<Void> editGroup(@QueryMap Map<String, String> parameters);

    /**
     * Method that deletes the group from the server
     *
     * @param groupId: ID of the group
     * @return Call to the server (no additional information in Response)
     */
    @DELETE("group/delete")
    public Call<Void> deleteGroup(@Query("groupId") long groupId);

    /**
     * Method that puts the acceptance of the group request by the user
     * through the server
     *
     * @param parameters:
     *                  (String) long groupId: ID of the group
     *                  String userId: ID of the user
     * @return Call to the server (no additional information in Response)
     */
    @PUT("group/accept")
    public Call<Void> acceptRequest(@QueryMap Map<String, String> parameters);

    /**
     * Method that puts the denial of the group request by the user
     * through the server
     *
     * @param parameters:
     *                  (String) long groupId: ID of the group
     *                  String userId: ID of the user
     * @return Call to the server (no additional information in Response)
     */
    @PUT("group/deny")
    public Call<Void> denyRequest(@QueryMap Map<String, String> parameters);

    /**
     * Method that removes member of the group on server
     *
     * @param parameters:
     *                  (String) long groupId: ID of the group
     *                  String userId: ID of the user
     * @return Call to the server (no additional information in Response)
     */
    @DELETE("group/remove")
    public Call<Void> removeMember(@QueryMap Map<String, String> parameters);

    /**
     * Method that sends a group request to some user
     *
     * @param parameters:
     *                  (String) long groupId: ID of the group
     *                  String email: E-Mail address of the user
     * @return Call to the server (no additional information in Response)
     */
    @PUT("group/invite")
    public Call<Void> inviteMember(@QueryMap Map<String, String> parameters);

    /**
     * Method that adds new administrator to the group on server
     *
     * @param parameters:
     *                  (String) long groupId: ID of the group
     *                  String userId: ID of the user
     * @return Call to the server (no additional information in Response)
     */
    @PUT("group/add")
    public Call<Void> addAdmin(@QueryMap Map<String, String> parameters);

    /**
     * Method that creates a new GO in the group on server
     *
     * @param parameters:
     *                  String name: Name of the GO
     *                  String description: Description of the GO
     *                  String start: Start time of the GO
     *                  String end: End time of the GO
     *                  (String) double lat: Desired latitude (for location)
     *                  (String) double lon: Desired longitude (for location)
     *                  (String) int threshold: Threshold
     *                  (String) long groupId: ID of the group
     *                  String userId: ID of the responsible for this GO user
     * @return Call to the server (with the ID of GO in Response)
     */
    @PUT("group/createGo")
    public Call<Long> createGo(@QueryMap Map<String, String> parameters);


    /**
     * Method that changes the status of the user in the GO on server
     *
     * @param parameters:
     *                  String userId: ID of the user
     *                  (String) long goId: ID of the GO
     *                  (String) Status status: New status of the user in this GO
     * @return Call to the server (no additional information in Response)
     */
    @PUT("go/change")
    public Call<Void> changeStatus(@QueryMap Map<String, String> parameters);

    /**
     * Method that returns the list of positions (clusters) of the GO participants
     *
     * @param parameters:
     *                  String userId: ID of the user
     *                  (String) long goId: ID of the current GO
     *                  (String) double lat: Latitude of this user (to be counted
     *                                       to the cluster)
     *                  (String) double lon Longitude of this user (to be counted
     *                                       to the cluster)
     * @return Call to the server (with List of Clusters for the current GO)
     */
    @GET("go/get")
    public Call<List<Cluster>> getLocation(@QueryMap Map<String, String> parameters);

    /**
     * Method that deletes current GO from server
     *
     * @param goId: ID of the GO to be deleted
     * @return Call to the server (no additional information in Response)
     */
    @DELETE("go/delete")
    public Call<Void> deleteGo(@Query("goId") long goId);

    /**
     * Method that changes the information about the current GO on server
     *
     * @param parameters:
     *                  (String) long goId: ID of the GO
     *                  (String) long groupId: ID of the group
     *                  String userId: ID of the user
     *                  String name: New name of the GO
     *                  String description: New description of the GO
     *                  String start: New start time
     *                  String end: New end time
     *                  (String) double lat: New desired latitude
     *                  (String) double lon: New desired longitude
     *                  (String) int threshold: New threshold
     * @return Call to the server (no additional information in Response)
     */
    @PUT("go/edit")
    public Call<Void> editGo(@QueryMap Map<String, String> parameters);
}