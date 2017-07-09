package edu.kit.pse17.go_app;

/**
 * Created by tina on 06.07.17.
 */

/**
 * Diese Klasse implementiert einen Befehl, der bei dem Senden einer Gruppenanfrage
 * vom Admin der Gruppe zu einem Benutzer ausgeführt wird.
 */
public class GroupRequestReceivedCommand extends ServerCommand {

    /**
     * Diese Methode ändert folgende Daten in Repositorien der App:
     *
     * -Dem ausgewählten Benutzer wird eine Gruppenanfrage gesendet, die in der
     * App des Benutzers gezeigt wird.
     */
    @Override
    public void onCommandReceived() {

    }
}
