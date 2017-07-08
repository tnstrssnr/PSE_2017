package edu.kit.pse17.go_app.PersistenceLayer.daos;

import edu.kit.pse17.go_app.PersistenceLayer.GoEntity;
import edu.kit.pse17.go_app.PersistenceLayer.Status;
import edu.kit.pse17.go_app.ServiceLayer.Observable;
import edu.kit.pse17.go_app.ServiceLayer.Observer;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityNotFoundException;

/**
 * Created by tina on 30.06.17.
 */
public class GoDaoImp implements AbstractDao<GoEntity, Long>, GoDao, Observable<GoEntity> {

    /**
     * Eine Sessionfactory, die Sessions bereitstellt. Die Sessions werden benötigt, damit die Klasse direkt mit der Datenbank
     * kommunizieren kann und dort die Änderungen vorhnehmen. Das Attribut ist mit "@Autowired" annotiert, damit es automatisch
     * mit einem gültigen Objekt instanziiert wird.
     *
     * Auf dieses Feld darf nur innerhalb dieser Klasse zugegriffen werden. nach der Instanziierung ist diese Objekt unveränderbar und
     * bleibt bestehen, bis die Instanz dieser klasse wieder zerstört wird.
     *
     * Diese Klasse implementiert darüber hinaus das Interface Observable. Das heißt die Klasse besitzt Beobachter, die bei Ändeurngen des Datenbestands
     * benachrichtigt werden müssen. Als Teil des Beobachter-Entwurfsmusters übernimmt diese Klasse die Rolle des konkreten Subjekts.
     */
    @Autowired
    SessionFactory sessionFactory;


    /**
     *
     * @param observer  der Observer, der registriert werden soll. Dabei spielt es keine Rolle, um welche Implementierung eines
     */
    @Override
    public void register(Observer observer) {

    }

    /**
     *
     * @param observer Der Observer der aus der Liste entfernt werden soll. es muss vor dem Aufruf dieser Methode sichergestellt werden, dass
     */
    @Override
    public void unregister(Observer observer) {

    }

    /**
     *
     * @param impCode Ein Code, der angibt, welche Observer-Implementierung benachrichtigt werden soll. dabei handelt es sich immer um ein
     *                öffentliches statisches Attribut in der Observer-Klasse. Handelt es sich um keinen gültigen Implementierungs-Code, wird
     *                kein Observer auf das notify() reagieren.
     * @param observable Eine Instanz des Observables, das die notify()-Methode aufgerufen hat. Durch diese Referenz weiß der observer, von wo er eine
     *                   Benachrichtigung bekommen hat.
     * @param goEntity Das Go an dem Änderungen vorgenommen wurden
     */
    @Override
    public void notify(String impCode, Observable observable, GoEntity goEntity) {

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
