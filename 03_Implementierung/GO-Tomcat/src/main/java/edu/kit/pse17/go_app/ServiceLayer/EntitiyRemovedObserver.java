package edu.kit.pse17.go_app.ServiceLayer;

import edu.kit.pse17.go_app.ClientCommunication.Downstream.FcmClient;
import edu.kit.pse17.go_app.PersistenceLayer.GoEntity;
import edu.kit.pse17.go_app.PersistenceLayer.GroupEntity;

import java.util.List;

/**
 * Bei dieser Klasse handelt es sich um eine Implementierung des Observer interfaces. Dementsprechned ist diese Klasse Teil des Observer-
 * Entwurfsmusters und übernimmt die Rolle des konkreten Observers.
 *
 * Die Aufgabe dieser Klasse ist das Beobachten der DAO-Klassen und auf Entfernen von Entitäten zu reagieren. Dies schließt folgende Ereignisse mit ein:
 * - Entfernen eines GOs
 * - Entfernen eines Gruppenmitglieds / einer Gruppenanfrage
 * - Entfernen einer Gruppe
 *
 * Um auf diese verschiedenen Änderungen regiaeren zu können, muss bei der Implementierung die update()-Methode überladen werden oder
 * innerhalb der Methode anhand der übergebenen Änderung entschieden werden, welche weitere Vorgehensweise gewählt werden muss.
 */
public class EntitiyRemovedObserver implements Observer {

    /**
     * Der Code anhand dem der Observer erkennt, dass er auf ein notify() reagieren soll. Bei jeglichen Änderungen wird jeder observer benachrichtigt,
     * wer regieren muss wird anhand dieses Codes entschieden. Er wird als erstes Argument der update()-Methode verwendet.
     */
    public static final String OBSERVER_CODE = "remove";

    /**
     * Eine Instanz eines FcmClients, der dafür verwendet wird, Nachrichten an die Clients zu schicken. Das Attribut wird bei der Erzeugun eines OBserver
     * Objekts automatisch instanziiert (durch Benutzung des einzigen, argumentlosen Konstruktors der FcmClient-Klasse). Danach kann das von außen Attribut nicht
     * mehr verändert werden.
     */
    private FcmClient fcmClient;

    /**
     * Die Klasse verfügt lediglich über einen Kosntruktor, der keine Argumente entgegen nimmt. In dem Konstruktor wird das Attribut fcmClient
     * instanziiert.

    public EntityRemovedObserver() {

    }
    */

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
     * Überladung der update()-methode des Observers. Diese Methode kümmert sich um das Entfernen einer Gruppe. Die Daten der Gruppe
     * werden in dieser Methode zu einem passenden JSON-Objekt umgewandelt und an den FcmClient weitergegeben, um von dort an die
     * entsprechenden Clients geschickt zu werden.
     *
     * Wer diese Clients sind wird ebenfalls in dieser Methode bestimmt und kann anhand der übergebenen Go-Entity ermittelt werden.
     *
     * @param arg Ein Argument, das die aufgetretene Änderung beschreibt. Ein Beobachter reagiert nur dann auf die Änderung, wenn dieses Argument
     *            mit dem statischen Feld OBSERVER_CODE, das jeder Beobachter besitzt übereinstimmt.
     * @param observable Eine Instanz des Observable-Objekts, dass den Beobachter benachrichtigt hat. Dadurch kann der Beobachter zurückverfolgen
     *                   von wo in der Anwendung er benachrichtigt wurde.
     * @param group Die Gruppe, die aus der Datenbank entfernt wurde.
     */
    public void update(String arg, Observable observable, GroupEntity group) {

    }

    /**
     * Überladung der update()-methode des Observers. Diese Methode kümmert sich um das Entfernen eines GOs. Die Daten des GOs
     * werden in dieser Methode zu einem passenden JSON-Objekt umgewandelt und an den FcmClient weitergegeben, um von dort an die
     * entsprechenden Clients geschickt zu werden.
     *
     * Wer diese Clients sind wird ebenfalls in dieser Methode bestimmt und kann anhand der übergebenen Go-Entity ermittelt werden.
     *
     * @param arg in Argument, das die aufgetretene Änderung beschreibt. Ein Beobachter reagiert nur dann auf die Änderung, wenn dieses Argument
     *            mit dem statischen Feld OBSERVER_CODE, das jeder Beobachter besitzt übereinstimmt.
     * @param observable Eine Instanz des Observable-Objekts, dass den Beobachter benachrichtigt hat. Dadurch kann der Beobachter zurückverfolgen
     *                   von wo in der Anwendung er benachrichtigt wurde.
     * @param go Das GO, das aus der Datenbank entfernt wurde.
     */
    public void update(String arg, Observable observable, GoEntity go) {

    }

    /**
     * Überladung der update()-Methode des Observers. Diese Methode kümmert sich um das Entfernen eines Gruppenmitglieds. Die Daten der Änderung
     * werden in dieser Methode zu einem passenden JSON-Objekt umgewandelt und an den FcmClient weitergegeben, um von dort an die
     * entsprechenden Clients geschickt zu werden.
     *
     * Wer diese Clients sind wird ebenfalls in dieser Methode bestimmt und kann anhand der übergebenen übergebenen Daten ermittelt werden.
     *
     * @param arg in Argument, das die aufgetretene Änderung beschreibt. Ein Beobachter reagiert nur dann auf die Änderung, wenn dieses Argument
     *            mit dem statischen Feld OBSERVER_CODE, das jeder Beobachter besitzt übereinstimmt.
     * @param observable Eine Instanz des Observable-Objekts, dass den Beobachter benachrichtigt hat. Dadurch kann der Beobachter zurückverfolgen
     *                   von wo in der Anwendung er benachrichtigt wurde.
     * @param changes Eine Liste mit Objekten, die die Änderungen beschreiben. Dabei muss die Liste folgenden Aufbau haben:
     *                1. GroupEntity -- Gruppe, aus der der Benutzer entfernt werden soll
     *                2. UserEntity -- Benutzer, der aus der Gruppe entfernt werden soll
     */
    public void update(String arg, Observable observable, List<Object> changes) {

    }
}
