package edu.kit.pse17.go_app.serverCommunication.upstream;

import java.util.List;

import edu.kit.pse17.go_app.model.entities.Group;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * das Interface ist die Schnittstelle des Clients zur REST-API des Tomcat-Servers.
 *
 * Created by tina on 29.06.17.
 */


public interface TomcatRestApi {

    @GET("/users/{group}")
    Call<Group> getGroup(@Path("group") long groupId);

    @GET("/groups/{user}")
    Call<List<Group>> getGroupsByUser(@Path("user") String uid);

}