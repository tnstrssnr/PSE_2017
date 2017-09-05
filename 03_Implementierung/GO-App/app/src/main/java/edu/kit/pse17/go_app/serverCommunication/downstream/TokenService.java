package edu.kit.pse17.go_app.serverCommunication.downstream;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import edu.kit.pse17.go_app.repositories.GroupRepository;
import edu.kit.pse17.go_app.view.GroupListActivity;

/**
 * The class generates an InstanceID Token, which will be given to the Server,
 * to make Server-side messages to a single device.
 *
 * Every time you log into the App, such InstanceID must be generated to ensure
 * that it is current and valid.
 * Then, it is forwarded to the Server. During the execution of the App it
 * can also lead to a renewal of the InstanceID. In this case, the
 * onTokenRefresh() Method of this class is called.
 *
 */

public class TokenService extends FirebaseInstanceIdService {

    private static final String TAG = "TokenService";

    /**
     * When a new Token for a unit is created, it will automatically call this method.
     * In the method, the new Token to the Tomcat Server will be
     * sent so that the Server-messages could also be sent.
     */
    @Override
    public void onTokenRefresh() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed Token: " + refreshedToken);

        sendTokenToServer(refreshedToken);
    }

    /**
     * Method, which returns an InstanceID to the Tomcat Server. This is called
     * every time the method onTokenRefresh() is called.
     *
     * @param refreshedToken: The new InstanceID. It must be guaranteed before
     *                      calling this method that it a valid InstanceID is.
     */
    private void sendTokenToServer(String refreshedToken) {
        if (GroupListActivity.getUserId() != null)
            GroupRepository.getInstance().registerDevice(GroupListActivity.getUserId(), refreshedToken);
    }
}
