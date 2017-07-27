package edu.kit.pse17.go_app.ServiceLayer.observer;


import edu.kit.pse17.go_app.ClientCommunication.Downstream.EventArg;
import edu.kit.pse17.go_app.ClientCommunication.Downstream.FcmClient;
import edu.kit.pse17.go_app.PersistenceLayer.GoEntity;
import edu.kit.pse17.go_app.PersistenceLayer.daos.GoDao;
import org.json.simple.JSONObject;

import java.util.List;

public class GoRemovedObserver implements Observer {

    private final FcmClient messenger;
    private GoDao goDao;

    public GoRemovedObserver(FcmClient messenger, GoDao goDao) {
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
        GoEntity removedGo = goDao.get(Long.valueOf(entity_ids.get(0)));
        JSONObject json = new JSONObject();
        json.put("id", entity_ids.get(0));
        String data = json.toJSONString();

        messenger.send(data, EventArg.GO_REMOVED_EVENT, removedGo.getGroup().getMembers());
        messenger.send(data, EventArg.GO_REMOVED_EVENT, removedGo.getGroup().getRequests());

    }
}
