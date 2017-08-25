package edu.kit.pse17.go_app.ServiceLayer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Diese Klasse bietet eine Schnittstelle für den GoRestController, an die Anfragen, die den User- bzw. Gruppenstandort
 * betreffen, weitergeleitet werden können. Die Aufgabe dieser Klassen ist das Verwalten und Bearbeiten dieser
 * Anfragen.
 * <p>
 * Um die Anfragen bearbeiten zu können, bedient sich die die Klasse dem Clustering Algorithmus, der implementiert ist
 * mit der Schnittstelle, wie sie in dem Interface ClusterStrategy beschrieben ist.
 * <p>
 * Alle Programmteile, die Funktionalitäten aus dieser Klasse benötigen, stellen ihre Anfragen an statische Methoden.
 * Erst innerhalb der Klasse wird die Anfrage dem richtigen LocationService-Objekt zugeordnet. Dies erlaubt eine klare
 * Trennung der Teile des GOs, die in der Datenbank verwaltet werden und denen, die in dieser Klasse verwaltet werden.
 * <p>
 * Diese Klasse ist Teil einer Implementierung des Entwurfsmusters "Strategie". Sie übernimmt die Rolle des Aufrufers,
 * d.h. sie ruft eine Implementierung einer abstrakten Strategie (hier: eine Implementierung von ClusterStrategie) auf,
 * ohne dass des dabei eine Rolle spielt, wie genau die Implementierung aussieht.
 */

public class LocationService {

    /**
     * Diese Map enthält für jedes gerade aktive GO ein LocationService Objekt, welches alle Anfragen betreffend
     * dieses Go übernimmt.
     * <p>
     * Der Schlüssel der Map ist die Id des GOs zu dem das LocationService-Objekt gehört. Die maximale Länge der Map
     * beträgt 3000 Wertepaare.
     * <p>
     * Ein neues Objekt wird in die Liste eingefügt, wenn die getLocationService()-Methode aufgerufen wird, und dabei
     * kein passender Service gefunden wird. Die Erstellung der LocationService-Objekte findet ausschließlich in dieser
     * Klasse statt.
     */
    private static Map<Long, LocationService> activeServices = new HashMap<>();

    /**
     * Ein Clustering-Strategie, die den Algorithmus, der für das Clustering benutzt wird festlegt. Das Attribut ist
     * final, da es, nachdem es einmal festgelegt wurde nicht mehr verändert werden sollte. Ein GO sollte stattdessen
     * immer den gleichen Algorithmus benutzen.
     */
    private final ClusterStrategy strat;

    /**
     * Eine Liste mit den UserLocations aller Benutzer, die momentan ihren Standort mit den anderen teilen.
     * Die Länge der Liste liegt zwischen 0 und 50 UserLocation-Objekten.
     */
    List<UserLocation> activeUsers;
    /**
     * Eine Liste mit den aktuellen Clustern der Standorte des Gos. Diese Liste repräsentiert die Ergebnisse des
     * Clustering für den input "activeUsers" Die Länge der Liste liegt zwischen 0 und 50 Cluster-Objekten.
     */
    List<Cluster> groupLocation;
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
     * Der einzige Konstruktor diser Klasse nimmt eine Go-Entity entgegen. Sämtliche Attribute werden nur innerhalb
     * dieser Klasse gesetzt und verändert. Die default-Werte der Attribute sind:
     * <p>
     * - activeUsers: leere Liste - groupLocation: leere Liste - strat: Objekt einer Klasse, die ClusterStrategy
     * implementiert. Als threshold-Wert wird dem Konstruktor der Wert aus dem Go-Argument übergeben. -
     * newLocationCounter: 0 - userCounter: 0
     */
    public LocationService() throws IOException {
        this.activeUsers = new ArrayList<>();
        this.groupLocation = new ArrayList<>();
        this.strat = new GoClusterStrategy();
    }

