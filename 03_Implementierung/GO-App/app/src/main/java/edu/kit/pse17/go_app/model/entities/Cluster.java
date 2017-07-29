package edu.kit.pse17.go_app.model.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Die Objekte dieser Klasse repräsentieren die Cluster, die dem Benutzer während eines GOs auf der Karte
 * angezeigt werden.
 *
 * Im Gegensatz zu den anderen Entity-Klassen, wird diese Klasse nicht von Room in der lokalen SQLite Datenbank gespeichert,
 * da eine langfristige Verfügbarkeit der Daten nicht benötigt wird.
 *
 * Die Klasse dient Gson als Vorlage zum Parsen der Gso-objekte die via Retrofit gesendet und empfangen werden.
 */

public class Cluster {

    /**
     * Der geographische Breitengrad der Position des Clusters
     */
    @SerializedName("lat")
    @Expose
    private long lat;

    /**
     * Der geogrgaphische Längengrad der Position des Clusters
     */
    @SerializedName("lon")
    @Expose
    private long lon;

    /**
     * Die Größe des Clusters, sprich die Anzahl der Personen, die zu diesem Cluster gezählt werden
     */
    @SerializedName("size")
    @Expose
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
