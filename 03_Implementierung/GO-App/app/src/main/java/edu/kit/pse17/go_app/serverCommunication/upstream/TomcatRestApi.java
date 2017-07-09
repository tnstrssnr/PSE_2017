package edu.kit.pse17.go_app.serverCommunication.upstream;

import java.util.Date;
import java.util.List;

import edu.kit.pse17.go_app.model.Status;
import edu.kit.pse17.go_app.model.entities.Cluster;
import edu.kit.pse17.go_app.model.entities.Group;
import retrofit2.Call;

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
 * der Methoden dieses Interface mit den Rueckgabetypen der Rest-Methoden des Servers ueberein, sind jedoch in einem Call_Objket gewrappt, wie es bei der Benutzung des Retrofit Frameworks ueblich ist.
 */


public interface TomcatRestApi {

    public Call<List<Group>> getData(String userId);

    public Call<Void> createUser(String userId);

    public Call<Void> deleteUser(String userId);

    public Call<Void> registerDevice(String instanceId);

    public Call<Long> createGroup(String name, String description, String userId);

    public Call<Void> editGroup(long groupId, String name, String description);

    public Call<Void> deleteGroup(Long groupId);

    public Call<Void> acceptRequest(long groupId, String userId);

    public Call<Void> removeMember(String userId, long groupId);

    public Call<Void> inviteMember(long groupId, String userId);

    public Call<Void> denyRequest(String userId, String groupId);

    public Call<Void> addAdmin(String groupId, String userId);

    public Call<Long> createGo(String name, String description, Date start, Date end, double lat, double lon, int threshold, long groupId, String userId);

    public Call<Void> changeStatus(long goId, String userId, Status status);

    public Call<List<Cluster>> getLocation(String goId);

    public Call<Void> setLocation(String userId, long lat, long lon, String goId);

    public Call<Void> deleteGo(String goId);

    public Call<Void> editGo(String goId, String name, String description, Date start, Date end, long lat, long lon, int threshold);
}