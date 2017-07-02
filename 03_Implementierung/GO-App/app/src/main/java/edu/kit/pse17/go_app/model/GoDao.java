package edu.kit.pse17.go_app.model;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

/**
 *  Data-Access-Object Interface, das alle Zugriffe auf die lokale Datenbank-Tabelle "go" verwaltet.
 *  Die Implementierung der Gruppe wird vom Room-Framework realisiert.
 */

@Dao
public interface GoDao {

    /**
     * Persistiert ein neues Go-Objekt in der Datenbank
     *
     * @param go das neue Go
     */
    @Insert
    void save(Go go);

    /**
     * Gibt ein Livedata-Objekt des Gos mit der passenden Go-ID zurück.
     *
     * @param goId die Id des gesuchten Gos
     * @return LiveData Objekt des gesuchten Gos
     */
    @Query("SELECT * FROM go WHERE id =: goId")
    LiveData<Go> load(long goId);

}
