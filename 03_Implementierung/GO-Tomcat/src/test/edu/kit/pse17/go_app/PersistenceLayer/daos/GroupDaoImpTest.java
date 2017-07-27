package edu.kit.pse17.go_app.PersistenceLayer.daos;

import edu.kit.pse17.go_app.PersistenceLayer.GroupEntity;
import edu.kit.pse17.go_app.PersistenceLayer.daos.GroupDaoImp;
import edu.kit.pse17.go_app.ServiceLayer.observer.Observer;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.unitils.UnitilsJUnit4;
import org.unitils.orm.hibernate.HibernateUnitils;
import org.unitils.orm.hibernate.annotation.HibernateSessionFactory;

import static org.junit.Assert.assertEquals;

class GroupDaoImpTest extends UnitilsJUnit4 {

    @HibernateSessionFactory("testing.cfg.xml")
    private SessionFactory sf;
    private GroupDaoImp dao;
    private GroupEntity testGroup;

    @Before
    void setUp() {
        dao = new GroupDaoImp(sf);
        testGroup = new GroupEntity();
    }

    @After
    void tearDown() {
        dao = null;
    }

    @Test
    void testDbMapping() {
        HibernateUnitils.assertMappingWithDatabaseConsistent();
    }

    @Test
    void register() {
        assertEquals(0, dao.getObserver().size());
        Observer mockObserver = Mockito.mock(Observer.class);
        dao.register(mockObserver);
        assertEquals(1, dao.getObserver().size());
        assertEquals(mockObserver, dao.getObserver().get(0));
    }

    @Test
    void unregister() {
        Observer mockObserver = Mockito.mock(Observer.class);
        dao.getObserver().add(mockObserver);
        assertEquals(1, dao.getObserver().size());
        dao.unregister(mockObserver);
        assertEquals(0, dao.getObserver().size());
    }

    @Test
    void get() {
        GroupEntity result = dao.get(1l);
        assertEquals(testGroup, result);
    }

    @Test
    void persist() {
    }

    @Test
    void delete() {
    }

    @Test
    void update() {
    }

    @Test
    void addGroupMember() {
    }

    @Test
    void removeGroupRequest() {
    }

    @Test
    void addGroupRequest() {
    }

    @Test
    void removeGroupMember() {
    }

    @Test
    void addAdmin() {
    }

}