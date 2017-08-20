package edu.kit.pse17.go_app.ClientCommunication.Downstream;

import com.google.gson.Gson;
import edu.kit.pse17.go_app.PersistenceLayer.UserEntity;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Callback;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Realisiert die Kommunikation mit dem Firebase Cloud Messaging Server um das Senden von Nachrichten vom Server zum Client zu erlauben.
 * Methoden dieser Klasse werden durch die Observer aufgerufen.
 *
 */
public class FcmClient {

    //base url of the fcm server, that post-requests have to be sent to
    private static final String BASE_URL = "http://fcm.googleapis.com/fcm/";

    //server key, supplied by firebase upon app registration -- necessary for the fcm server to identify the server that sent the request
    private static final String SERVER_KEY = "key=AAAAb7CRzJw:APA91bEYvvSvl0D7nykrgqWAExSamy3sXHLV76aOO7iFryptnilzXxIYJZe51fGjtEhNreONqqDTfvxmBTzJ8mPw3gTgcMDm2wv4abYbLQJgXi971poLZ9EQ91Sk4tU1FiN7p5hE3Cq8";

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private FcmApi fcmApi;
    private Retrofit retrofit;

    public FcmClient() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        fcmApi = retrofit.create(FcmApi.class);
    }

    /**
     * Sendet Anfragen an den FCM Server. Der User erhält dann den Json-String angegeben in data.
     *
     * @param data Json-String mit Daten die zum User gesendet werden sollten
     * @param command EventArg-String, welcher das Event spezifiziert, das diesen Methodenaufruf aufgerufen hat.
     *                --Erluabt dem User, der diese Nachricht empfangen hat, die Daten richtig zu handhaben.
     * @param receiver Liste aller insatnceIds der User welche die Nachricht empfangen sollen
     */

    public void send(String data, EventArg command, Set<UserEntity> receiver) {

        Map<String, String> eventData = new HashMap<>();
        eventData.put("tag", command.toString());
        eventData.put("data", data);

        for (UserEntity msg : receiver) {

            FcmMessage message = new FcmMessage(msg.getInstanceId(), eventData);
            RequestBody body = RequestBody.create(JSON, new Gson().toJson(message));
            System.out.println(new Gson().toJson(message));
            retrofit2.Call<Void> call = fcmApi.send(body);
            call.enqueue(new Callback<Void>() {

                @Override
                public void onResponse(retrofit2.Call<Void> call, retrofit2.Response<Void> response) {
                    System.out.println(response.isSuccessful());
                    System.out.println(response.errorBody());
                }

                @Override
                public void onFailure(retrofit2.Call<Void> call, Throwable throwable) {
                    //do nothing
                }
            });
        }


    }

}
