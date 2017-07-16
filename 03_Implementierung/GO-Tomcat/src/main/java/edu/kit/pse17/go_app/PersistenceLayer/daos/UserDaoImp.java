package edu.kit.pse17.go_app.PersistenceLayer.daos;

import edu.kit.pse17.go_app.PersistenceLayer.GroupEntity;
import edu.kit.pse17.go_app.PersistenceLayer.UserEntity;
import edu.kit.pse17.go_app.ServiceLayer.Observable;
import edu.kit.pse17.go_app.ServiceLayer.Observer;
import org.hibernate.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Diese Klasse implementiert die Interfaces UserDao, AbstractDao und Observable.
 * <p>
 * Sie übernimmt die konkreten Datenbankzugriffe auf die Tabelle "users". Dazu werden alle Methoden aus den DAO
 * Interfaces entsprechend implementiert. Aufgerufen werden die Methoden dieser Klasse von den RestController-Klassen,
 * wenn ein Client dem Server ein Anfrage zur manipulation seiner Daten geschickt hat.
 * <p>
 * Die Klasse gehört außerdem zu einer Implementierung des Beobachter-Entwurfsmusters und übernimmt dabei die rolle des
 * Subjekts. Die Klasse hat eine Liste von Beobachtern, die benachrichtigt werden, wenn sich in der Datenbank eine
 * Änderung ergibt. Es ist die Verantwortung der Beobachter zu entscheiden, ob die  Änderung eine Folgeaktion auslöst
 * oder nicht.
 */

@Repository
@Transactional
public class UserDaoImp implements UserDao, AbstractDao<UserEntity, String>, Observable<UserEntity> {

    /**
     * Eine Sessionfactory, die Sessions bereitstellt. Die Sessions werden benötigt, damit die Klasse direkt mit der
     * Datenbank kommunizieren kann und dort die Änderungen vorhnehmen. Das Attribut ist mit "@Autowired" annotiert,
     * damit es automatisch mit einem gültigen Objekt instanziiert wird.
     * <p>
     * Aud dieses Feld darf nur innerhalb dieser Klasse zugegriffen werden. nach der Instanzizerung ist diese Objekt
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

    public UserDaoImp(final SessionFactory sf) {
        this.sf = sf;
        this.observer = new ArrayList<>();
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
     * @param userEntity Die veränderte UserEntity wird zur Weiterverarbeitung an den Observer übergeben.
     */
    @Override
    public void notify(final String impCode, final Observable observable, final UserEntity userEntity) {
        for (final Observer observer : this.observer) {
            observer.update(impCode, observable, userEntity);
        }
    }


    /**
     * @param mail Die E-Mailadresse, anhand derer der Benutzer gesucht werden soll. Der String muss keinem besonderen
     *             Muster entsprechen, damit diese Methode fehlerfrei ausgeführt werden kann.
     * @return Das gefundene UserEntity_objekt aus der Datenbank. Wurde kein passender user gefunden gibt diese Methode
     * null zurück.
     */
    @Override
    public UserEntity getUserByEmail(final String mail) {
        List<UserEntity> user = null;
        final String hql = "FROM UserEntity WHERE UserEntity.email = :mail";

        try (Session session = sf.openSession()) {
            final Query query = session.createQuery(hql);
            query.setParameter("mail", mail);
            user = (List<UserEntity>) query.list();
        } catch (final HibernateException e) {
            handleHibernateException(e, null);
        }

        return (user != null) ? user.get(0) : null;
    }

    /**
     * @param userId Die ID des Benutzers, dessen Gruppen zurückgegeben werden sollen. Es wird garantiert, dass es sich
     *               beim Aufruf der Methode um eine gültige userid handelt.
     * @return Ein Set mit allen Gruppen, in denen der Benutzer Mitglied ist.
     */
    @Override
    public Set<GroupEntity> getGroups(final String userId) {
        final UserEntity user = get(userId);
        return user.getGroups();
    }

    /**
     * @param userId Die ID des Benutzers, dessen Gruppenanfragen zurückgegeben werden sollen. Es wird garantiert, dass
     *               es sich beim Aufruf der Methode um eine gültige userid handelt.
     * @return Ein Set mit allen Gruppen zu denen der Benutzer mit der ID "userId" eine Gruppenanfrage bekommen hat. In
     * keinen dieser Gruppen ist der Benutzer bereits Mitglied.
     */
    @Override
    public Set<GroupEntity> getRequests(final String userId) {
        final UserEntity user = get(userId);
        return user.getRequests();
    }

    /**
     * @param key Der Primärschlüssel der Entity, die aus der Datenbank geholt werden soll. Der Datentyp wird von dem
     *            Generic PK bestimmt, mit dem das Interface implementiert wird.
     * @return Der Benutzer mit der ID "key". Gibt null zurück, falls kein passender Benutzer in der Datenbank gefunden
     * wurde.
     */
    @Override
    public UserEntity get(final String key) {
        Transaction tx = null;
        UserEntity user = null;

        try (Session session = sf.openSession()) {
            tx = session.beginTransaction();
            user = session.get(UserEntity.class, key);
            tx.commit();
        } catch (final HibernateException e) {
            handleHibernateException(e, tx);
        }
        return user;
    }

    /**
     * @param entity Das Entity-Objekt, das in der Datenbank gespeichert werden soll. Es wird garantiert, dass das
     *               Objekt, welches der Methode
     */
    @Override
    public void persist(final UserEntity entity) {
        Transaction tx = null;

        try (Session session = sf.openSession()) {
            tx = session.beginTransaction();
            session.save(entity);
            tx.commit();
        } catch (final HibernateException e) {
            handleHibernateException(e, tx);
        }
    }

    /**
     * @param key Der Primärschlüssel der Entity, die aus der Datenbanktabelle gelöscht werden soll. Der Datentyp wird
     *            durch das Generic PK bei der Implementierung der Klasse spezifiziert.
     */
    @Override
    public void delete(final String key) {
        Transaction tx = null;

        final UserEntity user = get(key);

        try (Session session = sf.openSession()) {
            tx = session.beginTransaction();
            session.delete(user);
            tx.commit();
        } catch (final HibernateException e) {
            handleHibernateException(e, tx);
        }
    }

    /**
     * @param userEntity Die Entity des Users, der geändert werden soll. Dabei muss es sich um eine vorhandene Entity
     *                   handeln, ansonsten schlägt ide Ausführung der Methode fehl.
     */
    @Override
    public void update(final UserEntity userEntity) {
        Transaction tx = null;

        try (Session session = sf.openSession()) {
            tx = session.beginTransaction();
            final UserEntity oldUser = (UserEntity) session.get(userEntity.getUid(), UserEntity.class);
            oldUser.setEmail(userEntity.getEmail());
            oldUser.setInstanceId(userEntity.getInstanceId());
            oldUser.setName(userEntity.getName());

            session.update(oldUser);
            tx.commit();
        } catch (final HibernateException e) {
            handleHibernateException(e, tx);
        }
    }

    private void handleHibernateException(final HibernateException e, final Transaction tx) {
        e.printStackTrace();
        if (tx != null) {
            tx.rollback();
        }
    }


}
