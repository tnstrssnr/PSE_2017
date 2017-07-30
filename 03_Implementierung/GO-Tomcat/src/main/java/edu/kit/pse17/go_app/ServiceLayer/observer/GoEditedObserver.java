package edu.kit.pse17.go_app.ServiceLayer.observer;

import com.google.gson.Gson;
import edu.kit.pse17.go_app.ClientCommunication.Downstream.EventArg;
import edu.kit.pse17.go_app.ClientCommunication.Downstream.FcmClient;
import edu.kit.pse17.go_app.PersistenceLayer.GoEntity;
import edu.kit.pse17.go_app.PersistenceLayer.UserEntity;
import edu.kit.pse17.go_app.PersistenceLayer.daos.GoDao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GoEditedObserver implements Observer {

    private final FcmClient messenger;
    private GoDao goDao;

    public GoEditedObserver(FcmClient messenger, GoDao goDao) {
        this.messenger = messenger;
        this.goDao = goDao;
    }

    public GoEditedObserver(GoDao dao) {
        this.messenger = new FcmClient();
        this.goDao = dao;
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

        Set<UserEntity> receiver = new HashSet<>();
        receiver.addAll(goEntity.getGroup().getMembers());
        receiver.addAll(goEntity.getGroup().getRequests());

        goEntity.setGoingUsers(null);
        goEntity.setGoneUsers(null);
        goEntity.setNotGoingUsers(null);
        goEntity.setGroup(null);
        goEntity.setOwner(null);
        Gson gson = new Gson();
        String data = gson.toJson(goEntity);



        messenger.send(data, EventArg.GO_EDITED_COMMAND, receiver);


    }
}
