package edu.kit.pse17.go_app.ServiceLayer.observer;


import com.google.gson.Gson;
import edu.kit.pse17.go_app.ClientCommunication.Downstream.EventArg;
import edu.kit.pse17.go_app.ClientCommunication.Downstream.FcmClient;
import edu.kit.pse17.go_app.PersistenceLayer.GoEntity;
import edu.kit.pse17.go_app.PersistenceLayer.GroupEntity;
import edu.kit.pse17.go_app.PersistenceLayer.UserEntity;
import edu.kit.pse17.go_app.PersistenceLayer.daos.GroupDao;
import edu.kit.pse17.go_app.PersistenceLayer.daos.UserDao;

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

        List<Set<UserEntity>> userLists = new ArrayList<>();
        userLists.add(group.getAdmins());
        userLists.add(group.getMembers());

        for (GoEntity go : group.getGos()) {
            userLists.add(go.getNotGoingUsers());
            userLists.add(go.getGoneUsers());
            userLists.add(go.getGoingUsers());
            Set<UserEntity> owner = new HashSet<>();
            owner.add(go.getOwner());
            userLists.add(owner);
        }

        for(Set<UserEntity> userSet: userLists) {
            for(UserEntity member: userSet) {
                member.setGroups(null);
                member.setRequests(null);
                member.setGos(null);
            }
        }

        String data = new Gson().toJson(group);
        Set<UserEntity> receiver = new HashSet<>();
        receiver.add(user);

        messenger.send(data, EventArg.GROUP_REQUEST_RECEIVED_EVENT, receiver);

        //Admins cant see requests
    }
}
