package edu.kit.pse17.go_app.PersistenceLayer.daos;

import edu.kit.pse17.go_app.PersistenceLayer.GroupEntity;
import edu.kit.pse17.go_app.PersistenceLayer.UserEntity;

import javax.persistence.EntityNotFoundException;
import java.util.List;

/**
 * Bei diesem Interface handelt es sich um ein Interface für eine Data Access Object Klasse, die
 * die Datenbankzugriffe in sich kapselt.
 *
 * Die Methoden dieses Interfaces werden von dieser DAO Klasse implementiert und sind nach außen sichtbar.
 * Sie werden aufgerufen, von den RestController-Klassen, denn von dort werden die Server-Anfragen, die von Clients
 * gestellt werden, an die Persistence-Klassen weitergeleitet.
 */
public interface GroupDao {

    /**
     * Fügt der Gruppe mit der Id groupId den Benutzer mit der Id userId hinzu.
     *
     * Vor dem Aufruf dieser Methode, muss der Aufrufer sicherstellen, dass der Benutzer nicht bereits ein Mitglied in der Gruppe ist und,
     * dass der Benutzer zuvor eine Mitgliedschaftsanfrage zu der Gruppe bekommen hat bzw. es sich um dem Ersteller einer neuen Gruppe handelt.
     *
     * @param groupId Die ID der Gruppe, zu der der Benutzer hinzugefügt werden soll. Dabei handelt es sich um eine gültige Id, ansonsten kann die Methode
     *                nicht erfolgreich ausgeführt werden.
     * @param userId Die ID des Benutzers, der der Gruppe hinzugefügt werden soll. Dabei handelt es sich um eine gültige Id, ansonsten kann die Methode
     *                nicht erfolgreich ausgeführt werden.
     *
     * @throws EntityNotFoundException existiert keine Entity mit dem spezifizierten Schlüssel, wird eine EntityNotFoundException geworfen, die
     * von der aufrufenden Klasse behandelt werden muss.
     */
    public void addGroupMember(String userId, long groupId) throws EntityNotFoundException;

    /**
     * Diese Methode entfernt eine Gruppenmitgliedschaftsanfrage aus der Datenbank. Sie wird aufgerufen, wenn ein Benutzer eine Gruppenmitgliedschaftsanfrage
     * beantwortet hat.
     * @param userId
     * @param groupId
     * @throws EntityNotFoundException
     */
    public void removeGroupRequest(String userId, long groupId) throws EntityNotFoundException;

}
