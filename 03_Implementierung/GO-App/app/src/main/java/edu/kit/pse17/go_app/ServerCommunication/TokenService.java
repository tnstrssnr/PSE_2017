package edu.kit.pse17.go_app.ServerCommunication;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Die Klasse erzeugt ein InstanceID Token, welches an den Server übergeben wird, um von Server-Seite aus Nachrichten an ein einzelnes Gerät schicken zu können.
 *
 * Created by tina on 28.06.17.
 */

public class TokenService extends FirebaseInstanceIdService {

    private static final String TAG = "TokenService";

    /**
     * holt das neue Token und veranlasst das Programm, das neue Token an den Server zu übergeben.
     */
    @Override
    public void onTokenRefresh() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed Token: " + refreshedToken);

        sendTokenToServer(refreshedToken);
    }

    private void sendTokenToServer(String refreshedToken) {
        //TODO implement
    }
}
