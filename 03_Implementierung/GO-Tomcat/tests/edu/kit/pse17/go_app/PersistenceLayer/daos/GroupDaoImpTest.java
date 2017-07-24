package edu.kit.pse17.go_app.PersistenceLayer.daos;

import edu.kit.pse17.go_app.PersistenceLayer.GroupEntity;
import edu.kit.pse17.go_app.PersistenceLayer.daos.GroupDaoImp;
import edu.kit.pse17.go_app.ServiceLayer.Observer;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.unitils.UnitilsJUnit4;
import org.unitils.orm.hibernate.HibernateUnitils;
import org.unitils.orm.hibernate.annotation.HibernateSessionFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GroupDaoImpTest extends UnitilsJUnit4 {

    @HibernateSessionFactory("testing.cfg.xml")
    private SessionFactory sf;
    private GroupDaoImp dao;
    private GroupEntity testGroup;

    @BeforeEach
    void setUp() {
        dao = new GroupDaoImp(sf);
        testGroup = new GroupEntity();
    }

    @AfterEach
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