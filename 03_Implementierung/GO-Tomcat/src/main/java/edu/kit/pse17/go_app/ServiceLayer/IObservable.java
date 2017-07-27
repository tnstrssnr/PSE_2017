package edu.kit.pse17.go_app.ServiceLayer;

import edu.kit.pse17.go_app.ClientCommunication.Downstream.EventArg;
import edu.kit.pse17.go_app.ServiceLayer.observer.Observer;

/**
 * Dieses Interface ist Teil einer Implementierung eines Beobachter-Entwurfsmusters. Es übernimmt die Rolle
 * des abstrakten Subjekts. Es muss von allen Klassen, die beobachtet werden müssen implementiert werden.
 *
 * In dieser Anwendung sind dies die DAO Klassen. Diese werden beobachtet, um Änderungen am Datenbestand zu bemerken
 * und diese Änderungen an betroffene Clients weiterleiten zu können. Das bedeutet bei jeder Änderung an dem Datenbestand muss anschließend die notify()-Methode
 * aufgerufen werden, um den Beobachtern die Änderungen zu übergeben.
 *
 * Das Generic T gibt an, welcher Datentyp von den Änderungen betroffen ist und von den Beobachtern an die Clients weitergeleitet weden muss.
 */
public interface IObservable<T> {

    /**
     * Mit dieser Methode kann man einen neuen Observer registrieren. Er wird zu einer Liste von observern hinzugefügt,
     * falls diese Liste noch nicht vorhanden ist, wird sie erstellt. Der Beobachter ist nach dem Hinzufügen zu der Liste
     * funktionsfähig.
     * @param observer  der Observer, der registriert werden soll. Dabei spielt es keine Rolle, um welche Implementierung eines
     *                  Observers es sich handelt.
     */
    public void register(EventArg arg, Observer observer);

    /**
     * ein zuvor registrierter Observer kann wieder entfernt werden, indem diese Methode aufgerufen wird. Er wird aus der Liste entfernt.
     * @param observer Der Observer der aus der Liste entfernt werden soll. es muss vor dem Aufruf dieser Methode sichergestellt werden, dass
     *                 es sich bei dem Objekt um einen vorher registrierten, noch existenten Observer handelt.
     */
    public void unregister(Observer observer);

    /**
     * Mit dieser Methode können Observer über eine Änderung benachrichtigt werden. es muss dabei nicht angegeben werden, welche
     * Änderung vorgenommen wurde, dies wissen die Observer selbst. Die Methode löst nur dann eine Aktion bei einem der Observer aus,
     * wenn zuvor ein zu der Ändeurng passender Observer registriert wurde.
     *
     * @param impCode Ein Code, der angibt, welche Observer-Implementierung benachrichtigt werden soll. dabei handelt es sich immer um ein
     *                öffentliches statisches Attribut in der Observer-Klasse. Handelt es sich um keinen gültigen Implementierungs-Code, wird
     *                kein Observer auf das notify() reagieren.
     * @param observable Eine Instanz des Observables, das die notify()-Methode aufgerufen hat. Durch diese Referenz weiß der observer, von wo er eine
     *                   Benachrichtigung bekommen hat.
     * @param t Das Objekt das die Änderung enthält bzw. an dem die Änderung durchgeführt wurde.
     */
    public void notify(String impCode, IObservable observable, T t);

}
