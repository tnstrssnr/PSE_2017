package edu.kit.pse17.go_app.ServiceLayer.observer;


import edu.kit.pse17.go_app.ClientCommunication.Downstream.EventArg;
import edu.kit.pse17.go_app.ClientCommunication.Downstream.FcmClient;
import edu.kit.pse17.go_app.PersistenceLayer.GroupEntity;
import edu.kit.pse17.go_app.PersistenceLayer.daos.GroupDao;
import org.json.simple.JSONObject;

import java.util.List;

public class MemberRemovedObserver implements Observer {

    private final FcmClient messenger;
    private GroupDao groupDao;

    public MemberRemovedObserver(FcmClient messenger, GroupDao groupDao) {
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
        GroupEntity group = groupDao.get(Long.valueOf(entity_ids.get(1)));
        JSONObject json = new JSONObject();
        json.put("user_id", entity_ids.get(0));
        json.put("group_id", group.getID());

        messenger.send(json.toJSONString(), EventArg.MEMBER_REMOVED_EVENT, group.getRequests());
        messenger.send(json.toJSONString(), EventArg.MEMBER_REMOVED_EVENT, group.getMembers());
    }
}
