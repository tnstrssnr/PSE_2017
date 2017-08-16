package edu.kit.pse17.go_app.PersistenceLayer.daos;

import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.unitils.UnitilsJUnit4TestClassRunner;
import org.unitils.dbunit.annotation.DataSet;
import org.unitils.orm.hibernate.annotation.HibernateSessionFactory;

@Ignore
@RunWith(UnitilsJUnit4TestClassRunner.class)
public class UserDaoImpTest {

    @HibernateSessionFactory({"testing.cfg.xml"})
    private static SessionFactory sf;
    private UserDaoImp userDao;

    @Before
    public void setUp() throws Exception {
        userDao = new UserDaoImp();
    }

    @After
    public void tearDown() throws Exception {
        userDao = null;
    }

    @Test
    public void register() throws Exception {
    }

    @Test
    public void unregister() throws Exception {
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
    @DataSet("dataset.xml")
    public void get() throws Exception {
        userDao.get("testid_1");
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