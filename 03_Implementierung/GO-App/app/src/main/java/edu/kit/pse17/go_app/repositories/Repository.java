package edu.kit.pse17.go_app.repositories;

import android.arch.lifecycle.LiveData;

/**
 * Die Klasse dient als Vermittler zwischen ViewModel und Laden von Daten
 * Hier wird entschieden, wen man anspricht um bestimmte Daten zu laden
 */

public abstract class Repository <T>{
    /*
    * ViewModel beim ersten Laden der Daten lädt mit der Methode alle Daten runter
    * */
    public abstract LiveData<T> fetchData();
    /*
    * Nach dem Broadcast kann ViewModel über diese Methode neue Daten laden
    * */
    public abstract LiveData<T> getUpdatedData();
    /*
    * Command ruft die Methode auf und die Broadcasts werden versandt, die sagen, dass einige Infos
    * verändert wurden
    * */
    public static void receiveUpdatedData(){
        //
    };
}
