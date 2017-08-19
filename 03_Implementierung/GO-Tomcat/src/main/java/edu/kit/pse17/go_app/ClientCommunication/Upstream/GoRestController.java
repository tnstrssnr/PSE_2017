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
 * RestController class for Rest resources pertaining to Go functionality. All return values in this class are wrapped
 * in a responseEntity-object, containing a Http-StatusCode and the return data in its body formatted as a Json-String,
 * using Gson.
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
     * A call to this method makes the server persist the supplied Go object
     *
     * @param go the go that should be persisted. It should be transferred in the request-body of the POST Request as a
     *           Json-String. The program uses Gson to parse the Json to a GoEntity object. The ID value of the
     *           transferred Go is ignored, Hibernate will assign an new ID, based on the current database state.
     * @return The ID of the GO under which it is stored in the database.
     */
    @RequestMapping(
            method = RequestMethod.POST,
            value = "/"
    )
    public ResponseEntity<Long> createGo(@RequestBody Go go) {
        return new ResponseEntity<>(goService.createGo(go), HttpStatus.OK);
    }

    /**
     * A call to this method changes the status of the user in the specified go.
     *
     * @param goId                The ID of the affected go. This parameter is specified in the URI of this Rest
     *                            resource
     * @param statusChangeContext a String that specifies the context of the status change, formatted in the following
     *                            way: "{userId} {newStatus}", where userId is the ID of the user changing their status
     *                            and newStatus is the new status of the user (either "GOING", "NOT_GOING" or "GONE")
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
     * A call to this method returns the current location-clusters of the go
     *
     * @param goId the ID of the go, whose location-clusters should be returned. This parameter is specified in the URI
     *             of this Rest resource
     * @return the location clusters of the Go, as calculated by the currently used ClusterStrategy
     */
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/location/{goId}"
    )
    public ResponseEntity<List<Cluster>> getLocation(@PathVariable("goId") Long goId) {

        return new ResponseEntity<>(LocationService.getGroupLocation(goId), HttpStatus.OK);
    }


    /**
     * A call to this method will save the supplied location of the user to the UserLocation object of the group
     *
     * @param goId         ID of the affected go
     * @param userLocation A UserLocation object that contains the current location of the user
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
     * A call to this method will delete the
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
