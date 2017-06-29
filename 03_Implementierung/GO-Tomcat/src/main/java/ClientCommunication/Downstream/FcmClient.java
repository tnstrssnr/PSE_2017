package ClientCommunication.Downstream;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 *Client-Klasse, die ein HTTP POST_Request an den FCM-Server schickt, wo die Nachricht wiederum an das User-Endgerät weitergeleitet wird.
 *
 * Created by tina on 29.06.17.
 */
public class FcmClient {

    /**
     * Base URL des FCM-Servers
     */
    private static final String BASE_URL = "https://fcm.googleapis.com/";

    /**
     * Rest-Schnittstelle des FCM Servers, die vom Retrofit Framework angesprochen werden kann
     */
    private final FcmApi fcmApi;

    /**
     * Konstruktor
     */
    public FcmClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        fcmApi = retrofit.create(FcmApi.class);

    }

    /**
     * sendet eine Message an den FCM-Server, der diese an das User-Endgerät weiterleitet
     */
    public void send() {
        fcmApi.sendDownStreamMessage();
    }
}
