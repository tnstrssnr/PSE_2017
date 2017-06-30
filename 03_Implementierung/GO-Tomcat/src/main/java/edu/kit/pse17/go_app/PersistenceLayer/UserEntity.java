package edu.kit.pse17.go_app.PersistenceLayer;

import java.util.List;

/**
 * Created by tina on 30.06.17.
 */
public class UserEntity {

    private String uid;
    private String instanceId;
    private String name;
    private String email;
    private String imagePath;


    public UserEntity() {
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

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserEntity)) return false;

        UserEntity that = (UserEntity) o;

        if (!getUid().equals(that.getUid())) return false;
        if (!getInstanceId().equals(that.getInstanceId())) return false;
        if (!getName().equals(that.getName())) return false;
        if (!getEmail().equals(that.getEmail())) return false;
        return getImagePath().equals(that.getImagePath());
    }

    @Override
    public int hashCode() {
        int result = getUid().hashCode();
        result = 31 * result + getInstanceId().hashCode();
        result = 31 * result + getName().hashCode();
        result = 31 * result + getEmail().hashCode();
        result = 31 * result + getImagePath().hashCode();
        return result;
    }
}
