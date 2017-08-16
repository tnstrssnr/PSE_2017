package edu.kit.pse17.go_app.ClientCommunication.Downstream;

/**
 * Dieses Enum enthält String-Konstanten, die vom FcmClient in die Nachrichten an Clients eingefügt werden, damit der
 * Client anhand der Nachricht feststellen kann, welches Ereignis eingetreten ist.
 */
public enum EventArg {

    /**
     * Event wird ausgelöst, falls eine Gruppenentität gelöscht wird. Das Event wird nur an Clients gesendet, die
     * Mitglied in der Gruppe waren bzw. eine Anfrage für diese Gruppe erhalten haben.
     */
    GROUP_REMOVED_EVENT,

    /**
     * Event wird ausgelöst, falls ein Mitglied aus einer Gruppe gelöscht wird. Dies ist auch der Fall, wenn ein
     * Benutzer seinen Benutzeraccount gelöscht hat. Das Event wird an alle Mitglieder der gruppe gesendet und an
     * Benutzer, die eine Anfrage für die Gruppe erhalten haben.
     */
    MEMBER_REMOVED_EVENT,

    /**
     * Event wird ausgelöst, falls eine Go-Entität gelöscht wird. Die Benachrichtigung muss an alle Benutzer geschickt
     * werden, die Mitglied in der Gruppe des Gos sind bzw. eine Gruppenanfrage für diese Gruppe haben.
     */
    GO_REMOVED_EVENT,

    /**
     * Event wird ausgelöst, falls ein neues Mitglied zu einer Gruppe hinzugefügt wurde (also ein  Benutzer ein
     * gruppenanfrage bestätigt hat). Die Benachrichtigung muss an alle Benutzer geschickt werden, die Mitglied in der
     * Gruppe sind bzw. eine Gruppenanfrage für diese Gruppe haben.
     */
    MEMBER_ADDED_EVENT,

    /**
     * Event wird ausgelöst, wenn ein Benutzer eine Anfrage für eine Gruppe bekommen hat. Die Benachrichtigung wird an
     * diesen Benutzer und an alle Mitglieder der Gruppe gesendet bzw. Benutzer, die eine Gruppenanfrage für diese
     * Gruppe haben.
     */
    GROUP_REQUEST_RECEIVED_EVENT,


    GROUP_REQUEST_DENIED_EVENT,

    /**
     * Event wird ausgelöst, wenn ein neues GO in einer Gruppe erstellt wurde. Die Benachrichtigung muss an alle
     * Benutzer geschickt werden, die Mitglied in der Gruppe sind bzw. eine Gruppenanfrage für diese Gruppe haben.
     */
    GO_ADDED_EVENT,

    /**
     * Event wird ausgelöst, wenn die Daten in einem GO verändert werden. Die Benachrichtigung muss an alle Benutzer
     * geschickt werden, die Mitglied in der Gruppe sind bzw. eine Gruppenanfrage für diese Gruppe haben.
     */
    GO_EDITED_COMMAND,

    /**
     * Event wird ausgelöst, wenn die Daten in einer gruppe verändert werden. Die Benachrichtigung muss an alle Benutzer
     * geschickt werden, die Mitglied in der Gruppe sind bzw. eine Gruppenanfrage für diese Gruppe haben.
     */
    GROUP_EDITED_COMMAND,

    /**
     * Event wird ausgelöst, wenn ein Adminsitrator zu einer Gruppe hinzugefügt wurde. Die Benachrichtigung muss an alle
     * Benutzer geschickt werden, die Mitglied in der Gruppe sind bzw. eine Gruppenanfrage für diese Gruppe haben.
     */
    ADMIN_ADDED_EVENT,

    /**
     * Event wird ausgelöst, wenn ein GO-Teilnehmer seinen Teilnehmerstatus verändert hat. Die Benachrichtigung muss an
     * alle Benutzer geschickt werden, die Mitglied in der Gruppe des GOs sind bzw. eine Gruppenanfrage für diese Gruppe
     * haben.
     */
    STATUS_CHANGED_COMMAND


}
