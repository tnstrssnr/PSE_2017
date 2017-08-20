package edu.kit.pse17.go_app.ClientCommunication.Upstream;

import edu.kit.pse17.go_app.PersistenceLayer.clientEntities.Group;
import edu.kit.pse17.go_app.ServiceLayer.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.OK;

/**
 * Die Klasse GroupRestController gehört zum Upstream ClientCommunication Modul und bildet einen Teil der REST API, die
 * der Tomcat Server den Clients zur Kommunikation anbietet. Die Aufgabe dieser Klasse ist die Abwicklung von
 * REST-Requests, die Gruppen-spezifische Anfragen beinhalten. Dazu gehört: - das Empfangen und Senden von HTTP-Requests
 * - das Parsen der empfangenen / zu sendenden Daten von bzw. nach JSON - das Weiterleiten der Anfragen zur Bearbeitung
 * an die richtige Stelle im Programm (das GroupDAO)
 * <p>
 * Das REST API wird umgesetzt von dem Java Framework Spring, anhand der Annotationen der Methoden in dieser Klasse. Die
 * Klasse selbst ist annotiert mit "@RestController", um zu signalisieren, dass es sich um eine Klasse handelt, deren
 * Methoden Rest Resourcen beschreiben. Die Methoden dieser Klasse sind auf die URL {Base_URL}/groups gemappt.
 * <p>
 * Die Methoden der Klasse werden aufgerufen, von den Methoden des Interfaces "TomcatRestApi", das von den Clients des
 * Systems verwendet wird.
 * <p>
 * Bei einem Methodenaufruf in dieser Klasse, wird die Anfrage an die DAOs der MySQL Datenbank der Anwendung
 * weitergeleitet. Von dort werden die richtigen Daten geholt (falls der Client bestimmte Daten in der Antwort
 * erwartet). Dnach werden die Daten von dieser Klasse in JSON-Objekte umgewandelt (mithilfe der Gson Library) und dem
 * Client in der Antwort zugesendet.
 * <p>
 * Nähere Erläuterungen zum JSON-Schema und der Konvertierung finden sich im Entwurfsdokument
 * <p>
 * Die Klasse verfügt nur über den Standard-Konstruktor (der implizit gegeben ist). Es muss nirgends im Programm eine
 * Instanz dieser Klasse erzeugt werden. Um die Instanziierung und Objektverwaltung dieser Klasse kümmert sich das
 * Spring-Framework.
 */

@RestController
@RequestMapping("/group")
public class GroupRestController {

    @Autowired
    private GroupService groupService;

    public GroupRestController(GroupService groupService) {
        this.groupService = groupService;
    }

    public GroupRestController() {
    }

