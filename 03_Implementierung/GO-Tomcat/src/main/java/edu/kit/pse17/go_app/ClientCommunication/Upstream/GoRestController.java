package edu.kit.pse17.go_app.ClientCommunication.Upstream;

import edu.kit.pse17.go_app.PersistenceLayer.clientEntities.Go;
import edu.kit.pse17.go_app.ServiceLayer.Cluster;
import edu.kit.pse17.go_app.ServiceLayer.GoService;
import edu.kit.pse17.go_app.ServiceLayer.LocationService;
import edu.kit.pse17.go_app.ServiceLayer.UserLocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * RestController Klasse für Rest Ressourcen in Bezug zu Go Funktionalitäten. Alle Rückgabewerte in dieser Klasse sind in einem
 * responseEntity-Objekt umfasst, HTTP-StatusCode und die Rückgabedaten beinhaltend in seinem Aufbau, formattiert als Json-String, Gson benutzend.
 *
 */

@RestController
@RequestMapping("/gos")
public class GoRestController {

    @Autowired
    private GoService goService;

    //constructor used in test classes to inject mocked goService
    public GoRestController(GoService goService) {
        this.goService = goService;
    }

    //standard constructor to facilitate Spring DI
    public GoRestController() {
    }

    /**
     * Ein Aufruf dieser Methode lässt den Server das gegebene Go-Objekt persistieren.
     *
     * @param go Das Go, das persistiert werden soll. Es sollte im Request-body des POST-Request, als Json-String, transferiert werden.
     *           Das Programm benutzt Gson um Json zu einem GoEntity-Objekt zu parsen.
     *           Der Id-Wert des transferierten Gos ist ignoriert;
     *           Hibernate gibt ihm eine neue ID abhängig von seinem momentanen Status in der Datenbank.
     * @return Die Id des Gos, unter welcher es in der Datenbank gespeichert ist.
     */
    @RequestMapping(
            method = RequestMethod.POST,
            value = "/"
    )
    public ResponseEntity<Long> createGo(@RequestBody Go go) {
        return new ResponseEntity<>(goService.createGo(go), HttpStatus.OK);
    }

    /**
     * Ein Aufruf dieser Methode ändert den Status des Users im angegebenen Go.
     *
     * @param goId Die Id des betroffenen Gos. Dieser Parameter is spezifiziert in der URI der Rest Ressource.
     * @param statusChangeContext Ein String, das den Kontext des  Statuswechsels spezifiziert, formattiert wie folgt:
     *                            "{userId}{newStatus}", wobei userId die Id des Users ist, welcher seinen Status ändert,
     *                            und newStatus der neue Status des Users ist (entweder "GOING", "NOT_GOING" oder "GONE").
     *
     */
    @RequestMapping(
            method = RequestMethod.PUT,
            value = "/{goId}/status",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity changeStatus(@RequestBody Map<String, String> statusChangeContext, @PathVariable("goId") Long goId) {
        if (goService.changeStatus(statusChangeContext, goId)) {
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

    }

    /**
     * Ein Aufruf dieser Methode gibt die momentanen Location-Cluster des Gos wieder.
     *
     * @param goId Die Id des Gos, wessen Location-Cluster wiedergegeben werden sollen. Dieser Parameter ist in der URI der Rest-Ressource spezifiziert.
     * @return Die Location-Cluster des Gos, berechnet anhand der momentanen Clustering Strategy.
     *
     */
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/location/{goId}"
    )
    public ResponseEntity<List<Cluster>> getLocation(@PathVariable("goId") Long goId) {

        return new ResponseEntity<>(LocationService.getGroupLocation(goId), HttpStatus.OK);
    }


    /**
     * Ein Aufruf  dieser Methode speichert die gegebene Location des Users zum UserLocation-Objekt der Gruppe.
     *
     * @param goId Die Id des betroffenen Gos.
     * @param userLocation Ein UserLocation-Objekt, das den momentanen Aufenthaltorts des Users beinhaltet.
     *
     */
    @RequestMapping(
            method = RequestMethod.PUT,
            value = "/location/{goId}"
    )
    public ResponseEntity setLocation(@RequestBody UserLocation userLocation, @PathVariable("goId") Long goId) throws IOException {
        String userId = userLocation.getUserId();
        double lat = userLocation.getLat();
        double lon = userLocation.getLon();
        LocationService.setUserLocation(goId, userId, lat, lon);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * Ein Aufruf dieser Methode führt zum löschen des Gos, welches der Id entspricht.
     *
     * @param goId Die ID des GOs, das gelöscht werden soll. Der Wert dieses Arguments ist Teil der URL der REST
     *             Resource und wird entsprechend von Spring extrahiert und der Methode bereitgestellt. Die ID muss
     *             gülti sein und zu einem Long-Datentyp gecastet werden können.
     */
    @RequestMapping(
            method = RequestMethod.DELETE,
            value = "/{goId}"
    )
    public ResponseEntity deleteGo(@PathVariable("goId") long goId) {
        goService.delete(goId);
        LocationService.removeGo(goId);
        return ResponseEntity.status(HttpStatus.OK).build();
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
     */
    @RequestMapping(
            method = RequestMethod.PUT,
            value = "/"
    )
    public ResponseEntity<String> editGo(@RequestBody Go go) {
        goService.update(go);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
