package edu.kit.pse17.go_app.ServiceLayer.observer;

import com.google.gson.Gson;
import edu.kit.pse17.go_app.ClientCommunication.Downstream.EventArg;
import edu.kit.pse17.go_app.ClientCommunication.Downstream.FcmClient;
import edu.kit.pse17.go_app.PersistenceLayer.GroupEntity;
import edu.kit.pse17.go_app.PersistenceLayer.UserEntity;
import edu.kit.pse17.go_app.PersistenceLayer.daos.GroupDao;
import edu.kit.pse17.go_app.PersistenceLayer.daos.UserDao;

import java.util.List;

public class MemberAddedObserver implements Observer{

    private final FcmClient messenger;
    private UserDao userDao;
    private GroupDao groupdao;

    public MemberAddedObserver(FcmClient messenger, UserDao userDao, GroupDao groupdao) {
        this.messenger = messenger;
        this.userDao = userDao;
        this.groupdao = groupdao;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void update(List<String> entity_ids) {
        GroupEntity group = groupdao.get(Long.valueOf(entity_ids.get(1)));
        UserEntity newUser = userDao.get(entity_ids.get(0));

        newUser.setRequests(null);
        newUser.setGroups(null);
        newUser.setGos(null);

        String data = new Gson().toJson(newUser);
        messenger.send(data, EventArg.MEMBER_ADDED_EVENT, group.getMembers());
        messenger.send(data, EventArg.MEMBER_ADDED_EVENT, group.getRequests());
    }
}
