package edu.kit.pse17.go_app.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.Relation;
import android.location.Location;
import android.support.annotation.Nullable;

import java.util.Date;
import java.util.List;

/**
 * Diese Klasse verwaltet Go Objekte
 *
 * Created by tina on 17.06.17.
 */

@Entity
public class Go {

    @PrimaryKey
    public long id;
    public String name;
    public String description;
    public Date start;
    public Date end;
    public long lat;
    public long lon;
    public String owner;
    public String ownerName;
    public Status userStatus;

    @Ignore
    private GroupLocation locationData;

    @Relation(parentColumn = "id", entityColumn = "uid")
    private List<User> notGoingUsers;

    @Relation(parentColumn = "id", entityColumn = "uid")
    private List<User> goingUsers;

    @Relation(parentColumn = "id", entityColumn = "uid")
    private List<User> goneUsers;


    public Go(long id, String name, String description, Date start, Date end, long lat, long lon,
              String owner, String ownerName, Status userStatus, GroupLocation locationData,
              List<User> notGoingUsers, List<User> goingUsers, List<User> goneUsers) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.start = start;
        this.end = end;
        this.lat = lat;
        this.lon = lon;
        this.owner = owner;
        this.ownerName = ownerName;
        this.userStatus = userStatus;
        this.locationData = locationData;
        this.notGoingUsers = notGoingUsers;
        this.goingUsers = goingUsers;
        this.goneUsers = goneUsers;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public Status getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(Status userStatus) {
        this.userStatus = userStatus;
    }

    public GroupLocation getLocationData() {
        return locationData;
    }

    public void setLocationData(GroupLocation locationData) {
        this.locationData = locationData;
    }

    public List<User> getNotGoingUsers() {
        return notGoingUsers;
    }

    public void setNotGoingUsers(List<User> notGoingUsers) {
        this.notGoingUsers = notGoingUsers;
    }

    public List<User> getGoingUsers() {
        return goingUsers;
    }

    public void setGoingUsers(List<User> goingUsers) {
        this.goingUsers = goingUsers;
    }

    public List<User> getGoneUsers() {
        return goneUsers;
    }

    public void setGoneUsers(List<User> goneUsers) {
        this.goneUsers = goneUsers;
    }
}
