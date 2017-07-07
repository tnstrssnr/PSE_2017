package edu.kit.pse17.go_app.PersistenceLayer.daos;

import edu.kit.pse17.go_app.PersistenceLayer.GroupEntity;
import edu.kit.pse17.go_app.PersistenceLayer.UserEntity;
import edu.kit.pse17.go_app.ServiceLayer.Observable;
import edu.kit.pse17.go_app.ServiceLayer.Observer;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;

/**
 * Diese Klasse implementiert die Interfaces UserDao, AbstractDao und Observable.
 *
 * Sie übernimmt die konkreten Datenbankzugriffe auf die Tabelle "users". Dazu werden alle Methoden aus den DAO Interfaces
 * entsprechend implementiert. Aufgerufen werden die Methoden dieser Klasse von den RestController-Klassen, wenn ein Client dem Server ein
 * Anfrage zur manipulation seiner Daten geschickt hat.
 *
 * Die Klasse gehört außerdem zu einer Implementierung des Beobachter-Entwurfsmusters und übernimmt dabei die rolle des Subjekts. Die Klasse hat eine Liste
 * von Beobachtern, die benachrichtigt werden, wenn sich in der Datenbank eine Änderung ergibt. Es ist die Verantwortung der Beobachter zu entscheiden, ob
 * die  Änderung eine Folgeaktion auslöst oder nicht.
 */

@Repository
@Transactional
public class UserDaoImp implements UserDao, AbstractDao<UserEntity, String>, Observable<UserEntity> {

    /**
     * Eine Sessionfactory, die Sessions bereitstellt. Die Sessions werden benötigt, damit die Klasse direkt mit der Datenbank
     * kommunizieren kann und dort die Änderungen vorhnehmen. Das Attribut ist mit "@Autowired" annotiert, damit es automatisch
     * mit einem gültigen Objekt instanziiert wird.
     *
     * Aud dieses Feld darf nur innerhalb dieser Klasse zugegriffen werden. nach der Instanzizerung ist diese Objekt unveränderbar und
     * bleibt bestehen, bis die Instanz dieser klasse wieder zerstört wird.
     */
    @Autowired
    SessionFactory sessionFactory;

    /**
     * Eine Liste mit Observern, die benachrichtigt werden, sobald eine Änderung an der Datenbank vorgenommen wird, die auch die Daten
     * anderer Benutzer betrifft.
     */
    private List<Observer> observer;


    @Override
    public void register(Observer observer) {

    }

    @Override
    public void unregister(Observer observer) {

    }

    @Override
    public void notify(UserEntity userEntity) {

    }

    /**
     * @param mail Die E-Mailadresse, anhand derer der Benutzer gesucht werden soll. Der String muss keinem besonderen Muster entsprechen,
     *             damit diese Methode fehlerfrei ausgeführt werden kann.
     * @return
     */
    @Override
    public UserEntity getUserByEmail(String mail) {
        return null;
    }

    /**
     * @param user Die Entity, die in die Datenbank eingefügt werden soll. Dieses Objekt muss eine in der Datenbank noch nicht
     */
    @Override
    public void addUser(UserEntity user) {

    }

    /**
     * @param userId Die userId des Benutzers, dessen Account gelöscht werden soll. Es wird garantiert, dass es sich beim Aufruf
     */
    @Override
    public void deleteUser(String userId) {

    }

    /**
     *
     * @param userId Die ID des Benutzers, dessen Gruppen zurückgegeben werden sollen. Es wird garantiert, dass es sich beim Aufruf der
     *               Methode um eine gültige userid handelt.
     * @return
     */
    @Override
    public List<GroupEntity> getGroups(String userId) {
        return null;
    }

    /**
     *
     * @param userId Die ID des Benutzers, dessen Gruppenanfragen zurückgegeben werden sollen. Es wird garantiert, dass es sich beim Aufruf der
     *               Methode um eine gültige userid handelt.
     * @return
     */
    @Override
    public List<GroupEntity> getRequests(String userId) {
        return null;
    }

    /**
     *
     * @param key Der Primärschlüssel der Entity, die aus der Datenbank geholt werden soll. Der Datentyp wird von dem Generic PK bestimmt,
     *            mit dem das Interface implementiert wird.
     * @return
     */
    @Override
    public UserEntity get(String key) {
        return null;
    }

    /**
     *
     * @param entity Das Entity-Objekt, das in der Datenbank gespeichert werden soll. Es wird garantiert, dass das Objekt, welches der Methode
     */
    @Override
    public void persist(UserEntity entity) {

    }

    /**
     *
     * @param key Der Primärschlüssel der Entity, die aus der Datenbanktabelle gelöscht werden soll. Der datentyp wird durch das Generic PK bei der
     *            Implementierung der Klasse spezifiziert.
     *
     * @throws EntityNotFoundException
     */
    @Override
    public void delete(String key) throws EntityNotFoundException {

    }

    /**
     *
     * @param userEntity
     * @throws EntityNotFoundException
     */
    @Override
    public void update(UserEntity userEntity) throws EntityNotFoundException {

    }
}
