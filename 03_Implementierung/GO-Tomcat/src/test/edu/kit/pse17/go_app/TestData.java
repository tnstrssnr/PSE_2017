package edu.kit.pse17.go_app;


import edu.kit.pse17.go_app.PersistenceLayer.GoEntity;
import edu.kit.pse17.go_app.PersistenceLayer.GroupEntity;
import edu.kit.pse17.go_app.PersistenceLayer.Status;
import edu.kit.pse17.go_app.PersistenceLayer.UserEntity;
import edu.kit.pse17.go_app.PersistenceLayer.clientEntities.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Class provides TestData for the other Testclasses
 */
public class TestData {

    private static UserEntity alice;
    private static UserEntity bob;

    private static GroupEntity foo;
    private static GroupEntity bar;

    private static GoEntity lunch;
    private static GoEntity dinner;

    private static User cBob;
    private static User cAlice;

    private static Group cFoo;
    private static Group cBar;

    private static Go cLunch;
    private static Go cDinner;

    /**
     * Sets up the Test Objects. Method is invoked every time a getter-Method from this class is called, to make sure
     * the objects, that are returned are always the same.
     */
    private static void setUpData() {

        bob = new UserEntity();
        bob.setName("Bob");
        bob.setInstanceId("testInstance_1");
        bob.setEmail("bob@testmail.com");
        bob.setUid("testid_1");
        bob.setGos(new HashSet<>());
        bob.setRequests(new HashSet<>());
        bob.setGroups(new HashSet<>());

        alice = new UserEntity();
        alice.setName("Alice");
        alice.setUid("testid_2");
        alice.setEmail("alice@testmail.com");
        alice.setInstanceId("testInstance_2");
        alice.setGos(new HashSet<>());

        foo = new GroupEntity();
        foo.setID(0);
        foo.setName("Foo");
        foo.setDescription("Test Descritpion");
        Set<UserEntity> admins = new HashSet<>();
        admins.add(bob);
        Set<UserEntity> members = new HashSet<>();
        members.add(alice);
        members.add(bob);
        Set<UserEntity> fooRequests = new HashSet<>();
        foo.setRequests(fooRequests);
        foo.setAdmins(admins);
        foo.setMembers(members);
        foo.setGos(new HashSet<>());

        bar = new GroupEntity();
        bar.setID(1);
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

        lunch = new GoEntity(foo, bob, "lunch", "test description", new Date(2017, 7, 30).toString(), new Date(2017, 8, 1).toString(), 0, 0);
        dinner = new GoEntity(foo, alice, "dinner", "test description", new Date(2017, 7, 30).toString(), new Date(2017, 8, 1).toString(), 0, 0);
        lunch.setID(1);
        dinner.setID(2);
        lunch.setNotGoingUsers(new HashSet<>());
        lunch.setGoingUsers(new HashSet<>());
        lunch.setGoneUsers(new HashSet<>());

        foo.getGos().add(lunch);
        foo.getGos().add(dinner);

        cBob = new User(bob.getUid(), bob.getInstanceId(), bob.getName(), bob.getEmail());
        cAlice = new User(alice.getUid(), alice.getInstanceId(), alice.getName(), alice.getEmail());

        cFoo = new Group(foo.getID(), foo.getName(), foo.getDescription(), foo.getMembers().size(), null, new ArrayList<>(), new ArrayList<>());
        cFoo.getMembershipList().add(new GroupMembership(cBob, cFoo, true, false));
        cFoo.getMembershipList().add(new GroupMembership(cAlice, cFoo, false, false));
        cFoo.getCurrentGos().add(cLunch);
        cFoo.getCurrentGos().add(cDinner);

        cBar = new Group(bar.getID(), bar.getName(), bar.getDescription(), bar.getMembers().size(), null, new ArrayList<>(), new ArrayList<>());
        cBar.getMembershipList().add(new GroupMembership(cAlice, cBar, true, false));
        cBar.getMembershipList().add(new GroupMembership(cBob, cBar, false, true));

        cLunch = new Go(lunch.getID(), lunch.getName(), lunch.getDescription(), lunch.getStart(), lunch.getEnd(), cFoo, lunch.getLat(), lunch.getLon(), lunch.getOwner().getUid(), lunch.getOwner().getName(), new ArrayList<>(), null);
        cLunch.getParticipantsList().add(new UserGoStatus(cAlice, cLunch, Status.GOING));
        cLunch.getParticipantsList().add(new UserGoStatus(cBob, cLunch, Status.GOING));

        cDinner = new Go(dinner.getID(), dinner.getName(), dinner.getDescription(), dinner.getStart(), dinner.getEnd(), cFoo, dinner.getLat(), dinner.getLon(), dinner.getOwner().getUid(), dinner.getOwner().getName(), new ArrayList<>(), null);
    }

    public static GroupEntity getTestGroupFoo() {
        setUpData();
        System.out.println(foo.getName());
        return foo;
    }

    public static GroupEntity getTestGroupBar() {
        setUpData();
        return bar;
    }

    public static UserEntity getTestUserBob() {
        setUpData();
        return bob;
    }

    public static UserEntity getTestUserAlice() {
        setUpData();
        return alice;
    }

    public static GoEntity getTestGoLunch() {
        setUpData();
        return lunch;
    }

    public static GoEntity getTestGoDinner() {
        setUpData();
        return dinner;
    }

    public static User getTestcBob() {
        setUpData();
        return cBob;
    }

    public static User getTestcAlice() {
        setUpData();
        return cAlice;
    }

    public static Group getTestcFoo() {
        setUpData();
        return cFoo;
    }

    public static Group getTestcBar() {
        setUpData();
        return cBar;
    }

    public static Go getTestcLunch() {
        setUpData();
        return cLunch;
    }

    public static Go getTestcDinner() {
        setUpData();
        return cDinner;
    }
}
