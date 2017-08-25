package edu.kit.pse17.go_app.PersistenceLayer.daos;

import edu.kit.pse17.go_app.PersistenceLayer.GoEntity;
import edu.kit.pse17.go_app.PersistenceLayer.GroupEntity;
import edu.kit.pse17.go_app.PersistenceLayer.UserEntity;
import org.hibernate.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


/**
 * Diese Klasse implementiert die Interfaces UserDao, AbstractDao und IObservable.
 * <p>
 * Sie übernimmt die konkreten Datenbankzugriffe auf die Tabelle "groups". Dazu werden alle Methoden aus den DAO
 * Interfaces entsprechend implementiert. Aufgerufen werden die Methoden dieser Klasse von den RestController-Klassen,
 * wenn ein Client dem Server ein Anfrage zur Manipulation seiner Daten geschickt hat.
 * <p>
 * Die Klasse gehört außerdem zu einer Implementierung des Beobachter-Entwurfsmusters und übernimmt dabei die Rolle des
 * Subjekts. Die Klasse hat eine Liste von Beobachtern, die benachrichtigt werden, wenn sich in der Datenbank eine
 * Änderung ergibt. Es ist die Verantwortung der Beobachter zu entscheiden, ob die  Änderung eine Folgeaktion auslöst
 * oder nicht.
 */
@Repository
public class GroupDaoImp implements AbstractDao<GroupEntity, Long>, GroupDao {

    private static final String SQL_GOS_BY_USER_AND_GROUP = "SELECT * FROM GOS WHERE owner = :userId AND GOS.group_id = :groupId ;";

    private static final String SQL_GOS_BY_GROUP = "SELECT * FROM GOS WHERE GOS.group_id = :groupId ;";

    /**
     * Eine Sessionfactory, die Sessions bereitstellt. Die Sessions werden benötigt, damit die Klasse direkt mit der
     * Datenbank kommunizieren kann und dort die Änderungen vornehmen. Das Attribut ist mit "@Autowired" annotiert,
     * damit es automatisch mit einem gültigen Objekt instanziiert wird.
     * <p>
     * Auch dieses Feld darf nur innerhalb dieser Klasse zugegriffen werden. nach der Instanziierung ist dieses Objekt
     * unveränderbar und bleibt bestehen, bis die Instanz dieser Klasse wieder zerstört wird.
     * <p>
     * Diese Klasse implementiert darüber hinaus das Interface IObservable. Das heißt die Klasse besitzt Beobachter, die
     * bei Ändeurngen des Datenbestands benachrichtigt werden müssen. Als Teil des Beobachter-Entwurfsmusters übernimmt
     * diese Klasse die Rolle des konkreten Subjekts.
     */
    @Autowired
    private SessionFactory sf;

    @Autowired
    private GoDaoImp goDao;

    /**
     * Ein Konstruktor der keine Argumente entgegennimmt. In dem Konstruktor wird eine Instanz von SessionFactory
     * erzeugt, anhand der Spezifikationen der verwendetetn MySQL Datenbank.
     */
    public GroupDaoImp() {

    }

    public GroupDaoImp(SessionFactory sf) {
        this.sf = sf;
    }

    public SessionFactory getSf() {
        return sf;
    }

