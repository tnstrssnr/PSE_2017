package edu.kit.pse17.go_app.PersistenceLayer.daos;

import edu.kit.pse17.go_app.PersistenceLayer.GroupEntity;
import edu.kit.pse17.go_app.PersistenceLayer.UserEntity;
import edu.kit.pse17.go_app.ServiceLayer.*;
import edu.kit.pse17.go_app.ServiceLayer.observer.Observer;
import org.hibernate.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
/**
 * Diese Klasse implementiert die Interfaces UserDao, AbstractDao und Observable.
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
public class GroupDaoImp implements AbstractDao<GroupEntity, Long>, GroupDao, Observable<Object> {

    /**
     * Eine Sessionfactory, die Sessions bereitstellt. Die Sessions werden benötigt, damit die Klasse direkt mit der
     * Datenbank kommunizieren kann und dort die Änderungen vorhnehmen. Das Attribut ist mit "@Autowired" annotiert,
     * damit es automatisch mit einem gültigen Objekt instanziiert wird.
     * <p>
     * Auch dieses Feld darf nur innerhalb dieser Klasse zugegriffen werden. nach der Instanzizerung ist diese Objekt
     * unveränderbar und bleibt bestehen, bis die Instanz dieser klasse wieder zerstört wird.
     * <p>
     * Diese Klasse implementiert darüber hinaus das Interface Observable. Das heißt die Klasse besitzt Beobachter, die
     * bei Ändeurngen des Datenbestands benachrichtigt werden müssen. Als Teil des Beobachter-Entwurfsmusters übernimmt
     * diese Klasse die Rolle des konkreten Subjekts.
     */
    private final SessionFactory sf;

    /**
     * Eine Liste mit Observern, die benachrichtigt werden, sobald eine Änderung an der Datenbank vorgenommen wird, die
     * auch die Daten anderer Benutzer betrifft.
     */
    private final List<Observer> observer;

    /**
     * Ein Konstruktor der keine Argumente entgegennimmt. In dem Konstruktor wird eine Instanz von SessionFactory
     * erzeugt, anhand der Spezifikationen der verwendetetn MySQL Datenbank.
     */
    public GroupDaoImp(final SessionFactory sf) {
        this.sf = sf;
        this.observer = new ArrayList<>();
    }

    public SessionFactory getSf() {
        return sf;
    }

    public List<Observer> getObserver() {
        return observer;
    }

    /**
     * @param observer der Observer, der registriert werden soll. Dabei spielt es keine Rolle, um welche Implementierung
     *                 eines
     */
    @Override
    public void register(final Observer observer) {
        this.observer.add(observer);
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
    public void notify(final String impCode, final Observable observable, final Object o) {
        for (final Observer observer : this.observer) {
        }
    }

    /**
     * @param key Der Primärschlüssel der Entity, die aus der Datenbank geholt werden soll. Der Datentyp wird von dem
     *            Generic PK bestimmt, mit dem das Interface implementiert wird.
     * @return Die GroupEntity, mit der ID "key". Existiert keine Entity mit dieser ID, gibt die Methode null zurück.
     */
    @Override
    @Transactional
    public GroupEntity get(final Long key) {
        Transaction tx = null;
        GroupEntity group = null;

        try (Session session = sf.openSession()) {
            tx = session.beginTransaction();
            group = session.get(GroupEntity.class, key);
            Hibernate.initialize(group.getAdmins());
            Hibernate.initialize(group.getGos());
            Hibernate.initialize(group.getMembers());
            Hibernate.initialize(group.getRequests());
            tx.commit();
        } catch (final HibernateException e) {
            handleHibernateException(e, tx);
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
    public void persist(final GroupEntity entity) {
        Transaction tx = null;
        final Long id;

        try (Session session = sf.openSession()) {
            tx = session.beginTransaction();
            id = (Long) session.save(entity);
            tx.commit();
            entity.setID(id);
        } catch (final HibernateException e) {
            handleHibernateException(e, tx);
        }
    }

    /**
     * @param key Der Primärschlüssel der Entity, die aus der Datenbanktabelle gelöscht werden soll. Der datentyp wird
     *            durch das Generic PK bei der Implementierung der Klasse spezifiziert.
     */
    @Override
    public void delete(final Long key) {
        Transaction tx = null;
        final GroupEntity group = get(key);

        try (Session session = sf.openSession()) {
            tx = session.beginTransaction();
            session.delete(group);
            tx.commit();

        } catch (final HibernateException e) {
            handleHibernateException(e, tx);
        }
    }

    /**
     * @param groupEntity Die GroupEntity, die sich verändert hat. Das Objekt enthält alle Attribute, nicht nur die, die
     *                    sich verändret haben.
     */
    @Override
    public void update(final GroupEntity groupEntity) {
        Transaction tx = null;
        //final GroupEntity oldData;

        try (Session session = sf.openSession()) {
            tx = session.beginTransaction();

            session.update(groupEntity);
            tx.commit();


        } catch (final HibernateException e) {
            handleHibernateException(e, tx);
        }
    }

    @Override
    public void addGroupMember(final String userId, final long groupId) {
        Transaction tx = null;
        final GroupEntity group;
        final UserEntity user = new UserDaoImp(sf).get(userId);

        try (Session session = sf.openSession()) {
            tx = session.beginTransaction();
            group = session.get(GroupEntity.class, groupId);
            if (!group.getMembers().contains(user)) {
                group.getMembers().add(user);
            }
            tx.commit();


        } catch (final HibernateException e) {
            handleHibernateException(e, tx);
        }
    }

    @Override
    public void removeGroupRequest(final String userId, final long groupId) {
        Transaction tx = null;
        final GroupEntity group;
        final UserEntity user = new UserDaoImp(sf).get(userId);

        try (Session session = sf.openSession()) {
            tx = session.beginTransaction();
            group = session.get(GroupEntity.class, groupId);
            if (group.getRequests().contains(user)) {
                group.getRequests().remove(user);
            }
            tx.commit();
            //notify??
        } catch (final HibernateException e) {
            handleHibernateException(e, tx);
        }
    }

    @Override
    public void addGroupRequest(final String userId, final long groupId) {
        Transaction tx = null;
        final GroupEntity group;
        final UserEntity user = new UserDaoImp(sf).get(userId);

        try (Session session = sf.openSession()) {
            tx = session.beginTransaction();
            group = session.get(GroupEntity.class, groupId);
            if (!group.getRequests().contains(user)) {
                group.getRequests().add(user);
            }
            tx.commit();

        } catch (final HibernateException e) {
            handleHibernateException(e, tx);
        }
    }

    @Override
    public void removeGroupMember(final String userId, final long groupId) {
        Transaction tx = null;
        final GroupEntity group;
        final UserEntity user = new UserDaoImp(sf).get(userId);

        try (Session session = sf.openSession()) {
            tx = session.beginTransaction();
            group = session.get(GroupEntity.class, groupId);
            if (group.getMembers().contains(user)) {
                group.getMembers().remove(user);
            }
            tx.commit();
        } catch (final HibernateException e) {
            handleHibernateException(e, tx);
        }
    }

    @Override
    public void addAdmin(final String userId, final String groupId) {
        Transaction tx = null;
        final GroupEntity group;
        final UserEntity user = new UserDaoImp(sf).get(userId);

        try (Session session = sf.openSession()) {
            tx = session.beginTransaction();
            group = session.get(GroupEntity.class, groupId);
            if (group.getMembers().contains(user) && !group.getAdmins().contains(user)) {
                group.getAdmins().add(user);
            }
            tx.commit();
        } catch (final HibernateException e) {
            handleHibernateException(e, tx);
        }
    }
}
