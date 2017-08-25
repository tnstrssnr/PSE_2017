package edu.kit.pse17.go_app.ServiceLayer.observer;

import edu.kit.pse17.go_app.ClientCommunication.Downstream.EventArg;
import edu.kit.pse17.go_app.ClientCommunication.Downstream.FcmClient;
import edu.kit.pse17.go_app.PersistenceLayer.UserEntity;
import edu.kit.pse17.go_app.ServiceLayer.UserService;
import org.json.simple.JSONObject;

import java.util.List;

/**
 * Dieser Observer behandelt Fälle wo User entfernt werden.
 */

public class UserDeletedObserver implements Observer {

    private final FcmClient messenger;
    private UserService userService;

    public UserDeletedObserver(UserService userService) {
        this.messenger = new FcmClient();
        this.userService = userService;
    }

    @Override
    public void update(List<String> entity_ids, List<String> receiver) {
        UserEntity user = userService.getUserDao().get(entity_ids.get(0));
        JSONObject json = new JSONObject();
        json.put("user_id", entity_ids.get(0));

        messenger.send(json.toJSONString(), EventArg.USER_DELETED_EVENT, receiver);

    }

}
