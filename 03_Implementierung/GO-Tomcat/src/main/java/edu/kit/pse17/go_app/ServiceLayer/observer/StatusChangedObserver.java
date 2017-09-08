package edu.kit.pse17.go_app.ServiceLayer.observer;

import edu.kit.pse17.go_app.ClientCommunication.Downstream.EventArg;
import edu.kit.pse17.go_app.ClientCommunication.Downstream.FcmClient;
import edu.kit.pse17.go_app.PersistenceLayer.GoEntity;
import edu.kit.pse17.go_app.PersistenceLayer.UserEntity;
import edu.kit.pse17.go_app.PersistenceLayer.daos.UserDaoImp;
import edu.kit.pse17.go_app.ServiceLayer.GoService;
import org.json.simple.JSONObject;

import java.util.List;

/**
 * Dieser Observer behandelt Fälle wo der User Status sich verändert.
 */

public class StatusChangedObserver implements Observer {

    private final FcmClient messenger;
    private GoService goService;
    private UserDaoImp userDao;

    public StatusChangedObserver(FcmClient messenger, GoService goService) {
        this.messenger = messenger;
        this.goService = goService;
    }

    public StatusChangedObserver(GoService goService) {
        this.messenger = new FcmClient();
        this.goService = goService;
    }

    public void setUserDao(UserDaoImp userDao) {
        this.userDao = userDao;
    }

    public FcmClient getMessenger() {
        return messenger;
    }

    public GoService getGoService() {
        return goService;
    }

    @Override
    public void update(List<String> entity_ids, List<String> receiver) {
        int newStatus;

        GoEntity go = goService.getGoById(Long.valueOf(entity_ids.get(1)));
        if (userDao == null) {
            userDao = new UserDaoImp(goService.getGoDao().getSessionFactory());
        }
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

        messenger.send(data, EventArg.STATUS_CHANGED_EVENT, receiver);
    }
}
