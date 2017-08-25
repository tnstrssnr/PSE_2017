package edu.kit.pse17.go_app.ServiceLayer.observer;


import edu.kit.pse17.go_app.ClientCommunication.Downstream.EventArg;
import edu.kit.pse17.go_app.ClientCommunication.Downstream.FcmClient;
import edu.kit.pse17.go_app.PersistenceLayer.GroupEntity;
import edu.kit.pse17.go_app.PersistenceLayer.UserEntity;
import edu.kit.pse17.go_app.PersistenceLayer.daos.GroupDaoImp;
import edu.kit.pse17.go_app.ServiceLayer.GoService;
import org.json.simple.JSONObject;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Dieser Observer behandelt Fälle wo ein Go entfernt wird.
 */

public class GoRemovedObserver implements Observer {

    private final FcmClient messenger;
    private GoService goService;

    public GoRemovedObserver(FcmClient messenger, GoService goService) {
        this.messenger = messenger;
        this.goService = goService;
    }

    public GoRemovedObserver(GoService goService) {
        this.messenger = new FcmClient();
        this.goService = goService;
    }

    @Override
    public void update(List<String> entity_ids) {
        GroupEntity group = new GroupDaoImp(goService.getGoDao().getSessionFactory()).get(Long.valueOf(entity_ids.get(1)));
        JSONObject json = new JSONObject();
        json.put("id", entity_ids.get(0));
        String data = json.toJSONString();

        Set<UserEntity> receiver = new HashSet<>();
        receiver.addAll(group.getMembers());
        receiver.addAll(group.getRequests());

        messenger.send(data, EventArg.GO_REMOVED_EVENT, receiver);
    }
}
