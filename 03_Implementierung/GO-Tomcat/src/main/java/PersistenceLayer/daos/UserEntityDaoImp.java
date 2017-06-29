package PersistenceLayer.daos;

import PersistenceLayer.hibernateEntities.UserEntity;

import java.util.List;

/**
 * Created by tina on 28.06.17.
 */
public class UserEntityDaoImp implements CrudDao<UserEntity>, UserDao {


    @Override
    public UserEntity create(UserEntity userEntity) {
        return null;
    }

    @Override
    public UserEntity read(String id) {
        return null;
    }

    @Override
    public UserEntity update(UserEntity userEntity) {
        return null;
    }

    @Override
    public UserEntity delete(UserEntity userEntity) {
        return null;
    }

    @Override
    public List<UserEntity> getAll() {
        return null;
    }
}
