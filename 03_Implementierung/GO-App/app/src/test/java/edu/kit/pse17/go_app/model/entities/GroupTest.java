package edu.kit.pse17.go_app.model.entities;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Сеня on 28.08.2017.
 */

public class GroupTest {
    private Group group;
    private static final String USER_ID_FIRST = "ID1";
    private static final String USER_ID_SECOND = "ID21";

    @Before
    public void setUp() {
        group = new Group();
    }

    @After
    public void tearDown() {
        group = null;
    }

    @Test
    public void constructorTest() {
        assertEquals("Default Name", group.getName());
        assertEquals(0, group.getMemberCount());
    }

    @Test
    public void isRequestTest() {
        setMockedMemberships();

        assertTrue(group.isRequest(USER_ID_SECOND));
        assertFalse(group.isRequest(USER_ID_FIRST));
    }

    @Test
    public void isAdminTest() {
        setMockedMemberships();

        assertTrue(group.isAdmin(USER_ID_FIRST));
        assertFalse(group.isAdmin(USER_ID_SECOND));
    }

    private void setMockedMemberships() {
        User userFirst = new User(USER_ID_FIRST, "me@mail.com", "me");
        User userSecond = new User(USER_ID_SECOND, "me_21@mail.com", "me_21");

        List<GroupMembership> list = group.getMembershipList();
        GroupMembership memberFirst = new GroupMembership(userFirst, group, true, false);
        GroupMembership memberSecond = new GroupMembership(userSecond, group, false, true);
        list.add(memberFirst);
        list.add(memberSecond);
        group.setMembershipList(list);
    }
}
