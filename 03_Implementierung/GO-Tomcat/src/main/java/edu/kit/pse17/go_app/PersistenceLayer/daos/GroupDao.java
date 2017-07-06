package edu.kit.pse17.go_app.PersistenceLayer.daos;

import edu.kit.pse17.go_app.PersistenceLayer.GroupEntity;
import edu.kit.pse17.go_app.PersistenceLayer.UserEntity;

import java.util.List;

/**
 * Created by tina on 30.06.17.
 */
public interface GroupDao {

    public List<UserEntity> getAdmins(long id);

    public List<UserEntity> getRequests(long id);

    public List<GroupEntity> getGroupsByUser(String uid);

    public List<GroupEntity> getRequestsbyUser(String uid);

    public void deleteGroup();

    public void kickMember();

}
