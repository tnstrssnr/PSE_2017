package edu.kit.pse17.go_app.serverCommunication.downstream;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Die Klasse implementiert einen ServiceCommand, der auf den Go TOmcat-Server hört.
 * Bei Ankunft einer Nachricht des Servers, wird die onMessageReceived-methode aufgerufen (sofern die App im Vordergrund läuft).
 * Läuft die App im Hintergrund, ...
 *
 * Created by tina on 28.06.17.
 **/

public class MessagingService extends FirebaseMessagingService {

    // Tag, der zum Loggen der Nachrichten verwendet wird
    private static final String TAG = "Messaging ServiceCommand";

    // URL des Servers, von dem die Nachrichten kommen sollen. Nachrichten von anderen Absendern werden ignoriert
    private static final String SENDER = "Platzhalter";

    /**
     * wird aufgerufen, soblad die App eine Nachricht des Go Tomcat-Servers erhält.
     *
     * @param remoteMessage die erhaltene Nachricht
     */
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG, "Message: " + remoteMessage.getFrom());

        if (remoteMessage.getFrom() == SENDER) {

        } else {
            //do nothing
        }
    }
}
