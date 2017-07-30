package edu.kit.pse17.go_app.PersistenceLayer.daos;

import edu.kit.pse17.go_app.PersistenceLayer.GroupEntity;
import edu.kit.pse17.go_app.ServiceLayer.observer.Observer;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.unitils.UnitilsJUnit4;
import org.unitils.dbunit.annotation.DataSet;
import org.unitils.dbunit.datasetloadstrategy.impl.CleanInsertLoadStrategy;
import org.unitils.orm.hibernate.HibernateUnitils;
import org.unitils.orm.hibernate.annotation.HibernateSessionFactory;

import javax.sql.DataSource;

import static org.junit.Assert.assertEquals;

public class GroupDaoImpTest extends UnitilsJUnit4 {

    @HibernateSessionFactory("testing.cfg.xml")
    private SessionFactory sf;

    private DataSource dataSource;


    private GroupDaoImp dao;
    private GroupEntity testGroup;

    @Before
    public void setUp() {
        dao = new GroupDaoImp(sf);
        testGroup = new GroupEntity();
    }

    @After
    public void tearDown() {
        dao = null;
    }

    @Test
    public void testDbMapping() {
        HibernateUnitils.assertMappingWithDatabaseConsistent();
    }

    @Test
    public void register() {
        assertEquals(0, dao.getObserver().size());
        Observer mockObserver = Mockito.mock(Observer.class);
        dao.register(mockObserver);
        assertEquals(1, dao.getObserver().size());
        assertEquals(mockObserver, dao.getObserver().get(0));
    }

    @Test
    public void unregister() {
        Observer mockObserver = Mockito.mock(Observer.class);
        dao.getObserver().add(mockObserver);
        assertEquals(1, dao.getObserver().size());
        dao.unregister(mockObserver);
        assertEquals(0, dao.getObserver().size());
    }

    @Test
    public void get() {
        GroupEntity result = dao.get(1l);
        assertEquals(testGroup, result);
    }

    @Test
    public void persist() {
    }

    @Test
    public void delete() {
    }

    @Test
    public void update() {
    }

    @Test
    public void addGroupMember() {
    }

    @Test
    public void removeGroupRequest() {
    }

    @Test
    public void addGroupRequest() {
    }

    @Test
    public void removeGroupMember() {
    }

    @Test
    public void addAdmin() {
    }

}