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

public class RequestDeniedObserver implements Observer {

    private final FcmClient messenger;
    private GroupService groupService;

    public RequestDeniedObserver(FcmClient messenger, GroupService groupService) {
        this.messenger = messenger;
        this.groupService = groupService;
    }

    public RequestDeniedObserver(GroupService groupService) {
        this.groupService = groupService;
        this.messenger = new FcmClient();
    }

    @Override
    public void update(List<String> entity_ids) {
        GroupEntity group = groupService.getGroupById(Long.valueOf(entity_ids.get(1)));
        JSONObject json = new JSONObject();
        json.put("user_id", entity_ids.get(0));
        json.put("group_id", group.getID());

        Set<UserEntity> receiver = new HashSet<>();
        for (UserEntity usr : group.getMembers()) {
            receiver.add(usr);
        }

        messenger.send(json.toJSONString(), EventArg.MEMBER_REMOVED_EVENT, group.getAdmins());

    }
}
