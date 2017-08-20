package edu.kit.pse17.go_app.serverCommunication.downstream;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import edu.kit.pse17.go_app.repositories.GroupRepository;
import edu.kit.pse17.go_app.сommand.ServerCommand;

/**
 * This class is a subclass of fire base messaging service, which listens
 * to the Tomcat Server. This is when the server send via FCM a message to
 * the client, this will be received in this class.
 *
 * Is a message sent to the client while the App is in the Background running,
 * the contents of the message will be saved to the System Tray.
 * When you start the App, it will be checked whether in the meantime news arrived.
 * If there are too many messages have been saved on the System Tray, so can't
 * be guaranteed that all are still available. In this case, the method
 * onDeletedMessages() is called. Here is the entire set of users should be queried
 * for data from Server to ensure that the Client has all of the latest.
 *
 * Upon arrival of a message, the server calls the onMessageReceived() method,
 * where the message will be handled.
 * The Service must be started when the app is started and closed when
 * the app is closed.
 * The Service must run on a Background Thread and not on the main thread.
 */
public class MessageReceiver extends FirebaseMessagingService {

    /**
     * URL of the server from which the messages come from
     * (in this case, the Tomcat server of the application).
     * Messages from other senders will be ignored.
     */
    private static final String SENDER = "479703714972";

    /**
     * Command that will be called here.
     */
    private ServerCommand command;

    /**
     * This method is called when the App receives a message from the FCM Server
     * while it is running in the foreground. The message should be proceeded
     * at the very least 10s after the arrival (see specification of the Fcm API).
     *
     * @param remoteMessage: The received messsage, packed in a remote message object.
     *                     This object is generated from the FCM Server and
     *                     contains the attributes from and data.
     */
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (remoteMessage.getFrom().equals(SENDER)) {
            String tag = null;
            Map<String, String> map = remoteMessage.getData();
            JSONObject data = new JSONObject(map);

            try {
                tag = data.getString("tag");
                command = Command.valueOf(tag).getCommand();
                command.setMessage(data);
                command.onCommandReceived();
            } catch (JSONException e) {
                StackTraceElement[] t = e.getStackTrace();
                e.printStackTrace();
            }
        }
           // Log.d("MESSAGE_Firebase", "Message Notification Body: " + remoteMessage.getNotification().getBody());
        if(remoteMessage.getData().size() > 0){
           // Log.d("FIRST DATA FCM", remoteMessage.getData().toString());
        }
    }

    /**
     * This method is called, if there are too many notifications
     * have been sent to the client, while the App ran in the Background.
     * Then it is no longer guaranteed that all of these messages are
     * in the System tray. The entire user data must be fetched again from the
     * Server. This is what happens in this Method.
     */
    @Override
    public void onDeletedMessages() {
        GroupRepository.getInstance().updateData();;
    }
}
