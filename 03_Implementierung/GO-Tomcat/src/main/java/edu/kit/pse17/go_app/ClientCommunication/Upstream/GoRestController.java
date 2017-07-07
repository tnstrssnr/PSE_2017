package edu.kit.pse17.go_app.ClientCommunication.Upstream;

import edu.kit.pse17.go_app.PersistenceLayer.Status;
import edu.kit.pse17.go_app.PersistenceLayer.daos.GoDao;
import edu.kit.pse17.go_app.ServiceLayer.Cluster;
import edu.kit.pse17.go_app.ServiceLayer.LocationService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * Die Klasse GORestController gehört zum Upstream ClientCommunication Modul und bildet einen Teil der REST API, die der
 * Tomcat Server den Clients zur Kommunikation anbietet. Die Aufgabe dieser Klasse ist die Abwicklung von REST-Requests, die User-spezifische
 * Anfragen beinhalten. Dazu gehört:
 * - das Empfangen und Senden von HTTP-Requests
 * - das Parsen der empfangenen / zu sendenden Daten von bzw. nach JSON
 * - das Weiterleiten der Anfragen zur Bearbeitung an die richtige Stelle im Programm (das UserDAO)
 *
 * Das REST API wird umgesetzt von dem Java Framework Spring, anhand der Annotationen der Methoden in dieser Klasse. Die Klasse
 * selbst ist annotiert mit "@RestController", um zu signalisieren, dass es sich um eine Klasse handelt, deren Methoden Rest Resourcen beschreiben.
 * Die Methoden dieser Klasse sind auf die URL {Base_URL}/gos gemappt.
 *
 * Die Methoden der Klasse werden aufgerufen, von den Methoden des Interfaces "TomcatRestApi", das von den Clients des Systems verwendet wird.
 *
 * Bei einem Methodenaufruf in dieser Klasse, wird die Anfrage an die DAOs der MySQL Datenbank der Anwendung weitergeleitet. Von dort werden die richtigen
 * Daten geholt (falls der Client bestimmte Daten in der Antwort erwartet). Dnach werden die Daten von dieser Klasse in JSON-Objekte umgewandelt
 * (mithilfe der Gson Library) und dem Client in der Antwort zugesendet.
 *
 * Nähere Erläuterungen zum JSON-Schema und der Konvertierung finden sich in Abschnitt ????
 */

@RestController
@RequestMapping("/gos")
public class GoRestController {

    /**
     * Ein Objekt einer Klasse, die das Interface GoDao implementiert. Dieses Objekt besitzt Methoden, um auf die Datenbank
     * des Systems zuzugreifen und Daten zu manipulieren. Es wird benötigt, um die Anfragen, die durch die REST Calls an den Server gestellt werden, umzusetzen.
     */
    private GoDao goDao;

    /**
     * Da die Standorte bei er Standortverfolgung eines GOs immer nur für kurze Zeit persistiert werden müssen, werden diese nicht in der Datenbank gespeichert,
     * sondern temporär in einem Location-Objekt. Die Klasse locationService kümmert sich um die Verwaltung dieser Location-Objekte.
     *
     * Das Objekt kann in dieser Klasse für einen Methodenaufruf verwendet werden, der die aktuelle Location eines Benutzers in das Location-Objekt einfügt und
     * die geclustereten Locations der anderen Benutzer zurückgibt.
     */
    private LocationService locationService;


