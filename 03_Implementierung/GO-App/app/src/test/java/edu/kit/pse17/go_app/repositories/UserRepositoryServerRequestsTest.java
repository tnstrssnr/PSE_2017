package edu.kit.pse17.go_app.repositories;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

/**
 * Created by Сеня on 28.08.2017.
 */

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserRepositoryServerRequestsTest {
    private UserRepository userRepo;
    private String userId = "MrHCqabe6MOCsRLwUgVOkjOpPGf1";
    //private String instanceId = "dkVE5J7QcxM:APA91bFwKzvTSfXl5lS7_cPGL3kJC-D7C2tPIK_rVKWas5S-DZ8lPh1ERYQcwSo6Z4DzhQ5PRjnQ_2HltjI0EjlDeVUlauLH3gTlUys9PFVJQAh7K9DQahZtI3sYR_qXkbzhwdrCkEG4";

    //private String userId = "TestUser";

    @Before
    public void setUp() {
        userRepo = UserRepository.getInstance();
    }

    @After
    public void tearDown() {
        //userRepo.createUser(userId);
        userRepo.setResponseStatus(0);
        userRepo = null;
    }

    /*@Test
    public void b_createUserTest() throws InterruptedException {
        userRepo.createUser(userId);
        TimeUnit.SECONDS.sleep(5); // wait for the response of the server

        // 201 is HTTP CREATED status code
        assertEquals(userRepo.getResponseStatus(), 201);
    }*/

    /*@Test
    public void a_deleteUserTest() throws InterruptedException {
        userRepo.deleteUser(userId);
        TimeUnit.SECONDS.sleep(10); // wait for the response of the server

        // 200 is HTTP OK status code
        assertEquals(userRepo.getResponseStatus(), 200);
    }*/
}
