package PersistenceLayer.daos;

import PersistenceLayer.hibernateEntities.GoEntity;

import java.util.List;

/**
 * Created by tina on 28.06.17.
 */
public class GoEntityDaoImp implements CrudDao<GoEntity>, GoDao {


    @Override
    public GoEntity create(GoEntity goEntity) {
        return null;
    }

    @Override
    public GoEntity read(String id) {
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
    public List<GoEntity> getAll() {
        return null;
    }

    @Override
    public List<GoEntity> getGosByGroup(long groupId) {
        return null;
    }

    @Override
    public List<GoEntity> getGOsByUser(String userId) {
        return null;
    }
}
