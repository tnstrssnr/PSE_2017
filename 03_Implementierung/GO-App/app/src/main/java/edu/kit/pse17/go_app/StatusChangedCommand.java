package edu.kit.pse17.go_app;

/**
 * Created by Сеня on 09.07.2017.
 */

/**
 * Diese Klasse implementiert einen Befehl, der bei der Veränderung eines Status
 * des Benutzers innerhalb eines GOs ausgeführt wird.
 */
public class StatusChangedCommand extends ServerCommand {

    /**
     * Diese Methode ändert folgende Daten in Repositorien der App:
     *
     * -Alle Teilnehmer des GOs werden über dan neuen Status des Benutzers
     * benachrichtigt.
     */
    @Override
    public void onCommandReceived() {

    }
}
