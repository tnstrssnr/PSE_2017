package edu.kit.pse17.go_app.PersistenceLayer.clientEntities;

import com.google.gson.annotations.SerializedName;

/**
 * Die User-Klasse beschreibt die User-Objekte, welche es uns erlauben sämtliche potentiellen Information zu einem User zusammenzufassen..
 */

public class User {

    @SerializedName("userId")
    private String uid;

    @SerializedName("instanceId")
    private String instanceId;

    @SerializedName("name")
    private String name;

    @SerializedName("email")
    private String email;

    public User(String uid, String name, String email) {
        this.uid = uid;
        this.name = name;
        this.email = email;
    }

    public User(String uid, String instanceId, String name, String email) {
        this.uid = uid;
        this.instanceId = instanceId;
        this.name = name;
        this.email = email;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
