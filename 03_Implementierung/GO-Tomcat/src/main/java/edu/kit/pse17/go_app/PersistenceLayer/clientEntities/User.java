package edu.kit.pse17.go_app.PersistenceLayer.clientEntities;

import com.google.gson.annotations.SerializedName;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Die User-Klasse beschreibt die User-Objekte, welche es uns erlauben sämtliche potentiellen Information zu einem User
 * zusammenzufassen..
 */

@Entity
public class User {

    @SerializedName("userId")
    @Column(name = "userId")
    @Id
    private String uid;

    @SerializedName("instanceId")
    @Column
    private String instanceId;

    @SerializedName("name")
    @Column
    private String name;

    @SerializedName("email")
    @Column
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

    public User() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        if (getUid() != null ? !getUid().equals(user.getUid()) : user.getUid() != null) return false;
        if (getInstanceId() != null ? !getInstanceId().equals(user.getInstanceId()) : user.getInstanceId() != null)
            return false;
        if (getName() != null ? !getName().equals(user.getName()) : user.getName() != null) return false;
        return getEmail() != null ? getEmail().equals(user.getEmail()) : user.getEmail() == null;
    }

    @Override
    public int hashCode() {
        int result = getUid() != null ? getUid().hashCode() : 0;
        result = 31 * result + (getInstanceId() != null ? getInstanceId().hashCode() : 0);
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getEmail() != null ? getEmail().hashCode() : 0);
        return result;
    }
}
