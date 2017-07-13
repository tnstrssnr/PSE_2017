package edu.kit.pse17.go_app;

import edu.kit.pse17.go_app.PersistenceLayer.UserEntity;
import edu.kit.pse17.go_app.PersistenceLayer.daos.*;
import org.hibernate.SessionFactory;

import javax.security.auth.login.AppConfigurationEntry;
import javax.security.auth.login.Configuration;

/**
 * Die Main-Klasse der Server-Anwendung. In ihr wird keine Anwendungslogik ausgeführt.
 *
 * Sie enthält die main-methode, wo die Ausführung des Programms gestartet wird und danach die Kontrolle
 * über die Ausführung an andere Klassen abgegeben wird.
 */
public class Main {

    /**
     * Die Main-Methode, wo die Ausführung des Programms gestartet wird.
     *
     * In ihr werden alle wichtigen Objekte des Programms instanziiert, die zur Audführung benötigt werden (Starten der Spring-Applikation,
     * aufbauen einer Verbindung zur Datenbank,...)
     *
     * Danach wird an diese objekte due Kontrolle über den Programmablauf übergeben.
     * @param args Es werden der Main-Methode keine Argumente übergeben bzw. übergebene Argumente werden ignoriert.
     */
    public static void main(String[] args) {

        org.hibernate.cfg.Configuration config = new org.hibernate.cfg.Configuration();
        SessionFactory sf = config.configure().buildSessionFactory();
        UserEntity user = new UserEntity();

        UserDao userDao = new UserDaoImp(sf);
        GroupDao groupDao = (GroupDao) new GroupDaoImp(sf);
        GoDao goDao = (GoDao) new GoDaoImp(sf);

        user.setEmail("bob@test.com");
        user.setName("bob");
        user.setUid("testid");
        user.setInstanceId("instanceId");
        user.setGroups(null);

        userDao.addUser(user);
        }
    }
