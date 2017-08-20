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
 * Dieser Observer behandelt Fälle wo eine Gruppe entfernt wird.
 */

public class GroupRemovedObserver implements Observer {

    private final FcmClient messenger;
    private GroupService groupService;

    public GroupRemovedObserver(FcmClient messenger, GroupService groupService) {
        this.messenger = messenger;
        this.groupService = groupService;
    }

    public GroupRemovedObserver(GroupService groupService) {
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
        JSONObject json = new JSONObject();
        json.put("id", entity_ids.get(0));
        String data = json.toJSONString();

        GroupEntity groupEntity = groupService.getGroupById(Long.valueOf(entity_ids.get(0)));

        Set<UserEntity> receiver = new HashSet<>();
        for (UserEntity usr : groupEntity.getMembers()) {
            receiver.add(usr);
        }
        for (UserEntity usr : groupEntity.getRequests()) {
            receiver.add(usr);
        }

        messenger.send(data, EventArg.GROUP_REMOVED_EVENT, receiver);

    }
}
