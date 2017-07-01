package edu.kit.pse17.go_app.model;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

/**
 * Created by tina on 01.07.17.
 */

@Dao
public interface GoDao {

    @Insert
    void save(Go go);

    @Query("SELECT * FROM group WHERE id =: groupId")
    LiveData<Go> load(long goId);

}
