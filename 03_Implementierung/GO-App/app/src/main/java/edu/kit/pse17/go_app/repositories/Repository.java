package edu.kit.pse17.go_app.repositories;

/**
 * Die Klasse dient als Vermittler zwischen ViewModel und Laden von Daten
 * Hier wird entschieden, wen man anspricht um bestimmte Daten zu laden
 */

public abstract class Repository<T> {
    /*
    * ViewModel beim ersten Laden der Daten lädt mit der Methode alle Daten runter
    * */
    public abstract T fetchData();
    /*
    * Nach dem Broadcast kann ViewModel über diese Methode neue Daten laden
    * */
    public abstract T getUpdatedData();
    /*
    * Command ruft die Methode auf und die Broadcasts werden versandt, die sagen, dass einige Infos
    * verändert wurden
    * */
    public static void receiveUpdatedData(){
        //
    };
}
