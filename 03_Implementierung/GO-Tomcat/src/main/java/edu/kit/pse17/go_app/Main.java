package edu.kit.pse17.go_app;

import edu.kit.pse17.go_app.PersistenceLayer.GoEntity;
import edu.kit.pse17.go_app.PersistenceLayer.GroupEntity;
import edu.kit.pse17.go_app.PersistenceLayer.UserEntity;
import edu.kit.pse17.go_app.PersistenceLayer.daos.*;
import org.hibernate.SessionFactory;

import java.util.HashSet;

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
    public static void main(final String[] args) {

        final org.hibernate.cfg.Configuration config = new org.hibernate.cfg.Configuration();
        final SessionFactory sf = config.configure().buildSessionFactory();
        final UserEntity user = new UserEntity();

        final UserDao userDao = new UserDaoImp(sf);
        final GroupDao groupDao = new GroupDaoImp(sf);
        final GoDao goDao = new GoDaoImp(sf);

        user.setEmail("bob@test.com");
        user.setName("bob");
        user.setUid("testid");
        user.setInstanceId("instanceId");
        user.setGroups(null);

        userDao.persist(user);

        final GroupEntity group = new GroupEntity();
        group.setName("TestGruppe");
        group.setDescription("sdfgkslödfkspäerk");
        group.setMembers(new HashSet<>());
        group.setAdmins(new HashSet<>());

        groupDao.persist(group);
        groupDao.addGroupMember(user.getUid(), group.getID());
        final GroupEntity groupP = groupDao.get((long) 1);

        groupP.setName("NewName");
        groupDao.update(groupP);

        final UserEntity user2 = userDao.get(user.getUid());
        GroupEntity group2 = (GroupEntity) user2.getGroups().toArray()[0];
        System.out.println(group2.getMembers().toArray()[0]);


        GoEntity go = new GoEntity(group2, user2, "testgo", "sdfsdfsdf", null, null, (long) 12, (long) 12);
        goDao.persist(go);
        GroupEntity group3 = groupDao.get(group2.getID());
        System.out.println(group3.getGos().size());

        System.out.println(userDao.get(user2.getUid()).getGos().toArray().length);

        //sf.close();
    }
}
