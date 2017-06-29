package PersistenceLayer.hibernateEntities;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Werte, die der Benutzerstatus eines Benutzers in einem Go annehmen kann
 *
 * Created by tina on 29.06.17.
 */

public enum Status {

    /**
     * der benutzer nimmt nicht am GO teil
     */
    ABGELEHT,

    /**
     * Der Benutzer nimmt am GO teil, ist aber nich nicht losgegangen,
     * so dass andere den Standort des Benutzers noch nicht sehen können.
     */
    BESTÄTIGT,

    /**
     * Der Benutzer nimmt am GO teil und ist bereits losgegangen. Der Standort des Benutzers wird verfolgt und
     * mit berechtigten GO-Teilnehmern geteilt.
     */
    UNTERWEGS
}
