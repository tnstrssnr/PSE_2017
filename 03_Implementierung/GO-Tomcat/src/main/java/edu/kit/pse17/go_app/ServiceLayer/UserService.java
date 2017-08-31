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

import java.util.*;

/**
 * Diese Klasse handhabt alle Observerfunktion in Bezug auf UserDaos. Für mehr Information siehe Interface IObservable.
 */

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

    public static User userEntityToUser(UserEntity userEntity) {
        return new User(userEntity.getUid(), userEntity.getInstanceId(), userEntity.getName(), userEntity.getEmail());
    }

    public Map<EventArg, Observer> getObserverMap() {
        return observerMap;
    }

    public void setObserverMap(Map<EventArg, Observer> observerMap) {
        this.observerMap = observerMap;
    }

    public List<Group> getData(String key, String email, String userName) {
        UserEntity user = userDao.get(key);

        //Benutzer existiert nicht -- erstelle Objekt und speichere in Datenbank
        if (user == null) {
            user = new UserEntity();
            user.setUid(key);
            user.setName(userName);
            user.setEmail(email);
            user.setRequests(new HashSet<>());
            user.setGroups(new HashSet<>());
            user.setGos(new HashSet<>());
            createUser(user);
        }

        List<Group> groups = new ArrayList<>();
        for (GroupEntity groupEntity : user.getGroups()) {
            Group group = GroupService.groupEntityToGroup(groupEntity);
            GroupService.makeJsonable(group);
            groups.add(group);
        }
        for (GroupEntity groupEntity : user.getRequests()) {
            Group group = GroupService.groupEntityToGroup(groupEntity);
            GroupService.makeJsonable(group);
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
        UserEntity toBeDeleted = userDao.get(key);
        Set<UserEntity> receiver = new HashSet<>();
        for (GroupEntity group : toBeDeleted.getGroups()) {
            receiver.addAll(group.getMembers());
            receiver.addAll(group.getRequests());
        }

        List<String> receiver_ids = new ArrayList<>();
        for (UserEntity usr : receiver) {
            if (!usr.getInstanceId().equals(toBeDeleted.getInstanceId())) {
                receiver_ids.add(usr.getInstanceId());
            }
        }

        List<String> entity_ids = new ArrayList<>();
        entity_ids.add(key);
        userDao.delete(key);

        notify(EventArg.USER_DELETED_EVENT, this, entity_ids, receiver_ids);
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
            user = userEntityToUser(userEntity);
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
    public void notify(EventArg impCode, IObservable observable, List<String> entity_ids, List<String> receiver) {
        if (!this.observerInitialized) {
            registerAll();
        }
        observerMap.get(impCode).update(entity_ids, receiver);

    }

    private void registerAll() {
        this.observerMap = new HashMap<>();
        register(EventArg.USER_DELETED_EVENT, new UserDeletedObserver(this));
        observerInitialized = true;
    }
}
