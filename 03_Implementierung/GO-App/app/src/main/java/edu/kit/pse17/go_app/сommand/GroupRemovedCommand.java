package edu.kit.pse17.go_app.сommand;

/**
 * Created by tina on 06.07.17.
 */

/**
 * Diese Klasse implementiert einen Befehl, der bei dem Löschen einer Gruppe
 * ausgeführt wird.
 */
public class GroupRemovedCommand extends ServerCommand {

    /**
     * Diese Methode ändert folgende Daten in Repositorien der App:
     *
     * -Alle Mitglieder der Gruppe werden benachrichtigt, dass die Gruppe nicht mehr
     * existiert;
     * -Alle GOs der Gruppe werden gelöscht;
     * -Die Gruppe selbst (und alle Daten) werden gelöscht.
     */
    @Override
    public void onCommandReceived() {

    }
}
