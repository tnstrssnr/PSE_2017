package edu.kit.pse17.go_app.ServiceLayer.observer;

import edu.kit.pse17.go_app.ClientCommunication.Downstream.EventArg;
import edu.kit.pse17.go_app.ClientCommunication.Downstream.FcmClient;
import edu.kit.pse17.go_app.ServiceLayer.GroupService;
import org.json.simple.JSONObject;

import java.util.List;

/**
 * Dieser Observer behandelt Fälle wo ein Admin eine Gruppe hinzugefügt wird.
 */

public class AdminAddedObserver implements Observer {

    private FcmClient messenger;

    public AdminAddedObserver(FcmClient messenger, GroupService groupService) {
        this.messenger = messenger;
    }

    public AdminAddedObserver(GroupService groupService) {
        this.messenger = new FcmClient();
    }

    public FcmClient getMessenger() {
        return messenger;
    }

    @Override
    public void update(List<String> entity_ids, List<String> receiver) {

        //create JSON Object to send to clients
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("user_id", entity_ids.get(0));
        jsonObject.put("group_id", entity_ids.get(1));
        String data = jsonObject.toJSONString();

        messenger.send(data, EventArg.ADMIN_ADDED_EVENT, receiver);
    }
}
