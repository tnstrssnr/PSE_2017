package edu.kit.pse17.go_app.ServiceLayer;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import static java.lang.Math.sqrt;

/**
 * In dieser Klasse wird der in er Anwendung verwendete Clustering-Algorithmus implementiert.
 * <p>
 * Die Ausführung des Algorithmus wird von der Klasse LocationService aufgerufen.
 * <p>
 * Diese Klasse ist Teil einer Implementierung des Entwurfsmusters "Strategie" und übernimmt dabei die Rolle der
 * konkreten Strategie. Das Interface der abstrakten Strategie, in diesem Fall "ClsuterStrategy" wird implementiert. Die
 * "führeAus()"-Methode der Strategie ist die Methode "calculateCluster()"
 */
public class GoClusterStrategy implements ClusterStrategy {

    /**
     * Ein Schwellwert für die Genauigkeit, mit der der Clustering-Algorithmus ausgeführt wird. Der Wert liegt zwischen
     * 1 (sehr ungenau) und 10 (sehr genau). Dieser Wert kann nach der Instanziierung des GoClusterStrategy-Objekts
     * nicht mehr verändert werden. Wird der Wert nicht wenigstens einmal spezifiziert wird default-mäßig ein Wert von 5
     * benutzt.
     */
    private int threshold;

    /**
     * Ein Konstruktor, der den Wert des Clustering-Schwellwerts entgegen nimmt. Dieser Wert kann nach der
     * Instanziierung des GoClusterStrategy-Objekts nicht mehr verändert werden.
     *
     * @param threshold Eine Zahl zwischen 1 (sehr genaues Clustering) und 10 (sehr ungenaues Clustering)
     */
    public GoClusterStrategy(final int threshold) {
        this.threshold = threshold;
    }

    /**
     * Ein Konstruktor, für den Fall, dass der Clustering-Schwellwert nicht spezifiziert wurde. Hier wird der
     * default-Wert von 5 eingesetzt.
     */
    public GoClusterStrategy() {
    }

    /**
     * Methode des Interfaces, die hier implementiert wird. Der Aufruf deiser Methode stößt die Ausfühtung des
     * Algorithmus an und sie liefert die Ergebnisse des Clsutering-Vorgangs an denAufrufer zurück.
     *
     * @param userLocationList Eine Liste mit den aktuellen Standorten der einzelnen GO-Teilnehmer. Die Länge der Liste
     *                         beträgt dabei mindestens drei Objekte und maximal 50 Objekte.
     * @return ein Dataset von Clustern, die den aktuellen Standort der Gruppe beschreiben.
     */


    @Override
    public List<Cluster> calculateCluster(final List<UserLocation> userLocationList) {

        final DBScan algorithm = new DBScan(userLocationList);
        Vector<List> Clusterlist = algorithm.applyDbscan(threshold, 3, userLocationList);
        List<Cluster> resultList = new ArrayList<Cluster>();
        for (int i = 0; i < Clusterlist.size(); i++) {
            int participants = 0;
            double lat = 0;
            double lon = 0;
            double maxDistance= 0;

            for (int j = 0; j < Clusterlist.get(i).size(); j++) {
                UserLocation currentLocation = (UserLocation) Clusterlist.get(i).get(j);
                lat = lat + currentLocation.getLat();
                lon = lon + currentLocation.getLon();
                participants++;
            }
            lat = lat / participants;
            lon = lon / participants;
            for (int j = 0; j < Clusterlist.get(i).size(); j++) {
                UserLocation currentLocation = (UserLocation) Clusterlist.get(i).get(j);
                double comparisonValue = Math.sqrt (lat * currentLocation.getLat() + lon * currentLocation.getLon());
                if (comparisonValue > maxDistance) {maxDistance = comparisonValue;}
            }

            Cluster newCluster = new Cluster(participants, lat, lon, maxDistance);
            resultList.add(newCluster);
        }
        return resultList;
        /*final Clusterer clusterer = new OPTICS();
        final DefaultDataset initialDataset = new DefaultDataset();
        final Iterator<UserLocation> iterator = userLocationList.iterator();
        int size = userLocationList.size();
        while (iterator.hasNext() && size > 0) {
            final UserLocation currentUser = iterator.next();
            final double[] location = new double[]{currentUser.getLat(), currentUser.getLon()};
            final Instance dataInstance = new SparseInstance(location);
            initialDataset.add(dataInstance);
            size -= size;
        }
        return clusterer.cluster(initialDataset);*/
    }


}
