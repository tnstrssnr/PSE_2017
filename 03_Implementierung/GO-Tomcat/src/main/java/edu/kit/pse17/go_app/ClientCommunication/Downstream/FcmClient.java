package edu.kit.pse17.go_app.ClientCommunication.Downstream;

import com.google.gson.Gson;
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

    //BASE URL des FCM-Servers
    private static final String BASE_URL = "http://fcm.googleapis.com/fcm/";

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private FcmApi fcmApi;

    public FcmClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        fcmApi = retrofit.create(FcmApi.class);
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

        Map<String, String> eventData = new HashMap<>();
        eventData.put("tag", command.toString());
        eventData.put("data", data);

        for (String rec : receiver) {

            FcmMessage message = new FcmMessage(rec, eventData);
            RequestBody body = RequestBody.create(JSON, new Gson().toJson(message));
            System.out.println(new Gson().toJson(message));
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
