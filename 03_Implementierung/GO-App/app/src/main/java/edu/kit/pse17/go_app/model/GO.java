package edu.kit.pse17.go_app.model;

import android.location.Location;
import android.support.annotation.Nullable;

import java.util.Date;
import java.util.List;

/**
 * Diese Klasse verwaltet GO Objekte
 *
 * Created by tina on 17.06.17.
 */

public class GO {

    private long ID;
    private String name;
    private String description;
    private Date start;
    private Date end;
    private long lat;
    private long lon;
    private User owner;
    private GroupLocation locationData;

    private List<User> notGoingUsers;
    private List<User> goingUsers;
    private List<User> goneUsers;

    /**
     * Konstruktor
     * @param ID eindeutige Nummer, mit der ein GO identifiziert werden kann
     * @param name GO Bezeichnung
     * @param description Go Beschreibung
     * @param start Startzeitpunkt
     * @param end Endzeitpunkt
     * @param location Treffpunkt (kann null sein)
     * @param lat
     * @param lon
     * @param owner GO-Verantwortlicher
     * @param locationData Die GPS-Daten der Go-Teilnehmer
     * @param notGoingUsers
     * @param goingUsers
     * @param goneUsers
     */
    public GO(long ID, String name, String description, Date start, Date end, @Nullable Location location, long lat, long lon, User owner, GroupLocation locationData, List<User> notGoingUsers, List<User> goingUsers, List<User> goneUsers) {
        this.ID = ID;
        this.name = name;
        this.description = description;
        this.start = start;
        this.end = end;
        this.lat = lat;
        this.lon = lon;
        this.owner = owner;
        this.locationData = locationData;
        this.notGoingUsers = notGoingUsers;
        this.goingUsers = goingUsers;
        this.goneUsers = goneUsers;
    }

    /**
     * erzeugt ein neues GO-Objekt und speichert die GO-Daten in der Datenbank auf dem Tomcat Server
     *
     * @return  das neue GO
     */
    public static GO createGO() {
        return null;
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

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public boolean isOwner(User user) {
        return false;
    }

    public List<User> getGoingUsers() {
        return null;
    }

    public List<User> getGoneUsers() {
        return null;
    }

    private void onDataChanged() {

    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
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

    public void setGoingUsers(List<User> goingUsers) {
        this.goingUsers = goingUsers;
    }

    public void setGoneUsers(List<User> goneUsers) {
        this.goneUsers = goneUsers;
    }

    public Status getUserStatus(User user) {
        return null;
    }
}
