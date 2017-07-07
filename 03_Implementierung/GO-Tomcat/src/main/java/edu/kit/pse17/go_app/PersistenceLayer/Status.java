package edu.kit.pse17.go_app.PersistenceLayer;

/**
 * Dieses Enum definiert die verschiedenen Teilnahmestati, die ein Benutzer in einem GO innehaben kann.
 */
public enum Status {

    /**
     * bedeutet, dass der Benutzer nicht an dem GO teilnehmen wird. Er teilt seinen Standort nicht mit anderen und kann
     * auch die Standorte der anderen Go-Teilnehmer nicht sehen.
     *
     * Dieser Teilnahmestatus ist der default-Status bei der Erstellung eines GOs für alle Teilnehmer, außer dem Ersteller selbst.
     * Der Ersteller kann niemals den Status "ABGELEHNT" annehmen.
     */
    ABGELEHNT,

    /**
     * bedeutet, dass der Benutzer an dem GO teilnehmen wird. Er ist in dem GO aller dings noch nicht aktiv, d.h. er teilt seinen
     * Standort nicht mit anderen. Gibt es andere Teilnehmer in dem GO, die bereits "UNTERWEGS" sind, kann der Benutzer deren Standorte sehen.
     *
     * Dieser Teilnahemstatus ist der default-Status für den Ersteller eines GOs.
     */
    BESTÄTIGT,

    /**
     * bedeutet, dass der Benutzer an dem GO teilnimmt und bereits aktiv ist, d.h. er teilt gerade seinen Standort mit anderen GO-Teilnehmern.
     * Dieser kann von Benutzern mit dem Status "UNTERWEGS" oder "BESTÄTIGT" angesehen werden.
     */
    UNTERWEGS
}
