package edu.kit.pse17.go_app.ServiceLayer.observer;

import com.google.gson.Gson;
import edu.kit.pse17.go_app.ClientCommunication.Downstream.EventArg;
import edu.kit.pse17.go_app.ClientCommunication.Downstream.FcmClient;
import edu.kit.pse17.go_app.PersistenceLayer.GoEntity;
import edu.kit.pse17.go_app.PersistenceLayer.clientEntities.Go;
import edu.kit.pse17.go_app.PersistenceLayer.clientEntities.Group;
import edu.kit.pse17.go_app.ServiceLayer.GoService;

import java.util.List;

public class GoAddedObserver implements Observer {

    private final FcmClient messenger;
    private GoService goService;

    public GoAddedObserver(FcmClient messenger, GoService goService) {
        this.messenger = messenger;
        this.goService = goService;
    }

    public GoAddedObserver(GoService goService) {
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
        GoEntity goEntity = goService.getGoById(Long.valueOf(entity_ids.get(0)));
        Go go = GoService.goEntityToGo(goEntity);
        Group group = new Group(goEntity.getGroup().getID(), goEntity.getGroup().getName(), goEntity.getGroup().getDescription(), goEntity.getGroup().getMembers().size(), null, null, null);

        Gson gson = new Gson();
        String data = gson.toJson(go);

        System.out.println(data);

        messenger.send(data, EventArg.GO_ADDED_EVENT, goService.getGoById(Long.valueOf(entity_ids.get(0))).getGroup().getMembers());
        messenger.send(data, EventArg.GO_ADDED_EVENT, goService.getGoById(Long.valueOf(entity_ids.get(0))).getGroup().getRequests());
    }
}
