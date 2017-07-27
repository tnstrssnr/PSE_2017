package edu.kit.pse17.go_app.ServiceLayer.observer;

import com.google.gson.Gson;
import edu.kit.pse17.go_app.ClientCommunication.Downstream.EventArg;
import edu.kit.pse17.go_app.ClientCommunication.Downstream.FcmClient;
import edu.kit.pse17.go_app.PersistenceLayer.GroupEntity;
import edu.kit.pse17.go_app.PersistenceLayer.daos.GroupDao;
import org.json.simple.JSONObject;

import java.util.List;

public class GroupRemovedObserver implements Observer {

    private final FcmClient messenger;
    private GroupDao groupDao;

    public GroupRemovedObserver(FcmClient messenger, GroupDao groupDao) {
        this.messenger = messenger;
        this.groupDao = groupDao;
    }

    public GroupDao getGroupDao() {
        return groupDao;
    }

    public void setGroupDao(GroupDao groupDao) {
        this.groupDao = groupDao;
    }

    @Override
    public void update(List<String> entity_ids) {
        JSONObject json = new JSONObject();
        json.put("id", entity_ids.get(0));
        String data = json.toJSONString();

        GroupEntity groupEntity = groupDao.get(Long.valueOf(entity_ids.get(0)));

        messenger.send(data, EventArg.GO_REMOVED_EVENT, groupEntity.getMembers());
        messenger.send(data, EventArg.GO_REMOVED_EVENT, groupEntity.getRequests());
    }
}
