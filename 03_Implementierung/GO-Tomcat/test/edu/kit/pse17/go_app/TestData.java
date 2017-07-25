package edu.kit.pse17.go_app;


import edu.kit.pse17.go_app.PersistenceLayer.GoEntity;
import edu.kit.pse17.go_app.PersistenceLayer.GroupEntity;
import edu.kit.pse17.go_app.PersistenceLayer.UserEntity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class TestData {
    private static boolean isInitialized;

    private static UserEntity alice;
    private static UserEntity bob;

    private static GroupEntity foo;
    private static GroupEntity bar;

    private static GoEntity lunch;
    private static GoEntity dinner;

    private static void setUpData() {

        if (!isInitialized) {
            bob = new UserEntity();
            bob.setName("Bob");
            bob.setInstanceId("testInstance_1");
            bob.setEmail("bob@testmail.com");
            bob.setUid("testid_1");
            bob.setGos(new HashSet<>());

            alice = new UserEntity();
            alice.setName("Alice");
            alice.setUid("testid_2");
            alice.setEmail("alice@testmail.com");
            alice.setInstanceId("testInstance_2");
            alice.setGos(new HashSet<>());

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

            GoEntity lunch = new GoEntity(foo, bob, "lunch", "test description", new Date(2017, 7, 30), new Date(2017, 8, 1), 0l, 0l);
            GoEntity dinner = new GoEntity(foo, alice, "dinner", "test description", new Date(2017, 7, 30), new Date(2017, 8, 1), 0l, 0l);
            lunch.setNotGoingUsers(null);
            lunch.setGoneUsers(null);
            lunch.setGoingUsers(null);
            lunch.setOwner(null);
            lunch.setGroup(null);
        }

    }

    public static GroupEntity getTestGroupFoo() {
        setUpData();
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
}
