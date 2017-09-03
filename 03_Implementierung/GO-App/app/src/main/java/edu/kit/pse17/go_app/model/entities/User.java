package edu.kit.pse17.go_app.model.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Entity-Class. On the basis of this class, a table is generated
 * in the local SQLite database, which User objects are persisted.
 * Access to the data is possible only through the UserEntityDAO-class.
 */

@Entity(tableName = "users")
public class User implements Serializable {

    /**
     * The User ID of the user. This attribute is the primary key of the
     * Relation. The ID is in the registry Firebase Authentication Service.
     */
    @PrimaryKey
    @SerializedName("userId")
    private String uid;

    /**
     * The Instance-ID of a user and the Android device is used
     * by the Tomcat Server to send notifications to the device.
     * Generated by Firebase Cloud Messaging Service.
     */
    @SerializedName("instanceId")
    @Expose
    private String instanceId;

    /**
     * User name of the User. This can be freely chosen and need not be unique.
     * The default value for this is the Email address of the user.
     */
    @SerializedName("name")
    @Expose
    private String name;

    /**
     * The e-mail address of the user registered in the App.
     */
    @SerializedName("email")
    @Expose
    private String email;

    public User(String uid, String email, String name) {
        this.uid = uid;
        this.name = name;
        this.email = email;
    }

    public User(String uid, String email, String name, String instanceId) {
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
