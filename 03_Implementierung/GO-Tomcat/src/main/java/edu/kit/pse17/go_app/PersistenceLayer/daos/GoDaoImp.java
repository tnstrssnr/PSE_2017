package edu.kit.pse17.go_app.PersistenceLayer.daos;

import edu.kit.pse17.go_app.ClientCommunication.Downstream.EventArg;
import edu.kit.pse17.go_app.PersistenceLayer.GoEntity;
import edu.kit.pse17.go_app.PersistenceLayer.GroupEntity;
import edu.kit.pse17.go_app.PersistenceLayer.Status;
import edu.kit.pse17.go_app.PersistenceLayer.UserEntity;
import edu.kit.pse17.go_app.ServiceLayer.IObservable;
import edu.kit.pse17.go_app.ServiceLayer.observer.*;
import org.hibernate.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.HashSet;

@Repository
public class GoDaoImp implements AbstractDao<GoEntity, Long>, GoDao, IObservable<GoEntity> {

    /**
     * Eine Sessionfactory, die Sessions bereitstellt. Die Sessions werden benötigt, damit die Klasse direkt mit der
     * Datenbank kommunizieren kann und dort die Änderungen vorhnehmen. Das Attribut ist mit "@Autowired" annotiert,
     * damit es automatisch mit einem gültigen Objekt instanziiert wird.
     * <p>
     * Auf dieses Feld darf nur innerhalb dieser Klasse zugegriffen werden. nach der Instanziierung ist diese Objekt
     * unveränderbar und bleibt bestehen, bis die Instanz dieser klasse wieder zerstört wird.
     * <p>
     * Diese Klasse implementiert darüber hinaus das Interface IObservable. Das heißt die Klasse besitzt Beobachter, die
     * bei Ändeurngen des Datenbestands benachrichtigt werden müssen. Als Teil des Beobachter-Entwurfsmusters übernimmt
     * diese Klasse die Rolle des konkreten Subjekts.
     */

    @Autowired
    private SessionFactory sf;

    /**
     * Eine Liste mit Observern, die benachrichtigt werden, sobald eine Änderung an der Datenbank vorgenommen wird, die
     * auch die Daten anderer Benutzer betrifft.
     */
    private HashMap<EventArg, Observer> observer;

    /**
     * Ein Konstruktor der keine Argumente entgegennimmt. In dem Konstruktor wird eine Instanz von SessionFactory
     * erzeugt, anhand der Spezifikationen der verwendetetn MySQL Datenbank.
     */
    public GoDaoImp(final SessionFactory sf) {
        this.observer = new HashMap<>();
        register(EventArg.GO_EDITED_COMMAND, new GoEditedObserver(this));
        register(EventArg.GO_REMOVED_EVENT, new GoRemovedObserver(this));
        register(EventArg.STATUS_CHANGED_COMMAND, new StatusChangedObserver(this));
        register(EventArg.GO_ADDED_EVENT, new GoAddedObserver(this));
        this.sf = sf;
    }

    public SessionFactory getSessionFactory() {
        return this.sf;
    }

    /**
     * @param observer der Observer, der registriert werden soll. Dabei spielt es keine Rolle, um welche Implementierung
     *                 eines
     */
    @Override
    public void register(EventArg arg, Observer observer) {
        this.observer.put(arg, observer);

    }

    /**
     * @param observer Der Observer der aus der Liste entfernt werden soll. es muss vor dem Aufruf dieser Methode
     *                 sichergestellt werden, dass
     */
    @Override
    public void unregister(Observer observer) {
        this.observer.remove(observer);
    }

    /**
     * @param impCode    Ein Code, der angibt, welche Observer-Implementierung benachrichtigt werden soll. dabei handelt
     *                   es sich immer um ein öffentliches statisches Attribut in der Observer-Klasse. Handelt es sich
     *                   um keinen gültigen Implementierungs-Code, wird kein Observer auf das notify() reagieren.
     * @param observable Eine Instanz des Observables, das die notify()-Methode aufgerufen hat. Durch diese Referenz
     *                   weiß der observer, von wo er eine Benachrichtigung bekommen hat.
     * @param goEntity   Das Go an dem Änderungen vorgenommen wurden
     */
    @Override
    public void notify(String impCode, IObservable observable, GoEntity goEntity) {

    }

    /**
     * @param key Der Primärschlüssel der Entity, die aus der Datenbank geholt werden soll. Der Datentyp wird von dem
     *            Generic PK bestimmt, mit dem das Interface implementiert wird.
     * @return Die GoEntity mit dem entsprechenden Schlüssel. Exisitert eine solche Entity nicht, wird null
     * zurückgegeben.
     */
    @Override
    public GoEntity get(Long key) {
        Transaction tx = null;
        Session session;
        GoEntity go = null;

        try {
            session = sf.openSession();
            tx = session.beginTransaction();
            go = (GoEntity) session.get(GoEntity.class, key);
            Hibernate.initialize(go.getGoingUsers());
            Hibernate.initialize(go.getGoneUsers());
            Hibernate.initialize(go.getNotGoingUsers());
            tx.commit();
        } catch (HibernateException e) {
            handleHibernateException(e, tx);
        }
        return go;
    }

