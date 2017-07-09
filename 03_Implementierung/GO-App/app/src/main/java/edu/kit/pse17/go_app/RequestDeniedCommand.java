package edu.kit.pse17.go_app;

/**
 * Created by Сеня on 09.07.2017.
 */

/**
 * Diese Klasse implementiert einen Befehl, der bei dem Ablehnen einer
 * Gruppenanfrage vom Benutzer ausgeführt wird.
 * D.h. der Benutzer hat die Gruppenanfrage abgelehnt.
 */
public class RequestDeniedCommand extends ServerCommand {

    /**
     * Diese Methode ändert folgende Daten in Repositorien der App:
     *
     * -Alle Administratoren der Gruppe werden benachrichtigt (d.h., diese
     * eröffnete Gruppenanfrage wird gelöscht).
     */
    @Override
    public void onCommandReceived() {

    }
}
