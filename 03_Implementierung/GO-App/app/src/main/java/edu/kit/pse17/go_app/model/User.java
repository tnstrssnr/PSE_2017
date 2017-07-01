package edu.kit.pse17.go_app.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.graphics.drawable.Icon;

import java.io.Serializable;
import java.util.List;

/**
 * Diese Klasse verwaltet User Objekte
 *
 * Created by tina on 17.06.17.
 */

@Entity
public class User implements Serializable {

    @PrimaryKey
    private String uid;
    private String instanceId;
    private String name;
    private String email;

    @Ignore
    private Icon icon;

    public User(String uid, String instanceId, String name, String email, Icon icon) {
        this.uid = uid;
        this.instanceId = instanceId;
        this.name = name;
        this.email = email;
        this.icon = icon;
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

    public Icon getIcon() {
        return icon;
    }

    public void setIcon(Icon icon) {
        this.icon = icon;
    }
}
