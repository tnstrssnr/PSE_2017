package edu.kit.pse17.go_app.ClientCommunication.Downstream;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

/**
 *
 */
public class FcmMessage {

    @SerializedName("to")
    String to;

    @SerializedName("data")
    Map<String, String> data;

    public FcmMessage() {
    }

    public FcmMessage(String to, Map<String, String> data) {
        this.to = to;
        this.data = data;
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
