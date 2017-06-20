package edu.kit.pse17.go_app.Model;

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

    private int ID;
    private String name;
    private String description;
    private Date start;
    private Date end;
    private Location location;
    private User owner;
    private GroupLocation locationData;

    /**
     * Konstruktor
     * @param ID eindeutige Nummer, mit der ein GO identifiziert werden kann
     * @param name GO Bezeichnung
     * @param description Go Beschreibung
     * @param start Startzeitpunkt
     * @param end Endzeitpunkt
     * @param location Treffpunkt (kann null sein)
     * @param owner GO-Verantwortlicher
     * @param locationData Die GPS-Daten der Go-Teilnehmer
     */
    public GO(int ID, String name, String description, Date start, Date end, @Nullable Location location, User owner, GroupLocation locationData) {
        this.ID = ID;
        this.name = name;
        this.description = description;
        this.start = start;
        this.end = end;
        this.location = location;
        this.owner = owner;
        this.locationData = locationData;
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

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
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

    public Status getUserStatus(User user) {
        return null;
    }
}
