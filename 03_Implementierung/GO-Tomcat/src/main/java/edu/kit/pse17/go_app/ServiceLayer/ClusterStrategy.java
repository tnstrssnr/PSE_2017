package edu.kit.pse17.go_app.ServiceLayer;

import java.util.List;

/**
 * Created by tina on 30.06.17.
 */
public interface ClusterStrategy {

    List<LocationService.Cluster> calculateCluster(List<LocationService.UserLocation> userLocationList);

}
