package edu.kit.pse17.go_app.ServiceLayer;


import edu.kit.pse17.go_app.ClientCommunication.Downstream.EventArg;
import edu.kit.pse17.go_app.PersistenceLayer.GroupEntity;
import edu.kit.pse17.go_app.PersistenceLayer.UserEntity;
import edu.kit.pse17.go_app.PersistenceLayer.clientEntities.Group;
import edu.kit.pse17.go_app.PersistenceLayer.clientEntities.User;
import edu.kit.pse17.go_app.PersistenceLayer.daos.UserDaoImp;
import edu.kit.pse17.go_app.ServiceLayer.observer.Observer;
import edu.kit.pse17.go_app.ServiceLayer.observer.UserDeletedObserver;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService implements IObservable {

    @Autowired
    private UserDaoImp userDao;

    private Map<EventArg, Observer> observerMap;

    private boolean observerInitialized;

    public UserService() {
        registerAll();
    }

    public UserService(UserDaoImp userDao) {
        this.userDao = userDao;
        registerAll();
    }

    public static void editUserForJson(UserEntity user) {

        //has to be checked, in case user is owner of a go --> this method is called for the user twice
        if (user != null) {
            user.setGos(null);
            user.setGroups(null);
            user.setRequests(null);
        }
    }

    public static User userEntityToUser(UserEntity userEntity) {
        return new User(userEntity.getUid(), userEntity.getInstanceId(), userEntity.getName(), userEntity.getEmail());
    }

    public UserDaoImp getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDaoImp userDao) {
        this.userDao = userDao;
    }

    public List<Group> getData(String key, String email) {
        UserEntity user = userDao.get(key);

        //user doesnt exist -- create and store in database
        if (user == null) {
            user = new UserEntity();
            user.setUid(key);
            user.setName(email);
            user.setEmail(email);
            createUser(user);
        }

        List<Group> groups = new ArrayList<>();
        for (GroupEntity groupEntity : user.getGroups()) {
            Group group = GroupService.groupEntityToGroup(groupEntity);
            group.makeJsonable();
            groups.add(group);
        }

        return groups;
    }

    public boolean createUser(UserEntity user) {
        if (user.getUid() != null) {
            try {
                userDao.persist(user);

            } catch (final ConstraintViolationException e) {
                return false;
            }
        }
        return true;
    }

    public void updateUser(UserEntity user) {
        userDao.update(user);
    }

    public void deleteUser(String key) {
        userDao.delete(key);
    }

    public void registerDevice(String userId, String instanceId) {
        UserEntity user = userDao.get(userId);
        user.setInstanceId(instanceId);
        userDao.update(user);
    }

    public User getUserbyMail(String mail) {
        UserEntity userEntity = userDao.getUserByEmail(mail);
        User user = null;

        if (userEntity != null) {
            userEntityToUser(userEntity);
        }

        return user;
    }

    @Override
    public void register(EventArg arg, Observer observer) {
        if (observerMap == null) {
            observerMap = new HashMap<>();
        }
        observerMap.put(arg, observer);

    }

    @Override
    public void unregister(EventArg arg) {
        observerMap.remove(arg);

    }

    @Override
    public void notify(EventArg impCode, IObservable observable, List<String> entity_ids) {
        if (!this.observerInitialized) {
            registerAll();
        }
        observerMap.get(impCode).update(entity_ids);

    }

    private void registerAll() {
        register(EventArg.USER_DELETED_EVENT, new UserDeletedObserver(this));
        observerInitialized = true;
    }
}
