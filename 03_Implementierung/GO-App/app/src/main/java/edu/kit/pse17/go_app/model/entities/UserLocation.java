package edu.kit.pse17.go_app.model.entities;

/**
 * Created by Vovas on 19.08.2017.
 */

/**
 * This class represents the location of the user.
 */
public class UserLocation {

    /**
     * ID of the user.
     */
    private String userId;

    /**
     * Latitude.
     */
    private double lat;

    /**
     * Longitude.
     */
    private double lon;

    public UserLocation(String userId, double lat, double lon) {
        this.userId = userId;
        this.lat = lat;
        this.lon = lon;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }
}
