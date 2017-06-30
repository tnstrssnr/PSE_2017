package edu.kit.pse17.go_app.PersistenceLayer;

import edu.kit.pse17.go_app.PersistenceLayer.daos.PersistentClass;

import java.io.Serializable;
import java.util.List;

/**
 * Created by tina on 30.06.17.
 */
public class GroupEntity implements Serializable, PersistentClass {

    private int ID;
    private String name;
    private String description;
    private String imagePath;
    private int memberCount;

    private List<UserEntity> members;
    private List<UserEntity> admins;
    private List<UserEntity> requests;

    public GroupEntity() {
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public int getMemberCount() {
        return memberCount;
    }

    public void setMemberCount(int memberCount) {
        this.memberCount = memberCount;
    }

    public List<UserEntity> getMembers() {
        return members;
    }

    public void setMembers(List<UserEntity> members) {
        this.members = members;
    }

    public List<UserEntity> getAdmins() {
        return admins;
    }

    public void setAdmins(List<UserEntity> admins) {
        this.admins = admins;
    }

    public List<UserEntity> getRequests() {
        return requests;
    }

    public void setRequests(List<UserEntity> requests) {
        this.requests = requests;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GroupEntity)) return false;

        GroupEntity that = (GroupEntity) o;

        if (getID() != that.getID()) return false;
        if (getMemberCount() != that.getMemberCount()) return false;
        if (!getName().equals(that.getName())) return false;
        if (!getDescription().equals(that.getDescription())) return false;
        if (!getImagePath().equals(that.getImagePath())) return false;
        if (!getMembers().equals(that.getMembers())) return false;
        if (!getAdmins().equals(that.getAdmins())) return false;
        return getRequests().equals(that.getRequests());
    }

    @Override
    public int hashCode() {
        int result = getID();
        result = 31 * result + getName().hashCode();
        result = 31 * result + getDescription().hashCode();
        result = 31 * result + getImagePath().hashCode();
        result = 31 * result + getMemberCount();
        result = 31 * result + getMembers().hashCode();
        result = 31 * result + getAdmins().hashCode();
        result = 31 * result + getRequests().hashCode();
        return result;
    }
}
