package edu.kit.pse17.go_app.ServiceLayer.observer;

import com.google.gson.Gson;
import edu.kit.pse17.go_app.ClientCommunication.Downstream.EventArg;
import edu.kit.pse17.go_app.ClientCommunication.Downstream.FcmClient;
import edu.kit.pse17.go_app.PersistenceLayer.GroupEntity;
import edu.kit.pse17.go_app.ServiceLayer.GroupService;

import java.util.List;

public class GroupEditedObserver implements Observer {

    private final FcmClient messenger;
    private GroupService groupService;
    private Gson gson;

    public GroupEditedObserver(FcmClient messenger, GroupService groupService) {
        this.messenger = messenger;
        this.groupService = groupService;
        this.gson = new Gson();
    }

    public GroupEditedObserver(GroupService groupService) {
        this.messenger = new FcmClient();
        this.groupService = groupService;
    }


    @Override
    public void update(List<String> entity_ids) {
        GroupEntity changedGroup = groupService.getGroupById(Long.valueOf(entity_ids.get(0)));
        changedGroup.setRequests(null);
        changedGroup.setMembers(null);
        changedGroup.setAdmins(null);
        changedGroup.setGos(null);
        String data = gson.toJson(changedGroup);

        messenger.send(data, EventArg.GROUP_EDITED_COMMAND, changedGroup.getMembers());
        messenger.send(data, EventArg.GROUP_EDITED_COMMAND, changedGroup.getRequests());


    }
}
