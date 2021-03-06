package edu.kit.pse17.go_app.PersistenceLayer.daos;

import edu.kit.pse17.go_app.PersistenceLayer.GoEntity;
import edu.kit.pse17.go_app.PersistenceLayer.Status;


/**
 * Bei diesem Interface handelt es sich um ein Interface für eine Data Access Object Klasse, die
 * die Datenbankzugriffe in sich kapselt.
 * <p>
 * Die Methoden dieses Interfaces werden von dieser DAO Klasse implementiert und sind nach außen sichtbar.
 * Sie werden aufgerufen, von den RestController-Klassen, denn von dort werden die Server-Anfragen, die von Clients
 * gestellt werden, an die Persistence-Klassen weitergeleitet.
 */
public interface GoDao extends AbstractDao<GoEntity, Long> {

    /**
     * Mit dieser Methode wird der Teilnahmestatus eines Go-Teilnehnmers geändert. Der Status kann entweder "NOT_GOING",
     * "GOING", oder "GONE" lauten.
     * <p>
     * Vor dem Aufruf der Methode muss sichergestellt werden, dass es sich bei dem Benutzer um ein Mitglied der Gruppe
     * handelt und die Statusänderung die vorgenommen werden soll legal ist.
     *
     * @param userId Die Id des Benutzers, dessen Teilnahmestatus geändert werden soll. Dabei handelt es sich um eine
     *               gültige Id, ansonsten kann die Methode nicht erfolgreich ausgeführt werden.
     * @param goId   Die des Gos, für den der Teilnahmestatus geändert werden soll. Dabei handelt es sich um eine
     *               gültige Id, ansonsten kann die Methode nicht erfolgreich ausgeführt werden.
     * @param status Der neue Status des Benutzers.
     */
    public void changeStatus(String userId, long goId, Status status);


}
