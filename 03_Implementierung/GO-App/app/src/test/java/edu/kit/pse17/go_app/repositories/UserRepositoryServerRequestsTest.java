package edu.kit.pse17.go_app.repositories;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

/**
 * Created by Сеня on 28.08.2017.
 */

public class UserRepositoryServerRequestsTest {
    private UserRepository userRepo;
    private GroupRepository groupRepo;
    private String userId = "TestUser";

    @Before
    public void setUp() throws InterruptedException {
        userRepo = UserRepository.getInstance();
        groupRepo = GroupRepository.getInstance();
    }

    @After
    public void tearDown() {
        groupRepo.setResponseStatus(0);
        groupRepo = null;
        userRepo.setResponseStatus(0);
        userRepo = null;
    }

    @Test
    public void createAndDeleteUserTest() throws InterruptedException {
        /* This method also creates user, if this user is not there */
        groupRepo.getData(userId, "test@test.com", "not used", "TestName");
        TimeUnit.SECONDS.sleep(3);
        assertEquals(groupRepo.getResponseStatus(), 200);

        userRepo.deleteUser(userId);
        TimeUnit.SECONDS.sleep(1); // wait for the response of the server

        // 200 is HTTP OK status code
        assertEquals(userRepo.getResponseStatus(), 200);
    }
}
