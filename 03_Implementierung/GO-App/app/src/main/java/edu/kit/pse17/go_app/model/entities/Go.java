package edu.kit.pse17.go_app.model.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;
import java.util.List;

import edu.kit.pse17.go_app.model.Status;

/**
 *  Entity-Klasse. Anhand dieser Klasse wird eine Tabelle in der lokalen SQLite Datenbank generiert, die Go-Objekte persistiert.
 *  Der Zugriff auf die Daten läuft ausschließlich über die GoEntityDAO-Klasse
 *
 */

@Entity(tableName = "gos")
public class Go {

    /**
     * ID des GOs. Das Attribut ist der Primärschlüssel der Relation und nicht nur lokal, sondern global eindeutig
     */
    @PrimaryKey
    private long id;

    /**
     * GO-Bezeichnug (frei wählbar, muss nicht eindeutig sein)
     */
    private String name;

    /**
     * Beschreibungstext des GOs
     */
    private String description;

    /**
     * Startdatum und -zeitpunkt des GOs
     */
    private Date start;

    /**
     * Enddatum und -zeitpunkt des GOs
     */
    private Date end;

    /**
     *  Die Gruppe, zu der das Go gehört
     */
    private Group group;

    /**
     * Breitengrad des GO Zielorts (Wert ist -1 falls Zielort nicht gesetzt)
     */
    private long desLat;

    /**
     * Längengrad des GO Zielorts (Wert ist -1 falls Zielort nicht gesetzt)
     */
    private long desLon;

    /**
     * Die User-ID des GO-Verantwortlichen
     */
    private String owner;

    /**
     * Der Benutzername des GO-Verantwortlichen
     */
    private String ownerName;

    /**
     * Der Teilnahmestatus des Benutzers
     */
    private Status userStatus;

    /**
     * Eine Liste mit dem UserStatus jedes Gruppenmitglieds der Gruppe des GOs
     */
    private List<UserGoStatus> participantsList;

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

    public long getDesLat() {
        return desLat;
    }

    public void setDesLat(long lat) {
        this.desLat = lat;
    }

    public long getDesLon() {
        return desLon;
    }

    public void setDesLon(long lon) {
        this.desLon = lon;
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

    public List<UserGoStatus> getParticipantsList() {
        return participantsList;
    }

    public void setParticipantsList(List<UserGoStatus> participantsList) {
        this.participantsList = participantsList;
    }
}
