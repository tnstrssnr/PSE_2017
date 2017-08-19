package edu.kit.pse17.go_app.serverCommunication.upstream;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.DateFormat;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Сеня on 21.07.2017.
 */

/**
 * The client to the Tomcat REST API Interface. It contains of the BASE URL
 * of the server and object of the Retrofit class.
 *
 * The Retrofit class is singleton.
 */
public class TomcatRestApiClient {

    /**
     * Base URL of the server
     */
    public static final String BASE_URL = "http://i43pc164.ipd.kit.edu/PSESoSe17Gruppe3/go-tomcat/";
    //public static final String BASE_URL = "141.3.211.32:8080/go-tomcat/";

    /**
     * Retrofit singleton
     */
    private static Retrofit retrofit = null;

    public static Gson gson;

    /**
     * Method that builds and returns the Retrofit object (with GSON converter)
     *
     * @return Retrofit object
     */
    public static Retrofit getClient() {
        if (retrofit == null) {
            gson = new GsonBuilder()
                    //.registerTypeAdapter(Group.class, Deserializer.getDeserializer())
                    //.addSerializationExclusionStrategy(Serializer.getGroupExclusionStrategy())
                    .enableComplexMapKeySerialization()
                    .serializeNulls()
                    .setDateFormat(DateFormat.LONG)
                    .setPrettyPrinting()
                    .setVersion(1.0)
                    .create();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }
}