    /**
     * Diese Methode speichert eine UserLocation in der acitveUsers Liste des entsprechenden Gos. Sie wird aufgerufen
     * von der GoRestController Klasse, wenn diese eine setLocation-Anfrage von einem Benutzer bekommt.
     * <p>
     * Bei einem Methodenaufruf wird aus der Liste der activeGos das richtige locationService Objekt ausgewählt und dort
     * die Liste der aktiven User zurückgesetzt und neu eingetragen. Da die User in regelmäßigen Abständen die
     * getLocations abrufen ist gewährleistet, dass jeder User einen vollen Zeitintervall Zeit hat um seine Location zu
     * setten. Sollte dieses Objekt in der Liste nicht existierten, wird es erzeugt und der Liste hinzugefügt.
     * <p>
     * Sollte das Go in der Map activeGos nicht zu finden sein, wird ein neues LocationService-Objekt erzeugt und der
     * Map hinzugefügt.
     *
     * @param goId   Die Id des Gos zu dem die Location gehört. Es muss eine gültige Id eines Gos sein. Sie wird
     *               verwendet, um den richtigen locationService aus einer statischen Map zu finden.
     * @param userId Die Id des Benutzers, zu dem der Standort gehört. Es muss sich um eine gültige Benutzer-Id
     *               handeln.
     * @param lat    Der geographische Breitengrad des Standorts des Benutzers. Der Wert muss als Breitengrad
     *               interpretierbar sein, muss also zwischen +90 und -90 liegen.
     * @param lon    Der geographische Längengrad des Standorts des Benutzers. Der Wert muss als Längengrad
     *               interpretierbar sein, muss also zwischen +180 und -180 liegen.
     */
    public static void setUserLocation(final long goId, final String userId, final double lat, final double lon) throws IOException {

        boolean validation = false;
        int index = 0;

        if (LocationService.activeServices.get(goId) != null) {

            while (index < LocationService.activeServices.get(goId).activeUsers.size()
                    && LocationService.activeServices.get(goId).activeUsers.get(index).getUserId() != userId) {
                index++;
            }
                if (LocationService.activeServices.get(goId).activeUsers.get(index).getUserId() == userId) {
                    LocationService.activeServices.get(goId).activeUsers.clear();
                    LocationService.activeServices.get(goId).activeUsers.add(new UserLocation(userId, lat, lon));
                    validation = true;
                }


            if (validation == false) {
                LocationService.activeServices.get(goId).activeUsers.add(new UserLocation(userId, lat, lon));
            }
        } else {
            LocationService.activeServices.put(goId, new LocationService());
            LocationService.activeServices.get(goId).activeUsers.add(new UserLocation(userId, lat, lon));
        }
    }

    /**
     * Gibt die aktuelle GroupLocation des spezifizierten Gos an den Aufrufer zurück. Aufgerufen wird diese Methode von
     * einem GoRestController, der von einem Client eine Anfrage nach der groupLocation eines Gos bekommen hat.
     * <p>
     * Bei einem Methodenaufruf wird das entsprechende Go aus der Map activeServices anhand der goId herausgesucht.
     *
     * @param goId Die Id des Gos, dessen groupLocation gesucht wird. Dabei handelt es sich um eine gültige Go-Id, die
     *             ein Schlüssel in der Map acitve Gos ist.
     * @return Eine Liste mit Cluster-Objekten. Diese stellt den aktuellen Standort der Go-Teilnehmer dar. Dabei ist die
     * Länge der Liste zwischen 0 und 50. Ist die Liste leer, heißt das, dass noch nicht genügend Teilnehmer ihren
     * Standort übermittelt haben, um eine groupLocation ausrechnen zu können, ohne die Anonymisierungs-Vorschriften der
     * Anwendung zu verletzen.
     */
    public static List<Cluster> getGroupLocation(final long goId) {
        LocationService.activeServices.get(goId).groupLocation = null;
        LocationService.activeServices.get(goId).groupLocation = LocationService.activeServices.get(goId).strat.calculateCluster(LocationService.activeServices.get(goId).activeUsers);
        return LocationService.activeServices.get(goId).groupLocation;
    }

    /**
     * Diese Methode bietet eine Möglichkeit ein Go aus der Liste der aktiven Gos zu löschen. Dabei wird diese Methode
     * vom GoRestController aufgerufen wenn ein Go gelöscht wird.
     *
     * @param goId Die Id des zu löschenden Gos.
     */

    public static void removeGo(final long goId) {
        if (LocationService.activeServices.get(goId) != null) {
            LocationService.activeServices.remove(goId);
        }
    }

    /**
     * Einzig und allein gedacht für Tests. activeServices ist eine private class auf die
     * niemand ausser der Klasse selbst Zugriff haben soll!
     * Fügt einen neues aktives Go der Liste hinzu.
     *
     * @param goId       Id des hinzuzufügenden Gos.
     * @param newService Gemappter LocationService.
     */
    public void putGo(Long goId, LocationService newService) throws IOException {
        if (LocationService.activeServices.get(goId) == null) {
            LocationService.activeServices.put(goId, new LocationService());
        }
    }
}
