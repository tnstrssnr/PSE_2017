package edu.kit.pse17.go_app.ServiceLayer.observer;


import edu.kit.pse17.go_app.ClientCommunication.Downstream.EventArg;
import edu.kit.pse17.go_app.ClientCommunication.Downstream.FcmClient;
import edu.kit.pse17.go_app.ServiceLayer.GroupService;
import org.json.simple.JSONObject;

import java.util.List;

/**
 * Dieser Observer behandelt Fälle wo Gruppenanfragen abgelehnt werden.
 */

public class RequestDeniedObserver implements Observer {

    private final FcmClient messenger;
    private GroupService groupService;

    public RequestDeniedObserver(GroupService groupService) {
        this.groupService = groupService;
        this.messenger = new FcmClient();
    }

    public RequestDeniedObserver(FcmClient messenger, GroupService groupService) {
        this.messenger = messenger;
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
        JSONObject json = new JSONObject();
        json.put("user_id", entity_ids.get(0));
        json.put("group_id", Long.valueOf(entity_ids.get(1)));

        messenger.send(json.toJSONString(), EventArg.GROUP_REQUEST_DENIED_EVENT, receiver);

    }
}
