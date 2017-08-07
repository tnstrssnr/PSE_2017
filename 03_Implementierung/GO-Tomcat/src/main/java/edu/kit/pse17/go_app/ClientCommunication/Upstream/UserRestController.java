package edu.kit.pse17.go_app.ClientCommunication.Upstream;

import edu.kit.pse17.go_app.PersistenceLayer.GoEntity;
import edu.kit.pse17.go_app.PersistenceLayer.GroupEntity;
import edu.kit.pse17.go_app.PersistenceLayer.UserEntity;
import edu.kit.pse17.go_app.PersistenceLayer.daos.UserDao;
import edu.kit.pse17.go_app.PersistenceLayer.daos.UserDaoImp;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Die Klasse UserRestController gehört zum Upstream ClientCommunication Modul und bildet einen Teil der REST API, die der
 * Tomcat Server den Clients zur Kommunikation anbietet. Die Aufgabe dieser Klasse ist die Abwicklung von REST-Requests, die User-spezifische
 * Anfragen beinhalten. Dazu gehört:
 * - das Empfangen und Senden von HTTP-Requests
 * - das Parsen der empfangenen / zu sendenden Daten von bzw. nach JSON
 * - das Weiterleiten der Anfragen zur Bearbeitung an die richtige Stelle im Programm (das UserDAO)
 *
 * Das REST API wird umgesetzt von dem Java Framework Spring, anhand der Annotationen der Methoden in dieser Klasse. Die Klasse
 * selbst ist annotiert mit "@RestController", um zu signalisieren, dass es sich um eine Klasse handelt, deren Methoden Rest Resourcen beschreiben.
 * Die Methoden dieser Klasse sind auf die URL {Base_URL}/user gemappt.
 *
 * Die Methoden der Klasse werden aufgerufen, von den Methoden des Interfaces "TomcatRestApi", das von den Clients des Systems verwendet wird.
 *
 * Bei einem Methodenaufruf in dieser Klasse, wird die Anfrage an die DAOs der MySQL Datenbank der Anwendung weitergeleitet. Von dort werden die richtigen
 * Daten geholt (falls der Client bestimmte Daten in der Antwort erwartet). Dnach werden die Daten von dieser Klasse in JSON-Objekte umgewandelt
 * (mithilfe der Gson Library) und dem Client in der Antwort zugesendet.
 *
 * Nähere Erläuterungen zum JSON-Schema und der Konvertierung finden sich im Entwurfsdokument
 *
 * Die Klasse verfügt nur über den Standard-Konstruktor (der implizit gegeben ist). Es muss nirgends im Programm eine Instanz dieser Klasse erzeugt werden. Um die Instanziierung und
 * Objektverwaltung dieser Klasse kümmert sich das Spring-Framework.
 *
 */

@RestController
@RequestMapping("/user")
public class UserRestController {

    /**
     * Ein Objekt einer Klasse, die das Interface UserDAo implementiert. Dieses Objekt besitzt Methoden, um auf die Datenbank
     * des Systems zuzugreifen und Daten zu manipulieren. Es wird benötigt, um die Anfragen, die durch die REST Calls an den Server gestellt werden, umzusetzen.
     */

    @Autowired
    private UserDao userDao;

