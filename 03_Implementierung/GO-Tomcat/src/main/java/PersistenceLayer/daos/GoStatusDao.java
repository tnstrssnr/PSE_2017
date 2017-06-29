package PersistenceLayer.daos;

import PersistenceLayer.hibernateEntities.GoEntity;
import PersistenceLayer.hibernateEntities.GoStatusEntity;

import java.util.List;

/**
 * DAO-interface für DB-Operationen, die auf der GoStatus-relation ausgeführt werden.
 *
 * Created by tina on 29.06.17.
 */
public interface GoStatusDao {

    /**
     * gibt eine Liste aller aktiven GOs eines. Users zurück. Ein Go ist aktiv, wenn der Startzeitpunkt bereits eingetreten ist,
     * der Endzeitpunkt jedoch noch nicht
     *
     * @param userId ID des users, dessen aktive GOs gesucht werden
     * @return Liste der akitven GOs
     */
    public List<GoEntity> getActiveGosByUser(String userId);

    /**
     * Gibt eine Liste aller GOStatus-Tupel zurück, die zu einem bestimmten GO gehören.
     *
     * @param goId ID des GOs, dessen Status-Liste gesucht wird
     * @return Liste von GoStatus Entities
     */
    public List<GoStatusEntity> getStatusListForUsers(long goId);


    /**
     * gibt eine Liste aller aktiven Benutzer in einem GO zurück.
     * Ein Benutzer ist aktiv, wenn der Teilnahmestatus entweder "BESTÄTIGT" oder "UNTERWEGS" lautet.
     *
     * @param goId ID des GOs, dessen aktive User gesucht werden
     * @return Liste der aktiven User
     */
    public List<GoStatusEntity> getActiveUsers(long goId);

}
