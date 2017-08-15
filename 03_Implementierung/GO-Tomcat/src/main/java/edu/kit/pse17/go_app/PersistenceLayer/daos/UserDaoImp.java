package edu.kit.pse17.go_app.PersistenceLayer.daos;

import edu.kit.pse17.go_app.PersistenceLayer.GroupEntity;
import edu.kit.pse17.go_app.PersistenceLayer.UserEntity;
import org.hibernate.*;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * Diese Klasse implementiert die Interfaces UserDao, AbstractDao und IObservable.
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
public class UserDaoImp implements UserDao, AbstractDao<UserEntity, String> {

    /**
     * Eine Sessionfactory, die Sessions bereitstellt. Die Sessions werden benötigt, damit die Klasse direkt mit der
     * Datenbank kommunizieren kann und dort die Änderungen vorhnehmen. Das Attribut ist mit "@Autowired" annotiert,
     * damit es automatisch mit einem gültigen Objekt instanziiert wird.
     * <p>
     * Aud dieses Feld darf nur innerhalb dieser Klasse zugegriffen werden. nach der Instanzizerung ist diese Objekt
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

    public UserDaoImp(final SessionFactory sf) {
        this.sf = sf;
    }

    public UserDaoImp() {
    }

    /**
     * @param mail Die E-Mailadresse, anhand derer der Benutzer gesucht werden soll. Der String muss keinem besonderen
     *             Muster entsprechen, damit diese Methode fehlerfrei ausgeführt werden kann.
     * @return Das gefundene UserEtity_objekt aus der Datenbank. Wurde kein passender user gefunden gibt diese Methode
     * null zurück.
     */
    @Override
    public UserEntity getUserByEmail(final String mail) {
        Session session = null;
        List<UserEntity> user = null;
        final String sql = "SELECT * FROM USERS WHERE email = :mail ;";

        try {
            session = sf.openSession();
            final SQLQuery query = session.createSQLQuery(sql);
            query.addEntity(UserEntity.class);
            query.setParameter("mail", mail);
            user = query.list();
            System.out.println(user.size());
            Hibernate.initialize(user);
        } catch (final HibernateException e) {
            handleHibernateException(e, null);
        } finally {
            if (session != null) {
                session.close();
            }
        }

        return (user.size() != 0) ? user.get(0) : null;
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
        Session session;
        UserEntity user = null;

        try {
            session = sf.openSession();
            tx = session.beginTransaction();
            user = (UserEntity) session.get(UserEntity.class, key);
            Hibernate.initialize(user.getGroups());
            for (final GroupEntity group : user.getGroups()) {
                Hibernate.initialize(group.getMembers());
            }
            Hibernate.initialize(user.getGos());
            Hibernate.initialize(user.getRequests());
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
    public String persist(final UserEntity entity) {
        Session session = null;
        Transaction tx = null;
        String id = null;

        try {
            session = sf.openSession();
            tx = session.beginTransaction();
            id = (String) session.save(entity);
            tx.commit();
        } catch (final ConstraintViolationException c) {
            throw c;
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
     * @param key Der Primärschlüssel der Entity, die aus der Datenbanktabelle gelöscht werden soll. Der Datentyp wird
     *            durch das Generic PK bei der Implementierung der Klasse spezifiziert.
     */
    @Override
    public void delete(final String key) {
        Transaction tx = null;
        Session session = null;

        try {
            session = sf.openSession();
            tx = session.beginTransaction();
            final UserEntity user = (UserEntity) session.get(UserEntity.class, key);
            session.delete(user);
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
     * @param userEntity Die Entity des Users, der geändert werden soll. Dabei muss es sich um eine vorhandene Entity
     *                   handeln, ansonsten schlägt ide Ausführung der Methode fehl.
     */
    @Override
    public void update(final UserEntity userEntity) {
        Transaction tx = null;
        Session session = null;

        try {
            session = sf.openSession();
            tx = session.beginTransaction();
            final UserEntity oldUser = (UserEntity) session.get(UserEntity.class, userEntity.getUid());
            oldUser.setInstanceId(userEntity.getInstanceId());
            oldUser.setName(userEntity.getName());
            session.update(oldUser);
            tx.commit();
        } catch (final HibernateException e) {
            handleHibernateException(e, tx);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    private void handleHibernateException(final HibernateException e, final Transaction tx) {
        e.printStackTrace();
        if (tx != null) {
            tx.rollback();
        }
    }

}
