package edu.kit.pse17.go_app.ServiceLayer;

import java.util.List;

/**
 * Created by tina on 30.06.17.
 */
public class GoClusterStrategy implements ClusterStrategy {

    private int threshold;
    private long destLat;
    private long destLon;

    public GoClusterStrategy(int threshold, long destLat, long destLon) {
        this.threshold = threshold;
        this.destLat = destLat;
        this.destLon = destLon;
    }

    public int getThreshold() {
        return threshold;
    }

    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }

    public long getDestLat() {
        return destLat;
    }

    public void setDestLat(long destLat) {
        this.destLat = destLat;
    }

    public long getDestLon() {
        return destLon;
    }

    public void setDestLon(long destLon) {
        this.destLon = destLon;
    }

    @Override
    public List<LocationService.Cluster> calculateCluster(List<LocationService.UserLocation> userLocationList) {
        return null;
    }
}