    /**
     * Diese Methode wird von einem Client aufgerufen, wenn eine neue Gruppe erstellt werden soll. Die Methode liest die Argumente aus dem Request Body
     * der HTTP Anfrage aus und übergibt diese an das goDao zur Erzeugung des GOs in der Datenbank.
     * Zusätzlich zur Erzeugung des GOs wird der Ersteller als Verantwortlicher des GOs gespeichert und für jedes Gruppenmitglied der Teilnahmestatus 'Abgelehnt'
     * gespeichert.
     *
     * es ist garantiert, dass dder Client, der die Methode aufruft eine Mitglied in der Gruppe ist, in der das GO erstellt werden soll.
     *
     * Der Aufruf dieser Methode entspricht einem HTTP POST-Request an den Server an die URL {Base_URL}/gos.
     *
     * @param name Der Name des GOs. Es handelt sich um einen String, der bis zu 50 Zeichen enthält.
     * @param description Eine Beschreibung für das GO. Diese Argument dar den wert null annehmen. Ist der Wert nicht null, darf der String
     *                    bis zu 140 Zeichen enthalten.
     * @param start Ein Datum mit Uhrzeit an dem das GO beginnt. Dieses Datum darf nicht in der Vergangenheit liegen.
     * @param end Ein Datum mit Uhrzeit an dem das GO zu Ende ist. Dieses Datum darf nicht vor dem Startdatum liegen.
     * @param lat Der geographische Breitengrad des Zielorts des GOs. Der Wert muss als Breitengrad interpretierbar sein,
     *            muss also zwischen +90 und -90 liegen. Der Wert darf außerdem "null" sein, falls kein Zielort für das GO ausgewählt wurde.
     * @param lon Der geographische Längengrad des Zielorts des GOs. Der Wert muss als Breitengrad interpretierbar sein,
     *            muss also zwischen +180 und -180 liegen. Der Wert darf außerdem "null" sein, falls kein Zielort für das GO ausgewählt wurde.
     * @param threshold Ein Schwellwert für die Genauigkeit, mit der der Clustering-Algorithmus ausgeführt wird. Der Wert liegt zwischen 1 (sehr ungenau) und 10 (sehr
     *                  genau). Wird der Wert in dem Request Body nicht spezifiziert wird default-mäßig ein Wert von 5 gespeichert.
     * @param groupId Die ID der Gruppe, in der das GO angelegt werden soll. Der Wert muss eine gültige Group-ID sein und sich zu einem Long casten lassen.
     * @param userId Die ID des Benutzers, der das GO erstellt. Der Wert muss eine gültige UserID sein. Dieser Benutzer wird als GO-Verantwortlicher gespeichert.
     * @return Die Methode gibt in der Antwort die im System eindeutige ID des Gos zurück. Diese wird im Header der HTTP-Response im Location-Feld
     * an den Client zurückgesendet, also : {Base_URL}/gos/{goId} und kann dort vom Client ausgelesen werden. Der Wert ist eine positive ganze Zahl,
     * die im Wertebereich des primitiven Datentyps long liegt.
     */
    @RequestMapping(
            method = RequestMethod.POST,
            value = "/"
    )
    public long createGo(String name, String description, Date start, Date end, double lat, double lon, int threshold, long groupId, String userId) {
        return -1;
    }

    /**
     * Diese Methode wird von einem Client aufgerufen, um seinen Teilnahmerstatus in einem Go zu ändern. IN der Methode werden die Anfrage-
     * daten aus dem Request Body ausgewertet und an das goDao weitergegeben, um die entsprechenden Änderungen in der Datenbank vorzunehmen.
     *
     * Es ist garantiert, dass der Benutzer ein Mitglied des GOs ist und das er die geforderte Statusänderung vornehmen darf.
     *
     * Der Aufruf dieser Methode entspricht einem HTTP PUT-Request an den Server an die URL {Base_URL}/gos/status.
     *
     * @param goId Die ID des GOs, in dem der Benutzer seinen Teilnahmestatus ändern will. es muss sich dabei um eine gültige Go-ID sein, ansonsten schlägt
     *             die Anfrage fehl. Die ID muss sich zu einem long casten lassen.
     * @param userId Die ID des Users, der seinen Teilnahmestatus ändern will. Diese ID muss eine gültige, auf dem System registrierte User ID sein.
     * @param status Der neue Status des Clients. Dieser hat entweder den Wert "Abgelehnt", "Bestätigt" oder "Losgegangen"
     */
    @RequestMapping(
            method = RequestMethod.PUT,
            value = "/status"
    )
    public void changeStatus(long goId, String userId, Status status) {

    }

