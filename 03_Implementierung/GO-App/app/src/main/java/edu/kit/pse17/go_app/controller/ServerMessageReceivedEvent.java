package edu.kit.pse17.go_app.controller;

import edu.kit.pse17.go_app.model.GO;
import edu.kit.pse17.go_app.model.Group;

/**
 * Created by tina on 28.06.17.
 */

public class ServerMessageReceivedEvent {


    /**
     * Methode wird aufgerufen, wenn der Server der App meldet, dass sich Daten in der Gruppe group verändert haben.
     * Dazu gehören Änderungen in den Gruppendaten selbst, sowie hinzugefügte und entfernte Mitglieder.
     *
     * @param group Die veränderte Gruppe
     */
    public void onGroupInfoChanged(Group group) {

    }

    /**
     * Methode wird aufgerufen, wenn der Server der App meldet, dass sich Daten innerhalb des GOs go verändert haben.
     * Dazu gehören nicht die Standorte der Mitglieder. Diese werden separat von der App in der Klasse *** von Server abgefragt
     *
     * @param go Das veränderte GO
     */
    public void onGoInfoChanged(GO go) {

    }

    /**
     * Methode wird aufgerufen, wenn der Benutzer der App eine neue Gruppenanfrage erhält, d.h. seinem Account eine neue Gruppe hinzugefügt wird
     *
     * @param group Die Gruppe, zu der der Benutzer eingeladen wurde
     */
    public void onGroupRequestReceived(Group group) {

    }

    /**
     * Methode wird aufgerufen, wenn in einer Gruppe des Benutzers ein neues GO erstellt wird.
     *
     * @param go Das neu erstellte GO
     */
    public void onGoCreated(GO go) {

    }

}
