package PersistenceLayer.daos;

import PersistenceLayer.hibernateEntities.GoEntity;

import java.util.List;

/**
 * DAO-interface für DB-Operationen, die auf der GO-Relation ausgeführt werden sollen
 *
 * Created by tina on 29.06.17.
 */
public interface GoDao {

    /**
     * Gibt eine Liste aller GOs zurück, die zu der selben Gruppe gehören
     *
     * @param groupId ID der Gruppe, deren GOs gesucht wernde
     * @return Liste der GOs der Gruppe
     */
    public List<GoEntity> getGosByGroup(long groupId);

    /**
     * Gibt eine Liste aller GOs zurpck, die zu einem User gehören (also die zu Gruppen gehören, in denen der Benutzer Mitglied ist)
     *
     * @param userId Id des Users, dessen GOs gesucht werden
     * @return Liste der GOs des Users
     */
    public List<GoEntity> getGOsByUser(String userId);

}
