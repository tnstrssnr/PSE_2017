package edu.kit.pse17.go_app.ServiceLayer;

import edu.kit.pse17.go_app.ClientCommunication.Downstream.FcmClient;
import edu.kit.pse17.go_app.PersistenceLayer.GoEntity;
import edu.kit.pse17.go_app.PersistenceLayer.GroupEntity;
import edu.kit.pse17.go_app.PersistenceLayer.UserEntity;

/**
 * Bei dieser Klasse handelt es sich um eine Implementierung des Observer interfaces. Dementsprechned ist diese Klasse Teil des Observer-
 * Entwurfsmusters und übernimmt die Rolle des konkreten Observers.
 *
 * Die Aufgabe dieser Klasse ist das Beobachten der DAO-Klassen und auf das Erstellen neuer Entitäten zu reagieren. Dies schließt folgende Ereignisse mit ein:
 * - Hinzufügen eines GOs
 * - Hinzufügen einer Gruppenanfrage
 * - Hinzufügen eines Gruppenmitglieds
 *
 * Um auf diese verschiedenen Änderungen regiaeren zu können, muss bei der Implementierung die update()-Methode überladen werden oder
 * innerhalb der Methode anhand der übergebenen Änderung entschieden werden, welche weitere Vorgehensweise gewählt werden muss.
 */
public class EntityAddedObserver implements Observer {

    /**
     * Der Code anhand dem der Observer erkennt, dass er auf ein notify() reagieren soll. Bei jeglichen Änderungen wird jeder observer benachrichtigt,
     * wer regieren muss wird anhand dieses Codes entschieden. Er wird als erstes Argument der update()-Methode verwendet.
     */
    public static final String OBSERVER_CODE = "add";

    /**
     * Eine Instanz eines FcmClients, der dafür verwendet wird, Nachrichten an die Clients zu schicken. Das Attribut wird bei der Erzeugun eines OBserver
     * Objekts automatisch instanziiert (durch Benutzung des einzigen, argumentlosen Konstruktors der FcmClient-Klasse). Danach kann das von außen Attribut nicht
     * mehr verändert werden.
     */
    private FcmClient fcmClient;

    /**
     * Die Klasse verfügt lediglich über einen Kosntruktor, der keine Argumente entgegen nimmt. In dem Konstruktor wird das Attribut fcmClient
     * instanziiert.
     */
    public EntityAddedObserver() {

    }

    /**
     * Implementierung der update()-Methode. Wird überladen, um die unterschiedlichen Ereignisse, auf die dieser Observer reagieren kann zu unterscheiden.
     * @param arg Ein Argument, das die aufgetretene Änderung beschreibt. Ein Beobachter reagiert nur dann auf die Änderung, wenn dieses Argument
     *            mit dem statischen Feld OBSERVER_CODE, das jeder Beobachter besitzt übereinstimmt.
     * @param observable Eine Instanz des Observable-Objekts, dass den Beobachter benachrichtigt hat. Dadurch kann der Beobachter zurückverfolgen
     *                   von wo in der Anwendung er benachrichtigt wurde.
     * @param o Ein Objekt, das die Änderungen, um die der Beobachter sich kümmern muss enthält. Da es sich um den Datentyp "Object" handelt, ist der Beobachter
     *          sehr flexibel, welche Änderungen ihm übergeben werden können. Dies erleichert auch das Überladen der Methode, wodurch ein Beobachter mehrere
     */
    @Override
    public void update(String arg, Observable observable, Object o) {

    }

