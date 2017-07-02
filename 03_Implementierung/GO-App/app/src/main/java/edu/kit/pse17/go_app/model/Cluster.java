package edu.kit.pse17.go_app.model;

import android.location.Location;

/**
 * Created by tina on 02.07.17.
 */

public class Cluster {

    private long lat;
    private long lon;
    private int size;

    public Cluster(long lat, long lon, int size) {
        this.lat = lat;
        this.lon = lon;
        this.size = size;
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

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
