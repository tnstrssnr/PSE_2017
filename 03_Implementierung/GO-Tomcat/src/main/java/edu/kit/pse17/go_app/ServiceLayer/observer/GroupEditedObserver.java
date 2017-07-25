package edu.kit.pse17.go_app.ServiceLayer.observer;

import com.google.gson.Gson;
import edu.kit.pse17.go_app.ClientCommunication.Downstream.EventArg;
import edu.kit.pse17.go_app.ClientCommunication.Downstream.FcmClient;
import edu.kit.pse17.go_app.PersistenceLayer.GroupEntity;
import edu.kit.pse17.go_app.PersistenceLayer.daos.GroupDao;

import java.util.List;

public class GroupEditedObserver implements Observer {

    private final FcmClient messenger;
    private GroupDao groupDao;
    private Gson gson;

    public GroupEditedObserver(FcmClient messenger, GroupDao groupDao) {
        this.messenger = messenger;
        this.groupDao = groupDao;
        this.gson = new Gson();
    }

    public GroupDao getGroupDao() {
        return groupDao;
    }

    public void setGroupDao(GroupDao groupDao) {
        this.groupDao = groupDao;
    }

    @Override
    public void update(List<String> entity_ids) {
        GroupEntity changedGroup = groupDao.get(Long.valueOf(entity_ids.get(0)));
        changedGroup.setRequests(null);
        changedGroup.setMembers(null);
        changedGroup.setAdmins(null);
        changedGroup.setGos(null);
        String data = gson.toJson(changedGroup);

        messenger.send(data, EventArg.GROUP_EDITED_COMMAND, changedGroup.getMembers());
        messenger.send(data, EventArg.GROUP_EDITED_COMMAND, changedGroup.getRequests());


    }
}
