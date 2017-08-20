package edu.kit.pse17.go_app.PersistenceLayer.daos;

import edu.kit.pse17.go_app.ClientCommunication.Downstream.EventArg;
import edu.kit.pse17.go_app.PersistenceLayer.GoEntity;
import edu.kit.pse17.go_app.PersistenceLayer.GroupEntity;
import edu.kit.pse17.go_app.PersistenceLayer.Status;
import edu.kit.pse17.go_app.PersistenceLayer.UserEntity;
import edu.kit.pse17.go_app.ServiceLayer.observer.Observer;
import org.hibernate.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.HashSet;

@Repository
public class GoDaoImp implements AbstractDao<GoEntity, Long>, GoDao {

    /**
     * Eine Sessionfactory, die Sessions bereitstellt. Die Sessions werden benötigt, damit die Klasse direkt mit der
     * Datenbank kommunizieren kann und dort die Änderungen vorhnehmen. Das Attribut ist mit "@Autowired" annotiert,
     * damit es automatisch mit einem gültigen Objekt instanziiert wird.
     * <p>
     * Auf dieses Feld darf nur innerhalb dieser Klasse zugegriffen werden. nach der Instanziierung ist dieses Objekt
     * unveränderbar und bleibt bestehen, bis die Instanz dieser Klasse wieder zerstört wird.
     * <p>
     * Diese Klasse implementiert darüber hinaus das Interface IObservable. Mit anderen Worten: die Klasse besitzt Beobachter, die
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
    public GoDaoImp() {
        this.observer = new HashMap<>();
    }

    public GoDaoImp(SessionFactory sf) {
        this.observer = new HashMap<>();
        this.sf = sf;
    }

    public SessionFactory getSessionFactory() {
        return this.sf;
    }


    /**
     * Diese Methode wird dazu genutzt um eine GoEntity aus der Datenbank zu holen.
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
     * Dieser Methodenaufruf  wird dazu genutzt um eine GoEntity in der Datenbank zu speichern.
     * @param entity Das Entity-Objekt, das in der Datenbank gespeichert werden soll. Es wird garantiert, dass das
     *               Objekt, welches der Methode gegeben wird ein legales Objekt ist.
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
     * Diese Methode wird zum löschen einer Entity aus der Datenbank genutzt.
     * @param key Der Primärschlüssel der Entity, die aus der Datenbanktabelle gelöscht werden soll. Der Datentyp wird
     *            durch das Generic PK bei der Implementierung der Klasse spezifiziert.
     */
    @Override
    public void delete(Long key) {
        Transaction tx = null;
        Session session = null;

        try {
            session = sf.openSession();
            tx = session.beginTransaction();
            onDeleteGo(key, session);
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
     * Diese Methode wird aufgerufen mit der delete(Go)-Methode und dient der Beseitigung sämtlicher Entities die ncoh Bezug
     * auf das gelöschte Objekt haben, da sonst Hibernate eine ObjectDeletedException werfen könnte.
     * @param key Der Primärschlüssel der Entity die gelöscht wird.
     * @param session Session der GoEntity.
     */

    public void onDeleteGo(Long key, Session session) {
        GoEntity go = (GoEntity) session.get(GoEntity.class, key);

        //remove associated entities, otherwise hibernate will throw ObjectDeletedException
        go.setGroup(null);
        go.setOwner(null);
        go.getNotGoingUsers().clear();
        go.getGoingUsers().clear();
        go.getGoneUsers().clear();
        session.delete(go);
    }

    /**
     * Diese Methode wird aufgerufen wenn eine GoEntity mit neuen Daten geupdatet werden soll.
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
     * Diese Methode wird aufgerufen wenn der Status eines Users in einem Go geändert werden soll. ("GOING","NOT_GOING","GONE")
     *
     * @param userId Die Id des Benutzers, dessen Teilnahmestatus geändert werden soll. Dabei handelt es sich um eine
     *               gültige Id, ansonsten kann die Methode nicht erfolgreich ausgeführt werden.
     * @param goId   Die Id des GOs, für den der Teilnahmestatus geändert werden soll. Dabei handelt es sich um eine
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
                case NOT_GOING:
                    go.getNotGoingUsers().add(user);
                    break;
                case GOING:
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

    /**
     * Diese Methode wird aufgerufen während ein Nutzer einer Gruppe hinzugefügt wird. Dabei wird er automatisch als
     * "NOT_GOING" eingestuft. Fügt den Nutzer dem aktiven Go hinzu.
     * @param userId Die UserId des Users der hinzugefügt wurde.
     * @param groupId Die Id der Gruppe, die er hinzugefügt wurde.
     * @param session Die Session der Go-Entity.
     */

    public void onGroupMemberAdded(String userId, long groupId, Session session) {

        UserEntity user = (UserEntity) session.get(UserEntity.class, userId);
        GroupEntity group = (GroupEntity) session.get(GroupEntity.class, groupId);

        for (GoEntity go : group.getGos()) {
            go.getNotGoingUsers().add(user);
        }

    }

    /**
     * Diese Methode wird aufgerufen während ein Gruppenmitglied der Gruppe entfernt ird. Falls der entfernte Nutzer der Besitzer,
     * beziehungsweise der Go-Verantwortliche war wird das Go gelöscht. Entfernt den Nutzer dem aktiven Go der Gruppe.
     * @param userId Die UserId des zu löschenden Users.
     * @param groupId Die Id der Gruppe, aus die der User gelöscht
     * @param session Die Session der Go-Entity.
     */

    public void onGroupMemberRemoved(String userId, Long groupId, Session session) {

        UserEntity user = (UserEntity) session.get(UserEntity.class, userId);
        GroupEntity group = (GroupEntity) session.get(GroupEntity.class, groupId);

        for (GoEntity go : group.getGos()) {

            if (go.getOwner().equals(user)) {
                //User is Go-Owner --> delete Go
                go.setGroup(null);
                go.setOwner(null);
                go.getNotGoingUsers().clear();
                go.getGoneUsers().clear();
                go.getGoingUsers().clear();
                session.delete(go);

            } else {
                //remove User from Go
                go.getNotGoingUsers().remove(user);
                go.getGoingUsers().remove(user);
                go.getGoneUsers().remove(user);
            }
        }

    }
}
