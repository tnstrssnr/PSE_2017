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
 * das Interface ist die Schnittstelle des Clients zur REST-API des Tomcat-Servers. Die Kommunikation mit der Rest-API des Servers
 * wird von dem Framework Retrofit uebernommen.
 *
 * In diesem Interface werden deshalb alle Methoden definiert, die von der REST API des Servers angeboten werden. Die Implementierung ist nicht noetig, dies
 * wird von Retrofit uebernommen.
 *
 * Aufgerufen werden diese Methoden in der Klassen des Repository Moduls.
 *
 * Genauere Beschreibungen zur Funktion, den Argumenten und Rueckgabetypen der Methoden sind in den Implementierungen der REST-API zu finden. dabei stimmen die Rueckgabetypen
 * der Methoden dieses Interface mit den Rueckgabetypen der Rest-Methoden des Servers ueberein, sind jedoch in einem Call_Objekt gewrappt, wie es bei der Benutzung des Retrofit Frameworks ueblich ist.
 */


public interface TomcatRestApi {

    /**
     *
     * @param parameters:
     *                  String userId
     *                  String email
     *                  String instanceId
     * @return
     */
    @GET("user/get")
    public Call<List<Group>> getData(@QueryMap Map<String, String> parameters);

    @PUT("user/create")
    public Call<Void> createUser(@Query("userId") String userId);

    @DELETE("user/delete")
    public Call<Void> deleteUser(@Query("userId") String userId);

    @PUT("user/register")
    public Call<Void> registerDevice(@Query("instanceId") String instanceId);

    /**
     *
     * @param parameters:
     *                  String name
     *                  String description
     *                  String userId
     * @return Long groupId
     */
    @PUT("group/create")
    public Call<Long> createGroup(@QueryMap Map<String, String> parameters);

    /**
     *
     * @param parameters:
     *                  long groupId
     *                  String name
     *                  String description
     * @return
     */
    @PUT("group/edit")
    public Call<Void> editGroup(@QueryMap Map<String, String> parameters);

    @DELETE("group/delete")
    public Call<Void> deleteGroup(@Query("groupId") long groupId);

    /**
     *
     * @param parameters:
     *                  long groupId
     *                  String userId
     * @return
     */
    @PUT("group/accept")
    public Call<Void> acceptRequest(@QueryMap Map<String, String> parameters);

    /**
     *
     * @param parameters:
     *                  long groupId
     *                  String userId
     * @return
     */
    @DELETE("group/remove")
    public Call<Void> removeMember(@QueryMap Map<String, String> parameters);

    /**
     *
     * @param parameters:
     *                  long groupId
     *                  String email
     * @return
     */
    @PUT("group/invite")
    public Call<Void> inviteMember(@QueryMap Map<String, String> parameters);

    /**
     *
     * @param parameters:
     *                  long groupId
     *                  String userId
     * @return
     */
    @PUT("group/deny")
    public Call<Void> denyRequest(@QueryMap Map<String, String> parameters);

    /**
     *
     * @param parameters:
     *                  long groupId
     *                  String userId
     * @return
     */
    @PUT("group/add")
    public Call<Void> addAdmin(@QueryMap Map<String, String> parameters);

    /**
     *
     * @param parameters:
     *                  String name
     *                  String description
     *                  Date start
     *                  Date end
     *                  double lat
     *                  double lon
     *                  int threshold
     *                  long groupId
     *                  String userId
     * @return Long goId
     */
    @PUT("go/create")
    public Call<Long> createGo(@QueryMap Map<String, String> parameters);

    /**
     *
     * @param parameters:
     *                  String userId
     *                  long goId
     *                  Status status
     * @return
     */
    @PUT("go/change")
    public Call<Void> changeStatus(@QueryMap Map<String, String> parameters);

    /**
     *
     * @param parameters:
     *                  String userId
     *                  long goId
     *                  double lat
     *                  double lon
     * @return
     */
    @GET("go/get")
    public Call<List<Cluster>> getLocation(@QueryMap Map<String, String> parameters);

    //public Call<Void> setLocation(String userId, long lat, long lon, String goId);

    @DELETE("go/delete")
    public Call<Void> deleteGo(@Query("goId") long goId);

    /**
     *
     * @param parameters:
     *                  long goId
     *                  long groupId
     *                  String userId
     *                  String name
     *                  String description
     *                  Date start
     *                  Date end
     *                  double lat
     *                  double lon
     *                  int threshold
     * @return
     */
    @PUT("go/edit")
    public Call<Void> editGo(@QueryMap Map<String, String> parameters);
}