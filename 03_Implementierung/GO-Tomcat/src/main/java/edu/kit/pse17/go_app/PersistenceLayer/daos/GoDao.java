package edu.kit.pse17.go_app.PersistenceLayer.daos;

import edu.kit.pse17.go_app.PersistenceLayer.GoEntity;
import edu.kit.pse17.go_app.PersistenceLayer.UserEntity;

import java.util.List;

/**
 * Bei diesem Interface handelt es sich um ein Interface für eine Data Access Object Klasse, die
 * die Datenbankzugriffe in sich kapselt.
 *
 * Die Methoden dieses Interfaces werden von dieser DAO Klasse implementiert und sind nach außen sichtbar.
 * Sie werden aufgerufen, von den RestController-Klassen, denn von dort werden die Server-Anfragen, die von Clients
 * gestellt werden, an die Persistence-Klassen weitergeleitet.
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
