package edu.kit.pse17.go_app.PersistenceLayer.daos;

import edu.kit.pse17.go_app.PersistenceLayer.GoEntity;
import edu.kit.pse17.go_app.PersistenceLayer.GroupEntity;
import edu.kit.pse17.go_app.PersistenceLayer.UserEntity;
import edu.kit.pse17.go_app.TestData;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;


public class EntityTests {

    @Test
    public void equalsContractUserTest() {
        EqualsVerifier.forClass(UserEntity.class)
                .withPrefabValues(UserEntity.class, TestData.getTestUserAlice(), TestData.getTestUserBob())
                .withPrefabValues(GroupEntity.class, TestData.getTestGroupBar(), TestData.getTestGroupFoo())
                .withPrefabValues(GoEntity.class, TestData.getTestGoDinner(), TestData.getTestGoLunch())
                .withIgnoredFields("groups", "gos", "requests")
                .verify();
    }

    @Test
    public void equalsContractGroupTest() {
        EqualsVerifier.forClass(GroupEntity.class)
                .withPrefabValues(UserEntity.class, TestData.getTestUserAlice(), TestData.getTestUserBob())
                .withPrefabValues(GroupEntity.class, TestData.getTestGroupBar(), TestData.getTestGroupFoo())
                .withPrefabValues(GoEntity.class, TestData.getTestGoDinner(), TestData.getTestGoLunch())
                .withIgnoredFields("admins", "members", "requests", "gos")
                .verify();
    }

    @Test
    public void equalsContractGoTest() {
        EqualsVerifier.forClass(GoEntity.class)
                .withPrefabValues(UserEntity.class, TestData.getTestUserAlice(), TestData.getTestUserBob())
                .withPrefabValues(GroupEntity.class, TestData.getTestGroupBar(), TestData.getTestGroupFoo())
                .withPrefabValues(GoEntity.class, TestData.getTestGoDinner(), TestData.getTestGoLunch())
                .withIgnoredFields("group", "owner", "goneUsers", "goingUsers", "notGoingUsers")
                .verify();
    }
    
}
