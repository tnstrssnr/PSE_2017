package edu.kit.pse17.go_app.ServiceLayer.observer;

import com.google.gson.Gson;
import edu.kit.pse17.go_app.ClientCommunication.Downstream.EventArg;
import edu.kit.pse17.go_app.ClientCommunication.Downstream.FcmClient;
import edu.kit.pse17.go_app.PersistenceLayer.GoEntity;
import edu.kit.pse17.go_app.PersistenceLayer.daos.GoDao;

import java.util.List;

public class GoEditedObserver implements Observer {

    private final FcmClient messenger;
    private GoDao goDao;

    public GoEditedObserver(FcmClient messenger, GoDao goDao) {
        this.messenger = messenger;
        this.goDao = goDao;
    }

    public GoDao getGoDao() {
        return goDao;
    }

    public void setGoDao(GoDao goDao) {
        this.goDao = goDao;
    }

    @Override
    public void update(List<String> entity_ids) {
        GoEntity goEntity = goDao.get(Long.valueOf(entity_ids.get(0)));
        goEntity.setGoingUsers(null);
        goEntity.setGoneUsers(null);
        goEntity.setNotGoingUsers(null);
        goEntity.setGroup(null);
        goEntity.setOwner(null);
        Gson gson = new Gson();
        String data = gson.toJson(goEntity);

        messenger.send(data, EventArg.GO_EDITED_COMMAND, goEntity.getGroup().getMembers());
        messenger.send(data, EventArg.GO_EDITED_COMMAND, goEntity.getGroup().getRequests());

    }
}
