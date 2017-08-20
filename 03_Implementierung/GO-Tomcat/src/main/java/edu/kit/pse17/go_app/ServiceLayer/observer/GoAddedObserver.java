package edu.kit.pse17.go_app.ServiceLayer.observer;

import com.google.gson.Gson;
import edu.kit.pse17.go_app.ClientCommunication.Downstream.EventArg;
import edu.kit.pse17.go_app.ClientCommunication.Downstream.FcmClient;
import edu.kit.pse17.go_app.PersistenceLayer.GoEntity;
import edu.kit.pse17.go_app.PersistenceLayer.UserEntity;
import edu.kit.pse17.go_app.PersistenceLayer.clientEntities.Go;
import edu.kit.pse17.go_app.PersistenceLayer.clientEntities.Group;
import edu.kit.pse17.go_app.ServiceLayer.GoService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    public FcmClient getMessenger() {
        return messenger;
    }

    @Override
    public void update(List<String> entity_ids) {
        GoEntity goEntity = goService.getGoById(Long.valueOf(entity_ids.get(0)));
        Go go = GoService.goEntityToGo(goEntity);
        Group group = new Group(goEntity.getGroup().getID(), goEntity.getGroup().getName(), goEntity.getGroup().getDescription(), 0, null, new ArrayList<>(), new ArrayList<>());
        go.setGroup(group);
        Gson gson = new Gson();
        GoService.makeJsonable(go, true);
        go.setGroup(group);
        String data = gson.toJson(go);

        Set<UserEntity> receiver = new HashSet<>();
        for (UserEntity usr : goEntity.getGroup().getMembers()) {
            receiver.add(usr);
        }
        for (UserEntity usr : goEntity.getGroup().getRequests()) {
            receiver.add(usr);
        }

        messenger.send(data, EventArg.GO_ADDED_EVENT, goService.getGoById(Long.valueOf(entity_ids.get(0))).getGroup().getMembers());
    }
}
