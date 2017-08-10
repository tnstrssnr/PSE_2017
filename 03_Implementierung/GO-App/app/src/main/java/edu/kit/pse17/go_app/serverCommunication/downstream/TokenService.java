package edu.kit.pse17.go_app.serverCommunication.downstream;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Die Klasse erzeugt ein InstanceID Token, welches an den Server uebergeben wird, um von Server-Seite aus Nachrichten an ein einzelnes Geraet schicken zu koennen.
 *
 * Bei jeder Anmeldung in der App muss eine solche InstanceID erzeugt werden, um sicher zu gehen, dass diese auch aktuell und gueltig ist.
 * Danach wird diese an den Server weitergeleitet. Es kann waehrend der Ausfuehrung der App auch zu einer Erneuerung der InstanceID kommen. In diesem Fall wird die onTokenReferesh()
 * Methode dieser Klasse aufgerufen.
 *
 */

public class TokenService extends FirebaseInstanceIdService {

    private static final String TAG = "TokenService";

    /**
     * Wird ein neues Token fuer ein Geraet erzeugt, wird automatisch diese Methode aufgerufen. In der Methode wird das neue Token an den Tomcat-Server
     * gesendet, damit weiterhin Server-Nachrichten an diesen Client gesendet werden koennen.
     */
    @Override
    public void onTokenRefresh() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed Token: " + refreshedToken);

        sendTokenToServer(refreshedToken);
    }

    /**
     * Methode, die eine InstanceID an den Tomcat-Server uebergibt. Die wird jedes mal aufgerufen, wenn die Methode
     * onTokenRefresh() aufgerufen wird.
     *
     * @param refreshedToken Die neue InstanceID. Es muss vor Aufruf dieser Methode garantiert sein, dass es sich um eine aktuelle,
     *                       gueltige InstanceID handelt.
     */
    private void sendTokenToServer(String refreshedToken) {
        //TODO implement
    }
}
