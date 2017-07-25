package edu.kit.pse17.go_app.сommand;

/**
 * Created by tina on 06.07.17.
 */

/**
 * Diese Klasse implementiert einen Befehl, der bei dem Austreten (oder Löschen)
 * eines Mitglieds aus der Gruppe ausgeführt wird.
 */
public class MemberRemovedCommand extends ServerCommand {

    /**
     * Diese Methode ändert folgende Daten in Repositorien der App:
     *
     * -Alle Mitglieder der Gruppe werden benachrichtigt, dass ein Mitglied aus
     * der Gruppe ausgetreten (oder gelöscht) ist;
     * -Alle GOs, bei denen der Benutzer GO-Verantwortlicher war, werden gelöscht;
     * -Wenn die Gruppe nur einen einzigen Mitglied hatte, wird diese Gruppe gelöscht.
     */
    @Override
    public void onCommandReceived() {

    }
}
