package edu.kit.pse17.go_app;

import com.google.gson.Gson;
import edu.kit.pse17.go_app.PersistenceLayer.GoEntity;
import edu.kit.pse17.go_app.PersistenceLayer.GroupEntity;
import edu.kit.pse17.go_app.PersistenceLayer.UserEntity;
import edu.kit.pse17.go_app.PersistenceLayer.daos.*;
import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.database.QueryDataSet;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.hibernate.SessionFactory;
import org.json.simple.JSONObject;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;

/**
 * Die Main-Klasse der Server-Anwendung. In ihr wird keine Anwendungslogik ausgeführt.
 * <p>
 * Sie enthält die main-methode, wo die Ausführung des Programms gestartet wird und danach die Kontrolle
 * über die Ausführung an andere Klassen abgegeben wird.
 */
public class Main {

    /**
     * Die Main-Methode, wo die Ausführung des Programms gestartet wird.
     * <p>
     * In ihr werden alle wichtigen Objekte des Programms instanziiert, die zur Audführung benötigt werden (Starten der
     * Spring-Applikation, aufbauen einer Verbindung zur Datenbank,...)
     * <p>
     * Danach wird an diese objekte due Kontrolle über den Programmablauf übergeben.
     *
     * @param args Es werden der Main-Methode keine Argumente übergeben bzw. übergebene Argumente werden ignoriert.
     */
    public static void main(final String[] args) throws DatabaseUnitException, FileNotFoundException {
        List<String> entity_ids = new ArrayList<>();

        UserEntity bob = new UserEntity();
        bob.setName("Bob");
        bob.setInstanceId("testInstance_1");
        bob.setEmail("bob@testmail.com");
        bob.setUid("testid_1");
        bob.setGos(new HashSet<>());

        UserEntity alice = new UserEntity();
        alice.setName("Alice");
        alice.setUid("testid_2");
        alice.setEmail("alice@testmail.com");
        alice.setInstanceId("testInstance_2");
        alice.setGos(new HashSet<>());

        //userDao.persist(bob);
        //userDao.persist(alice);

        GroupEntity foo = new GroupEntity();
        foo.setName("Foo");
        foo.setDescription("Test Descritpion");
        Set<UserEntity> admins = new HashSet<>();
        admins.add(bob);
        Set<UserEntity> members = new HashSet<>();
        members.add(alice);
        members.add(bob);
        foo.setAdmins(admins);
        foo.setMembers(members);
        foo.setGos(new HashSet<>());

        GoEntity go = new GoEntity(foo, bob, "lunch", "test description", new Date(2017, 7, 30), new Date(2017, 8, 1), 0l, 0l);

        GroupEntity group = foo;
        UserEntity user = alice;
        entity_ids.add(String.valueOf(alice.getUid()));
        entity_ids.add(String.valueOf(foo.getID()));

        int newStatus = 1;

        //create JSON Object to send to clients
        JSONObject json = new JSONObject();
        json.put("user_id", user.getUid());
        json.put("go_id", go.getID());
        json.put("status", newStatus);

        String data = json.toJSONString();


        System.out.println(data);


        /**
        final org.hibernate.cfg.Configuration config = new org.hibernate.cfg.Configuration();
        final SessionFactory sf = config.configure().buildSessionFactory();

        final UserDao userDao = new UserDaoImp(sf);
        final GroupDao groupDao = new GroupDaoImp(sf);
        final GoDao goDao = new GoDaoImp(sf);

        UserEntity bob = new UserEntity();
        bob.setName("Bob");
        bob.setInstanceId("testInstance_1");
        bob.setEmail("bob@testmail.com");
        bob.setUid("testid_1");
        bob.setGos(new HashSet<>());

        UserEntity alice = new UserEntity();
        alice.setName("Alice");
        alice.setUid("testid_2");
        alice.setEmail("alice@testmail.com");
        alice.setInstanceId("testInstance_2");
        alice.setGos(new HashSet<>());

        userDao.persist(bob);
        userDao.persist(alice);

        GroupEntity foo = new GroupEntity();
        foo.setName("Foo");
        foo.setDescription("Test Descritpion");
        Set<UserEntity> admins = new HashSet<>();
        admins.add(bob);
        Set<UserEntity> members = new HashSet<>();
        members.add(alice);
        members.add(bob);
        foo.setAdmins(admins);
        foo.setMembers(members);
        foo.setGos(new HashSet<>());

        GroupEntity bar = new GroupEntity();
        bar.setName("Bar");
        bar.setDescription("Test Description");
        HashSet<UserEntity> barAdmins = new HashSet<>();
        barAdmins.add(alice);
        Set<UserEntity> requests = new HashSet<>();
        Set<UserEntity> barMembers = new HashSet<>();
        barMembers.add(alice);
        requests.add(bob);
        bar.setAdmins(barAdmins);
        bar.setMembers(barMembers);
        bar.setRequests(requests);
        bar.setGos(new HashSet<>());

        groupDao.persist(foo);
        groupDao.persist(bar);

        GoEntity lunch = new GoEntity(foo, bob, "lunch", "test description", new Date(2017, 7, 30), new Date(2017, 8, 1), 0l, 0l);
        GoEntity dinner = new GoEntity(foo, alice, "dinner", "test description", new Date(2017, 7, 30), new Date(2017, 8, 1), 0l, 0l);
        goDao.persist(lunch);
        goDao.persist(dinner);
        lunch.setNotGoingUsers(null);
        lunch.setGoneUsers(null);
        lunch.setGoingUsers(null);
        lunch.setOwner(null);
        lunch.setGroup(null);
        System.out.println(new Gson().toJson(lunch));


        Class driverClass;
        try {
            driverClass =  Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection jdbcConnection = null;
        try {
            jdbcConnection = DriverManager.getConnection("jdbc:mysql://localhost/pse_development", "root", "69h97jnv");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        IDatabaseConnection connection = new DatabaseConnection(jdbcConnection);

        QueryDataSet dataSet = null;

            dataSet = new QueryDataSet(connection);
            dataSet.addTable("USERS");
            dataSet.addTable("GOS");
            dataSet.addTable("GROUPS");
            dataSet.addTable("ADMINS");
            dataSet.addTable("GOING_USERS");
            dataSet.addTable("GONE_USERS");
            dataSet.addTable("NOT_GOING_USERS");
            dataSet.addTable("MEMBERS");
            dataSet.addTable("REQUESTS");
            dataSet.addTable("hibernate_sequence");

        try {
            FlatXmlDataSet.write(dataSet, new FileOutputStream("dataset.xml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
         */

    }
}
