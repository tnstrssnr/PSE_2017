package edu.kit.pse17.go_app.сommand;

/**
 * Created by Сеня on 09.07.2017.
 */

/**
 * Diese Klasse implementiert einen Befehl, der bei dem Löschen vom Account
 * eines Benutzers ausgeführt wird.
 */
public class UserDeletedCommand extends ServerCommand {

    /**
     * Diese Methode ändert folgende Daten in Repositorien der App:
     *
     * -Alle Mitglieder der Gruppen, bei denen der Benutzer Mitglied war,
     * werden benachrichtigt, dass dieser Mitglied gelöscht ist;
     * -Alle Teilnehmer der GOs, bei denen der Benutzer Teilnehmer war,
     * werden benachrichtigt, dass dieser Benutzer gelöscht ist;
     * -Alle GOs, bei denen der Benutzer GO-Verantwortlicher war, werden gelöscht;
     * -Wenn die Gruppe nur einen einzigen Mitglied hatte, wird diese Gruppe gelöscht.
     */
    @Override
    public void onCommandReceived() {

    }
}
