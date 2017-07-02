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
 *  Entity-Klasse. Anhand dieser Klasse wird eine Tabelle in der lokalen SQLite Datenbank generiert, die Go-Objekte persistiert.
 *  Der Zugriff auf die Daten läuft ausschließlich über die GoDao-Klasse
 *
 */

@Entity
public class Go {

    /**
     * ID des GOs. Das Attribut ist der Primärschlüssel der Relation und nicht nur lokal, sondern global eindeutig
     */
    @PrimaryKey
    public long id;

    /**
     * GO-Bezeichnug (frei wählbar, muss nicht eindeutig sein)
     */
    public String name;

    /**
     * Beschreibungstext des GOs
     */
    public String description;

    /**
     * Startdatum und -zeitpunkt des GOs
     */
    public Date start;

    /**
     * Enddatum und -zeitpunkt des GOs
     */
    public Date end;

    /**
     *  Die Gruppe, zu der das Go gehört
     */
    public Group group;

    /**
     * Breitengrad des GO Zielorts (Wert ist -1 falls Zielort nicht gesetzt)
     */
    public long lat;

    /**
     * Längengrad des GO Zielorts (Wert ist -1 falls Zielort nicht gesetzt)
     */
    public long lon;

    /**
     * Die User-ID des GO-Verantwortlichen
     */
    public String owner;

    /**
     * Der Benutzername des GO-Verantwortlichen
     */
    public String ownerName;

    /**
     * Der Teilnahmestatus des Benutzers
     */
    public Status userStatus;

    /**
     * Die aktuelle Position der Gruppe. Ist null, falls das GO noch nicht gestartet ist.
     */
    @Ignore
    private GroupLocation locationData;

    /**
     * Eine Liste aller Benutzer mit Teilnahmestatus "Unterwegs"
     */
    @Relation(parentColumn = "id", entityColumn = "uid")
    private List<User> notGoingUsers;

    /**
     * Eine Liste aller Benutzer mit Teilnahmestatus "Bestätigt"
     */
    @Relation(parentColumn = "id", entityColumn = "uid")
    private List<User> goingUsers;

    /**
     * Eine Liste aller Benutzer mit Teilnahmestatus "Unterwegs"
     */
    @Relation(parentColumn = "id", entityColumn = "uid")
    private List<User> goneUsers;


    public Go() {
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
