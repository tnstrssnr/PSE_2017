package edu.kit.pse17.go_app.ServiceLayer;

import edu.kit.pse17.go_app.PersistenceLayer.GroupEntity;
import edu.kit.pse17.go_app.PersistenceLayer.daos.GroupDaoImp;

/**
 * Created by tina on 30.06.17.
 */
public class GroupObserver extends Observer<GroupDaoImp, GroupEntity> {

    private GroupEntity state;

    @Override
    public void update() {

    }
}
