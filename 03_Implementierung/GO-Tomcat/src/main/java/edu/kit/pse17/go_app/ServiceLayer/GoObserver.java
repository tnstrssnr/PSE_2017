package edu.kit.pse17.go_app.ServiceLayer;

import edu.kit.pse17.go_app.PersistenceLayer.GoEntity;
import edu.kit.pse17.go_app.PersistenceLayer.daos.GoDaoImp;

/**
 * Created by tina on 30.06.17.
 */
public class GoObserver extends Observer<GoDaoImp, GoEntity> {

    private GoEntity state;

    @Override
    public void update() {

    }
}
