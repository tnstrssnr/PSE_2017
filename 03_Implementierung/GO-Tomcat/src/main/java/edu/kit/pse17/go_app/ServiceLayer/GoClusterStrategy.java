package edu.kit.pse17.go_app.ServiceLayer;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * In dieser Klasse wird der in er Anwendung verwendete Clustering-Algorithmus ausgeführt.
 * <p>
 * Die finale Ausgabe wird hier berechnet und von der Klasse LocationService aufgerufen.
 */
public class GoClusterStrategy implements ClusterStrategy {

    /**
     * Ein Schwellwert für die Genauigkeit, mit der der Clustering-Algorithmus ausgeführt wird. Der Wert liegt zwischen
     * 1 (sehr genau) und 10 (sehr ungenau). Dieser Wert kann nach der Instanziierung des GoClusterStrategy-Objekts
     * nicht mehr verändert werden. Wird der Wert nicht wenigstens einmal spezifiziert wird default-mäßig ein Wert von 4
     * benutzt.
     */
    private double threshold;

    /**
     * Ein Konstruktor, für den Fall, dass der Clustering-Schwellwert nicht spezifiziert wurde. Hier wird der
     * default-Wert von 4 eingesetzt.
     */
    public GoClusterStrategy() {
        this.threshold = 0.00045;
    }

    /**
     * Methode des Interfaces, die hier implementiert wird. Der Aufruf deiser Methode stößt die Ausfühtung des
     * Algorithmus an und sie liefert die Ergebnisse des Clustering-Vorgangs an den Aufrufer zurück.
     *
     * @param userLocationList Eine Liste mit den aktuellen Standorten der einzelnen Go-Teilnehmer. Die Länge der Liste
     *                         beträgt dabei mindestens drei Objekte und maximal 50 Objekte.
     * @return Eine Liste mit den berechneten Clustern.
     */

    @Override
    public List<Cluster> calculateCluster(final List<UserLocation> userLocationList) {

        if (userLocationList.size() == 0) {
            return null;
        }

        DBScan algorithm = new DBScan(userLocationList);

        Vector<List> clusterList = algorithm.applyDbscan(threshold, 2, userLocationList);

        List<Cluster> resultList = new ArrayList<>();
        for (int i = 0; i < clusterList.size(); i++) {
            int participants = 0;
            double lat = 0;
            double lon = 0;
            double maxDistance = 0;

            for (int j = 0; j < clusterList.get(i).size(); j++) {
                UserLocation currentLocation = (UserLocation) clusterList.get(i).get(j);
                lat = lat + currentLocation.getLat();
                lon = lon + currentLocation.getLon();
                participants++;
            }
            lat = lat / participants;
            lon = lon / participants;
            for (int j = 0; j < clusterList.get(i).size(); j++) {
                UserLocation currentLocation = (UserLocation) clusterList.get(i).get(j);
                double dx = lat - currentLocation.getLat();
                double dy = lon - currentLocation.getLon();
                double comparisonValue = Math.sqrt(dx * dx + dy * dy);
                if (comparisonValue > maxDistance) {
                    maxDistance = comparisonValue;
                }
            }

            Cluster newCluster = new Cluster(participants, lat, lon, maxDistance);
            resultList.add(newCluster);
        }
        return resultList;
    }


}
