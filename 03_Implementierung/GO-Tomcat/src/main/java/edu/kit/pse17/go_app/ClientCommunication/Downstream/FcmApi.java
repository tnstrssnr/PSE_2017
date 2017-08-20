package edu.kit.pse17.go_app.ClientCommunication.Downstream;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;


public interface FcmApi {

    @Headers({
            "https://fcm.googleapis.com/fcm/send",
            "Content-Type:application/json",
            "Authorization:key=AAAAb7CRzJw:APA91bEYvvSvl0D7nykrgqWAExSamy3sXHLV76aOO7iFryptnilzXxIYJZe51fGjtEhNreONqqDTfvxmBTzJ8mPw3gTgcMDm2wv4abYbLQJgXi971poLZ9EQ91Sk4tU1FiN7p5hE3Cq8"
    })
    @POST("send")
    Call<Void> send(@Body RequestBody body);

}