    /**
     * Überladung der Update-Methode des Observers. Diese Implementierung der Methode wird aufgerufen, wenn ein neues GO in einer Gruppe erstellt wurde.
     * Die Methode wandelt das GO daraufhin in ein passendes JSON-objekt um, um es an den FcmClient weiterzugeben, der es wiederum an die passenden Clients
     * schickt.
     *
     * Wer diese Clients sind wird ebenfalls in dieser Methode bestimmt und kann anhand der übergebenen Go-Entity ermittelt werden.
     *
     * @param arg Ein Argument, das die aufgetretene Änderung beschreibt. Ein Beobachter reagiert nur dann auf die Änderung, wenn dieses Argument
     *            mit dem statischen Feld OBSERVER_CODE, das jeder Beobachter besitzt übereinstimmt.
     * @param observable Eine Instanz des Observable-Objekts, dass den Beobachter benachrichtigt hat. Dadurch kann der Beobachter zurückverfolgen
     *                   von wo in der Anwendung er benachrichtigt wurde.
     * @param go Das Go das neu erstellt wurde. Es enthält alle Daten die wichtig sind für das GO und die an die Clients weitergegeben werden müssen.
     */
    public void update(String arg, Observable observable, GoEntity go) {

    }

    /**
     * Überladung der Update-Methode des Observers. Diese Implementierung der Methode wird aufgerufen, wenn eine neue Gruppenanfrage in einer Gruppe erstellt wurde.
     * Die Methode wandelt die Anfrage daraufhin in ein passendes JSON-Objekt um, um es an den FcmClient weiterzugeben, der es wiederum an die passenden Clients
     * schickt.
     *
     * Wer diese Clients sind wird ebenfalls in dieser Methode bestimmt und kann anhand der übergebenen Go-Entity ermittelt werden.
     *
     * @param arg Ein Argument, das die aufgetretene Änderung beschreibt. Ein Beobachter reagiert nur dann auf die Änderung, wenn dieses Argument
     *            mit dem statischen Feld OBSERVER_CODE, das jeder Beobachter besitzt übereinstimmt.
     * @param observable Eine Instanz des Observable-Objekts, dass den Beobachter benachrichtigt hat. Dadurch kann der Beobachter zurückverfolgen
     *                   von wo in der Anwendung er benachrichtigt wurde.
     * @param group Die Gruppe in der die Anfrage neu erstellt wurde. Sie enthält alle Daten die wichtig sind für das GO und die an die Clients weitergegeben werden müssen.
     *              Da die Gruppenanfragen in einer Liste gespeichert werden, wird immer das letzte Listenelement als die neu hinzugefügte Anfrage betrachtet.
     *              Darauf muss geachtet werden, wenn in der DAO-Klasse eine neue Anfrage angelegt wird.
     */
    public void update(String arg, Observable observable, GroupEntity group) {

    }

    /**
     * Überladung der Update-Methode des Observers. Diese Implementierung der Methode wird aufgerufen, wenn ein neuer Bernutzer zu einer Gruppe hinzugefügt wurde.
     * Die Methode wandelt die Änderung daraufhin in ein passendes JSON-Objekt um, um es an den FcmClient weiterzugeben, der es wiederum an die passenden Clients
     * schickt.
     *
     * Wer diese Clients sind wird ebenfalls in dieser Methode bestimmt und kann anhand der übergebenen User-Entity ermittelt werden.
     *
     * @param arg Ein Argument, das die aufgetretene Änderung beschreibt. Ein Beobachter reagiert nur dann auf die Änderung, wenn dieses Argument
     *            mit dem statischen Feld OBSERVER_CODE, das jeder Beobachter besitzt übereinstimmt.
     * @param observable Eine Instanz des Observable-Objekts, dass den Beobachter benachrichtigt hat. Dadurch kann der Beobachter zurückverfolgen
     *                   von wo in der Anwendung er benachrichtigt wurde.
     * @param user Der Benutzer, der der Gruppe hinzugefügt wurde. Die Entität enthält alle Daten die wichtig sind für die Änderung und die an die Clients weitergegeben werden müssen.
     *              Da die gruppenmitgliedschaften in einer Liste gespeichert werden, wird immer das letzte Listenelement als die neu hinzugefügte Gruppe betrachtet.
     *              Darauf muss geachtet werden, wenn in der DAO-Klasse eine neue Anfrage angelegt wird.
     */
    public void update(String arg, Observable observable, UserEntity user) {

    }
}
