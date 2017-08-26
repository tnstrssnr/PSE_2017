package edu.kit.pse17.go_app.ServiceLayer.observer;

import com.google.gson.Gson;
import edu.kit.pse17.go_app.ClientCommunication.Downstream.EventArg;
import edu.kit.pse17.go_app.ClientCommunication.Downstream.FcmClient;
import edu.kit.pse17.go_app.PersistenceLayer.GroupEntity;
import edu.kit.pse17.go_app.PersistenceLayer.clientEntities.Group;
import edu.kit.pse17.go_app.ServiceLayer.GroupService;

import java.util.List;

/**
 * Dieser Observer behandelt Fälle wo eine Gruppe verändert wird.
 */

public class GroupEditedObserver implements Observer {

    private final FcmClient messenger;
    private GroupService groupService;

    public GroupEditedObserver(FcmClient messenger, GroupService groupService) {
        this.messenger = messenger;
        this.groupService = groupService;
    }

    public GroupEditedObserver(GroupService groupService) {
        this.messenger = new FcmClient();
        this.groupService = groupService;
    }

    public FcmClient getMessenger() {
        return messenger;
    }

    public GroupService getGroupService() {
        return groupService;
    }
    
    @Override
    public void update(List<String> entity_ids, List<String> receiver) {
        GroupEntity group = groupService.getGroupById(Long.valueOf(entity_ids.get(0)));

        Group cGroup = GroupService.groupEntityToGroup(group);
        GroupService.makeJsonable(cGroup);

        String data = new Gson().toJson(cGroup);

        messenger.send(data, EventArg.GROUP_EDITED_EVENT, receiver);

    }
}
