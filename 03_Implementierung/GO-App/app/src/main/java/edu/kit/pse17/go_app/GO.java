package edu.kit.pse17.go_app;

import android.location.Location;
import android.support.annotation.Nullable;

import java.util.Date;

import edu.kit.pse17.go_app.User;

/**
 * Diese Klasse verwaltet GO Objekte
 * Created by tina on 17.06.17.
 */

public class GO {

    private String name;
    private String description;
    private Date start;
    private Date end;
    private Location location;
    private User owner;

    /**
     * Konstruktor
     * @param name GO Bezeichnung
     * @param description Go Beschreibung
     * @param start Startzeitpunkt
     * @param end Endzeitpunkt
     * @param location Treffpunkt (kann null sein)
     * @param owner GO-Verantwortlicher
     */
    public GO(String name, String description, Date start, Date end, @Nullable Location location, User owner) {
        this.name = name;
        this.description = description;
        this.start = start;
        this.end = end;
        this.location = location;
        this.owner = owner;
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
}
