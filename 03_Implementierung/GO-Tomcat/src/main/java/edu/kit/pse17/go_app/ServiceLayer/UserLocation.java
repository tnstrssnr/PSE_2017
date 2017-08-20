package edu.kit.pse17.go_app.ServiceLayer;


/**
 * Bei dieser Klasse handelt es sich um eine Datenhaltungsklasse, die dem Clustering-Algorithmus das hantieren mit den
 * Standorten erleichtert. Ein Objekt dieser Klasse beschreibt dabei einen Benutzerstandort.
 */
public class UserLocation {

    /**
     * Die Id des Benutzers, um dessen Standort es sich handelt. Es handelt sich um eine gültige Benutzer-Id, die von
     * anderen Klassen der Anwendung erkannt werden kann.
     */
    private String userId;

    /**
     * Der geographische Breitengrad des Standorts des Benutzers. Der Wert muss als Breitengrad interpretierbar sein,
     * muss also zwischen +90 und -90 liegen.
     */
    private double lat;

    /**
     * Der geographische Längengrad des Standorts des Benutzers. Der Wert muss als Längengrad interpretierbar sein,
     * muss also zwischen +180 und -180 liegen.
     */
    private double lon;

    /**
     * Ein Konstruktor, der sämtliche Attribute der Klasse als Argumente entgegennimmt und daraus ein Objekt erzeugt.
     *
     * @param userId Die Id des Benutzers, um dessen Standort es sich handelt. Es handelt sich um eine gültige
     *               Benutzer-Id, die von anderen Klassen der Anwendung erkannt werden kann.
     * @param lat    Der geographische Breitengrad des Standorts des Benutzers. Der Wert muss als Breitengrad
     *               interpretierbar sein, muss also zwischen +90 und -90 liegen.
     * @param lon    Der geographische Längengrad des Standorts des Benutzers. Der Wert muss als Längengrad
     *               interpretierbar sein, muss also zwischen +180 und -180 liegen.
     */
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
