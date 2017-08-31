package edu.kit.pse17.go_app.PersistenceLayer.daos;

import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;


public class UserDaoImpTest {

    private SessionFactory mockSf;
    private GroupDaoImp mockGroupDao;
    private GoDaoImp mockGoDao;
    private UserDaoImp daoUnderTest;

    @Before
    public void setUp() throws Exception {
        mockSf = Mockito.mock(SessionFactory.class);
        mockGoDao = Mockito.mock(GoDaoImp.class);
        mockGroupDao = Mockito.mock(GroupDaoImp.class);
        daoUnderTest = new UserDaoImp(mockSf);
        daoUnderTest.setGoDao(mockGoDao);
        daoUnderTest.setGroupDao(mockGroupDao);
    }

    @After
    public void tearDown() throws Exception {
        daoUnderTest = null;
        mockSf = null;
        mockGroupDao = null;
        mockGoDao = null;
    }

    @Test
    public void getUserByEmail() throws Exception {
    }

    @Test
    public void getGroups() throws Exception {
    }

    @Test
    public void getRequests() throws Exception {
    }

    @Test
    public void get() throws Exception {
    }

    @Test
    public void persist() throws Exception {
    }

    @Test
    public void delete() throws Exception {
    }

    @Test
    public void update() throws Exception {
    }

}