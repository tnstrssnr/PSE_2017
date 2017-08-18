package edu.kit.pse17.go_app.ClientCommunication.Downstream;

import edu.kit.pse17.go_app.PersistenceLayer.UserEntity;
import okhttp3.*;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.Set;

/**
 * realizes the communication w/ the Firebase Cloud Messaging Server to allow sending messages from the server to the
 * clients. Methods of this class are invoked by the observers
 */
public class FcmClient {

    //base url of the fcm server, that post-requests have to be sent to
    private static final String BASE_URL = "https://fcm.googleapis.com/send";

    //server key, supplied by firebase upon app registration -- necessary for the fcm server to identify the server that sent the request
    private static final String SERVER_KEY = "key=AAAAjalbaUE:APA91bHDguerPdnJ9fOLxvXynowziqVzgw5HB0ug0ZIEFDaFE0AezmBZoD1gcaRROMfriNi7IRKrC5ROVpdOz2Ohegcoj-X4Wphpii1QSgHvRRm6JTxdLi_QUv8GRcenNrMbGEYDEKWC";

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private OkHttpClient httpClient;

    public FcmClient() {
        this.httpClient = new OkHttpClient();
    }

    //constructor is used for unit testing to inject mocked httpClient
    public FcmClient(OkHttpClient httpClient) {
        this.httpClient = httpClient;
    }


    /**
     * sends request to FCM server. The user then receives the Json-String specified in data.
     *
     * @param data     Json-String w/ data that sould be sent to the user
     * @param command  EventArg-String, that specifies the event, wich triggered this method call -- allows user to
     *                 correctly handle the data received in the message
     * @param receiver List of all instanceIds of the users that are supposed to receive the message
     */

    public void send(String data, EventArg command, Set<UserEntity> receiver) {

        JSONObject eventData = new JSONObject();
        eventData.put("tag", command.toString());
        eventData.put("data", data);

        for (UserEntity msg : receiver) {

            JSONObject json = new JSONObject();
            json.put("to", msg.getInstanceId());
            json.put("data", eventData);

            String jsonString = json.toString();
            RequestBody body = RequestBody.create(JSON, jsonString);
            Request request = new Request.Builder()
                    .url(BASE_URL)
                    .header("Content-Type", "application/json")
                    .addHeader("Authorization", SERVER_KEY)
                    .post(body)
                    .build();

            try {
                final Call call = httpClient.newCall(request);
                Response response = call.execute();
            } catch (IOException e) {
                e.printStackTrace();
            }

            //TODO: do something w/ response
        }


    }

}
