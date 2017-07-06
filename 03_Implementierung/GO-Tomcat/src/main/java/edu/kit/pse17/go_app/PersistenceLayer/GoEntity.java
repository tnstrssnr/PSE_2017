package edu.kit.pse17.go_app.PersistenceLayer;

import edu.kit.pse17.go_app.PersistenceLayer.daos.PersistentClass;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by tina on 30.06.17.
 */
public class GoEntity implements Serializable, PersistentClass {

    private long ID;
    private String name;
    private String description;
    private Date start;
    private Date end;
    private long lat;
    private long lon;
    private List<UserEntity> notGoingUsers;
    private List<UserEntity> goingUsers;
    private List<UserEntity> goneUsers;

    public GoEntity() {

    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
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

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public long getLat() {
        return lat;
    }

    public void setLat(long lat) {
        this.lat = lat;
    }

    public long getLon() {
        return lon;
    }

    public void setLon(long lon) {
        this.lon = lon;
    }

    public List<UserEntity> getNotGoingUsers() {
        return notGoingUsers;
    }

    public void setNotGoingUsers(List<UserEntity> notGoingUsers) {
        this.notGoingUsers = notGoingUsers;
    }

    public List<UserEntity> getGoingUsers() {
        return goingUsers;
    }

    public void setGoingUsers(List<UserEntity> goingUsers) {
        this.goingUsers = goingUsers;
    }

    public List<UserEntity> getGoneUsers() {
        return goneUsers;
    }

    public void setGoneUsers(List<UserEntity> goneUsers) {
        this.goneUsers = goneUsers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GoEntity)) return false;

        GoEntity goEntity = (GoEntity) o;

        if (getID() != goEntity.getID()) return false;
        if (getLat() != goEntity.getLat()) return false;
        if (getLon() != goEntity.getLon()) return false;
        if (!getName().equals(goEntity.getName())) return false;
        if (!getDescription().equals(goEntity.getDescription())) return false;
        if (!getStart().equals(goEntity.getStart())) return false;
        if (!getEnd().equals(goEntity.getEnd())) return false;
        if (!getNotGoingUsers().equals(goEntity.getNotGoingUsers())) return false;
        if (!getGoingUsers().equals(goEntity.getGoingUsers())) return false;
        return getGoneUsers().equals(goEntity.getGoneUsers());
    }

    @Override
    public int hashCode() {
        int result = (int) (getID() ^ (getID() >>> 32));
        result = 31 * result + getName().hashCode();
        result = 31 * result + getDescription().hashCode();
        result = 31 * result + getStart().hashCode();
        result = 31 * result + getEnd().hashCode();
        result = 31 * result + (int) (getLat() ^ (getLat() >>> 32));
        result = 31 * result + (int) (getLon() ^ (getLon() >>> 32));
        result = 31 * result + getNotGoingUsers().hashCode();
        result = 31 * result + getGoingUsers().hashCode();
        result = 31 * result + getGoneUsers().hashCode();
        return result;
    }
}
