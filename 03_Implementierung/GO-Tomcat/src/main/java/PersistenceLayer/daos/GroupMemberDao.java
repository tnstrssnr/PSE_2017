package PersistenceLayer.daos;

import PersistenceLayer.hibernateEntities.GroupEntity;
import PersistenceLayer.hibernateEntities.GroupMemberEntity;
import PersistenceLayer.hibernateEntities.UserEntity;

import java.util.List;

/**
 * DAO-Interface für DB-Operationen die auf der Relation "groupMembers" ausgeführt werden
 *
 * Created by tina on 29.06.17.
 */
public interface GroupMemberDao {

    /**
     * gibt eine Liste aller Gruppen zurück, in den ein User Mitglied ist (ohne Mitgliedschaftsanfragen!)
     *
     * @param userId ID des Users, dessen Gruppen ermittelt werden sollen
     * @return Liste von Gruppen-Entities, in denen der User Mitglied ist
     */
    public List<GroupEntity> getGroupsByUser(String userId);

    /**
     * Gibt eine Liste aller User zurück, die Administratoren einer Gruppe sind
     *
     * @param groupId ID der Gruppe, deren Admins ermittelt werden sollen
     * @return Liste von Benutzern, die Administratoren sind
     */
    public List<UserEntity> getGroupAdmins(long groupId);

    /**
     * Gibt eine Liste aller Gruppen zurück, zu denen ein Benutzer offenstehende Gruppenanfragen hat
     *
     * @param userid ID des Users, dessen Mitgliedschaftsanfragen ermittelt werden sollen
     * @return Liste von Gruppen-Entities, zu denen der User offene Anfrage hat
     */
    public List<GroupEntity> getGroupRequestsByUser(String userid);

    /**
     * Gibt eine Liste aller Benutzer zurück, die eine offene Gruppenanfrage zur spezifizierten Gruppe haben.
     *
     * @param groupId ID der Gruppe, zu der offene Mitgliedschaftsanfragen ermittelt werden sollen
     * @return Lsite von Usern, die eine offene Gruppenanfrage haben
     */
    public List<UserEntity> getPendingGroupmembers(long groupId);

}
