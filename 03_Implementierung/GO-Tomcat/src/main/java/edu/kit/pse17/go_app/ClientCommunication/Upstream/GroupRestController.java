package edu.kit.pse17.go_app.ClientCommunication.Upstream;

import edu.kit.pse17.go_app.PersistenceLayer.GroupEntity;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

/**
 * Created by tina on 29.06.17.
 */

@RestController
@RequestMapping("/groups")
public class GroupRestController {

    @RequestMapping(
            method = RequestMethod.POST,
            value = "/",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public void addGroup(@RequestBody GroupEntity groupEntity) {

    }

    @RequestMapping(
            method = RequestMethod.PUT,
            value = "/{groupId}")
    public void alterGroup(@PathVariable Long groupId) {

    }

    @RequestMapping(
            method = RequestMethod.GET,
            value="/{groupId}")
    public GroupEntity getgroupInfo(@PathVariable Long groupId) {
        return null;
    }

    @RequestMapping(
            method = RequestMethod.DELETE,
            value = "/{groupId}")
    public void deleteGroup(@PathVariable Long groupId) {

    }

    @RequestMapping(
            method = RequestMethod.PUT,
            value = "/{groupId}/members/{userId}")
    public void addMember(@PathVariable Long groupId, @PathVariable String userId) {

    }

    @RequestMapping(
            method = RequestMethod.DELETE,
            value = "/{groupId}/members/{userId}")
    public void removeMember(@PathVariable String userId, @PathVariable("groupId") String groupId) {
    }

    @RequestMapping(
            method = RequestMethod.POST,
            value = "/{groupId}/members/{userId}")
    public void inviteMember(@PathVariable Long groupId, @PathVariable String userId) {

    }

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/{userId}")
    public Collection<GroupEntity> getGroupsById(@PathVariable String userId) {
        return null;
    }


}
