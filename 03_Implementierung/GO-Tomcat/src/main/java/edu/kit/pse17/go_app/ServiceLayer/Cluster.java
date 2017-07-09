package edu.kit.pse17.go_app.ServiceLayer;

/**
 * Bei dieser Klasse handelt es sich um eien Datenhaltungsklasse, die dem Clustering-Algoeithmus das hantieren mit den Standorten erleichtert.
 * Ein Objekt dieser Klasse beschreibt dabei ein Cluster, bestehend aus mehreren GO-Teilnehmern, die sich nahe genug beieinander befinden, um von
 * dem benutzten Clustering-Algorithmus als Cluster erkannt worden zu sein.
 */
public class Cluster {

    /**
     * Anzahl an Personen, die sich in dem Cluster befinden. Der Wert liegt zwischen 1 und 60 Teilnehmern.
     */
    private int size;

    /**
     * Der geographische Breitengrad des Standorts des Clusters. Der Wert muss als Breitengrad interpretierbar sein,
     * muss also zwischen +90 und -90 liegen.
     */
    private long lat;

    /**
     * Der geographische Längengrad des Standorts des Clusters. Der Wert muss als Längengrad interpretierbar sein,
     * muss also zwischen +180 und -180 liegen.
     */
    private long lon;

    /**
     * Ein Konstruktor, der sämtliche Attribute der Klasse entgegen nimmt.
     * @param size Die Anzahl der Personen in diesem Cluster. Es handelt sich dabei um eine Zahl twischen 1 und 50
     * @param lat Der geographische Breitengrad des Standorts des Clusters. Der Wert muss als Breitengrad interpretierbar sein,
     * muss also zwischen +90 und -90 liegen.
     * @param lon Der geographische Längengrad des Standorts des Clusters. Der Wert muss als Längengrad interpretierbar sein,
     * muss also zwischen +180 und -180 liegen.
     */
    public Cluster(int size, long lat, long lon) {
        this.size = size;
        this.lat = lat;
        this.lon = lon;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
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
}
