package edu.kit.pse17.go_app.ServiceLayer;


import edu.kit.pse17.go_app.PersistenceLayer.GoEntity;
import edu.kit.pse17.go_app.PersistenceLayer.GroupEntity;
import edu.kit.pse17.go_app.PersistenceLayer.UserEntity;
import edu.kit.pse17.go_app.PersistenceLayer.daos.UserDaoImp;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserDaoImp userDao;

    public UserService() {
    }

    public UserService(UserDaoImp userDao) {
        this.userDao = userDao;
    }


    public static void editUserForJson(UserEntity user) {

        //has to be checked, in case user is owner of a go --> this method is called for the user twice
        if (user != null) {
            user.setGos(null);
            user.setGroups(null);
            user.setRequests(null);
        }
    }


    public UserEntity getData(String key) {
        UserEntity user = userDao.get(key);
        System.out.println(user.getRequests().size());


        for (GroupEntity group : user.getGroups()) {
            GroupService.editGroupForJson(group);
        }

        user.setRequests(userDao.get(key).getRequests());

        for (GroupEntity group : user.getRequests()) {
            GroupService.editGroupForJson(group);
        }

        user.setGos(userDao.get(key).getGos());

        for (GoEntity go : user.getGos()) {
            GoService.editGoForJson(go, true);
        }

        return user;
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

    public UserEntity getUserbyMail(String mail) {
        UserEntity user = userDao.getUserByEmail(mail);

        if (user != null) {
            editUserForJson(user);
        }

        return user;
    }
}
