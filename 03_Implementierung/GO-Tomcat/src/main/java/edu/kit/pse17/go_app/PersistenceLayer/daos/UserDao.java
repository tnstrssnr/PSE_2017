package edu.kit.pse17.go_app.PersistenceLayer.daos;

import edu.kit.pse17.go_app.PersistenceLayer.GroupEntity;
import edu.kit.pse17.go_app.PersistenceLayer.UserEntity;

import java.util.Set;

/**
 * Bei diesem Interface handelt es sich um ein Interface für eine Data Access Object Klasse, die
 * die Datenbankzugriffe in sich kapselt.
 * <p>
 * Die Methoden dieses Interfaces werden von dieser DAO Klasse implementiert und sind nach außen sichtbar.
 * Sie werden aufgerufen, von den RestController-Klassen, denn von dort werden die Server-Anfragen, die von Clients
 * gestellt werden, an die Persistence-Klassen weitergeleitet.
 */
public interface UserDao extends AbstractDao<UserEntity, String> {

    /**
     * Diese Methode sucht ein User-Objekt anhand einer E-Mailadresse und gibt, falls die Suche erfolgreich ist, dieses
     * Objekt zurück.
     *
     * @param mail Die E-Mailadresse, anhand derer der Benutzer gesucht werden soll. Der String muss keinem besonderen
     *             Muster entsprechen, damit diese Methode fehlerfrei ausgeführt werden kann.
     * @return Die Methode gibt das gefundene UserEntity Objekt zurück. Gibt es keinen Benutzer mit der übergegebenen
     * E-mailadresse, gibt die Methode null zurück.
     */
    UserEntity getUserByEmail(String mail);

    /**
     * Diese Methode gibt eine Liste mit allen Gruppen zurück, in denen der Benutzer Mitglied ist. Dies schließt Gruppen
     * nicht mit ein, zu denen der Benutzer eingeladen wurde, er die Gruppenanfrage aber noch nicht beantwortet hat.
     *
     * @param userId Die Id des Benutzers, dessen Gruppen zurückgegeben werden sollen. Es wird garantiert, dass es sich
     *               beim Aufruf der Methode um eine gültige userid handelt.
     * @return Eine Liste mit GroupEntites. Die Länge der Liste liegt zwischen 0 und 300. Bei allen Listenelementen
     * handelt es sich um vollständige, gültige GroupEntity Objekte.
     */
    Set<GroupEntity> getGroups(String userId);

    /**
     * Diese Methode gibt eine Liste von Gruppen zurück, zu denen der Benutzer eine Gruppenanfrage erhalten hat, die er
     * noch nicht beantwortet hat.
     *
     * @param userId Die Id des Benutzers, dessen Gruppenanfragen zurückgegeben werden sollen. Es wird garantiert, dass
     *               es sich beim Aufruf der Methode um eine gültige userid handelt.
     * @return Eine Liste mit GroupEntites. Die Länge der Liste liegt zwischen 0 und 300. Bei allen Listenelementen
     * handelt es sich um vollständige, gültige GroupEntity Objekte.
     */
    Set<GroupEntity> getRequests(String userId);

}