    /**
     * Die Methode gibt eine Liste mit Cluster-Objketen zurück, die die aktuellen Positionen der Go-Mitglieder beschreiben. Diese Methode,
     * wird von Clients periodisch aufgerufen, um während eines GOs die Standorte der anderen mitglieder zu erfahren.
     * Um den eigenen Standort mit den anderen Mitgliedern zu teilen, wird nicht diese Methode verwendet, sondern die Methode setLocation().
     *
     * Im Gegensatz zu den meisten anderen Methoden der restController-Klassen, wird diese Anfrage nicht an eine DAO Objekt weitergeleitet.
     * Da die Standort-Daten nicht langfristig gespeichert werden müssen, wird die Anfrage an ein locationService Objekt gegeben und dort behandelt.
     *
     * Es ist garantiert, dass der Benutzer, der diese Methode aufruft, dazu berechtigt ist, die Standorte der anderen Mitglieder zu erfahren.
     *
     * Der Aufruf dieser Methode entspricht einem HTTP GET-Request an den Server an die URL {Base_URL}/gos/location/{goId}.
     *
     * @param goId Die ID des GOs, dessen Location-Daten angefragt werden. Dabei muss es sich um eine gültige GO ID handeln, die sich zu einem Long
     *             casten lässt. Der Wert dieses Arguments ist Teil der URL der REST Resource und wird entsprechend von Spring extrahiert und
     *             der Methode bereitgestellt.
     * @return Eine Liste mit Clustern. Ein Cluster besteht dabei aus drei Feldern: Der Längen- und Breitengrad der Position des Clusters und der größe, also der Anzahl
     * an Personen, die sich in diesem Cluster befinden. Der rückgabewert dieser Methode kann auch null sein, z.B. dann wenn für das Clustering zu wenig Personen
     * an dem GO teilnehmen. Maximal besteht die Liste aus 50 Clustern, da die Anzahl der Gruppenmitglieder auf 50 beschränkt ist.
     */
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/location/{goId}"
    )
    public List<Cluster> getLocation(@PathVariable("goId") String goId) {
        return null;
    }


    /**
     * Diese Methode wird von einem Client aufgerufen, um seinen Standort für das Clustering dem Server mitzuteilen. Der übermittelte
     * Standort wird aus dem RequestBody ausgelesen und zur Weiterverarbeitung an den locationService weitergeleitet.
     *
     * Es wird garantiert, dass der Client, der diese Methode aufruft, ein aktiver Teilnehmer des entsprechenden GOs ist.
     *
     * Der Aufruf dieser Methode entspricht einem HTTP PUT-Request an den Server an die URL {Base_URL}/gos/location/{goId}.
     *
     * @param userId Die ID des Benutzers, der seinen Standort teilen will. Dabei muss es sich um eine gültige, im System registrierte
     *               BenutzerID handeln.
     * @param lat Der geographische Breitengrad des Standorts des Benutzers. Der Wert muss als Breitengrad interpretierbar sein,
     *            muss also zwischen +90 und -90 liegen.
     * @param lon Der geographische Längengrad des Standorts des Benutzers. Der Wert muss als Längengrad interpretierbar sein,
     *            muss also zwischen +180 und -180 liegen.
     * @param goId Die ID des GOs, zu dessen Location-Daten der Standort des Benutzers gehört. Dabei muss es sich um eine gültige GO ID handeln, die sich zu einem Long
     *             casten lässt. Der Wert dieses Arguments ist Teil der URL der REST Resource und wird entsprechend von Spring extrahiert und
     *             der Methode bereitgestellt.
     */
    @RequestMapping(
            method = RequestMethod.PUT,
            value = "/location/{goId}"
    )
    public void setLocation(String userId, long lat, long lon, @PathVariable("goId") String goId) {

    }

    /**
     *
     * Diese Methode wird von einem Client aufgerufen, wenn er ein GO löschen möchte. Durch einen Methodenaufruf bei dem goDao wird
     * das GO entsprechend aus der Datenbank entfernt.
     *
     * Es ist garantiert, dass der Client, der die Gruppe aufruft dazu berechtigt ist, d.h. er der GO-Verantwortliche des GOs ist.
     *
     * Der Aufruf dieser Methode entspricht einem HTTP DELETE-Request an den Server an die URL {Base_URL}/gos/{goId}.
     *
     * @param goId Die ID des GOs, das gelöscht werden soll. Der Wert dieses Arguments ist Teil der URL der REST Resource
     *             und wird entsprechend von Spring extrahiert und der Methode bereitgestellt. Die ID muss gülti sein und zu einem Long-Datentyp gecastet
     *             werden können.
     */
    @RequestMapping(
            method = RequestMethod.DELETE,
            value = "/{goId}"
    )
    public void deleteGo(@PathVariable("goId") String goId) {

    }

    /**
     * Diese Methode wird von einem Benutzer aufgerufen, wenn er die Daten eines GOs ändern will. Zu den Daten, die mit dieser Methode geändert werden können,
     * gehören:
     *
     * - der GO-Name
     * - die GO-Beschreibung
     * - Der Anfangs- und Endzeitpunkt
     * - Der Zielort
     * - Der Clustering-Schwellwert
     *
     * Es ist garantiert, dass dieser Aufruf nur von einem Go-Verantwortlichen des zu ändernden GOs kommt.
     *
     * Der Aufruf dieser Methode entspricht einem HTTP PUT-Request an den Server an die URL {Base_URL}/gos/{goId}.
     *
     * Abgesehen von der Go ID, können sämtliche Argumente dieser Methode den Wert null annehmen. Dies signalisiert der Methode, das der Wert nicht geändert wurde
     * und die bisherigen Daten beibehalten werden sollen.
     *
     * @param goId Die ID des GOs, das gelöscht werden soll. Der Wert dieses Arguments ist Teil der URL der REST Resource
     *             und wird entsprechend von Spring extrahiert und der Methode bereitgestellt. Die ID muss gülti sein und zu einem Long-Datentyp gecastet
     *             werden können.
     * @param name Der Name des GOs. Es handelt sich um einen String, der bis zu 50 Zeichen enthält.
     * @param description Eine Beschreibung für das GO. Diese Argument dar den wert null annehmen. Ist der Wert nicht null, darf der String
     *                    bis zu 140 Zeichen enthalten.
     * @param start Ein Datum mit Uhrzeit an dem das GO beginnt. Dieses Datum darf nicht in der Vergangenheit liegen.
     * @param end Ein Datum mit Uhrzeit an dem das GO zu Ende ist. Dieses Datum darf nicht vor dem Startdatum liegen.
     * @param lat Der geographische Breitengrad des Zielorts des GOs. Der Wert muss als Breitengrad interpretierbar sein,
     *            muss also zwischen +90 und -90 liegen. Der Wert darf außerdem "null" sein, falls kein Zielort für das GO ausgewählt wurde.
     * @param lon Der geographische Längengrad des Zielorts des GOs. Der Wert muss als Breitengrad interpretierbar sein,
     *            muss also zwischen +180 und -180 liegen. Der Wert darf außerdem "null" sein, falls kein Zielort für das GO ausgewählt wurde.
     * @param threshold Ein Schwellwert für die Genauigkeit, mit der der Clustering-Algorithmus ausgeführt wird. Der Wert liegt zwischen 1 (sehr ungenau) und 10 (sehr
     *                  genau). Wird der Wert in dem Request Body nicht spezifiziert wird default-mäßig ein Wert von 5 gespeichert.
     */
    @RequestMapping(
            method = RequestMethod.PUT,
            value = "/{goId}"
    )
    public void editGo(@PathVariable("goId") String goId, String name, String description, Date start, Date end, long lat, long lon, int threshold) {

    }

}
