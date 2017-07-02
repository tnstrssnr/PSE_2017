package edu.kit.pse17.go_app.model;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

/**
 * Data-Access-Object Interface, das alle Zugriffe auf die lokale Datenbank-Tabelle "group" verwaltet.
 * Die Implementierung der Gruppe wird vom Room-Framework realisiert.
 */

@Dao
public interface GroupDao {

    @Insert
    void save(Group group);

    @Query("SELECT * FROM group WHERE id =: groupId")
    LiveData<Group> load(long groupId);
}
