package edu.kit.pse17.go_app.PersistenceLayer.daos;

import edu.kit.pse17.go_app.ClientCommunication.Downstream.EventArg;
import edu.kit.pse17.go_app.PersistenceLayer.GroupEntity;
import edu.kit.pse17.go_app.PersistenceLayer.UserEntity;
import edu.kit.pse17.go_app.ServiceLayer.IObservable;
import edu.kit.pse17.go_app.ServiceLayer.observer.*;
import org.hibernate.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.HashSet;


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
public class GroupDaoImp implements AbstractDao<GroupEntity, Long>, GroupDao, IObservable<Object> {

    /**
     * Eine Liste mit Observern, die benachrichtigt werden, sobald eine Änderung an der Datenbank vorgenommen wird, die
     * auch die Daten anderer Benutzer betrifft.
     */
    private final HashMap<EventArg, Observer> observer;
    /**
     * Eine Sessionfactory, die Sessions bereitstellt. Die Sessions werden benötigt, damit die Klasse direkt mit der
     * Datenbank kommunizieren kann und dort die Änderungen vorhnehmen. Das Attribut ist mit "@Autowired" annotiert,
     * damit es automatisch mit einem gültigen Objekt instanziiert wird.
     * <p>
     * Auch dieses Feld darf nur innerhalb dieser Klasse zugegriffen werden. nach der Instanzizerung ist diese Objekt
     * unveränderbar und bleibt bestehen, bis die Instanz dieser klasse wieder zerstört wird.
     * <p>
     * Diese Klasse implementiert darüber hinaus das Interface IObservable. Das heißt die Klasse besitzt Beobachter, die
     * bei Ändeurngen des Datenbestands benachrichtigt werden müssen. Als Teil des Beobachter-Entwurfsmusters übernimmt
     * diese Klasse die Rolle des konkreten Subjekts.
     */
    @Autowired
    private SessionFactory sf;

    /**
     * Ein Konstruktor der keine Argumente entgegennimmt. In dem Konstruktor wird eine Instanz von SessionFactory
     * erzeugt, anhand der Spezifikationen der verwendetetn MySQL Datenbank.
     */
    public GroupDaoImp() {
        this.observer = new HashMap<>();
        register(EventArg.ADMIN_ADDED_EVENT, new AdminAddedObserver(this));
        register(EventArg.GROUP_EDITED_COMMAND, new GroupEditedObserver(this));
        register(EventArg.GROUP_REMOVED_EVENT, new GroupRemovedObserver(this));
        register(EventArg.GROUP_REQUEST_RECEIVED_EVENT, new GroupRequestReceivedObserver(this));
        register(EventArg.MEMBER_ADDED_EVENT, new MemberAddedObserver(this));
        register(EventArg.MEMBER_REMOVED_EVENT, new MemberRemovedObserver(this));
    }

    public SessionFactory getSf() {
        return sf;
    }

    public HashMap<EventArg, Observer> getObserver() {
        return observer;
    }

    /**
     * @param observer der Observer, der registriert werden soll. Dabei spielt es keine Rolle, um welche Implementierung
     *                 eines
     */

    @Override
    public void register(final EventArg arg, final Observer observer) {
        this.observer.put(arg, observer);
    }

    /**
     * @param observer Der Observer der aus der Liste entfernt werden soll. es muss vor dem Aufruf dieser Methode
     *                 sichergestellt werden, dass
     */
    @Override
    public void unregister(final Observer observer) {
        this.observer.remove(observer);
    }

    /**
     * @param impCode    Ein Code, der angibt, welche Observer-Implementierung benachrichtigt werden soll. dabei handelt
     *                   es sich immer um ein öffentliches statisches Attribut in der Observer-Klasse. Handelt es sich
     *                   um keinen gültigen Implementierungs-Code, wird kein Observer auf das notify() reagieren.
     * @param observable Eine Instanz des Observables, das die notify()-Methode aufgerufen hat. Durch diese Referenz
     *                   weiß der observer, von wo er eine Benachrichtigung bekommen hat.
     * @param o          Die veränderte GroupEntity, die zur Weiterverarbeitung an die Observer weitergereicht wird.
     */
    @Override
    public void notify(final String impCode, final IObservable observable, final Object o) {

    }

    /**
     * @param key Der Primärschlüssel der Entity, die aus der Datenbank geholt werden soll. Der Datentyp wird von dem
     *            Generic PK bestimmt, mit dem das Interface implementiert wird.
     * @return Die GroupEntity, mit der ID "key". Existiert keine Entity mit dieser ID, gibt die Methode null zurück.
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
     * @param entity Das Entity-Objekt, das in der Datenbank gespeichert werden soll. Es wird garantiert, dass das
     *               Objekt, welches der Methode
     */
    @Override
    public Long persist(GroupEntity entity) {
        Transaction tx = null;
        Long id = -1l;
        Session session = null;

        //when group is created, there is only 1 member --> the creator of the group
        UserEntity creator = (UserEntity) entity.getMembers().toArray()[0];

        //clear all sets from the object, otherwise hibernate will throw NonUniqueObjectException.
        // GOs and Requests are null by default
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
     * @param key Der Primärschlüssel der Entity, die aus der Datenbanktabelle gelöscht werden soll. Der datentyp wird
     *            durch das Generic PK bei der Implementierung der Klasse spezifiziert.
     */
    @Override
    public void delete(final Long key) {
        Transaction tx = null;
        Session session = null;

        try {
            session = sf.openSession();
            tx = session.beginTransaction();
            GroupEntity group = (GroupEntity) session.get(GroupEntity.class, key);
            group.setMembers(null);
            group.setRequests(null);
            group.setAdmins(null);
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

    /**
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
            if (!group.getMembers().contains(user)) {
                group.getMembers().add(user);
            }

            //adds users status to gos -- is 'not going' by default
            new GoDaoImp(this.getSf()).onGroupMemberAdded(user.getUid(), group.getID());
            tx.commit();


        } catch (final HibernateException e) {
            handleHibernateException(e, tx);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void removeGroupRequest(final String userId, final long groupId) {
        Transaction tx = null;
        Session session = null;
        final GroupEntity group;
        final UserEntity user;

        try {
            session = sf.openSession();
            tx = session.beginTransaction();
            user = (UserEntity) session.get(UserEntity.class, userId);
            group = (GroupEntity) session.get(GroupEntity.class, groupId);

            if (group.getRequests().contains(user)) {
                group.getRequests().remove(user);
            }
            tx.commit();
            //notify??
        } catch (final HibernateException e) {
            handleHibernateException(e, tx);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

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

    @Override
    public void removeGroupMember(final String userId, final long groupId) {
        Transaction tx = null;
        Session session = null;
        final GroupEntity group;
        final UserEntity user;

        try {
            session = sf.openSession();
            tx = session.beginTransaction();

            user = (UserEntity) session.get(UserEntity.class, userId);
            group = (GroupEntity) session.get(GroupEntity.class, groupId);

            if (group.getMembers().contains(user)) {
                group.getMembers().remove(user);
            }
            if (group.getAdmins().contains(user)) {
                group.getAdmins().remove(user);
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

    @Override
    public void addAdmin(final String userId, final Long groupId) {
        Transaction tx = null;
        Session session = null;
        final GroupEntity group;
        final UserEntity user;

        try {
            session = sf.openSession();
            tx = session.beginTransaction();

            //retrieve persisted objects
            user = (UserEntity) session.get(UserEntity.class, userId);
            group = (GroupEntity) session.get(GroupEntity.class, groupId);


            //add the new admin to the list
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
