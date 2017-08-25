package edu.kit.pse17.go_app.ClientCommunication.Upstream;

import edu.kit.pse17.go_app.PersistenceLayer.clientEntities.Go;
import edu.kit.pse17.go_app.PersistenceLayer.clientEntities.Group;
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
 * RestController Klasse für Rest Ressourcen in Bezug zu Go Funktionalitäten. Alle Rückgabewerte in dieser Klasse sind
 * in einem responseEntity-Objekt umfasst, HTTP-StatusCode und die Rückgabedaten beinhaltend in seinem Aufbau,
 * formattiert als Json-String, Gson benutzend.
 */

@RestController
@RequestMapping("/gos")
public class GoRestController {


    //Felder, die mit @Autowired annotiert sind, werden bei Erzeugung des Objekts automatisch instanziiert
    @Autowired
    private GoService goService;

    private LocationService masterLocationService;

    //constructor used in test classes to inject mocked goService
    public GoRestController(GoService goService) {
        this.goService = goService;
    }

    public GoRestController() {
    }

    /**
     * Ein Aufruf dieser Methode lässt den Server das gegebene Go-Objekt persistieren.
     *
     * @param go Das Go, das persistiert werden soll. Es sollte im Request-body des POST-Request, als Json-String,
     *           transferiert werden. Das Programm benutzt Gson um Json zu einem GoEntity-Objekt zu parsen. Der Id-Wert
     *           des transferierten Gos ist ignoriert; Hibernate gibt ihm eine neue ID abhängig von seinem momentanen
     *           Status in der Datenbank.
     * @return Die Id des Gos, unter welcher es in der Datenbank gespeichert ist.
     */
    @RequestMapping(
            method = RequestMethod.POST,
            value = "/{groupId}/{userId}"
    )
    public ResponseEntity<Long> createGo(@PathVariable("groupId") long groupId, @PathVariable("userId") String userId, @RequestBody Go go) {
        Group group = new Group();
        group.setId(groupId);
        go.setGroup(group);
        go.setOwnerId(userId);
        return new ResponseEntity<>(goService.createGo(go), HttpStatus.OK);
    }

    /**
     * Ein Aufruf dieser Methode ändert den Status des Users im angegebenen Go.
     *
     * @param goId                Die Id des betroffenen Gos. Dieser Parameter is spezifiziert in der URI der Rest
     *                            Ressource.
     * @param statusChangeContext Ein String, das den Kontext des  Statuswechsels spezifiziert, formattiert wie folgt:
     *                            "{userId}{newStatus}", wobei userId die Id des Users ist, welcher seinen Status
     *                            ändert, und newStatus der neue Status des Users ist (entweder "GOING", "NOT_GOING"
     *                            oder "GONE").
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
     * @param goId Die Id des Gos, wessen Location-Cluster wiedergegeben werden sollen. Dieser Parameter ist in der URI
     *             der Rest-Ressource spezifiziert.
     * @return Die Location-Cluster des Gos, berechnet anhand der momentanen Clustering Strategy.
     */
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/location/{goId}"
    )
    public ResponseEntity<List<Cluster>> getLocation(@PathVariable("goId") Long goId) {
        return new ResponseEntity<>(masterLocationService.getGroupLocation(goId), HttpStatus.OK);
    }


    /**
     * Ein Aufruf  dieser Methode speichert die gegebene Location des Users zum UserLocation-Objekt der Gruppe.
     *
     * @param goId         Die Id des betroffenen Gos.
     * @param userLocation Ein UserLocation-Objekt, das den momentanen Aufenthaltorts des Users beinhaltet.
     */
    @RequestMapping(
            method = RequestMethod.PUT,
            value = "/location/{goId}"
    )
    public ResponseEntity setLocation(@RequestBody UserLocation userLocation, @PathVariable("goId") Long goId) throws IOException {
        String userId = userLocation.getUserId();
        double lat = userLocation.getLat();
        double lon = userLocation.getLon();
        masterLocationService.setUserLocation(goId, userId, lat, lon);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * Ein Aufruf dieser Methode führt zum löschen des Gos, welches der Id entspricht.
     *
     * @param goId Id of the Go to be deleted. This parameter is specified in the URI of this Rest resource
     */
    @RequestMapping(
            method = RequestMethod.DELETE,
            value = "/{goId}"
    )
    public ResponseEntity deleteGo(@PathVariable("goId") long goId) {
        goService.delete(goId);
        //masterLocationService.removeGo(goId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * A Call to this method allows the client to change the data of the Go. Fields that can not be edited via this
     * method are all informations pertaining to the group and the owner of the Go, as well as the statuses of thew Go
     * members.
     *
     * @param go The Go to e edited. This parameter is specified in the URI of this Rest resource
     */
    @RequestMapping(
            method = RequestMethod.PUT,
            value = "/{goId}"
    )
    public ResponseEntity editGo(@PathVariable("goId") Long goId, @RequestBody Go go) {
        go.setId(goId);
        goService.update(go);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
