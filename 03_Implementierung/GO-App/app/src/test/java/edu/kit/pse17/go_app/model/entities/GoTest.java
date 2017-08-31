package edu.kit.pse17.go_app.model.entities;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import edu.kit.pse17.go_app.model.Status;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Сеня on 28.08.2017.
 */

public class GoTest {
    private Go go;

    @Before
    public void setUp() {
        go = new Go();
    }

    @After
    public void tearDown() {
        go = null;
    }

    @Test
    public void constructorTest() {
        assertEquals("Default Go Name", go.getName());
    }

    @Test
    public void getStatusTest() {
        String userId = "ID1";
        User user = new User(userId, "me@mail.com", "me");
        UserGoStatus status = new UserGoStatus(user, go, Status.GOING);
        List<UserGoStatus> list = go.getParticipantsList();
        list.add(status);
        go.setParticipantsList(list);

        UserGoStatus newStatus = go.getStatus(userId);
        assertNotNull(newStatus);
        assertEquals("ID1", newStatus.getUser().getUid());
        assertEquals("Default Go Name", newStatus.getGo().getName());
        assertEquals(Status.GOING, newStatus.getStatus());
    }
}
