package edu.kit.pse17.go_app.model;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

/**
 * Created by tina on 01.07.17.
 */

@Database(entities = {Group.class, Go.class, User.class}, version = 1)
public abstract class LocalDatabase extends RoomDatabase {

    public abstract GroupDao groupDao();

    public abstract GoDao goDao();

    public abstract UserDao userDao();

}
