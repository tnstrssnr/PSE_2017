package edu.kit.pse17.go_app.PersistenceLayer.daos;

import edu.kit.pse17.go_app.PersistenceLayer.GoEntity;
import edu.kit.pse17.go_app.PersistenceLayer.UserEntity;
import edu.kit.pse17.go_app.ServiceLayer.Observable;
import edu.kit.pse17.go_app.ServiceLayer.Observer;

import java.io.Serializable;
import java.util.List;

/**
 * Created by tina on 30.06.17.
 */
public class GoDaoImp extends AbstractDao<Long, GoEntity> implements GoDao, Serializable, Observable<GoEntity> {

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
    public List<UserEntity> getDeclinedUsers(long id) {
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
    public void deleteGo() {

    }

    @Override
    public void kickUser(String userId) {

    }

    @Override
    public void register(Observer observer) {

    }

    @Override
    public void unregister(Observer observer) {

    }

    @Override
    public void notify(GoEntity goEntity) {

    }

    public class GoEventArg {

        public static final String GO_CREATED = "go_created";
        public static final String GO_DATA_CHANGED = "go_data_changed";
        public static final String GO_DELETED = "go_deleted";
        public static final String GO_STATUS_CHANGED = "go_status_changed";

    }
}
