package edu.kit.pse17.go_app.ServiceLayer;

import net.sf.javaml.clustering.Clusterer;
import net.sf.javaml.clustering.OPTICS;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.DefaultDataset;
import net.sf.javaml.core.Instance;
import net.sf.javaml.core.SparseInstance;
import org.unitils.dbunit.annotation.DataSet;

import java.util.Iterator;
import java.util.List;

/**
 * In dieser Klasse wird der in er Anwendung verwendete Clustering-Algorithmus implementiert.
 *
 * Die Ausführung des Algorithmus wird von der Klasse LocationService aufgerufen.
 *
 * Diese Klasse ist Teil einer Implementierung des Entwurfsmusters "Strategie" und übernimmt dabei die Rolle der konkreten Strategie.
 * Das Interface der abstrakten Strategie, in diesem Fall "ClsuterStrategy" wird implementiert. Die "führeAus()"-Methode der Strategie ist
 * die Methode "calculateCluster()"
 */
public class GoClusterStrategy implements ClusterStrategy {

    /**
     * Ein Schwellwert für die Genauigkeit, mit der der Clustering-Algorithmus ausgeführt wird. Der Wert liegt zwischen 1 (sehr ungenau) und 10 (sehr
     * genau). Dieser Wert kann nach der Instanziierung des GoClusterStrategy-Objekts
     * nicht mehr verändert werden. Wird der Wert nicht wenigstens einmal spezifiziert wird default-mäßig
     * ein Wert von 5 benutzt.
     */
    private int threshold;

    /**
     *Ein Konstruktor, der den Wert des Clustering-Schwellwerts entgegen nimmt. Dieser Wert kann nach der Instanziierung des GoClusterStrategy-Objekts
     * nicht mehr verändert werden.
     * @param threshold Eine Zahl zwischen 1 (sehr genaues Clustering) und 10 (sehr ungenaues Clustering)
     */
    public GoClusterStrategy(int threshold) {
        this.threshold = threshold;
    }

    /**
     * Ein Konstruktor, für den Fall, dass der Clustering-Schwellwert nicht spezifiziert wurde. Hier wird der default-Wert von 5 eingesetzt.
     */
    public GoClusterStrategy() {}

    /**
     * Methode des Interfaces, die hier implementiert wird. Der Aufruf deiser Methode stößt die Ausfühtung des Algorithmus an und sie liefert die Ergebnisse
     * des Clsutering-Vorgangs an denAufrufer zurück.
     *
     * @param userLocationList Eine Liste mit den aktuellen Standorten der einzelnen GO-Teilnehmer. Die Länge der Liste beträgt dabei mindestens drei
     *                         Objekte und maximal 50 Objekte.
     * @return ein Dataset von Clustern, die den aktuellen Standort der Gruppe beschreiben.
     */
    @Override
    public Dataset[] calculateCluster(List<UserLocation> userLocationList) {
        Clusterer clusterer = new OPTICS();
        DefaultDataset initialDataset = new DefaultDataset();
        Iterator<UserLocation> iterator = userLocationList.iterator();
        int size = userLocationList.size();
        while(iterator.hasNext() && size > 0) {
            UserLocation currentUser = iterator.next();
            double[] location = new double[] {currentUser.getLat(), currentUser.getLon()};
            Instance dataInstance = new SparseInstance(location);
            initialDataset.add(dataInstance);
            size -= size;
        }
        return clusterer.cluster(initialDataset);
    }
}
