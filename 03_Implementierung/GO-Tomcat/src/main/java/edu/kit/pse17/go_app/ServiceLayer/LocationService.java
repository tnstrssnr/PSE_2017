package edu.kit.pse17.go_app.ServiceLayer;

import edu.kit.pse17.go_app.ClientCommunication.Upstream.GoRestController;
import edu.kit.pse17.go_app.PersistenceLayer.GoEntity;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Diese Klasse bietet eine Schnittstelle für den GOrestController, an die Anfragen, die den User- bzw. Gruppenstandort
 * betreffen, weitergeleitet werden können. Die Aufgabe dieser Klassen ist das Verwalten und Bearbeiten dieser
 * Anfragen.
 * <p>
 * Um die Anfragen bearbeiten zu können, bedient sich die die Klasse dem Clustering Algorithmus, der implementiert ist
 * mit der Schnittstellt, wie sie in dem Interface ClusterStrategy beschrieben ist.
 * <p>
 * Alle Programmteile, die Funktinalitäten aus dieser Klasse benötigen, stellen ihre Anfragen an statische Methoden.
 * Erst innerhalb der Klasse wird die anfrage dem richtigen LocationService-Objekt zugeordnet. Dies erlaubt eine klare
 * Trennung der Teile des GOs, die in der Datenbank verwaltet werden und denen, die in dieser Klasse verwaltet werden.
 * <p>
 * Diese Klasse ist Teil einer Implementierung des Entwurfsmusters "Strategie". Sie übernimmt die Rolle des Aufrufers,
 * d.h. sie ruft eine Implementierung einer abstrakten Strategie (hier: eine Implementierung von ClusterStrategie) auf,
 * ohne dass des dabei eine Rolle spielt, wie genau die Implementierung aussieht.
 */

public class LocationService {

    /**
     * Diese Map enthält für jedes gerade aktive GO ein LocationService Objekt, welches die alle dieses GO betreffende
     * Anfragen übernimmt.
     * <p>
     * Der Schlüssel der Map ist die ID des GOs zu dem das LocationService-Objekt gehört. Die maximale Länge der Map
     * beträgt 3000 Wertepaare.
     * <p>
     * Ein neues Objekt wird in die Liste eingefügt, wenn die getLocationService()-Methode aufgerufen wird, und dabei
     * kein passender Service gefunden wird. Die Erstellung der LocationService-Objekte findet ausschließlich in dieser
     * Klasse statt.
     */
    private static Map<Long, LocationService> activeServices;

    /**
     * Ein Clustering-Strategie, die den Algorithmus, der für das Clustering benutzt wird festlegt. Das Attribut ist
     * final, da es, nachdem es einmal festgelegt wurde nicht mehr verändert werden sollte. Ein GO sollte stattdessen
     * immer den gleichen Algorithmus benutzen.
     */
    private final ClusterStrategy strat;
    /**
     * Eine Zählvariable, um sich zu merken, wie viele neue Locations übermittlet wurden, set das letzte Mal die
     * groupLocation des GOs berechnet wurde. Die Berechnung findet nur statt, wenn diese Variable einen Wert größer als
     * 5 hat. Danach wird der Zähler wieder auf 0 gesetzt.
     * <p>
     * Sämtliche Manipulationen an diesem Attribut finden innerhalb dieser Klasse statt. NAch außen hin sit diese
     * Variable nicht sichtbar und insbesondere nicht veränderbar.
     */
    private int newLocationCounter;
    /**
     * Eine Zählvariable, um sich zu merken, wie viele verschiedene Benutzer bereits ihren Standort geteilt haben. Ist
     * diese Zahl kleiner als 3, so wird keine groupLocation berechnet. Dies dient der Anonymisierung der einzelnen
     * Benutzer, was bei einer Anzahl von UserLocations kleiner als 3 nicht mehr garantiert werden kann.
     */
    private final int userCounter;
    /**
     * Eine Liste mit den UserLocations aller Benutzer, die momentan ihren Standort mit den anderen teilen.
     * Die Länge der Liste liegt zwischen 0 und 50 UserLocation-Objekten.
     */
    List<UserLocation> activeUsers;
    /**
     * Eine Liste mit den aktuellen Clustern der Standorte des GOs. Diese Liste repräsentiert die Ergebnisse des
     * Clustering für den input "activeUsers" Die Länge der Liste liegt zwischen 0 und 50 Cluster-Objekten.
     */
    List<Cluster> groupLocation;

    /**
     * Der einzige Konstruktor diser Klasse nimmt eine Go-Entity entgegen. Sämtliche Attribute werden nur innerhalb
     * dieser Klasse gesetzt und verändert. Die default-Werte der Attribute sind:
     * <p>
     * - activeUsers: leere Liste - groupLocation: leere Liste - strat: Objekt einer Klasse, die ClusterStrategy
     * implementiert. Als threshold-Wert wird dem Konstruktor der Wert aus dem Go-Argument übergeben. -
     * newLocationCounter: 0 - userCounter: 0
     */
    public LocationService(final GoEntity go) throws IOException {
        this.activeUsers = activeUsers;
        this.groupLocation = groupLocation;
        this.userCounter = 0;
        this.strat = new GoClusterStrategy();
    }

