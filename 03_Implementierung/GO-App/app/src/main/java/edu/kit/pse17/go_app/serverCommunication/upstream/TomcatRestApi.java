package edu.kit.pse17.go_app.serverCommunication;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;

/**
 * das Interface ist die Schnittstelle des Clients zur REST-API des Tomcat-Servers.
 *
 * Created by tina on 29.06.17.
 */


public interface TomcatRestApi {

    /**
     * Gibt alle Gruppen zurück, in denen der Benutzer Mitglied ist bzw. eine Mitgliedschaftsanfrage
     * erhalten hat.
     *
     * @param uid Benutzer.ID des Benutzers
     * @return Liste von Gruppen
     */
    @GET("/groups/getAllgroups/{userId}")
    public Call<String> getGroupsByUid(String uid);

    @GET("/groups/getGroupInfo/{groupId}")
    public Call<String> getGroupInfo(int groupId);

    @PUT("/groups/newRequest/{groupId}/{userId}")
    public Call<String> inviteToGroup(int groupId, String userId);

    @PUT("/groups/newMember/{groupId}/{userId}")
    public Call<String> acceptGroupRequest(int groupId, String userId);

    @PUT("/groups/alter/{groupId}")
    public Call<String> alterGroupData(int groupId);

    @POST("/groups/add")
    public Call<String> createGroup();

    @DELETE("/groups/delete/{groupId}")
    public Call<String> deleteGroup(int groupId);

    @DELETE("/groups/delete/{userId}")
    public Call<String> deleteMember(String userId);
}