    /**
     * Diese Methode wird von einem Client aufgerufen, wenn eine neue Gruppe erstellt werden soll. Die Methode liest die
     * Argumente aus dem Request Body der HTTP Anfrage aus und übergibt diese an das groupDao zur Erzeugung der Gruppe
     * in der Datenbank. Zusätzlich zur Erzeugung der Gruppe wird der Ersteller als Gruppenmitglied und Administrator zur
     * Gruppe hinzugefügt.
     * <p>
     * Der Aufruf dieser Methode entspricht einem HTTP POST-Request an den Server an die URL {Base_URL}/groups.
     *
     * @param group Gruppe die vom groupService erstellt werden soll.
     *
     * @return Die global eindeutige ID, die dieser Gruppe zugewiesen wurde. Diese wird im Header der HTTP-Response im
     * Location-Feld an den Client zurückgesendet, also : {Base_URL}/gos/{goId} und kann dort vom Client ausgelesen
     * werden. Der Wert ist eine positive ganze Zahl, die im Wertebereich des primitiven Datentyps long liegt.
     */
    @RequestMapping(
            method = RequestMethod.POST,
            value = "/",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Long> createGroup(@RequestBody final Group group) {
        return new ResponseEntity<>(groupService.createGroup(group), OK);
    }

    /**
     * Diese Methode wird von einem Benutzer aufgerufen, wenn er die Daten der Gruppe ändern will. Zu den daten, die mit
     * dieser Methode geändert werden können, gehören:
     * <p>
     * - der Gruppenname - die Gruppenbeschreibung
     * <p>
     * Es ist garantiert, dass dieser Aufruf nur von einem Administrator der zu ändernden Gruppe kommt.
     * <p>
     * Der Aufruf dieser Methode entspricht einem HTTP PUT-Request an den Server an die URL
     * {Base_URL}/groups/{groupId}.
     *
     * @param group Die zu ändernde Gruppe.
     */
    @RequestMapping(
            method = RequestMethod.PUT,
            value = "/")
    public ResponseEntity editGroup(@RequestBody final Group group) {
        groupService.editGroup(group);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * Diese Methode wird von einem Client aufgerufen, wenn er eine Gruppe löschen möchte. Durch einen Methodenaufruf
     * bei dem groupDao wird die Gruppe entsprechend aus der Datenbank entfernt. Durch Konsistenzkriterien in der
     * Datenbank werden zusätzlich alle Gos, die es in der Gruppe gab ebenfalls entfernt.
     * <p>
     * Es ist garantiert, dass der Client, der die Gruppe aufruft dazu berechtigt ist, d.h. er ein Administrator der
     * Gruppe ist.
     * <p>
     * Der Aufruf dieser Methode entspricht einem HTTP DELETE-Request an den Server an die URL
     * {Base_URL}/groups/{groupId}.
     *
     * @param groupId Die Id der Gruppe, die gelöscht werden soll. Der Wert dieses Arguments ist Teil der URL der REST
     *                Ressource und wird entsprechend von Spring extrahiert und der Methode bereitgestellt. Die Id muss
     *                zu einem Long-Datentyp gecastet werden können.
     */
    @RequestMapping(
            method = RequestMethod.DELETE,
            value = "/{groupId}")
    public ResponseEntity deleteGroup(@PathVariable final Long groupId) {
        groupService.deleteGroup(groupId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * Diese Methode wird dann aufgerufen, wenn ein Benutzer eine bestehende Gruppenmitgliedschaftsanfrage bestätigt und
     * somit zu einem vollwertigen Mitglied der Gruppe wird.
     * <p>
     * Bei einem Aufruf, müssen zwei Methoden des GroupDaos aufgerufen werden: Zunächst muss die Mitgliedschaftsanfrage,
     * die soeben beantwortet wurde, gelöscht werden, danach muss der Benutzer als Mitglied in die Gruppe eingefügt
     * werden.
     * <p>
     * Der Aufruf dieser Methode entspricht einem HTTP PUT-Request an den Server an die URL
     * {Base_URL}/groups/members/{groupId}/{userId}.
     *
     * @param groupId Die Id der Gruppe, zu die der Benutzer hinzugefügt werden soll. Der Wert dieses Arguments ist Teil
     *                der URL der REST Ressource und wird entsprechend von Spring extrahiert und der Methode
     *                bereitgestellt. Die Id muss zu einem Long-Datentyp gecastet werden können.
     * @param userId  Die Id des Benutzers, der der Gruppe hinzugefügt werden soll. Der Wert dieses Arguments ist Teil
     *                der URL der REST Ressource und wird entsprechend von Spring extrahiert und der Methode
     *                bereitgestellt.
     */
    @RequestMapping(
            method = RequestMethod.PUT,
            value = "/members/{groupId}/{userId}")
    public ResponseEntity acceptRequest(@PathVariable final Long groupId, @PathVariable final String userId) {
        groupService.acceptRequest(groupId, userId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * Diese Methode kann von einem Client aufgerufen werden, wenn ein Gruppenmitglied aus einer Gruppe entfernt werden
     * soll. Dies kann der Fall sein, wenn ein Benutzer freiwillig aus einer Gruppe austritt oder wenn er von einem
     * Administrator aus der Gruppe entfernt wird.
     * <p>
     * Bei einem Aufruf leitet die Methode die Anfrage an die entsprechende Methode des groupDAOs weiter. Dieses
     * entfernt den Benutzer aus der Gruppe. Durch Foreign Key Constraints in der Datenbank wird der Benutzer auch aus
     * allen GOs der Gruppe entfernt. Darum muss sich diese Methode demnach nicht darum kümmern.
     * <p>
     * Der Aufruf dieser Methode entspricht einem HTTP POST-Request an den Server an die URL
     * {Base_URL}/groups/members/{groupId}/{userId}.
     *
     * @param userId  Die Id des Benutzers, der aus der Gruppe entfernt werden soll. Der Wert dieses Arguments ist Teil
     *                der URL der REST Ressource und wird entsprechend von Spring extrahiert und der Methode
     *                bereitgestellt.
     * @param groupId Die Id der Gruppe, aus der der Benutzer entfernt werden soll. Der Wert dieses Arguments ist Teil
     *                der URL der REST Ressource und wird entsprechend von Spring extrahiert und der Methode
     *                bereitgestellt. Die Id muss zu einem Long-Datentyp gecastet werden können.
     */
    @RequestMapping(
            method = RequestMethod.DELETE,
            value = "/members/{groupId}/{userId}")
    public ResponseEntity removeMember(@PathVariable final String userId, @PathVariable("groupId") final Long groupId) {
        groupService.removeGroupMember(userId, groupId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * Diese Methode wird von einem Client aufgerufen, der einen Benutzer zu einer Gruppe einladen will. Bei Aufruf
     * dieser Methode wird mittels des groupDAOs die Information über den Group Request in der Datenbank gespeichert.
     * <p>
     * Es ist garantiert, dass der Client, der diese Methode aufruft ein Administrator ist und der eingeladene Benutzer
     * nicht bereits Mitglied der Gruppe ist. Diese Vorbedingungen müssen in der Methode nicht überprüft werden.
     * <p>
     * Der Aufruf dieser Methode entspricht einem HTTP POST-Request an den Server an die URL
     * {Base_URL}/groups/requests/{groupId}/{userId}.
     *
     * @param groupId Die Id der Gruppe, zu die der Benutzer eingladen werden soll. Der Wert dieses Arguments ist Teil
     *                der URL der REST Ressource und wird entsprechend von Spring extrahiert und der Methode
     *                bereitgestellt. Die Id muss zu einem Long-Datentyp gecastet werden können.
     * @param userId  Die Id des Benutzers, der zu der Gruppe eingeladen werden soll. Der Wert dieses Arguments ist Teil
     *                der URL der REST Ressource und wird entsprechend von Spring extrahiert und der Methode
     *                bereitgestellt.
     */
    @RequestMapping(
            method = RequestMethod.POST,
            value = "/requests/{groupId}/{userId}")
    public ResponseEntity inviteMember(@PathVariable final Long groupId, @PathVariable final String userId) {
        groupService.addGroupRequest(userId, groupId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * Diese Methode wird aufgerufen, wenn ein Benutzer eine Gruppenmitgliedschaftsanfrage ablehnt. Beim Aufruf wird das
     * groupDAO dazu veranlasst, die Anfrage aus der Datenbank zu löschen.
     * <p>
     * Der Aufruf dieser Methode entspricht einem HTTP POST-Request an den Server an die URL
     * {Base_URL}/groups/requests/{groupId}/{userId}.
     *
     * @param groupId Die Id der Gruppe, zu die der Benutzer eingeladen war. Der Wert dieses Arguments ist Teil der URL
     *                der REST Ressource und wird entsprechend von Spring extrahiert und der Methode bereitgestellt. Die
     *                ID muss zu einem Long-Datentyp gecastet werden können.
     * @param userId  Die Id des Benutzers, der die Anfrage abgelehnt hat. Der Wert dieses Arguments ist Teil der URL der
     *                REST Ressource und wird entsprechend von Spring extrahiert und der Methode bereitgestellt.
     */
    @RequestMapping(
            method = RequestMethod.DELETE,
            value = "/requests/{groupId}/{userId}"
    )
    public ResponseEntity denyRequest(@PathVariable("groupId") final Long groupId, @PathVariable("userId") final String userId) {
        groupService.removeGroupRequest(userId, groupId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    /**
     * Diese Methode wird aufgerufen, wenn ein Administrator einer Gruppe ein anderes Gruppenmitglied zum Administrator
     * ernennen will. In der Methode wird eine Methode des groupDaos aufgerufen, die einen Datenbankzugriff ausführt und
     * den entsprechenden Benutzer zu den Administratoren der Gruppe hinzufügt.
     * <p>
     * Es ist garantiert, dass der aufrufende Client ein Administrator der Gruppe ist und der neue Administrator bereits
     * ein Gruppenmitglied ist.
     * <p>
     * Der Aufruf dieser Methode entspricht einem HTTP POST-Request an den Server an die URL
     * {Base_URL}/groups/admins/{groupId}/{userId}.
     *
     * @param groupId Die Id der Gruppe, in die der neue Administrator hinzugefügt werden soll. Der Wert dieses
     *                Arguments ist Teil der URL der REST Ressource und wird entsprechend von Spring extrahiert und der
     *                Methode bereitgestellt. Die ID muss zu einem Long-Datentyp gecastet werden können.
     * @param userId  Die Id des Benutzer, der zum Adminsitrator ernannt wird. Der Wert dieses Arguments ist Teil der
     *                URL der REST Ressource und wird entsprechend von Spring extrahiert und der Methode bereitgestellt.
     */
    @RequestMapping(
            method = RequestMethod.POST,
            value = "/admins/{groupId}/{userId}"
    )
    public void addAdmin(@PathVariable("groupId") final Long groupId, @PathVariable("userId") final String userId) {
        groupService.addAdmin(userId, groupId);
    }


}
