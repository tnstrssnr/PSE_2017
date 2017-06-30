package edu.kit.pse17.go_app.PersistenceLayer.daos;

import edu.kit.pse17.go_app.PersistenceLayer.GoEntity;
import edu.kit.pse17.go_app.PersistenceLayer.UserEntity;

import java.util.List;

/**
 * Created by tina on 30.06.17.
 */
public interface GoDao {

    public List<UserEntity> getDeclinedUsers(long id);

    public List<UserEntity> getActiveUsers(long id);

    public List<UserEntity> getGoingUsers(long id);

    public List<GoEntity> getAllGosByUser(String uid);

    public List<GoEntity> getAllGosByGroup(long id);

    public List<GoEntity> getActiveGosByUser(String uid);

    public List<GoEntity> getActiveGosByGroup(long id);

    public void deleteGo();

    public void kickUser(String userId);
}
