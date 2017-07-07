package edu.kit.pse17.go_app.PersistenceLayer.daos;

import edu.kit.pse17.go_app.PersistenceLayer.GoEntity;
import edu.kit.pse17.go_app.PersistenceLayer.Status;
import edu.kit.pse17.go_app.PersistenceLayer.UserEntity;
import edu.kit.pse17.go_app.ServiceLayer.Observable;
import edu.kit.pse17.go_app.ServiceLayer.Observer;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityNotFoundException;
import java.io.Serializable;
import java.util.List;

/**
 * Created by tina on 30.06.17.
 */
public class GoDaoImp implements AbstractDao<GoEntity, Long>, GoDao, Observable<GoEntity> {

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
     *
     * @param observer
     */
    @Override
    public void register(Observer observer) {

    }

    /**
     *
     * @param observer
     */
    @Override
    public void unregister(Observer observer) {

    }

    /**
     *
     * @param goEntity
     */
    @Override
    public void notify(GoEntity goEntity) {

    }

    /**
     *
     * @param key Der Primärschlüssel der Entity, die aus der Datenbank geholt werden soll. Der Datentyp wird von dem Generic PK bestimmt,
     *            mit dem das Interface implementiert wird.
     * @return
     */
    @Override
    public GoEntity get(Long key) {
        return null;
    }

    /**
     *
     * @param entity Das Entity-Objekt, das in der Datenbank gespeichert werden soll. Es wird garantiert, dass das Objekt, welches der Methode
     */
    @Override
    public void persist(GoEntity entity) {

    }

    /**
     *
     * @param key Der Primärschlüssel der Entity, die aus der Datenbanktabelle gelöscht werden soll. Der datentyp wird durch das Generic PK bei der
     *            Implementierung der Klasse spezifiziert.
     *
     * @throws EntityNotFoundException
     */
    @Override
    public void delete(Long key) throws EntityNotFoundException {

    }

    /**
     *
     * @param goEntity
     * @throws EntityNotFoundException
     */
    @Override
    public void update(GoEntity goEntity) throws EntityNotFoundException {

    }

    /**
     *
     * @param userId Die ID des Benutzers, dessen Teilnahmestatus geändert werden soll. Dabei handelt es sich um eine gültige Id, ansonsten kann die Methode
     *                nicht erfolgreich ausgeführt werden.
     * @param goId Die des GOs, für den der Teilnahmestatus geändert werden soll. Dabei handelt es sich um eine gültige Id, ansonsten kann die Methode
     *                nicht erfolgreich ausgeführt werden.
     * @param status Der neue Status des Benutzers.
     */
    @Override
    public void changeStatus(String userId, long goId, Status status) {

    }
}
