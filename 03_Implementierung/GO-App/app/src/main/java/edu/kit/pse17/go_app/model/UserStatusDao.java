package edu.kit.pse17.go_app.model;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Created by tina on 02.07.17.
 */

@Dao
public interface UserStatusDao {

    @Insert
    public void save(UserGoStatus status);

    @Query("SELECT user FROM group_memberships WHERE goID =: goID")
    public List<User> getAcceptedUsers(long goId);

    @Query("")
    public List<User> getDeclinedUsers(long goId);

    @Query("")
    public List<User> getActiveUsers(long goId);

    @Update
    public void updateStats(UserGoStatus status);
}
