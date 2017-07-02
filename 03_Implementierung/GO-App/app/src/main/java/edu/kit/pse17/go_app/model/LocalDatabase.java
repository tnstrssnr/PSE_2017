package edu.kit.pse17.go_app.model;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

/**
 * abstrakte Klasse, zur Erzeugung einer lokalen SQLite Datenbank.
 * Die Erzeugung des Datenbankschemas und der Relationen wird vom Room-Framework übernommen.
 */

@Database(entities = {Group.class, Go.class, User.class}, version = 1)
public abstract class LocalDatabase extends RoomDatabase {

    public abstract GroupDao groupDao();

    public abstract GoDao goDao();

    public abstract UserDao userDao();

}
