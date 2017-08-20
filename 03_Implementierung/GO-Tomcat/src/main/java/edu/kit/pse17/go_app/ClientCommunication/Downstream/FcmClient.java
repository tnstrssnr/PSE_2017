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
 * realizes the communication w/ the Firebase Cloud Messaging Server to allow sending messages from the server to the
 * clients. Methods of this class are invoked by the observers
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
     * sends request to FCM server. The user then receives the Json-String specified in data.
     *
     * @param data     Json-String w/ data that sould be sent to the user
     * @param command  EventArg-String, that specifies the event, wich triggered this method call -- allows user to
     *                 correctly handle the data received in the message
     * @param receiver List of all instanceIds of the users that are supposed to receive the message
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
