package edu.kit.pse17.go_app.model;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;

import java.util.List;

import edu.kit.pse17.go_app.model.entities.Go;
import edu.kit.pse17.go_app.model.entities.Group;

/**
 * Data-Access-Object Interface, das alle Zugriffe auf die lokale Datenbank-Tabelle "users" verwaltet.
 * Die Implementierung der Gruppe wird vom Room-Framework realisiert.
 */

@Dao
public interface UserDao {

    LiveData<List<Group>> getGroupsForUser(String uid);

    LiveData<List<Go>> getGosForUser(String uid);

}


