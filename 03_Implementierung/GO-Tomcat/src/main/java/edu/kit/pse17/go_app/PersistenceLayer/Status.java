package edu.kit.pse17.go_app.PersistenceLayer;

/**
 * Dieses Enum definiert die verschiedenen Teilnahmestati, die ein Benutzer in einem Go innehaben kann.
 */
public enum Status {

    /**
     * Bedeutet, dass der Benutzer nicht an dem Go teilnehmen wird. Er teilt seinen Standort nicht mit anderen und kann
     * auch die Standorte der anderen Go-Teilnehmer nicht sehen.
     * <p>
     * Dieser Teilnahmestatus ist der default-Status bei der Erstellung eines Gos für alle Teilnehmer, außer dem
     * Ersteller selbst. Der Ersteller kann niemals den Status "NOT_GOING" annehmen.
     */
    NOT_GOING,

    /**
     * Bedeutet, dass der Benutzer an dem Go teilnehmen wird. Er ist in dem Go allerdings noch nicht aktiv, d.h. er
     * teilt seinen Standort nicht mit anderen. Gibt es andere Teilnehmer in dem Go, die bereits "GONE" sind, kann
     * der Benutzer deren Standorte sehen.
     * <p>
     * Dieser Teilnahemstatus ist der default-Status für den Ersteller eines Gos.
     */
    GOING,

    /**
     * Bedeutet, dass der Benutzer an dem Go teilnimmt und bereits aktiv ist, d.h. er teilt gerade seinen Standort mit
     * anderen Go-Teilnehmern. Dieser kann von Benutzern mit dem Status "GONE" oder "GOING" angesehen werden.
     */
    GONE
}
