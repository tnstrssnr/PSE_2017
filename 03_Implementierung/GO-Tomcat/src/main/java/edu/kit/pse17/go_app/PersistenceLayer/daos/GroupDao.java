package edu.kit.pse17.go_app.PersistenceLayer.daos;

import edu.kit.pse17.go_app.PersistenceLayer.GroupEntity;

import javax.persistence.EntityNotFoundException;

/**
 * Bei diesem Interface handelt es sich um ein Interface für eine Data Access Object Klasse, die
 * die Datenbankzugriffe in sich kapselt.
 * <p>
 * Die Methoden dieses Interfaces werden von dieser DAO Klasse implementiert und sind nach außen sichtbar.
 * Sie werden aufgerufen, von den RestController-Klassen, denn von dort werden die Server-Anfragen, die von Clients
 * gestellt werden, an die Persistence-Klassen weitergeleitet.
 */
public interface GroupDao extends AbstractDao<GroupEntity, Long> {

    /**
     * Fügt der Gruppe mit der Id groupId den Benutzer mit der Id userId hinzu.
     * <p>
     * Vor dem Aufruf dieser Methode, muss der Aufrufer sicherstellen, dass der Benutzer nicht bereits ein Mitglied in
     * der Gruppe ist und, dass der Benutzer zuvor eine Mitgliedschaftsanfrage zu der Gruppe bekommen hat bzw. es sich
     * um dem Ersteller einer neuen Gruppe handelt.
     *
     * @param groupId Die Id der Gruppe, zu der der Benutzer hinzugefügt werden soll. Dabei handelt es sich um eine
     *                gültige Id, ansonsten kann die Methode nicht erfolgreich ausgeführt werden.
     * @param userId  Die Id des Benutzers, der der Gruppe hinzugefügt werden soll. Dabei handelt es sich um eine
     *                gültige Id, ansonsten kann die Methode nicht erfolgreich ausgeführt werden.
     * @throws EntityNotFoundException Existiert keine Entity mit dem spezifizierten Schlüssel, wird eine
     *                                 EntityNotFoundException geworfen, die von der aufrufenden Klasse behandelt werden
     *                                 muss.
     */
    void addGroupMember(String userId, long groupId) throws EntityNotFoundException;

    /**
     * Diese Methode entfernt eine Gruppenmitgliedschaftsanfrage aus der Datenbank. Sie wird aufgerufen, wenn ein
     * Benutzer eine Gruppenmitgliedschaftsanfrage beantwortet hat.
     *
     * @param groupId Die Id der Gruppe, zu die der Benutzer eingeladen war. Dabei handelt es sich um eine gültige Id,
     *                ansonsten kann die Methode nicht erfolgreich ausgeführt werden.
     * @param userId  Die Id des Benutzers, der zu der Gruppe eingeladen war. Dabei handelt es sich um eine gültige Id,
     *                ansonsten kann die Methode nicht erfolgreich ausgeführt werden.
     * @throws EntityNotFoundException Existiert keine Entity mit dem spezifizierten Schlüssel, wird eine
     *                                 EntityNotFoundException geworfen, die von der aufrufenden Klasse behandelt werden
     *                                 muss.
     */
    void removeGroupRequest(String userId, long groupId) throws EntityNotFoundException;

    /**
     * Mit dieser Methode lässt sich eine neue Gruppenanfrage in der Datenbank speichern. Sie muss also aufgerufen
     * werden, wenn ein Administrator einen Benutzer zur Gruppe einlädt.
     * <p>
     * Vor dem Aufruf der Methode muss sichergestellt werden, dass der Empfänger der Anfrage kein Gruppenmitglied ist
     * und auch noch keine Anfrage erhalten hat.
     *
     * @param userId  Die Id des Benutzers, der zu der Gruppe eingeladen wird. Dabei handelt es sich um eine gültige Id,
     *                ansonsten kann die Methode nicht erfolgreich ausgeführt werden.
     * @param groupId die Id der Gruppe, zu der der Benutzer eingeladen wird. Dabei handelt es sich um eine gültige Id,
     *                ansonsten kann die Methode nicht erfolgreich ausgeführt werden.
     * @throws EntityNotFoundException Existiert keine Entity mit dem spezifizierten Schlüssel, wird eine
     *                                 EntityNotFoundException geworfen, die von der aufrufenden Klasse behandelt werden
     *                                 muss.
     */
    void addGroupRequest(String userId, long groupId) throws EntityNotFoundException;

    /**
     * Diese Methode entfernt ein Gruppenmitglied aus einer Gruppe. Sie wird aufgerufen, wenn entweder ein
     * Gruppenmitglied aus einer Gruppe austritt oder ein Administrator ein Gruppenmitglied entfernt.
     * <p>
     * Vor dem Aufruf der Methode muss sichergestellt werden, dass es sich bei dem Benutzer um ein Mitglied der Gruppe
     * handelt. Diese Methode kann nicht dazu verwendet werden, eine Gruppenanfrage zu löschen.
     * <p>
     * Sämtliche Gos, die dem entfernten Gruppenmitglied gehören werden automatisch gelöscht bei Aufruf der Methode, um
     * die Konsistenz des Datenbestands zu erhalten.
     *
     * @param groupId Die Id der Gruppe, aus der der Benutzer entfernt werden soll. Dabei handelt es sich um eine
     *                gültige Id, ansonsten kann die Methode nicht erfolgreich ausgeführt werden.
     * @param userId  Die Id des Benutzers, der aus der Gruppe entfernt werden soll. Dabei handelt es sich um eine
     *                gültige Id, ansonsten kann die Methode nicht erfolgreich ausgeführt werden.
     * @throws EntityNotFoundException Existiert keine Entity mit dem spezifizierten Schlüssel, wird eine
     *                                 EntityNotFoundException geworfen, die von der aufrufenden Klasse behandelt werden
     *                                 muss.
     */
    void removeGroupMember(String userId, long groupId) throws EntityNotFoundException;


    /**
     * Diese Methode fügt einen Administrator bei einer Gruppe hinzu. Anfragen zu dieser Methode sollte nur von anderen
     * Adminsitratoren dieser Gruppe kommen.
     * <p>
     * Es muss vor dem Aufruf der Methode sichergestellt werden, dass es sich bei dem Benutzer um ein vollwertiges
     * Gruppenmitglied handelt und dieser nicht bereits ein Administrator ist.
     *
     * @param userId  Die Id des Benutzers, der als Administrator hinzugefügt werden soll. Dabei handelt es sich um eine
     *                gültige Id, ansonsten kann die Methode nicht erfolgreich ausgeführt werden.
     * @param groupId Die Id der Gruppe, zu der der Administrator hinzugefügt werden soll. Dabei handelt es sich um eine
     *                gültige Id, ansonsten kann die Methode nicht erfolgreich ausgeführt werden.
     * @throws EntityNotFoundException Existiert keine Entity mit dem spezifizierten Schlüssel, wird eine
     *                                 EntityNotFoundException geworfen, die von der aufrufenden Klasse behandelt werden
     *                                 muss.
     */
    void addAdmin(String userId, Long groupId) throws EntityNotFoundException;

}
