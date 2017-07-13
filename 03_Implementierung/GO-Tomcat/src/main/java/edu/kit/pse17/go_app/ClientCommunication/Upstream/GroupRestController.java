package edu.kit.pse17.go_app.ClientCommunication.Upstream;

import edu.kit.pse17.go_app.PersistenceLayer.GroupEntity;
import edu.kit.pse17.go_app.PersistenceLayer.daos.GroupDao;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

/**
 *Die Klasse GroupRestController gehört zum Upstream ClientCommunication Modul und bildet einen Teil der REST API, die der
 * Tomcat Server den Clients zur Kommunikation anbietet. Die Aufgabe dieser Klasse ist die Abwicklung von REST-Requests, die Gruppen-spezifische
 * Anfragen beinhalten. Dazu gehört:
 * - das Empfangen und Senden von HTTP-Requests
 * - das Parsen der empfangenen / zu sendenden Daten von bzw. nach JSON
 * - das Weiterleiten der Anfragen zur Bearbeitung an die richtige Stelle im Programm (das GroupDAO)
 *
 * Das REST API wird umgesetzt von dem Java Framework Spring, anhand der Annotationen der Methoden in dieser Klasse. Die Klasse
 * selbst ist annotiert mit "@RestController", um zu signalisieren, dass es sich um eine Klasse handelt, deren Methoden Rest Resourcen beschreiben.
 * Die Methoden dieser Klasse sind auf die URL {Base_URL}/groups gemappt.
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
 */

@RestController
@RequestMapping("/groups")
public class GroupRestController {

    /**
     * Ein Objekt einer Klasse, die das Interface GroupDao implementiert. Dieses Objekt besitzt Methoden, um auf die Datenbank
     * des Systems zuzugreifen und Daten zu manipulieren. Es wird benötigt, um die Anfragen, die durch die REST Calls an den Server gestellt werden, umzusetzen.
     */
    private GroupDao groupDAO;


