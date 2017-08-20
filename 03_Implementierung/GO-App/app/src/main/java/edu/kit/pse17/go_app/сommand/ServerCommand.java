package edu.kit.pse17.go_app.сommand;

/**
 * Created by tina on 06.07.17.
 */

import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONObject;

import edu.kit.pse17.go_app.model.entities.Group;

/**
 * The abstract class is a generic class for commands, which at arrival
 * a message from the Server will be called. These commands change the data
 * in the repositories, so that the App can fetch it later.
 */
public abstract class ServerCommand {

    /**
     * Message from server.
     */
    private JSONObject message;

    /**
     * This method implements commands that drops the new data in the repository.
     * It is called from the method onMessageReceived() of the class
     * of the message receiver,
     * once the App receives a message from the Tomcat server and the command is
     * decoded.
     */
    public abstract void onCommandReceived();

    /**
     * Getter for message.
     *
     * @return Message
     */
    public JSONObject getMessage() {
        return message;
    }

    /**
     * Setter for message.
     *
     * @param message: JSON object message
     */
    public void setMessage(JSONObject message) {
        this.message = message;
    }
}
