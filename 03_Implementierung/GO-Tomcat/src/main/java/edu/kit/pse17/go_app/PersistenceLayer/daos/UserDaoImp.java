package edu.kit.pse17.go_app.PersistenceLayer.daos;

import edu.kit.pse17.go_app.PersistenceLayer.UserEntity;
import edu.kit.pse17.go_app.ServiceLayer.Observable;
import edu.kit.pse17.go_app.ServiceLayer.Observer;

import java.io.Serializable;
import java.util.List;

/**
 * Created by tina on 30.06.17.
 */
public class UserDaoImp extends AbstractDao<String, UserEntity> implements UserDao, Serializable, Observable<UserEntity> {

    private List<Observer> observer;

    @Override
    public UserEntity getUserByEmail(String mail) {
        return null;
    }

    @Override
    public UserEntity create(UserEntity userEntity) {
        return null;
    }

    @Override
    public UserEntity getById(String id) {
        return null;
    }

    @Override
    public List<UserEntity> getAll() {
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
    public void register(Observer observer) {

    }

    @Override
    public void unregister(Observer observer) {

    }

    @Override
    public void notify(UserEntity userEntity) {

    }
}