    private void handleHibernateException(HibernateException e, Transaction tx) {
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
    public Long persist(GoEntity entity) {
        Transaction tx = null;
        Session session = null;
        long id = -1;

        String ownerId = entity.getOwner().getUid();
        Long groupId = entity.getGroup().getID();

        //set all contained objects to null in order to reset them later w/ the perissted objects from the database
        entity.setGroup(null);
        entity.setGoneUsers(new HashSet<>());
        entity.setGoingUsers(new HashSet<>());
        entity.setNotGoingUsers(new HashSet<>());
        entity.setOwner(null);

        try {
            session = sf.openSession();
            tx = session.beginTransaction();

            UserEntity owner = (UserEntity) session.get(UserEntity.class, ownerId);
            GroupEntity group = (GroupEntity) session.get(GroupEntity.class, groupId);

            //set owner and group
            entity.setOwner(owner);
            entity.setGroup(group);

            //add all group members to status lists
            for (UserEntity member : group.getMembers()) {
                if (member.equals(owner)) {
                    entity.getGoingUsers().add(member);
                } else {
                    entity.getNotGoingUsers().add(member);
                }
            }

            id = (long) session.save(entity);
            tx.commit();
        } catch (HibernateException e) {
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
    public void delete(Long key) {
        Transaction tx = null;
        Session session = null;

        try {
            session = sf.openSession();
            tx = session.beginTransaction();
            GoEntity go = (GoEntity) session.get(GoEntity.class, key);

            //remove associated entites, to avoid them being deleted
            go.setGroup(null);
            go.setOwner(null);
            go.setNotGoingUsers(null);
            go.setGoingUsers(null);
            go.setGoneUsers(null);

            session.delete(go);
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
     * @param goEntity Die GoEntity, die geupdated werden soll. Das Objekt enthält bereits die neuen Daten.
     */
    @Override
    public void update(GoEntity goEntity) {
        Transaction tx = null;
        Session session = null;

        try {
            session = sf.openSession();
            tx = session.beginTransaction();

            GoEntity persistedOBject = (GoEntity) session.get(GoEntity.class, goEntity.getID());

            persistedOBject.setName(goEntity.getName());
            persistedOBject.setEnd(goEntity.getEnd());
            persistedOBject.setStart(goEntity.getStart());
            persistedOBject.setLat(goEntity.getLat());
            persistedOBject.setLon(goEntity.getLon());
            persistedOBject.setDescription(goEntity.getDescription());

            session.update(persistedOBject);
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
     * @param userId Die ID des Benutzers, dessen Teilnahmestatus geändert werden soll. Dabei handelt es sich um eine
     *               gültige Id, ansonsten kann die Methode nicht erfolgreich ausgeführt werden.
     * @param goId   Die des GOs, für den der Teilnahmestatus geändert werden soll. Dabei handelt es sich um eine
     *               gültige Id, ansonsten kann die Methode nicht erfolgreich ausgeführt werden.
     * @param status Der neue Status des Benutzers.
     */
    @Override
    public void changeStatus(String userId, long goId, Status status) {
        Transaction tx = null;
        Session session;

        try {
            session = sf.openSession();
            tx = session.beginTransaction();

            UserEntity user = (UserEntity) session.get(UserEntity.class, userId);
            GoEntity go = (GoEntity) session.get(GoEntity.class, goId);

            go.getGoingUsers().remove(user);
            go.getNotGoingUsers().remove(user);
            go.getGoneUsers().remove(user);

            switch (status) {
                case ABGELEHNT:
                    go.getNotGoingUsers().add(user);
                    break;
                case BESTÄTIGT:
                    go.getGoingUsers().add(user);
                    break;
                default:
                    go.getGoneUsers().add(user);
                    break;
            }

            session.update(go);
            tx.commit();
        } catch (HibernateException e) {
            handleHibernateException(e, tx);
        }
    }

    public void onGroupMemberAdded(String userId, long groupId) {
        Transaction tx = null;
        Session session = null;

        try {
            if (sf == null) {
                System.out.println("Check");
            }
            session = sf.openSession();
            tx = session.beginTransaction();
            UserEntity user = (UserEntity) session.get(UserEntity.class, userId);
            GroupEntity group = (GroupEntity) session.get(GroupEntity.class, groupId);
            for (GoEntity go : group.getGos()) {
                go.getNotGoingUsers().add(user);
            }
            tx.commit();
        } catch (HibernateException e) {
            handleHibernateException(e, tx);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
