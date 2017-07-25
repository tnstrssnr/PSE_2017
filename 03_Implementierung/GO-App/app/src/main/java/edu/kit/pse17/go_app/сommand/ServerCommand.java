package edu.kit.pse17.go_app.сommand;

/**
 * Created by tina on 06.07.17.
 */

/**
 * Die abstrakte Klasse ist eine allgemeine Klasse für Befehle, die bei Ankunft
 * einer Nachricht vom Server ausgeführt werden. Diese Befehle ändern die Daten
 * in den Repositorien, sodass die App diese später holen kann.
 */
public abstract class ServerCommand {

    /**
     * Diese Methode implementiert Befehle, die die neuen Daten im Repositorium
     * ablegen.
     * Wird von der Methode onMessageReceived() der Klasse MessageReceiver aufgerufen,
     * sobald die App eine Nachricht des Tomcat-Servers erhält und in einen Befehl
     * dekodiert.
     */
    public abstract void onCommandReceived();
}
