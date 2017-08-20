package edu.kit.pse17.go_app.ServiceLayer;

/**
 * Bei dieser Klasse handelt es sich um eine Datenhaltungsklasse, die dem Clustering-Algorithmus das hantieren mit den
 * Standorten erleichtert. Ein Objekt dieser Klasse beschreibt dabei ein Cluster, bestehend aus mehreren Go-Teilnehmern,
 * die sich nahe genug beieinander befinden, um von dem benutzten Clustering-Algorithmus als Cluster erkannt worden zu
 * sein.
 */
public class Cluster {

    /**
     * Anzahl an Personen, die sich in dem Cluster befinden. Der Wert liegt zwischen 1 und 50 Teilnehmern.
     */
    private int size;

    /**
     * Der geographische Breitengrad des Standorts des Clusters. Der Wert muss als Breitengrad interpretierbar sein,
     * muss also zwischen +90 und -90 liegen. Standort bezieht sich auf das Zentrum des Clusters.
     */
    private double lat;

    /**
     * Der geographische Längengrad des Standorts des Clusters. Der Wert muss als Längengrad interpretierbar sein,
     * muss also zwischen +180 und -180 liegen. Standort bezieht sich auf das Zentrum des Clusters.
     */
    private double lon;

    /**
     * Radius(als Kreis) des Clusters auf einer Karte. Dies dient der Anonymisierung der einzelnen Punkte/Standorte der User.
     * Dieser Wert muss anhand vom Maßstab der Karte interpretiert werden.
     */

    private double radius;

    /**
     * Ein Konstruktor, der sämtliche Attribute der Klasse entgegen nimmt.
     *
     * @param size      Die Anzahl der Personen in diesem Cluster. Es handelt sich dabei um eine Zahl twischen 1 und 50
     * @param lat       Der geographische Breitengrad des Standorts des Clusters. Der Wert muss als Breitengrad
     *                   interpretierbar sein, muss also zwischen +90 und -90 liegen.
     * @param lon       Der geographische Längengrad des Standorts des Clusters. Der Wert muss als Längengrad interpretierbar
     *                   sein, muss also zwischen +180 und -180 liegen.
     * @param radius    Der Radius dieses Clusters. Ist abhängig von dem Punkt/Nutzer der am weitesten vom Zentrum des Clusters entfernt ist.
     */
    public Cluster(int size, double lat, double lon, double radius) {
        this.size = size;
        this.lat = lat;
        this.lon = lon;
        this.radius = radius;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
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

    public double getRadius() { return radius; }

    public void setRadius(int radius) { this.radius = radius; }
}