    /**
     * Diese Methode speichert eine UserLocation in der acitveUsers Liste des entsprechenden GOs. Sie wird aufgerufen
     * von der GoRestController Klasse, wenn diese eine setLocation-Anfrage von einem Benutzer bekommt.
     * <p>
     * Bei einem Methodenaufruf wird aus der Liste der activeGos das richtige locationService Objekt ausgewählt und dort
     * die userLocation in der activeUsers Liste aktualisert. Sollte dieses Objekt in der Liste nicht existierten, wird
     * es erzeugt und der Liste hinzugefügt. In diesem Fall wird die Variable userCounter um eines erhöht, da ein neuer
     * Benutzer angefangen hat, seinen Standort zu teilen.
     * <p>
     * Bei jedem Aufruf dieser Methode wird außerdem der Wert von newLocationCounter um 1 erhöht.
     * <p>
     * Sollte das GO in der Map activeGos nicht zu finden sein, wird ein neues LocationService-Objekt erzeugt und der
     * Map hinzugefügt.
     *
     * @param goId   Die ID des GOs zu dem die Location gehört. Es muss eine gültige ID eines GOs sein. Sie wird
     *               verwendet, um den richtigen locationService aus einer statischen Map zu finden.
     * @param userId Die ID des Benutzers, zu dem der Standort gehört. Es muss sich um eine gültige Benutzer-ID
     *               handeln.
     * @param lat    Der geographische Breitengrad des Standorts des Benutzers. Der Wert muss als Breitengrad
     *               interpretierbar sein, muss also zwischen +90 und -90 liegen.
     * @param lon    Der geographische Längengrad des Standorts des Benutzers. Der Wert muss als Längengrad
     *               interpretierbar sein, muss also zwischen +180 und -180 liegen.
     */
    public static void setUserLocation(final long goId, final String userId, final double lat, final double lon) throws IOException {

        boolean validation = false;
        int index  = 0;

        if (LocationService.activeServices.get(goId) != null) {

            while (index < LocationService.activeServices.get(goId).activeUsers.size()
                    && LocationService.activeServices.get(goId).activeUsers.get(index).getUserId() != userId) {
                LocationService.activeServices.get(goId).activeUsers.get(index);
                if (LocationService.activeServices.get(goId).activeUsers.get(index).getUserId() == userId) {
                    LocationService.activeServices.get(goId).activeUsers.clear();
                    LocationService.activeServices.get(goId).activeUsers.add(new UserLocation(userId, lat, lon));
                    validation = true;
                }
                index++;
            }
            if (validation == false) {
                LocationService.activeServices.get(goId).activeUsers.add(new UserLocation(userId, lat, lon));
            }
        }

        else { LocationService.activeServices.put(goId, new LocationService(GoService.getGoById(goId))); };
    }

    /**
     * Gibt die aktuelle GroupLocation des spezifizierten GOs an den Aufrufer zurück. Aufgerufen wird diese Methode von
     * einem GoRestController, der von einem Client eine Anfrage nach der groupLocation eines GOs bekommen hat.
     * <p>
     * Bei einem Methodenaufruf wird das entsprechende GO aus der Map activeGos anhand der goId herausgesucht. Es ist
     * dabei garantiert, dass das GO in der Map zu finden ist. Aus dem locationService-Objekts des Gos wird zuerst der
     * Wert des newLocationCounters betrachtet. Ist dieser größer oder gleich 5, wird die ClusterStrategy aufgerufen und
     * die groupLocation neu berechnet. Anschließend wird der newLocationCounter zurückgesetzt und die groupLocation
     * wird zurückgegeben.
     *
     * @param goId Die ID des GOs, dessen groupLocation gesucht wird. Dabei handelt es sich um eine gültige GO-ID, die
     *             ein Schlüssel in der Map acitve GOs ist.
     * @return Eine Liste mit Cluster-Objekten. Diese stellt den aktuellen Standort der GO-Teilnehmer dar. Dabei ist die
     * Länge der Liste zwischen 0 und 50. Ist die Liste leer, hei?t das, dass noch nicht genügend Teilnehmer ihren
     * Standort übermittelt haben, um eine groupLocation ausrechnen zu können, ohne die Anonymisierungs-Vorschriften der
     * Anwendung zu verletzen.
     */
    public static List<Cluster> getGroupLocation(final long goId) {
        LocationService.activeServices.get(goId).groupLocation = null;
        GoClusterStrategy clusterStrategy = new GoClusterStrategy();
        LocationService.activeServices.get(goId).groupLocation = clusterStrategy.calculateCluster(LocationService.activeServices.get(goId).activeUsers);
        return LocationService.activeServices.get(goId).groupLocation;
    }

    public static void removeGo(final long goId) {
        if(LocationService.activeServices.get(goId) != null) {
            LocationService.activeServices.remove(goId);
        }
    }


}
