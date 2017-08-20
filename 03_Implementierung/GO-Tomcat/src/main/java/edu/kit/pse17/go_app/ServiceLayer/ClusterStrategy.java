package edu.kit.pse17.go_app.ServiceLayer;

import java.util.List;

/**
 * Dieses Interface definiert die Schnittstelle, die eine Klasse, die einen Clustering-Algorithmus implementiert,
 * anbieten. muss.
 * <p>
 * Die Anzahl der Teilnehmer eines Gos liegt zwischen 1 und 50. Fürs Clustering muss die Anzahl bei mindestens 3 Teilnehmern liegen.
 * Der implementierende Algorithmus muss mit dieser Anzahl an Benutzern umgehen können.
 * <p>
 * Das Interface ist Teil eines Strategie-Entwurfsmusters und übernimmt die Rolle der allgemeinen Strategie.
 */
public interface ClusterStrategy {

    /**
     * Diese Methode muss von jeder konkreten Algorithmus-Klasse implementiert werden. Ein Aufruf dieser Methode führt
     * zu einer Ausführung des konkreten Algorithmus. Dabei ist es egal, wie der Algorithmus beim Clustering konkret
     * vorgeht.
     * <p>
     * In dem Entwurfsmuster Strategie übernimmt diese Methode die Rolle der "führeAus()" Methode in der abstrakten
     * Strategie.
     *
     * @param userLocationList Eine Liste mit den aktuellen Standorten der einzelnen Go-Teilnehmer. Die Länge der Liste
     *                         beträgt dabei mindestens drei Objekte und maximal 50 Objekte.
     * @return Eine Liste von Cluster-Objekten, die den aktuellen Standort der Gruppe beschreiben. Die Länge der Liste
     * liegt zwischen 0 und 16.
     */
    List<Cluster> calculateCluster(List<UserLocation> userLocationList);


}
