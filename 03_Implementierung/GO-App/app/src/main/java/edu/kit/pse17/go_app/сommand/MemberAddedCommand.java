package edu.kit.pse17.go_app.сommand;

/**
 * Created by tina on 06.07.17.
 */

/**
 * Diese Klasse implementiert einen Befehl, der bei dem Beitreten einer Gruppe
 * vom Benutzer ausgeführt wird.
 * D.h. der Benutzer hat die Gruppenanfrage bestätigt.
 */
public class MemberAddedCommand extends ServerCommand {

    /**
     * Diese Methode ändert folgende Daten in Repositorien der App:
     *
     * -Dem ausgewählten Benutzer werden alle Daten der Gruppe gesendet;
     * -Alle Mitglieder der Gruppe werden über den neuen Benutzer benachrichtigt.
     */
    @Override
    public void onCommandReceived() {

    }
}
