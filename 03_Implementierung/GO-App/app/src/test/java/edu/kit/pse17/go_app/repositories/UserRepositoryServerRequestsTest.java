package edu.kit.pse17.go_app.repositories;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

/**
 * Created by Сеня on 28.08.2017.
 */

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserRepositoryServerRequestsTest {
    private UserRepository userRepo;
    private String userId = "MrHCqabe6MOCsRLwUgVOkjOpPGf1";
    private String instanceId = "dkVE5J7QcxM:APA91bFwKzvTSfXl5lS7_cPGL3kJC-D7C2tPIK_rVKWas5S-DZ8lPh1ERYQcwSo6Z4DzhQ5PRjnQ_2HltjI0EjlDeVUlauLH3gTlUys9PFVJQAh7K9DQahZtI3sYR_qXkbzhwdrCkEG4";

    @Before
    public void setUp() {
        userRepo = UserRepository.getInstance();
    }

    @After
    public void tearDown() {
        GroupRepository groupRepo = GroupRepository.getInstance();
        userRepo.createUser(userId);
        groupRepo.registerDevice(userId, instanceId);

        userRepo = null;
    }

    @Test
    public void a_createUserTest() throws InterruptedException {
        userRepo.createUser(userId);
        TimeUnit.SECONDS.sleep(1); // wait for the response of the server

        /* 201 is HTTP CREATED status code */
        assertEquals(userRepo.getResponseStatus(), 201);
    }

    @Test
    public void b_deleteUserTest() throws InterruptedException {
        userRepo.deleteUser(userId);
        TimeUnit.SECONDS.sleep(1); // wait for the response of the server

        /* 200 is HTTP OK status code */
        assertEquals(userRepo.getResponseStatus(), 200);
    }
}
