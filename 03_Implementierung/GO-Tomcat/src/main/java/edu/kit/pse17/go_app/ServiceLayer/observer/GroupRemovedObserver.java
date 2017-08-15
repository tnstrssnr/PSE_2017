package edu.kit.pse17.go_app.ServiceLayer.observer;

import com.google.gson.Gson;
import edu.kit.pse17.go_app.ClientCommunication.Downstream.EventArg;
import edu.kit.pse17.go_app.ClientCommunication.Downstream.FcmClient;
import edu.kit.pse17.go_app.PersistenceLayer.GroupEntity;
import edu.kit.pse17.go_app.PersistenceLayer.UserEntity;
import edu.kit.pse17.go_app.PersistenceLayer.daos.GroupDao;
import edu.kit.pse17.go_app.PersistenceLayer.daos.GroupDaoImp;
import org.json.simple.JSONObject;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GroupRemovedObserver implements Observer {

    private final FcmClient messenger;
    private GroupDao groupDao;

    public GroupRemovedObserver(FcmClient messenger, GroupDao groupDao) {
        this.messenger = messenger;
        this.groupDao = groupDao;
    }

    public GroupRemovedObserver(GroupDao groupDao) {
        this.messenger = new FcmClient();
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

        Set<UserEntity> receiver = new HashSet<>();
        receiver.addAll(groupEntity.getRequests());
        receiver.addAll(groupEntity.getMembers());

        messenger.send(data, EventArg.GROUP_REMOVED_EVENT, receiver);

    }
}
