package edu.kit.pse17.go_app.ClientCommunication.Upstream;

import edu.kit.pse17.go_app.PersistenceLayer.GoEntity;
import edu.kit.pse17.go_app.PersistenceLayer.Status;
import edu.kit.pse17.go_app.PersistenceLayer.daos.GoDao;
import edu.kit.pse17.go_app.ServiceLayer.Cluster;
import edu.kit.pse17.go_app.ServiceLayer.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Die Klasse GoRestController gehört zum Upstream ClientCommunication Modul und bildet einen Teil der REST API, die der
 * Tomcat Server den Clients zur Kommunikation anbietet. Die Aufgabe dieser Klasse ist die Abwicklung von REST-Requests,
 * die User-spezifische Anfragen beinhalten. Dazu gehört: - das Empfangen und Senden von HTTP-Requests - das Parsen der
 * empfangenen / zu sendenden Daten von bzw. nach JSON - das Weiterleiten der Anfragen zur Bearbeitung an die richtige
 * Stelle im Programm (das UserDAO)
 * <p>
 * Das REST API wird umgesetzt von dem Java Framework Spring, anhand der Annotationen der Methoden in dieser Klasse. Die
 * Klasse selbst ist annotiert mit "@RestController", um zu signalisieren, dass es sich um eine Klasse handelt, deren
 * Methoden Rest Resourcen beschreiben. Die Methoden dieser Klasse sind auf die URL {Base_URL}/gos gemappt.
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
@RequestMapping("/gos")
public class GoRestController {

    /**
     * Ein Objekt einer Klasse, die das Interface GoDao implementiert. Dieses Objekt besitzt Methoden, um auf die
     * Datenbank des Systems zuzugreifen und Daten zu manipulieren. Es wird benötigt, um die Anfragen, die durch die
     * REST Calls an den Server gestellt werden, umzusetzen.
     */
    @Autowired
    private GoDao goDao;

    /**
     * Da die Standorte bei der Standortverfolgung eines GOs immer nur für kurze Zeit persistiert werden müssen, werden
     * diese nicht in der Datenbank gespeichert, sondern temporär in einem Location-Objekt. Die Klasse locationService
     * kümmert sich um die Verwaltung dieser Location-Objekte.
     * <p>
     * Das Objekt kann in dieser Klasse für einen Methodenaufruf verwendet werden, der die aktuelle Location eines
     * Benutzers in das Location-Objekt einfügt und die geclustereten Locations der anderen Benutzer zurückgibt.
     */
    private LocationService locationService;

    /**
     * Diese Methode wird von einem Client aufgerufen, wenn eine neue Gruppe erstellt werden soll. Die Methode liest die
     * Argumente aus dem Request Body der HTTP Anfrage aus und übergibt diese an das goDao zur Erzeugung des GOs in der
     * Datenbank. Zusätzlich zur Erzeugung des GOs wird der Ersteller als Verantwortlicher des GOs gespeichert und für
     * jedes Gruppenmitglied der Teilnahmestatus 'Abgelehnt' gespeichert.
     * <p>
     * es ist garantiert, dass dder Client, der die Methode aufruft eine Mitglied in der Gruppe ist, in der das GO
     * erstellt werden soll.
     * <p>
     * Der Aufruf dieser Methode entspricht einem HTTP POST-Request an den Server an die URL {Base_URL}/gos.
     *
     * @return Die Methode gibt in der Antwort die im System eindeutige ID des Gos zurück. Diese wird im Header der
     * HTTP-Response im Location-Feld an den Client zurückgesendet, also : {Base_URL}/gos/{goId} und kann dort vom
     * Client ausgelesen werden. Der Wert ist eine positive ganze Zahl, die im Wertebereich des primitiven Datentyps
     * long liegt.
     */
    @RequestMapping(
            method = RequestMethod.POST,
            value = "/"
    )
    public long createGo(@RequestBody GoEntity go) {
        return goDao.persist(go);
    }

