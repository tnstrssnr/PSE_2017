package edu.kit.pse17.go_app.ServiceLayer.observer;


import com.google.gson.Gson;
import edu.kit.pse17.go_app.ClientCommunication.Downstream.EventArg;
import edu.kit.pse17.go_app.ClientCommunication.Downstream.FcmClient;
import edu.kit.pse17.go_app.PersistenceLayer.GoEntity;
import edu.kit.pse17.go_app.PersistenceLayer.GroupEntity;
import edu.kit.pse17.go_app.PersistenceLayer.UserEntity;
import edu.kit.pse17.go_app.PersistenceLayer.daos.GroupDao;
import edu.kit.pse17.go_app.PersistenceLayer.daos.GroupDaoImp;
import edu.kit.pse17.go_app.PersistenceLayer.daos.UserDao;
import edu.kit.pse17.go_app.PersistenceLayer.daos.UserDaoImp;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GroupRequestReceivedObserver implements Observer{

    private final FcmClient messenger;
    private GroupDao groupDao;
    private UserDao userDao;

    public GroupRequestReceivedObserver(FcmClient messenger, GroupDao groupDao, UserDao userDao) {
        this.messenger = messenger;
        this.groupDao = groupDao;
        this.userDao = userDao;
    }

    public GroupRequestReceivedObserver(GroupDao groupDao) {
        this.messenger = new FcmClient();
        this.groupDao = groupDao;
        this.userDao = new UserDaoImp(((GroupDaoImp) groupDao).getSf());
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
        UserEntity user = userDao.get(entity_ids.get(0));

        for(UserEntity usr: group.getAdmins()) {
            usr.setGos(null);
            usr.setGroups(null);
            usr.setGroups(null);
        }

        for(UserEntity usr: group.getMembers()) {
            usr.setGos(null);
            usr.setGroups(null);
            usr.setGroups(null);
        }

        for(UserEntity usr: group.getRequests()) {
            usr.setGos(null);
            usr.setGroups(null);
            usr.setGroups(null);
        }

        for(GoEntity go: group.getGos()) {
            go.setGroup(null);
            go.getOwner().setGroups(null);
            go.getOwner().setGos(null);
            go.getOwner().setRequests(null);

            for(UserEntity usr: go.getNotGoingUsers()) {
                usr.setGos(null);
                usr.setGroups(null);
                usr.setGroups(null);
            }
            for(UserEntity usr: go.getGoingUsers()) {
                usr.setGos(null);
                usr.setGroups(null);
                usr.setGroups(null);
            }
            for(UserEntity usr: go.getGoneUsers()) {
                usr.setGos(null);
                usr.setGroups(null);
                usr.setGroups(null);
            }
        }

        String data = new Gson().toJson(group);
        Set<UserEntity> receiver = new HashSet<>();
        receiver.add(user);

        messenger.send(data, EventArg.GROUP_REQUEST_RECEIVED_EVENT, receiver);

        //Admins cant see requests
    }
}
