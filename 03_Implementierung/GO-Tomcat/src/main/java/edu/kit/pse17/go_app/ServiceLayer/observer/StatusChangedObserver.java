package edu.kit.pse17.go_app.ServiceLayer.observer;

import edu.kit.pse17.go_app.ClientCommunication.Downstream.EventArg;
import edu.kit.pse17.go_app.ClientCommunication.Downstream.FcmClient;
import edu.kit.pse17.go_app.PersistenceLayer.GoEntity;
import edu.kit.pse17.go_app.PersistenceLayer.UserEntity;
import edu.kit.pse17.go_app.PersistenceLayer.daos.UserDaoImp;
import edu.kit.pse17.go_app.ServiceLayer.GoService;
import org.json.simple.JSONObject;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Dieser Observer behandelt Fälle wo der User Status sich verändert.
 */

public class StatusChangedObserver implements Observer {

    private final FcmClient messenger;
    private GoService goService;

    public StatusChangedObserver(FcmClient messenger, GoService goService) {
        this.messenger = messenger;
        this.goService = goService;
    }

    public StatusChangedObserver(GoService goService) {
        this.messenger = new FcmClient();
        this.goService = goService;
    }

    public FcmClient getMessenger() {
        return messenger;
    }

    public GoService getGoService() {
        return goService;
    }

    public void setGoService(GoService goService) {
        this.goService = goService;
    }

    @Override
    public void update(List<String> entity_ids) {
        int newStatus;

        UserDaoImp userDao = new UserDaoImp(goService.getGoDao().getSessionFactory());
        GoEntity go = goService.getGoById(Long.valueOf(entity_ids.get(1)));
        UserEntity user = userDao.get(entity_ids.get(0));

        if (!(go.getGoingUsers() == null) && go.getGoingUsers().contains(user)) {
            newStatus = 1;
        } else if (!(go.getGoneUsers() == null) && go.getGoneUsers().contains(user)) {
            newStatus = 2;
        } else {
            newStatus = 0;
        }

        JSONObject json = new JSONObject();
        json.put("user_id", user.getUid());
        json.put("go_id", go.getID());
        json.put("status", newStatus);

        String data = json.toJSONString();

        Set<UserEntity> receiver = new HashSet<>();
        for (UserEntity usr : go.getGroup().getMembers()) {
            receiver.add(usr);
        }
        for (UserEntity usr : go.getGroup().getRequests()) {
            receiver.add(usr);
        }

        messenger.send(data, EventArg.STATUS_CHANGED_EVENT, receiver);
    }
}
