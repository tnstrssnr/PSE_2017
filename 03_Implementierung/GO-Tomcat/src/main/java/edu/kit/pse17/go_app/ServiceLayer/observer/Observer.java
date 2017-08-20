package edu.kit.pse17.go_app.ServiceLayer.observer;

import java.util.List;

/**
 * Dieses Interface gehört zu einer Implementierung des Entwurfsmusters  Beobachter. Es übernimmt dabei die Rolle des abstrakten Beobachters.
 * Jede konkrete Beobachter-Klasse muss dieses Interface implementieren und über eine update-Methode verfügen.
 *
 * Die Aufgabe der Beobachter in dieser Anwendung ist das Beobachten der DAO-Klassen und bei Änderungen im Datenbestand, diese Ändeurngen an die Clients
 * der betroffenen Benutzer weiterzuleiten. Dazu werden aus dem übergebenen Pbjekt die wichtigen Daten extrahiert und in ein JSON-Objekt umgewandelt.
 * Dieses kann an das Downstream-ClientCommunication Modul übergeben werden, wo es an die betroffenen Clients geschickt wird.
 *
 * Die Implementierung des Entwurfsmusters benutzt ein push-Modell, d.h. die Änderungen werden den Beobachtern bei einem notify()-Aufruf gleich mit übergeben. Die Beobachter
 * müssen sich diese Änderungen nicht selbst holen.
 */
public interface Observer {

    /**
     * Die update()-Methode, mit der die Beobachter die beobachteten Änderungen an die Clients weitergeben. Wie dieses Update genau aussieht, wird von der konkreten
     * Implementierung des Beobachters bestimmt.
     * @param entity_ids Ein Objekt, das die Änderungen, um die der Beobachter sich kümmern muss, enthält. Da es sich um den Datentyp "Object" handelt, ist der Beobachter
     *          sehr flexibel im Bezug auf welche Änderungen ihm übergeben werden können. Dies erleichtert auch das Überladen der Methode, wodurch ein Beobachter mehrere
     *          ähnliche Ereignisse beobachten kann.
     */
    public void update(List<String> entity_ids);
}
