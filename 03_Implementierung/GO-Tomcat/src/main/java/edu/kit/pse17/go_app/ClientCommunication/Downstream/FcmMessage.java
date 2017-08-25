package edu.kit.pse17.go_app.ClientCommunication.Downstream;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

/**
 * Hilfsklasse zur Erzeugung des Requestbodys der API Calls zum Firebase Server. Wird mit Gson automatisch in das
 * richtige JSON-Format geparst.
 */
public class FcmMessage {

    /**
     * InstanceId des Empfängers der Nachricht
     */
    @SerializedName("to")
    private String to;

    /**
     * Daten, die an den CLient geschickt werden sollen
     */
    @SerializedName("data")
    private Map<String, String> data;

    @SerializedName("notification")
    private Map<String, String> notification;

    public FcmMessage() {
    }

    public FcmMessage(String to, Map<String, String> data, Map<String, String> notification) {
        this.to = to;
        this.data = data;
        this.notification = notification;
    }

    public Map<String, String> getNotification() {
        return notification;
    }

    public void setNotification(Map<String, String> notification) {
        this.notification = notification;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public Map<String, String> getData() {
        return data;
    }

    public void setData(Map<String, String> data) {
        this.data = data;
    }
}
