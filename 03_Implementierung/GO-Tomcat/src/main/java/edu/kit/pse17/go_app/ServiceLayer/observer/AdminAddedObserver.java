package edu.kit.pse17.go_app.ServiceLayer.observer;

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

public class AdminAddedObserver implements Observer {

    private final FcmClient messenger;
    private GroupDao groupDao;

    public AdminAddedObserver(FcmClient messenger, GroupDao groupDao) {
        this.messenger = messenger;
        this.groupDao = groupDao;
    }

    public AdminAddedObserver(GroupDao groupDao) {
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
        GroupEntity changedGroup = groupDao.get((Long.valueOf(entity_ids.get(1))));

        //create JSON Object to send to clients
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("user_id", entity_ids.get(0));
        jsonObject.put("group_id", entity_ids.get(1));
        String data = jsonObject.toJSONString();

        Set<UserEntity> receiver = new HashSet<>();
        receiver.addAll(changedGroup.getMembers());
        receiver.addAll(changedGroup.getRequests());

        messenger.send(data, EventArg.ADMIN_ADDED_EVENT, receiver);
    }
}
