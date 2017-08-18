package edu.kit.pse17.go_app.ServiceLayer;

import net.sf.javaml.core.Dataset;

import java.util.List;

/**
 * Dieses Interface definiert die Schnittstelle, die eine Klasse, die einen Clustering-Algorithmus implementiert,
 * anbieten. muss.
 * <p>
 * Die Anzahl der Teilnehmer eines GOs liegt zwischen 3 und 50. Der imlpementierende Algorithmus muss mit dieser Anzahl
 * an Benutzern umgehen können.
 * <p>
 * Das Interface ist Teil eines Stategie-Entwurfsmusters und übernimmt die Rolle der allgemeinen Strategie.
 */
public interface ClusterStrategy {

    /**
     * Diese Methode muss von jeder konkreten Algorithmus-Klasse implementiert werden. Ein Aufruf dieser Methode führt
     * zu einer Ausführung des konkreten Algorithmus. Dabei ist es egal, wied der Algorithmus beim Clustering konkret
     * vorgeht.
     * <p>
     * In dem Entwurfsmuster Strategie übernimmt diese Methode die Rolle der "führeAus()" Methode in der abstrakten
     * Strategie.
     *
     * @param userLocationList Eine Liste mit den aktuellen Standorten der einzelnen GO-Teilnehmer. Die Länge der Liste
     *                         beträgt dabei mindestens drei Objekte und maximal 50 Objekte.
     * @return eine Liste von Cluster-Objekten, die den aktuellen Standort der Gruppe beschreiben. Die Länge der Liste
     * liegt zwis
     */
    Dataset[] calculateCluster(List<UserLocation> userLocationList);


}
