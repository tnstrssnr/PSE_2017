package edu.kit.pse17.go_app.ClientCommunication.Downstream;

import com.google.gson.Gson;
import edu.kit.pse17.go_app.TestUtil;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Callback;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Realisiert die Kommunikation mit dem Firebase Cloud Messaging Server um das Senden von Nachrichten vom Server zum
 * Client zu erlauben. Methoden dieser Klasse werden durch die Observer aufgerufen.
 */
public class FcmClient {

    private static final String testInstance = "dkVE5J7QcxM:APA91bFwKzvTSfXl5lS7_cPGL3kJC-D7C2tPIK_rVKWas5S-DZ8lPh1ERYQcwSo6Z4DzhQ5PRjnQ_2HltjI0EjlDeVUlauLH3gTlUys9PFVJQAh7K9DQahZtI3sYR_qXkbzhwdrCkEG4";

    private static final Map<EventArg, String> NOTIFICATION_MESSAGES;

    //BASE URL des FCM-Servers
    private static final String BASE_URL = "http://fcm.googleapis.com/fcm/";

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    static {
        NOTIFICATION_MESSAGES = new HashMap<>();
        NOTIFICATION_MESSAGES.put(EventArg.GO_ADDED_EVENT, "A new Go has been added to one of your Groups!");
        NOTIFICATION_MESSAGES.put(EventArg.GO_EDITED_EVENT, "One of your Gos has been changed!");
        NOTIFICATION_MESSAGES.put(EventArg.GO_REMOVED_EVENT, "One of your GOs has been removed!");
        NOTIFICATION_MESSAGES.put(EventArg.GROUP_EDITED_EVENT, "One of your Groups has been edited!");
        NOTIFICATION_MESSAGES.put(EventArg.GROUP_REMOVED_EVENT, "One of your Groups has been deleted!");
        NOTIFICATION_MESSAGES.put(EventArg.GROUP_REQUEST_RECEIVED_EVENT, "You have received a new Group Request!");
        NOTIFICATION_MESSAGES.put(EventArg.MEMBER_ADDED_EVENT, "A new user has joined one of your Groups!");
        NOTIFICATION_MESSAGES.put(EventArg.MEMBER_REMOVED_EVENT, "A User has left one of your Groups!");
        NOTIFICATION_MESSAGES.put(EventArg.GROUP_REQUEST_DENIED_EVENT, "A Group Request has been denied!");
        NOTIFICATION_MESSAGES.put(EventArg.STATUS_CHANGED_EVENT, "A User changed their status!");
        NOTIFICATION_MESSAGES.put(EventArg.USER_DELETED_EVENT, "A User deleted their Account!");
    }

    private FcmApi fcmApi;

    public FcmClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        fcmApi = retrofit.create(FcmApi.class);
    }

    public FcmApi getFcmApi() {
        return fcmApi;
    }

    public void setFcmApi(FcmApi fcmApi) {
        this.fcmApi = fcmApi;
    }

    /**
     * Sendet Anfragen an den FCM Server. Der User erhält dann den Json-String angegeben in data.
     *
     * @param data     Json-String mit Daten die zum User gesendet werden sollten
     * @param command  EventArg-String, welcher das Event spezifiziert, das diesen Methodenaufruf aufgerufen hat.
     *                 --Erlaubt dem User, der diese Nachricht empfangen hat, die Daten richtig zu handhaben.
     * @param receiver Liste aller insatnceIds der User welche die Nachricht empfangen sollen
     */

    public void send(String data, EventArg command, List<String> receiver) {

        //receiver.add(testInstance);
        Map<String, String> eventData = new HashMap<>();
        eventData.put("tag", command.toString());
        eventData.put("data", data);

        Map<String, String> notificationData = new HashMap<>();
        notificationData.put("title", NOTIFICATION_MESSAGES.get(command));

        if (TestUtil.runInTestMode) {
            FcmMessage message = new FcmMessage("testRec", eventData, notificationData);
            String jsonString = new Gson().toJson(message);
            TestUtil.recordData(command, jsonString, receiver);
        }

        for (String rec : receiver) {

            FcmMessage message = new FcmMessage(rec, eventData, notificationData);
            RequestBody body = RequestBody.create(JSON, new Gson().toJson(message));
            retrofit2.Call<Void> call = fcmApi.send(body);
            call.enqueue(new Callback<Void>() {

                // Die Response des Servers wird nicht weiter verarbeitet.
                @Override
                public void onResponse(retrofit2.Call<Void> call, retrofit2.Response<Void> response) {
                    // do nothing
                }

                @Override
                public void onFailure(retrofit2.Call<Void> call, Throwable throwable) {
                    // do nothing
                }
            });
        }
    }

}
