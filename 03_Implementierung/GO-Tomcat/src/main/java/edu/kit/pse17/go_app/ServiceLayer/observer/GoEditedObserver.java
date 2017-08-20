package edu.kit.pse17.go_app.ServiceLayer.observer;

import com.google.gson.Gson;
import edu.kit.pse17.go_app.ClientCommunication.Downstream.EventArg;
import edu.kit.pse17.go_app.ClientCommunication.Downstream.FcmClient;
import edu.kit.pse17.go_app.PersistenceLayer.GoEntity;
import edu.kit.pse17.go_app.PersistenceLayer.UserEntity;
import edu.kit.pse17.go_app.PersistenceLayer.clientEntities.Go;
import edu.kit.pse17.go_app.ServiceLayer.GoService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Dieser Observer behandelt Fälle wo ein Go verändert wird.
 */

public class GoEditedObserver implements Observer {

    private final FcmClient messenger;
    private GoService goService;

    public GoEditedObserver(FcmClient messenger, GoService goService) {
        this.messenger = messenger;
        this.goService = goService;
    }

    public GoEditedObserver(GoService dao) {
        this.messenger = new FcmClient();
        this.goService = dao;
    }

    @Override
    public void update(List<String> entity_ids) {
        GoEntity goEntity = goService.getGoById(Long.valueOf(entity_ids.get(0)));
        Go go = GoService.goEntityToGo(goEntity);

        Set<UserEntity> receiver = new HashSet<>();
        for (UserEntity usr : goEntity.getGroup().getMembers()) {
            receiver.add(usr);
        }
        for (UserEntity usr : goEntity.getGroup().getRequests()) {
            receiver.add(usr);
        }

        Gson gson = new Gson();
        GoService.makeJsonable(go, true);
        String data = gson.toJson(go);

        messenger.send(data, EventArg.GO_EDITED_EVENT, receiver);


    }
}
