package edu.kit.pse17.go_app.ServiceLayer.observer;

import edu.kit.pse17.go_app.ClientCommunication.Downstream.EventArg;
import edu.kit.pse17.go_app.ClientCommunication.Downstream.FcmClient;
import edu.kit.pse17.go_app.PersistenceLayer.GroupEntity;
import edu.kit.pse17.go_app.PersistenceLayer.UserEntity;
import edu.kit.pse17.go_app.ServiceLayer.GroupService;
import org.json.simple.JSONObject;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Dieser Observer behandelt Fälle wo ein Admin eine Gruppe hinzugefügt wird.
 */

public class AdminAddedObserver implements Observer {

    private final FcmClient messenger;
    private GroupService groupService;

    public AdminAddedObserver(FcmClient messenger, GroupService groupService) {
        this.messenger = messenger;
        this.groupService = groupService;
    }

    public AdminAddedObserver(GroupService groupService) {
        this.messenger = new FcmClient();
        this.groupService = groupService;
    }

    public GroupService getGroupService() {
        return groupService;
    }

    public void setGroupService(GroupService groupService) {
        this.groupService = groupService;
    }

    @Override
    public void update(List<String> entity_ids) {
        GroupEntity changedGroup = groupService.getGroupById((Long.valueOf(entity_ids.get(1))));

        //create JSON Object to send to clients
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("user_id", entity_ids.get(0));
        jsonObject.put("group_id", entity_ids.get(1));
        String data = jsonObject.toJSONString();

        Set<UserEntity> receiver = new HashSet<>();
        receiver.addAll(changedGroup.getMembers());
        receiver.addAll(changedGroup.getRequests());

        messenger.send(data, EventArg.ADMIN_ADDED_EVENT, receiver);
    }
}
