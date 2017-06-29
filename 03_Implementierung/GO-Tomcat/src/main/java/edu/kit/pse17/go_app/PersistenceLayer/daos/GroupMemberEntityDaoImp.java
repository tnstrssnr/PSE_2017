package edu.kit.pse17.go_app.PersistenceLayer.daos;

import edu.kit.pse17.go_app.PersistenceLayer.hibernateEntities.GroupEntity;
import edu.kit.pse17.go_app.PersistenceLayer.hibernateEntities.GroupMemberEntity;
import edu.kit.pse17.go_app.PersistenceLayer.hibernateEntities.UserEntity;

import java.util.List;

/**
 * Created by tina on 28.06.17.
 */
public class GroupMemberEntityDaoImp implements CrudDao<GroupMemberEntity>, GroupMemberDao {


    @Override
    public GroupMemberEntity create(GroupMemberEntity groupMemberEntity) {
        return null;
    }

    @Override
    public GroupMemberEntity read(String id) {
        return null;
    }

    @Override
    public GroupMemberEntity update(GroupMemberEntity groupMemberEntity) {
        return null;
    }

    @Override
    public GroupMemberEntity delete(GroupMemberEntity groupMemberEntity) {
        return null;
    }

    @Override
    public List<GroupMemberEntity> getAll() {
        return null;
    }

    @Override
    public List<GroupEntity> getGroupsByUser(String userId) {
        return null;
    }

    @Override
    public List<UserEntity> getGroupAdmins(long groupId) {
        return null;
    }

    @Override
    public List<GroupEntity> getGrouprequestsByUser(String userid) {
        return null;
    }

    @Override
    public List<UserEntity> getPendingGroupmembers(long groupId) {
        return null;
    }
}
