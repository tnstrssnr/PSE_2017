package edu.kit.pse17.go_app.PersistenceLayer.daos;

import edu.kit.pse17.go_app.PersistenceLayer.UserEntity;

/**
 * Created by tina on 30.06.17.
 */
public interface UserDao {

    public UserEntity getUserByEmail(String mail);

}
