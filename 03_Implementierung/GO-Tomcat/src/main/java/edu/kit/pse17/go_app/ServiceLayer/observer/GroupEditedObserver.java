package edu.kit.pse17.go_app.ServiceLayer.observer;

import com.google.gson.Gson;
import edu.kit.pse17.go_app.ClientCommunication.Downstream.EventArg;
import edu.kit.pse17.go_app.ClientCommunication.Downstream.FcmClient;
import edu.kit.pse17.go_app.PersistenceLayer.GroupEntity;
import edu.kit.pse17.go_app.PersistenceLayer.UserEntity;
import edu.kit.pse17.go_app.PersistenceLayer.clientEntities.Group;
import edu.kit.pse17.go_app.ServiceLayer.GroupService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        GroupEntity group = groupService.getGroupById(Long.valueOf(entity_ids.get(0)));

        Group cGroup = GroupService.groupEntityToGroup(group);
        GroupService.makeJsonable(cGroup);

        String data = new Gson().toJson(cGroup);
        Set<UserEntity> receiver = new HashSet<>();
        for (UserEntity usr : group.getMembers()) {
            receiver.add(usr);
        }
        //receiver.addAll(group.getAdmins());

        //send group to invited member

        messenger.send(data, EventArg.GROUP_EDITED_EVENT, receiver);

    }
}
