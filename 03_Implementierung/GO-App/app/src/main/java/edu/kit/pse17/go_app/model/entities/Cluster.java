package edu.kit.pse17.go_app.model.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * The objects of this class represent the Cluster. This will be displayed
 * to the user during a GO on the map.
 *
 * In contrast to the other Entity-classes, this is not saved in the
 * local SQLite database, as a long-term availability of the data is not needed.
 *
 * The class uses Gson to parse the Json-objects that are sent and received
 * via Retrofit.
 */
public class Cluster {

    /**
     * The geographical latitude of the position of the cluster.
     */
    @SerializedName("lat")
    @Expose
    private double lat;

    /**
     * The geographical longitude of the position of the cluster.
     */
    @SerializedName("lon")
    @Expose
    private double lon;

    /**
     * The size of the cluster, i.e. the number of people
     * to be counted on this Cluster.
     */
    @SerializedName("size")
    @Expose
    private int size;


    public Cluster() {
    }

    public Cluster(double lat, double lon, int size) {
        this.lat = lat;
        this.lon = lon;
        this.size = size;
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

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