    /**
     * Diese Methode liefert dem Anfragenden eine Liste aller Gruppen, in der der Benutzer mit der User ID {userId} Mitglied ist,
     * bzw. zu denen er eine Anfrage bekommen hat. Sie wird genau dann von einem Client aufgerufen, wenn ein Benutzer sich in der App anmeldet.
     *
     * Der Aufruf dieser Methode entspricht einem HTTP GET-Request an den Server an die URL {Base_URL}/user/{userId}, die {userId}
     *
     * Da in den Gruppen die einzelnen Gos dieser Gruppe gespeichert sind, erhält der Anfragende mit dem Aufruf dieser Methode sämtliche
     * Daten, die den Benutzer mit der User ID {userID} betreffen und für das Navigieren und Benutzen der App benötigt werden (angesehen von
     * Änderungen der Daten, die zu einem späteren Zeitpunkt stattfinden).
     *
     * @param userId Die ID des Benutzers, dessen Daten zurückgegeben werden sollen. Diese wird von Spring aus der URL extrahiert
     *               und als Argument der Methode verwendet.
     * @return eine Liste aller Gruppen von Gruppenobjekten. Der Rückgabewert dieser Methode wird innerhalb der Methode in ein JSON-Objekt geparst und in der empfangenden Methode des Clients
     * zu Java Objekten konvertiert. Die Konvertierung nach JSON und zurück ändert nicht den Inhalt der Daten.
     *
     * Die Liste kann leer sein, für den Fall dass ein Benutzer nicht Mitglied in irgendeiner Gruppe ist. In diesem Fall wird in dem JSON-Objekt
     * ein leerer data-Block übertragen. Die Länge der Liste ist auf 300 Gruppen beschränkt (dies ist die Gesamtanzahl an Gruppen, die von dem System unterstützt
     * werden)
     */

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/{userId}"
    )
    public Set<GroupEntity> getData(@PathVariable("userId") String userId) {
        Set<GroupEntity> groups; // = userDao.getGroups(userId);

        groups = new HashSet<>();
        groups.add(TestData.getTestGroupBar());
        groups.add(TestData.getTestGroupFoo());

        for(GroupEntity group: groups) {
            for(UserEntity usr: group.getAdmins()) {
                usr.setGos(null);
                usr.setGroups(null);
                usr.setGroups(null);
            }

            for(UserEntity usr: group.getMembers()) {
                usr.setGos(null);
                usr.setGroups(null);
                usr.setGroups(null);
            }

            for(UserEntity usr: group.getRequests()) {
                usr.setGos(null);
                usr.setGroups(null);
                usr.setGroups(null);
            }

            for(GoEntity go: group.getGos()) {
                go.setGroup(null);
                go.getOwner().setGroups(null);
                go.getOwner().setGos(null);
                go.getOwner().setRequests(null);

                for(UserEntity usr: go.getNotGoingUsers()) {
                    usr.setGos(null);
                    usr.setGroups(null);
                    usr.setGroups(null);
                }
                for(UserEntity usr: go.getGoingUsers()) {
                    usr.setGos(null);
                    usr.setGroups(null);
                    usr.setGroups(null);
                }
                for(UserEntity usr: go.getGoneUsers()) {
                    usr.setGos(null);
                    usr.setGroups(null);
                    usr.setGroups(null);
                }
            }
        }

        return groups;
    }

    /**
     * Diese Methode wird aufgerufen, wenn ein Benutzer sich zum ersten Mal in der App anmeldet. Die Methode veranlasst das userDAO
     * einen neuen eintrag in der Datenbank anzulegen. Dazu überträgt der Client die benötigten Daten im Request Body der HTTP-Anfrage,
     * verpackt als JSON-Objekt.
     *
     * Der Aufruf dieser Methode entspricht einem HTTP POST-Request an die URL {base_URL}/user/{userId}.
     *
     * Die Methode besitzt keinen Rückgabewert, lediglich einen Statuscode in der HTTP-Antwort, die an den Anfragenden gesendet wird. Der Statuscode
     * gibt an, ob die Transaktion erfolgreich war.
     *
     * @param userId Die ID des Benutzers, der sich registriert. Diese muss eindeutig sein. Die ID wird generiert von dem Firebase
     *               Authentication Service, der auch die Eindeutigkeit derselben sicherstellt. Diese wird von Spring aus der URL extrahiert
     *               und als Argument der Methode verwendet.
     * @param email Die E-Mailadresse des Benutzers, die mit dem Google-Account assoziiert ist, mit dem er sich angemeldet hat.
     */
    @RequestMapping(
            method = RequestMethod.POST,
            value = "/{userId}"
    )
    public void createUser(String email, @PathVariable("userId") String userId) {

    }

    /**
     * Diese Methode wird aufgerufen, wenn ein Benutzer seinen Benutzeraccount löschen möchte.
     *
     * In der Methode wird das UserDAO dazu aufgerufen, das Tupel aus der User-Relation zu entfernen. Durch Fremdschlüssel-Constraints
     * in der Datenbank, werden alle dem User gehörenden Gruppen (in denen er Admin war) , GOs (in denen er Go-Verantwortlicher war),
     * Gruppenmitgliedschaften sowie Gruppenanfragen an den User automatisch gelöscht.
     *
     * Der Aufruf dieser Methode entspricht einem HTTP DELETE-Request an die URL {base_URL}/user/{userId}.
     *
     * Die Methode besitzt keinen Rückgabewert, lediglich einen Statuscode in der HTTP-Antwort, die an den Anfragenden gesendet wird. Der Statuscode
     * gibt an, ob die Transaktion erfolgreich war.
     *
     * @param userId die ID des Benutzers, dessen Konto entfernt werden soll. Diese wird von Spring aus der URL extrahiert
     *               und als Argument der Methode verwendet.
     */

    @RequestMapping(
            method = RequestMethod.DELETE,
            value = "/{userId}"
    )
    public void deleteUser(String userId) {

    }

    /**
     * Diese Methoe wird aufgerufen, um das Gerät, dass ein Benutzer aktuell benutzt auf dem Server mit seiner InstanceId zu registrieren.
     * Die InstanceId wird vom Server benötigt, um das Gerät des Benutzers identifizieren zu können, um Kommunikaiotnsströme zu initiieren.
     * Da diese InstanceId sich von Gerät zu Gerät unterscheidet bzw. sich durch Konfiguationsänderungen ändern kann, sollte diese Methode
     * zusätzlich zu getData() bei jeder Anmeldung von dem Client aufgerufen werden.
     *
     *  Der Aufruf dieser Methode entspricht einem HTTP PUT-Request an die URL {base_URL}/user/device/{instanceId}.
     *
     *
     * @param instanceId Die InstanceID des Geräts, an dem sich der User angemeldet hat. Diese wird von Spring aus der URL extrahiert
     *                   und als Argument der Methode verwendet. Generiert wird die InstanceId von dem Service Firebase Cloud Messaging, der auch benutzt wird,
     *                   um Downstream-Kommunikation zu realisieren.
     */

    @RequestMapping(
            method = RequestMethod.PUT,
            value = "/device/{instanceId}"
    )
    public void registerDevice(@PathVariable("instanceId") String instanceId) {
    }
}
