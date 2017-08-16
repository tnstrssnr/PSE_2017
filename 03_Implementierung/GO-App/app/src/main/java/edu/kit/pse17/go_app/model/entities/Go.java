package edu.kit.pse17.go_app.model.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

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
    @SerializedName("goId")
    private long id;

    /**
     * GO-Bezeichnug (frei wählbar, muss nicht eindeutig sein)
     */
    @SerializedName("name")
    @Expose
    private String name;

    /**
     * Beschreibungstext des GOs
     */
    @SerializedName("description")
    @Expose
    private String description;

    /**
     * Startdatum und -zeitpunkt des GOs
     */
    @SerializedName("start")
    @Expose
    private String start;

    /**
     * Enddatum und -zeitpunkt des GOs
     */
    @SerializedName("end")
    @Expose
    private String end;

    /**
     *  Die Gruppe, zu der das Go gehört
     */
    @SerializedName("group")
    @Expose
    private Group group;

    /**
     * Breitengrad des GO Zielorts (Wert ist -1 falls Zielort nicht gesetzt)
     */
    @SerializedName("lat")
    private double desLat;

    /**
     * Längengrad des GO Zielorts (Wert ist -1 falls Zielort nicht gesetzt)
     */
    @SerializedName("lon")
    private double desLon;

    /**
     * Die User-ID des GO-Verantwortlichen
     */
    @SerializedName("userId")
    private String ownerId;

    /**
     * Der Benutzername des GO-Verantwortlichen
     */
    @SerializedName("ownerName")
    @Expose
    private String ownerName;

    /**
     * Eine Liste mit dem UserStatus jedes Gruppenmitglieds der Gruppe des GOs
     */
    @SerializedName("participantsList")
    @Expose
    private List<UserGoStatus> participantsList;

    @SerializedName("locations")
    @Expose
    private List<Cluster> locations;

    public Go(long id, String name, String description, String start, String end, Group group, double desLat, double desLon, String ownerId, String ownerName, List<UserGoStatus> participantsList, List<Cluster> locations) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.start = start;
        this.end = end;
        this.group = group;
        this.desLat = desLat;
        this.desLon = desLon;
        this.ownerId = ownerId;
        this.ownerName = ownerName;
        this.participantsList = participantsList;
        this.locations = locations;
    }

    public Go() {
        this.name = "Default Go Name";
        this.participantsList = new ArrayList<>();
        this.locations = new ArrayList<>();
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

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public double getDesLat() {
        return desLat;
    }

    public void setDesLat(double lat) {
        this.desLat = lat;
    }

    public double getDesLon() {
        return desLon;
    }

    public void setDesLon(double lon) {
        this.desLon = lon;
    }

    public String getOwner() {
        return ownerId;
    }

    public void setOwner(String owner) {
        this.ownerId = owner;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public List<UserGoStatus> getParticipantsList() {
        return participantsList;
    }

    public void setParticipantsList(List<UserGoStatus> participantsList) {
        this.participantsList = participantsList;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public List<Cluster> getLocations() {
        return locations;
    }

    public void setLocations(List<Cluster> locations) {
        this.locations = locations;
    }
}
