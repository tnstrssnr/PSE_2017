package edu.kit.pse17.go_app.ServiceLayer.observer;

import com.google.gson.Gson;
import edu.kit.pse17.go_app.ClientCommunication.Downstream.EventArg;
import edu.kit.pse17.go_app.ClientCommunication.Downstream.FcmClient;
import edu.kit.pse17.go_app.PersistenceLayer.GoEntity;
import edu.kit.pse17.go_app.PersistenceLayer.clientEntities.Go;
import edu.kit.pse17.go_app.PersistenceLayer.clientEntities.Group;
import edu.kit.pse17.go_app.ServiceLayer.GoService;

import java.util.ArrayList;
import java.util.List;

/**
 * Dieser Observer behandelt Fälle wo ein Go hinzugefügt wird.
 */

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

    public GoService getGoService() {
        return goService;
    }

    public FcmClient getMessenger() {
        return messenger;
    }

    @Override
    public void update(List<String> entity_ids, List<String> receiver) {
        GoEntity goEntity = goService.getGoById(Long.valueOf(entity_ids.get(0)));
        Go go = GoService.goEntityToGo(goEntity);
        Group group = new Group(goEntity.getGroup().getID(), goEntity.getGroup().getName(), goEntity.getGroup().getDescription(), 0, null, new ArrayList<>(), new ArrayList<>());
        GoService.makeJsonable(go, true);
        go.setGroup(group);

        Gson gson = new Gson();
        String data = gson.toJson(go);

        messenger.send(data, EventArg.GO_ADDED_EVENT, receiver);
    }
}
