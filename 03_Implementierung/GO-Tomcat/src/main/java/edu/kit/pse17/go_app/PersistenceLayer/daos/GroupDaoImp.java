package edu.kit.pse17.go_app.PersistenceLayer.daos;

import edu.kit.pse17.go_app.PersistenceLayer.GoEntity;
import edu.kit.pse17.go_app.PersistenceLayer.GroupEntity;
import edu.kit.pse17.go_app.PersistenceLayer.UserEntity;
import edu.kit.pse17.go_app.ServiceLayer.Observable;
import edu.kit.pse17.go_app.ServiceLayer.Observer;

import java.io.Serializable;
import java.util.List;

/**
 * Created by tina on 30.06.17.
 */
public class GroupDaoImp extends AbstractDao<Long, GoEntity> implements GoDao, Serializable, Observable<GroupEntity> {

    private static long idCounter;

    @Override
    public GoEntity create(GoEntity goEntity) {
        return null;
    }

    @Override
    public GoEntity getById(Long id) {
        return null;
    }

    @Override
    public List<GoEntity> getAll() {
        return null;
    }

    @Override
    public GoEntity update(GoEntity goEntity) {
        return null;
    }

    @Override
    public GoEntity delete(GoEntity goEntity) {
        return null;
    }

    @Override
    public List<UserEntity> getDeclinedusers(long id) {
        return null;
    }

    @Override
    public List<UserEntity> getActiveUsers(long id) {
        return null;
    }

    @Override
    public List<UserEntity> getGoingUsers(long id) {
        return null;
    }

    @Override
    public List<GoEntity> getAllGosByUser(String uid) {
        return null;
    }

    @Override
    public List<GoEntity> getAllGosByGroup(long id) {
        return null;
    }

    @Override
    public List<GoEntity> getActiveGosByUser(String uid) {
        return null;
    }

    @Override
    public List<GoEntity> getActiveGosByGroup(long id) {
        return null;
    }


    @Override
    public void register(Observer observer) {

    }

    @Override
    public void unregister(Observer observer) {

    }

    @Override
    public void notify(GroupEntity groupEntity) {

    }

}
