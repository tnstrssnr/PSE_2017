package edu.kit.pse17.go_app.ServiceLayer;

import edu.kit.pse17.go_app.ClientCommunication.Downstream.FcmClient;
import edu.kit.pse17.go_app.PersistenceLayer.GoEntity;
import edu.kit.pse17.go_app.PersistenceLayer.GroupEntity;

import java.util.List;

/**
 * Bei dieser Klasse handelt es sich um eine Implementierung des Oberver interfaces. dementsprechned ist diese Klasse Teil des Observer-
 * Entwurfsmusters und übernimmt die Rolle des konkreten Observers.
 *
 * Die Aufgabe dieser Klasse ist das Beobachten der DAO-Klassen und auf Änderungen einer bestehenden Entität zu reagieren. Dies schließt folgende Ereignisse mit ein:
 * - Änderung von GO-Daten
 * - Änderung von Gruppendaten
 * - Änderung des Teilnahmestatus
 * - Änderung der Adminsitratoren einer Gruppe
 *
 * Um auf diese verschiedenen Änderungen regiaeren zu können, muss bei der Implementierung die update()-Methode überladen werden oder
 * innerhalb der Methode anhand der übergebenen Änderung entschieden werden, welche weitere Vorgehensweise gewählt werden muss.
 */
public class EntityChangedObserver implements Observer {

    /**
     * Der Code anhand dem der Observer erkennt, dass er auf ein notify() reagieren soll. Bei jeglichen Änderungen wird jeder observer benachrichtigt,
     * wer regieren muss wird anhand dieses Codes entschieden. Er wird als erstes Argument der update()-Methode verwendet.
     */
    public static final String OBSERVER_CODE = "edit";

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
    public EntityRemovedObserver() {

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
     * Überladung der Methode update() des Observers. In dieser Methode werden Datenänderungen an einem GO behandelt. Das geänderte Go wird in ein JSON-Objekt
     * umgewandelt und an den FcmClient weiteregegeben, um es an die entsprechenden Clients weiterzuleiten.
     *
     * Wer diese Clients sind wird ebenfalls in dieser Methode bestimmt und kann anhand der übergebenen Go-Entity ermittelt werden.
     *
     * @param arg Ein Argument, das die aufgetretene Änderung beschreibt. Ein Beobachter reagiert nur dann auf die Änderung, wenn dieses Argument
     *            mit dem statischen Feld OBSERVER_CODE, das jeder Beobachter besitzt übereinstimmt.
     * @param observable Eine Instanz des Observable-Objekts, dass den Beobachter benachrichtigt hat. Dadurch kann der Beobachter zurückverfolgen
     *                   von wo in der Anwendung er benachrichtigt wurde.
     * @param go Das Go, dessen Daten verändert wurden.
     */
    public void update(String arg, Observable observable, GoEntity go) {

    }

    /**
     * Überladung der Methode update() des Observers. In dieser Methode werden Datenänderungen an einer Gruppe behandelt. Die geänderte gruppe wird in ein JSON-Objekt
     * umgewandelt und an den FcmClient weiteregegeben, um es an die entsprechenden Clients weiterzuleiten.
     *
     * Wer diese Clients sind wird ebenfalls in dieser Methode bestimmt und kann anhand der übergebenen Group-Entity ermittelt werden.
     *
     * @param arg Ein Argument, das die aufgetretene Änderung beschreibt. Ein Beobachter reagiert nur dann auf die Änderung, wenn dieses Argument
     *            mit dem statischen Feld OBSERVER_CODE, das jeder Beobachter besitzt übereinstimmt.
     * @param observable Eine Instanz des Observable-Objekts, dass den Beobachter benachrichtigt hat. Dadurch kann der Beobachter zurückverfolgen
     *                   von wo in der Anwendung er benachrichtigt wurde.
     * @param group Die Gruppe, deren Daten verändert wurden.
     */
    public void update(String arg, Observable observable, GroupEntity group) {

    }

    /**
     * Überladung der Methode update() des Observers. In dieser Methode wird das ereignis eines neu hinzugefügten Admins behandelt und an die
     * entsprechenden Clients weitergeleitet.
     *
     * Wer diese Clients sind wird ebenfalls in dieser Methode bestimmt und kann anhand der übergebenen Group-Entity ermittelt werden.
     *
     * @param arg Ein Argument, das die aufgetretene Änderung beschreibt. Ein Beobachter reagiert nur dann auf die Änderung, wenn dieses Argument
     *            mit dem statischen Feld OBSERVER_CODE, das jeder Beobachter besitzt übereinstimmt.
     * @param observable Eine Instanz des Observable-Objekts, dass den Beobachter benachrichtigt hat. Dadurch kann der Beobachter zurückverfolgen
     *                   von wo in der Anwendung er benachrichtigt wurde.
     * @param changes Eine Liste von Objekten, die die Änderung beschreiben. Dabei muss die Liste folgende Struktur haben:
     *                1. String -- "ADMIN"
     *                2. GroupEntity -- Gruppe, um die es sich handelt
     *                3. UserEntity -- Benutzer, der zum Administrator gemacht wurde.
     */
    public void update(String arg, Observable observable, List<Object> changes) {

    }

    /**
     * Überladung der Methode update() des Observers. In dieser Methode werden Statusänderungen von GO-Teilnehmern behandelt und die Änderung an die Clients
     * weitergeleitet. Dazu wandelt die Methode die Änderung in ein passendes JSON-Objekt um und ruft anschließend den FCM-Client auf, um eine Nachricht zu verschicken.
     *
     * An wen die Nachricht geschickt werden muss, wird ebenfalls in dieser Methode bestimmt und kann anhand der übergebenen Go-Entity ermittelt werden.
     *
     * @param arg Ein Argument, das die aufgetretene Änderung beschreibt. Ein Beobachter reagiert nur dann auf die Änderung, wenn dieses Argument
     *            mit dem statischen Feld OBSERVER_CODE, das jeder Beobachter besitzt übereinstimmt.
     * @param observable Eine Instanz des Observable-Objekts, dass den Beobachter benachrichtigt hat. Dadurch kann der Beobachter zurückverfolgen
     *                   von wo in der Anwendung er benachrichtigt wurde.
     * @param changes Eine Liste von Objekten, die die Änderung beschreiben. Dabei muss die Liste folgende Struktur haben:
     *                1. String -- "STATUS"
     *                2. GoEntity -- Go, um die es sich handelt
     *                3. UserEntity -- Benutzer, der seinen Status geändert hat.
     *                4. Status -- neuer Status des Benutzers
     */
    public void update(String arg, Observable observable, List<Object> changes) {

    }
}
