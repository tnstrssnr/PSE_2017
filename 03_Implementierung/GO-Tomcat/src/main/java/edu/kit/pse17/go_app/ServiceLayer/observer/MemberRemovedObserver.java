package edu.kit.pse17.go_app.ServiceLayer.observer;


import edu.kit.pse17.go_app.ClientCommunication.Downstream.EventArg;
import edu.kit.pse17.go_app.ClientCommunication.Downstream.FcmClient;
import edu.kit.pse17.go_app.ServiceLayer.GroupService;
import org.json.simple.JSONObject;

import java.util.List;

/**
 * Dieser Observer behandelt Fälle wo Gruppenmitglieder entfernt werden.
 */

public class MemberRemovedObserver implements Observer {

    private final FcmClient messenger;
    private GroupService groupService;

    public MemberRemovedObserver(FcmClient messenger, GroupService groupService) {
        this.messenger = messenger;
        this.groupService = groupService;
    }

    public MemberRemovedObserver(GroupService groupService) {
        this.messenger = new FcmClient();
        this.groupService = groupService;
    }

    public GroupService getGroupService() {
        return groupService;
    }

    public FcmClient getMessenger() {
        return messenger;
    }

    @Override
    public void update(List<String> entity_ids, List<String> receiver) {
        JSONObject json = new JSONObject();
        json.put("user_id", entity_ids.get(0));
        json.put("group_id", entity_ids.get(1));

        messenger.send(json.toJSONString(), EventArg.MEMBER_REMOVED_EVENT, receiver);
    }
}
