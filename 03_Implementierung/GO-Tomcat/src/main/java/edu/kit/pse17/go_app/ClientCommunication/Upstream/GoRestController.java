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
 * RestController class for Rest resources pertaining to Go functionality. All return values in this class are wrapped
 * in a responseEntity-object, containing a Http-StatusCode and the return data in its body formatted as a Json-String,
 * using Gson.
 */

@RestController
@RequestMapping("/gos")
public class GoRestController {


    //autowired fields are injected by Spring Framework on start-up
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
     * @param goId         ID of the affected go. This parameter is specified in the URI of this Rest resource
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
     * A call to this method will delete the specified go
     *
     * @param goId Id of the Go to be deleted. This parameter is specified in the URI of this Rest resource
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
