package edu.kit.pse17.go_app.PersistenceLayer.daos;

import edu.kit.pse17.go_app.PersistenceLayer.GoEntity;
import edu.kit.pse17.go_app.PersistenceLayer.Status;
import edu.kit.pse17.go_app.PersistenceLayer.UserEntity;

import java.util.List;

/**
 * Bei diesem Interface handelt es sich um ein Interface für eine Data Access Object Klasse, die
 * die Datenbankzugriffe in sich kapselt.
 *
 * Die Methoden dieses Interfaces werden von dieser DAO Klasse implementiert und sind nach außen sichtbar.
 * Sie werden aufgerufen, von den RestController-Klassen, denn von dort werden die Server-Anfragen, die von Clients
 * gestellt werden, an die Persistence-Klassen weitergeleitet.
 */
public interface GoDao {

    /**
     * Mit dieser Methode wird der Teilnahmestatus eines GO-Teilnehnmers geändert. Der Status kann entweder "ABGELEHNT", "BESTÄTIGT",
     * oder "UNTERWEGS" lauten.
     *
     * Vor dem Aufruf der Methode muss sichergestellt werden, dass es sich bei dem Benutzer um ein Mitglied der Gruppe handelt und die Statusänderung
     * die vorgenommen werden soll legal ist.
     *
     * @param userId Die ID des Benutzers, dessen Teilnahmestatus geändert werden soll. Dabei handelt es sich um eine gültige Id, ansonsten kann die Methode
     *                nicht erfolgreich ausgeführt werden.
     * @param goId Die des GOs, für den der Teilnahmestatus geändert werden soll. Dabei handelt es sich um eine gültige Id, ansonsten kann die Methode
     *                nicht erfolgreich ausgeführt werden.
     * @param status Der neue Status des Benutzers.
     */
    public void changeStatus(String userId, long goId, Status status);


}
