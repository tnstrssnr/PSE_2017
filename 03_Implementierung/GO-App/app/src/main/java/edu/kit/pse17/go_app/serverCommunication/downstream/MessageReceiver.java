package edu.kit.pse17.go_app.serverCommunication.downstream;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Dies Klasse ist eine Unterklasse von FirebaseMessagingService, die auf den Go Tomcat-Server hoert. DAs heisst, schickt der Server via FCM eine Nachricht an
 * den LCient, wird sie in dieser Klasse empfangen.
 *
 * Wird eine Nachricht an den CLient gesendet waehrend die App im Hintergrund laeuft, wird der Inhalt der Nachricht uaf dem System Tray gespeichert. Bei erneutem Aufrufen der App muss
 * ueberprueft werden, ob in der Zwischenzeit NAchrichten angekommen sind. Sind es zu viele Nachrichten die auf dem System Tray gespeichert worden sind, so kann nicht
 * garantiert werden, dass alle noch vorhanden sind. In diesem Fall wird die Methode onDeletedMessages() aufgerufen. Hier sollten die gesamten Nutzerdaten von Server abgefragt werden,
 * um sicherzugehen, dass der Client alle aktuellen bei sich hat.
 *
 * Bei Ankunft einer Nachricht des Servers, wird die onMessageReceived-Methode aufgerufen und dort weiter behandelt.
 * Der Service muss beim Start der App gesstartet werden und beim Schliessen der App wieder beendet. Dabei muss der Service auf einem Backgroun Thread und nicht auf dem
 * main UI Thread laufen.
 **/

public class MessageReceiver extends FirebaseMessagingService {

    /**
     * URL des Servers, von dem die Nachrichten kommen sollen (in diesem Fall der Tomcat_server der Anwendung).
     * Nachrichten von anderen Absendern werden ignoriert.
     */

    private static final String SENDER = "Platzhalter";

    /**
     * Diese Methode wird aufgerufen, wenn die App eine Nachricht vom FCM Server erhaelt waehrend sie im Vordergrund laeuft. Die Nachricht sollte spaetestens 10s nach ihrer Ankunft
     * behandelt werden, vie Spezifikation der Fcm API.
     *
     * @param remoteMessage die erhaltene Nachricht, verpackt in ein RemoteMessage-Objekt. Dieses Objekt wird vom FCM Server erzeugt und enthaelt die Attribute
     *                      from und data, mit denen die fuer diese Anwendung relevanten Daten ermittelt werden koennen.
     */
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        if (remoteMessage.getFrom() == SENDER) {

        } else {
            //do nothing
        }
    }

    /**
     * Diese Methode wird aufgerufen, falls zu viele Benachrichtigungen an den CLient gesendet wurden, waehrend die App im Hintergrund lief. Dann sit es nicht mehr garantiert,
     * dass alle diese Nachrichten noch in der System tray zu finden sind. Es sollten also die gesamten Nuterdaten nochmal vom Server angefragt werden. Das passiert in dieser
     * Methode.
     */
    @Override
    public void onDeletedMessages() {
        super.onDeletedMessages();
    }
}