    /**
     * Diese Methode wird von einem Client aufgerufen, um seinen Teilnahmerstatus in einem Go zu ändern. IN der Methode
     * werden die Anfrage- daten aus dem Request Body ausgewertet und an das goDao weitergegeben, um die entsprechenden
     * Änderungen in der Datenbank vorzunehmen.
     * <p>
     * Es ist garantiert, dass der Benutzer ein Mitglied des GOs ist und das er die geforderte Statusänderung vornehmen
     * darf.
     * <p>
     * Der Aufruf dieser Methode entspricht einem HTTP PUT-Request an den Server an die URL {Base_URL}/gos/status.
     */
    @RequestMapping(
            method = RequestMethod.PUT,
            value = "/{goId}/status",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<String> changeStatus(@RequestBody String statusChangeContext, @PathVariable("goId") Long goId) {
        String[] statusChangeArr = statusChangeContext.substring(1, statusChangeContext.length() - 1).split(" ");
        String userId = statusChangeArr[0];
        Status status;
        System.out.println(statusChangeContext);
        System.out.println(statusChangeArr[0]);
        System.out.println(statusChangeArr[1]);

        switch (statusChangeArr[1]) {
            case "ABGELEHNT":
                status = Status.ABGELEHNT;
                break;
            case "BESTÄTIGT":
                status = Status.BESTÄTIGT;
                break;
            case "UNTERWEGS":
                status = Status.UNTERWEGS;
                break;
            default:
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        goDao.changeStatus(userId, goId, status);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * Die Methode gibt eine Liste mit Cluster-Objketen zurück, die die aktuellen Positionen der Go-Mitglieder
     * beschreiben. Diese Methode, wird von Clients periodisch aufgerufen, um während eines GOs die Standorte der
     * anderen mitglieder zu erfahren. Um den eigenen Standort mit den anderen Mitgliedern zu teilen, wird nicht diese
     * Methode verwendet, sondern die Methode setLocation().
     * <p>
     * Im Gegensatz zu den meisten anderen Methoden der restController-Klassen, wird diese Anfrage nicht an eine DAO
     * Objekt weitergeleitet. Da die Standort-Daten nicht langfristig gespeichert werden müssen, wird die Anfrage an ein
     * locationService Objekt gegeben und dort behandelt.
     * <p>
     * Es ist garantiert, dass der Benutzer, der diese Methode aufruft, dazu berechtigt ist, die Standorte der anderen
     * Mitglieder zu erfahren.
     * <p>
     * Der Aufruf dieser Methode entspricht einem HTTP GET-Request an den Server an die URL
     * {Base_URL}/gos/location/{goId}.
     *
     * @param goId Die ID des GOs, dessen Location-Daten angefragt werden. Dabei muss es sich um eine gültige GO ID
     *             handeln, die sich zu einem Long casten lässt. Der Wert dieses Arguments ist Teil der URL der REST
     *             Resource und wird entsprechend von Spring extrahiert und der Methode bereitgestellt.
     * @return Eine Liste mit Clustern. Ein Cluster besteht dabei aus drei Feldern: Der Längen- und Breitengrad der
     * Position des Clusters und der größe, also der Anzahl an Personen, die sich in diesem Cluster befinden. Der
     * rückgabewert dieser Methode kann auch null sein, z.B. dann wenn für das Clustering zu wenig Personen an dem GO
     * teilnehmen. Maximal besteht die Liste aus 50 Clustern, da die Anzahl der Gruppenmitglieder auf 50 beschränkt
     * ist.
     */
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/location/{goId}"
    )
    public List<Cluster> getLocation(@PathVariable("goId") Long goId) {
        return LocationService.getGroupLocation(goId);
    }


    /**
     * Diese Methode wird von einem Client aufgerufen, um seinen Standort für das Clustering dem Server mitzuteilen. Der
     * übermittelte Standort wird aus dem RequestBody ausgelesen und zur Weiterverarbeitung an den locationService
     * weitergeleitet.
     * <p>
     * Es wird garantiert, dass der Client, der diese Methode aufruft, ein aktiver Teilnehmer des entsprechenden GOs
     * ist.
     * <p>
     * Der Aufruf dieser Methode entspricht einem HTTP PUT-Request an den Server an die URL
     * {Base_URL}/gos/location/{goId}.
     *
     * @param userId Die ID des Benutzers, der seinen Standort teilen will. Dabei muss es sich um eine gültige, im
     *               System registrierte BenutzerID handeln.
     * @param lat    Der geographische Breitengrad des Standorts des Benutzers. Der Wert muss als Breitengrad
     *               interpretierbar sein, muss also zwischen +90 und -90 liegen.
     * @param lon    Der geographische Längengrad des Standorts des Benutzers. Der Wert muss als Längengrad
     *               interpretierbar sein, muss also zwischen +180 und -180 liegen.
     * @param goId   Die ID des GOs, zu dessen Location-Daten der Standort des Benutzers gehört. Dabei muss es sich um
     *               eine gültige GO ID handeln, die sich zu einem Long casten lässt. Der Wert dieses Arguments ist Teil
     *               der URL der REST Resource und wird entsprechend von Spring extrahiert und der Methode
     *               bereitgestellt.
     */
    @RequestMapping(
            method = RequestMethod.PUT,
            value = "/location/{goId}"
    )
    public void setLocation(String userId, double lat, double lon, @PathVariable("goId") Long goId) {
        LocationService.setUserLocation(goId, userId, lat, lon);
    }

    /**
     * Diese Methode wird von einem Client aufgerufen, wenn er ein GO löschen möchte. Durch einen Methodenaufruf bei dem
     * goDao wird das GO entsprechend aus der Datenbank entfernt.
     * <p>
     * Es ist garantiert, dass der Client, der die Gruppe aufruft dazu berechtigt ist, d.h. er der GO-Verantwortliche
     * des GOs ist.
     * <p>
     * Der Aufruf dieser Methode entspricht einem HTTP DELETE-Request an den Server an die URL {Base_URL}/gos/{goId}.
     *
     * @param goId Die ID des GOs, das gelöscht werden soll. Der Wert dieses Arguments ist Teil der URL der REST
     *             Resource und wird entsprechend von Spring extrahiert und der Methode bereitgestellt. Die ID muss
     *             gülti sein und zu einem Long-Datentyp gecastet werden können.
     */
    @RequestMapping(
            method = RequestMethod.DELETE,
            value = "/{goId}"
    )
    public void deleteGo(@PathVariable("goId") long goId) {
        goDao.delete(goId);
    }

    /**
     * Diese Methode wird von einem Benutzer aufgerufen, wenn er die Daten eines GOs ändern will. Zu den Daten, die mit
     * dieser Methode geändert werden können, gehören:
     * <p>
     * - der GO-Name - die GO-Beschreibung - Der Anfangs- und Endzeitpunkt - Der Zielort - Der Clustering-Schwellwert
     * <p>
     * Es ist garantiert, dass dieser Aufruf nur von einem Go-Verantwortlichen des zu ändernden GOs kommt.
     * <p>
     * Der Aufruf dieser Methode entspricht einem HTTP PUT-Request an den Server an die URL {Base_URL}/gos/{goId}.
     * <p>
     * Abgesehen von der Go ID, können sämtliche Argumente dieser Methode den Wert null annehmen. Dies signalisiert der
     * Methode, das der Wert nicht geändert wurde und die bisherigen Daten beibehalten werden sollen.
     *
     * @param goId Die ID des GOs, das gelöscht werden soll. Der Wert dieses Arguments ist Teil der URL der REST
     *             Resource und wird entsprechend von Spring extrahiert und der Methode bereitgestellt. Die ID muss
     *             gülti sein und zu einem Long-Datentyp gecastet werden können.
     */
    @RequestMapping(
            method = RequestMethod.PUT,
            value = "/{goId}"
    )
    public ResponseEntity<String> editGo(@PathVariable("goId") long goId, @RequestBody GoEntity goEntity) {
        goDao.update(goEntity);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