    /**
     * Diese Methode wird von einem Client aufgerufen, wenn eine neue Gruppe erstellt werden soll. Die Methode liest die Argumente aus dem Request Body
     * der HTTP Anfrage aus und übergibt diese an das groupDao zur Erzeugung der Gruppe in der Datenbank.
     * Zusätzlich zur Erzeugung derGruppe wird der Ersteller als Gruppenmitglied und Administrator zur Gruppe hinzugefügt.
     *
     * Der Aufruf dieser Methode entspricht einem HTTP POST-Request an den Server an die URL {Base_URL}/groups.
     *
     * @param name Der Name, den die Gruppe haben soll. Der String darf bis zu 50 Zeichen lang sein.
     * @param description Eine Gruppenbeschreibung. Dieser Wert ist möglicherweise nicht im Body der HTTP NAchricht enthalten. Das bedeutet
     *                    der Benutzer hat keine Beschreibung eingegeben. Die Variable wird daraufhin auf null gesetzt. ist der Wert nicht null, darf der String
     *                    maximal 140 Zeichen enthalten.
     * @param userId Die ID des Benutzers, der die Gruppe erstellt hat.
     * @return Die global eindeutige ID, die dier Gruppe zugewiesen wurde. Diese wird im Header der HTTP-Response im Location-Feld
     * an den Client zurückgesendet, also : {Base_URL}/gos/{goId} und kann dort vom Client ausgelesen werden. Der Wert ist eine positive ganze Zahl,
     * die im Wertebereich des primitiven Datentyps long liegt.
     */
    @RequestMapping(
            method = RequestMethod.POST,
            value = "/",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public long createGroup(String name, String description, String userId) {
        return -1;
    }

    /**
     * Diese Methode wird von einem Benutzer aufgerufen, wenn er die Daten der Gruppe ändern will. Zu den daten, die mit dieser Methode geändert werden können,
     * gehören:
     *
     * - der Gruppenname
     * - die Gruppenbeschreibung
     *
     * Es ist garantiert, dass dieser Aufruf nur von einem Administrator der zu ändernden gruppe kommt.
     *
     * Der Aufruf dieser Methode entspricht einem HTTP PUT-Request an den Server an die URL {Base_URL}/groups/{groupId}.
     *
     * @param groupId Die ID der Gruppe, die geändert werden soll. Der Wert dieses Arguments ist Teil der URL der REST Resource
     *               und wird entsprechend von Spring extrahiert und der Methode bereitgestellt. Die ID muss zu einem Long-Datentyp gecastet werden können.
     * @param description Die neue Beschreibung, die die Gruppe erhalten soll. Dieser Wert kann null sein, falls die Beschreibung nicht geändert wird. Der Wert
     *                    des Attributs ist im request Body der Anfrage gespeichert und wird von Spring ausgelesen und der Methode zur Verfügung gestellt.
     * @param name Der neue Name, den die Gruppe erhalten soll. Dieser Wert kann null sein, falls der Name nicht geändert wird. Der Wert
     *                    des Attributs ist im request Body der Anfrage gespeichert und wird von Spring ausgelesen und der Methode zur Verfügung gestellt.
     */
    @RequestMapping(
            method = RequestMethod.PUT,
            value = "/{groupId}")
    public void editGroup(@PathVariable Long groupId, String name, String description) {

    }

    /**
     * Diese Methode wird von einem Client aufgerufen, wenn er eine Gruppe löschen möchte. Durch einen Methodenaufruf bei dem groupDao wird
     * die Gruppe entsprechend aus der datenbank entfernt. Durch konsistenzkriterien in der Datenbank werden zusätzlich alle GOs, die es in der Gruppe gab
     * ebenfalls entfernt.
     *
     * Es ist garantiert, dass der Client, der die Gruppe aufruft dazu berechtigt ist, d.h. er ein Administrator der Gruppe ist.
     *
     * Der Aufruf dieser Methode entspricht einem HTTP DELETE-Request an den Server an die URL {Base_URL}/groups/{groupId}.
     *
     * @param groupId Die ID der gruppe, die gelöscht werden soll. Der Wert dieses Arguments ist Teil der URL der REST Resource
     *               und wird entsprechend von Spring extrahiert und der Methode bereitgestellt. Die ID muss zu einem Long-Datentyp gecastet werden können.
     */
    @RequestMapping(
            method = RequestMethod.DELETE,
            value = "/{groupId}")
    public void deleteGroup(@PathVariable Long groupId) {

    }

    /**
     * Diese Methode wird dann aufgerufen, wenn ein Benutzer eine bestehende Gruppenmitgliedschaftsanfrage bestätigt und
     * somit zu einem vollwertigen Mitglied der Gruppe wird.
     *
     * Bei einem Aufruf, müssen zwei Methoden des GroupDaos aufgerufen werden: Zunächst muss die Mitgliedschaftsanfrage, die
     * soeben beantwortet wurde, gelöscht werden, danach muss der Benutzer als Mitglied in die Gruppe eingefügt werden.
     *
     * Der Aufruf dieser Methode entspricht einem HTTP PUT-Request an den Server an die URL {Base_URL}/groups/members/{groupId}/{userId}.
     *
     * @param groupId Die ID der Gruppe, zu der der Benutzer hinzugefügt werden soll. Der Wert dieses Arguments ist Teil der URL der REST Resource
     *               und wird entsprechend von Spring extrahiert und der Methode bereitgestellt. Die ID muss zu einem Long-Datentyp gecastet werden können.
     * @param userId Die ID des Benutzers, der der Gruppe hinzugefügt werden soll. Der Wert dieses Arguments ist Teil der URL der REST Resource
     *               und wird entsprechend von Spring extrahiert und der Methode bereitgestellt.
     */
    @RequestMapping(
            method = RequestMethod.PUT,
            value = "/members/{groupId}/{userId}")
    public void acceptRequest(@PathVariable Long groupId, @PathVariable String userId) {

    }

    /**
     * Diese Methode kann von einem Client aufgerufen werden, wenn ein Gruppenmitglied aus einer Gruppe entfernt werden soll.
     * Dies kann der Fall sein, wenn ein Benutzer freiwillig aus einer Gruppe austritt oder wenn er von einem Administrator
     * aus der Gruppe entfernt wird.
     *
     * Bei einem Aufruf leitet die Methode die Anfrage an die entsprechende Methode des groupDAOs weiter. Dieses entfernt den Benutzer
     * aus der Gruppe. Durch Foreign Key Constraints in der DAtenbank wird der Benutzer auch aus allen GOs der Gruppe entfernt. Darum muss sich diese Methode
     * demnach nicht kümmern.
     *
     * Der Aufruf dieser Methode entspricht einem HTTP POST-Request an den Server an die URL {Base_URL}/groups/members/{groupId}/{userId}.
     *
     * @param userId Die ID des Benutzers, der aus der Gruppe entfernt werden soll. Der Wert dieses Arguments ist Teil der URL der REST Resource
     *               und wird entsprechend von Spring extrahiert und der Methode bereitgestellt.
     * @param groupId Die ID der Gruppe, aus der der Benutzer entfernt werden soll. Der Wert dieses Arguments ist Teil der URL der REST Resource
     *               und wird entsprechend von Spring extrahiert und der Methode bereitgestellt. Die ID muss zu einem Long-Datentyp gecastet werden können.
     */
    @RequestMapping(
            method = RequestMethod.DELETE,
            value = "/members/{groupId}/{userId}")
    public void removeMember(@PathVariable String userId, @PathVariable("groupId") String groupId) {
    }

    /**
     * Diese Methode wird von einem Client aufgerufen, der einen Benutzer zu einer Gruppe einladen will. Bei Aufruf dieser Methode
     * wird mittels des groupDAOs die Information über den Group Request in der Datenbank gespeicert.
     *
     * Es ist garantiert, dass der Client, der diese Methode aufruft ein Administrator sit und der eingeladene Benutzer nicht bereits Mitglied der Gruppe ist.
     * Diese Vorbedingungen müssen in der Methode nicht überprüft werden.
     *
     * Der Aufruf dieser Methode entspricht einem HTTP POST-Request an den Server an die URL {Base_URL}/groups/requests/{groupId}/{userId}.
     *
     * @param groupId Die ID der Gruppe, zu der der Benutzer eingladen werden soll. Der Wert dieses Arguments ist Teil der URL der REST Resource
     *               und wird entsprechend von Spring extrahiert und der Methode bereitgestellt. Die ID muss zu einem Long-Datentyp gecastet werden können.
     * @param userId Die ID des Benutzers, der zu der gruppe eingeladen werden soll. Der Wert dieses Arguments ist Teil der URL der REST Resource
     *               und wird entsprechend von Spring extrahiert und der Methode bereitgestellt.
     */
    @RequestMapping(
            method = RequestMethod.POST,
            value = "/requests/{groupId}/{userId}")
    public void inviteMember(@PathVariable Long groupId, @PathVariable String userId) {

    }

    /**
     * Diese Methode wird aufgerufen, wenn ein Benutzer eine gruppenmitgliedschaftsanfrage ablehnt. Beim Aufruf wird das groupDAO dazu veranlasst,
     * die Anfrage aus der Datenbank zu löschen.
     *
     * Der Aufruf dieser Methode entspricht einem HTTP POST-Request an den Server an die URL {Base_URL}/groups/requests/{groupId}/{userId}.
     *
     * @param groupId Die ID der Gruppe, zu der der Benutzer eingeladen war. Der Wert dieses Arguments ist Teil der URL der REST Resource
     *               und wird entsprechend von Spring extrahiert und der Methode bereitgestellt. Die ID muss zu einem Long-Datentyp gecastet werden können.
     * @param userId Die ID des Benutzers, der die Anfrage abgeleht hat. Der Wert dieses Arguments ist Teil der URL der REST Resource
     *               und wird entsprechend von Spring extrahiert und der Methode bereitgestellt.
     */
    @RequestMapping(
            method = RequestMethod.DELETE,
            value = "/requests/{groupId}/{userId}"
    )
    public void denyRequest(@PathVariable("groupId") String groupId, @PathVariable("userId") String userId) {

    }


    /**
     * Diese Methode wird aufgerufen, wenn ein Administrator einer Gruppe ein anderes Gruppenmitglied zu administrator ernennen will.
     * In der Methode wird eine Methode des groupDaos aufgerufen, die einen Datenbankzugriff ausführt und den entsprechenden Benutzer zu den Administratoren
     * der Gruppe hinzufügt.
     *
     * Es ist garantiert, dass der aufrufende Client ein Administrator der Gruppe ist und der neue Administrator bereits ein Gruppenmitglied ist.
     *
     * Der Aufruf dieser Methode entspricht einem HTTP POST-Request an den Server an die URL {Base_URL}/groups/admins/{groupId}/{userId}.
     *
     * @param groupId Die ID der Gruppe, in der der neue Administrator hinzugefügt werden soll. Der Wert dieses Arguments ist Teil der URL der REST Resource
     *               und wird entsprechend von Spring extrahiert und der Methode bereitgestellt. Die ID muss zu einem Long-Datentyp gecastet werden können.
     * @param userId Die ID des Benutzer, der zum Adminsitrator ernannt wird. Der Wert dieses Arguments ist Teil der URL der REST Resource
     *               und wird entsprechend von Spring extrahiert und der Methode bereitgestellt.
     */
    @RequestMapping(
            method = RequestMethod.POST,
            value = "/admins/{groupId}/{userId}"
    )
    public void addAdmin(@PathVariable("groupId") String groupId, @PathVariable("userId") String userId) {

    }


}
