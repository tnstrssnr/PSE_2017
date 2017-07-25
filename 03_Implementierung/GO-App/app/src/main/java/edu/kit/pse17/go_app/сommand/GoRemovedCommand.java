package edu.kit.pse17.go_app.сommand;

/**
 * Created by tina on 06.07.17.
 */

/**
 * Diese Klasse implementiert einen Befehl, der bei dem Löschen eines GOs
 * der Gruppe ausgeführt wird.
 */
public class GoRemovedCommand extends ServerCommand {

    /**
     * Diese Methode ändert folgende Daten in Repositorien der App:
     *
     * -Alle Mitglieder der Gruppe werden benachrichtigt, dass das GO nicht mehr
     * existiert.
     */
    @Override
    public void onCommandReceived() {

    }
}
