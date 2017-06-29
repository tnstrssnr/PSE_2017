package edu.kit.pse17.go_app.PersistenceLayer.daos;

import edu.kit.pse17.go_app.PersistenceLayer.hibernateEntities.GoStatusEntity;

import java.util.List;

/**
 * Created by tina on 28.06.17.
 */

public class GoStatusEntityDaoImp implements CrudDao<GoStatusEntity>, GoStatusDao {


    @Override
    public GoStatusEntity create(GoStatusEntity goStatusEntity) {
        return null;
    }

    @Override
    public GoStatusEntity read(String id) {
        return null;
    }

    @Override
    public GoStatusEntity update(GoStatusEntity goStatusEntity) {
        return null;
    }

    @Override
    public GoStatusEntity delete(GoStatusEntity goStatusEntity) {
        return null;
    }

    @Override
    public List<GoStatusEntity> getAll() {
        return null;
    }

    @Override
    public List<GoStatusEntity> getActiveGosByUser(String userId) {
        return null;
    }

    @Override
    public List<GoStatusEntity> getStatusListForUsers(long goId) {
        return null;
    }

    @Override
    public List<GoStatusEntity> getStatusListForActiveUsers(long goId) {
        return null;
    }
}