    /**
     * @param key Der Primärschlüssel der Entity, die aus der Datenbank geholt werden soll. Der Datentyp wird von dem
     *            Generic PK bestimmt, mit dem das Interface implementiert wird.
     * @return Die GroupEntity, mit der Id "key". Existiert keine Entity mit dieser ID, gibt die Methode null zurück.
     */
    @Override
    public GroupEntity get(final Long key) {
        Transaction tx = null;
        Session session = null;
        GroupEntity group = null;

        try {
            session = sf.openSession();
            tx = session.beginTransaction();
            group = (GroupEntity) session.get(GroupEntity.class, key);

            //initialze all Sets of the object to avoid lazy-loading errors
            Hibernate.initialize(group.getAdmins());
            Hibernate.initialize(group.getGos());
            Hibernate.initialize(group.getMembers());
            Hibernate.initialize(group.getRequests());
            tx.commit();
        } catch (final HibernateException e) {
            handleHibernateException(e, tx);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return group;
    }

    private void handleHibernateException(final HibernateException e, final Transaction tx) {
        e.printStackTrace();
        if (tx != null) {
            tx.rollback();
        }
    }

    /**
     * Dieser Methodenaufruf  wird dazu genutzt um eine GroupEntity in der Datenbank zu speichern.
     *
     * @param entity Das Entity-Objekt, das in der Datenbank gespeichert werden soll. Es wird garantiert, dass das
     *               Objekt, welches der Methode gegeben wird ein legales Objekt ist.
     */
    @Override
    public Long persist(GroupEntity entity) {
        Transaction tx = null;
        Long id = -1l;
        Session session = null;

        //when group is created, there is only 1 member --> the creator of the group
        UserEntity creator = (UserEntity) entity.getMembers().toArray()[0];

        //clear all sets from the object, otherwise hibernate will throw NonUniqueObjectException.
        // GOs and Requests are null or empty by default
        entity.setAdmins(new HashSet<>());
        entity.setMembers(new HashSet<>());

        try {
            session = sf.openSession();
            tx = session.beginTransaction();

            //get the creator-object from the database, otherwise hibernate will not recognize that this object is persisted already and will throw an exception
            UserEntity persistedCreator = (UserEntity) session.get(UserEntity.class, creator.getUid());
            entity.getMembers().add(persistedCreator);
            entity.getAdmins().add(persistedCreator);
            id = (Long) session.save(entity);
            tx.commit();
            entity.setID(id);
        } catch (final HibernateException e) {
            handleHibernateException(e, tx);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return id;
    }

    /**
     * Diese Methode wird zum löschen einer Entity aus der Datenbank genutzt.
     *
     * @param key Der Primärschlüssel der Entity, die aus der Datenbanktabelle gelöscht werden soll. Der Datentyp wird
     *            durch das Generic PK bei der Implementierung der Klasse spezifiziert.
     */
    @Override
    public void delete(final Long key) {
        deleteGosForGroup(key);
        Transaction tx = null;
        Session session = null;

        try {
            session = sf.openSession();
            tx = session.beginTransaction();
            GroupEntity group = (GroupEntity) session.get(GroupEntity.class, key);

            //remove all associations w/ UserEntity objects
            group.getMembers().clear();
            group.getRequests().clear();
            group.getAdmins().clear();
            group.getGos().clear();

            session.delete(group);

            tx.commit();

        } catch (final HibernateException e) {
            handleHibernateException(e, tx);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    private void deleteGosForGroup(long groupId) {
        Transaction tx = null;
        Session session = null;
        List<GoEntity> gos = new ArrayList<>();

        try {
            session = sf.openSession();
            tx = session.beginTransaction();

            final SQLQuery query = session.createSQLQuery(SQL_GOS_BY_GROUP);
            query.addEntity(GoEntity.class);
            query.setParameter("groupId", groupId);
            gos = (List<GoEntity>) query.list();
            Hibernate.initialize(gos);
        } catch (HibernateException e) {
            handleHibernateException(e, tx);
        }

        for (GoEntity go : gos) {
            goDao.delete(go.getID());
        }
    }

    /**
     * Diese Methode wird aufgerufen wenn eine GroupEntity mit neuen Daten geupdatet wurde.
     *
     * @param groupEntity Die GroupEntity, die sich verändert hat. Das Objekt enthält alle Attribute, nicht nur die, die
     *                    sich verändret haben.
     */
    @Override
    public void update(final GroupEntity groupEntity) {
        Transaction tx = null;
        Session session = null;
        GroupEntity oldData;

        try {
            session = sf.openSession();
            tx = session.beginTransaction();

            //get object that is already persisted --> needs to be retrieved and updated in the same session
            oldData = (GroupEntity) session.get(GroupEntity.class, groupEntity.getID());

            //change data
            oldData.setName(groupEntity.getName());
            oldData.setDescription(groupEntity.getDescription());

            session.update(oldData);
            tx.commit();


        } catch (final HibernateException e) {
            handleHibernateException(e, tx);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    /**
     * Diese Methode wird aufgerufen wenn ein Nutzer einer Gruppe hinzugefügt wird.
     * Ein neu hinzugefügter User erhält automatisch den Status "NOT_GOING".
     *
     * @param userId  Die UserId des Users der hinzugefügt wurde.
     * @param groupId Die Id der Gruppe, die er hinzugefügt wurde.
     */

    @Override
    public void addGroupMember(final String userId, final long groupId) {
        Transaction tx = null;
        final GroupEntity group;
        Session session = null;

        try {
            session = sf.openSession();
            tx = session.beginTransaction();
            UserEntity user = (UserEntity) session.get(UserEntity.class, userId);
            group = (GroupEntity) session.get(GroupEntity.class, groupId);

            /**
             * Entfernt User von Requests.
             * Es ist wichtig keine separaten removeGroupRequest-Methoden zu verwenden, da dass zu inkonsistenten Daten führen kann.
             */
            if (group.getRequests().contains(user)) {
                group.getRequests().remove(user);
            }

            if (!group.getMembers().contains(user)) {
                group.getMembers().add(user);
            }

            /**
             * Fügt Gos User hinzu - "NOT_GOING" by Default.
             */
            new GoDaoImp(this.sf).onGroupMemberAdded(user.getUid(), group.getID(), session);
            tx.commit();


        } catch (final HibernateException e) {
            handleHibernateException(e, tx);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    /**
     * Diese Methode entfernt Gruppenanfragen eines Nutzers im Bezug zu einer bestimmten Guppe.
     *
     * @param userId  Die Id des Benutzers. Dabei handelt es sich um eine gültige Id, ansonsten kann die Methode nicht
     *                erfolgreich ausgeführt werden.
     * @param groupId Die Id der Gruppe. Dabei handelt es sich um eine gültige Id, ansonsten kann die Methode nicht
     *                erfolgreich ausgeführt werden.
     */

    @Override
    public void removeGroupRequest(final String userId, final long groupId) {
        Session session = null;
        Transaction tx = null;
        GroupEntity group;
        UserEntity user;

        try {
            session = sf.openSession();
            tx = session.beginTransaction();

            user = (UserEntity) session.get(UserEntity.class, userId);
            group = (GroupEntity) session.get(GroupEntity.class, groupId);

            group.getRequests().remove(user);

            tx.commit();
        } catch (HibernateException e) {
            handleHibernateException(e, tx);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    private void deleteOwnedGos(String userId, Long groupId) {
        Transaction tx = null;
        Session session = null;
        List<GoEntity> ownedGos = new ArrayList<>();

        try {
            session = sf.openSession();
            tx = session.beginTransaction();

            final SQLQuery query = session.createSQLQuery(SQL_GOS_BY_USER_AND_GROUP);
            query.addEntity(GoEntity.class);
            query.setParameter("userId", userId);
            query.setParameter("groupId", groupId);
            ownedGos = (List<GoEntity>) query.list();
            Hibernate.initialize(ownedGos);

            tx.commit();
        } catch (final HibernateException e) {
            handleHibernateException(e, tx);
        } finally {
            if (session != null) {
                session.close();
            }
        }

        for (GoEntity go : ownedGos) {
            goDao.delete(go.getID());
        }
    }

    /**
     * Diese Methode fügt eine Gruppenanfrage in Bezug zwischen einem Nutzer und einer Gruppe hinzu.
     *
     * @param userId  Die Id des Benutzers, der eingeladen war. Dabei handelt es sich um eine gültige Id, ansonsten kann
     *                die Methode nicht erfolgreich ausgeführt werden.
     * @param groupId Die Id der Gruppe, zu die der Benutzer eingeladen war. Dabei handelt es sich um eine gültige Id,
     *                ansonsten kann die Methode nicht erfolgreich ausgeführt werden.
     */

    @Override
    public void addGroupRequest(final String userId, final long groupId) {
        Transaction tx = null;
        Session session = null;
        final GroupEntity group;
        final UserEntity user;

        try {
            session = sf.openSession();
            tx = session.beginTransaction();

            user = (UserEntity) session.get(UserEntity.class, userId);
            group = (GroupEntity) session.get(GroupEntity.class, groupId);

            if (!group.getRequests().contains(user)) {
                group.getRequests().add(user);
            }
            tx.commit();

        } catch (final HibernateException e) {
            handleHibernateException(e, tx);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    /**
     * Diese Methode entfernt einen Nutzer von einer Gruppe. Falls der gelöschte Nutzer der einzige Admin der Gruppe
     * ist, wird die Gruppe ebenfalls gelöscht.
     * <p>
     * Die Methode geht davon aus, dass der gelöschte Benutzer kein Administrator der Gruppe ist. Da es momentan in
     * jeder Gruppe nur einen Admin gibt, wird bei Entfernen des Admins die Methode deleteGroup aufgerufen.
     *
     * @param userId  Die Id des Benutzers, der aus der Gruppe entfernt werden soll. Dabei handelt es sich um eine
     *                gültige Id, ansonsten kann die Methode nicht erfolgreich ausgeführt werden.
     * @param groupId Die Id der Gruppe, aus der der Benutzer entfernt werden soll. Dabei handelt es sich um eine
     *                gültige Id, ansonsten kann die Methode nicht erfolgreich ausgeführt werden.
     */

    @Override
    public void removeGroupMember(final String userId, final long groupId) {
        deleteOwnedGos(userId, groupId);

        Session session = null;
        Transaction tx = null;
        GroupEntity group;
        UserEntity user;

        try {
            session = sf.openSession();
            tx = session.beginTransaction();

            group = (GroupEntity) session.get(GroupEntity.class, groupId);
            user = (UserEntity) session.get(UserEntity.class, userId);

            for (GoEntity go : group.getGos()) {
                go.getGoneUsers().remove(user);
                go.getGoingUsers().remove(user);
                go.getNotGoingUsers().remove(user);
            }
            group.getMembers().remove(user);

            tx.commit();
        } catch (HibernateException e) {
            handleHibernateException(e, tx);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    /**
     * Diese Methode fügt einen Admin einer Gruppe hinzu. Dies Funktion kann nur von einem anderen Gruppenadmin genutzt
     * werden.
     *
     * @param userId  Die Id des Benutzers, der als Administrator hinzugefügt werden soll. Dabei handelt es sich um eine
     *                gültige Id, ansonsten kann die Methode nicht erfolgreich ausgeführt werden.
     * @param groupId Die Id der Gruppe, zu der der Administrator hinzugefügt werden soll. Dabei handelt es sich um eine
     *                gültige Id, ansonsten kann die Methode nicht erfolgreich ausgeführt werden.
     */

    @Override
    public void addAdmin(final String userId, final Long groupId) {
        Transaction tx = null;
        Session session = null;
        final GroupEntity group;
        final UserEntity user;

        try {
            session = sf.openSession();
            tx = session.beginTransaction();
            /**
             * Holt persistente Objekte.
             */
            user = (UserEntity) session.get(UserEntity.class, userId);
            group = (GroupEntity) session.get(GroupEntity.class, groupId);

            /**
             * Fügt einen neuen Admin hinzu.
             */
            if (group.getMembers().contains(user) && !group.getAdmins().contains(user)) {
                group.getAdmins().add(user);
            }
            tx.commit();
        } catch (final HibernateException e) {
            handleHibernateException(e, tx);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
