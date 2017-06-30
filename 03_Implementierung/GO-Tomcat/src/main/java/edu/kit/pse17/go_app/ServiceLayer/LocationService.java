package edu.kit.pse17.go_app.ServiceLayer;

import edu.kit.pse17.go_app.PersistenceLayer.GoEntity;
import edu.kit.pse17.go_app.PersistenceLayer.UserEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by tina on 30.06.17.
 */

@Service
public class LocationService {

    GoEntity go;
    List<UserEntity> activeUsers;
    List<Cluster> groupLocation;

    public LocationService(GoEntity go, List<UserEntity> activeUsers, List<Cluster> groupLocation, ClusterStrategy strat) {
        this.go = go;
        this.activeUsers = activeUsers;
        this.groupLocation = groupLocation;
        this.strat = strat;
    }

    public GoEntity getGo() {
        return go;
    }

    public void setGo(GoEntity go) {
        this.go = go;
    }

    public List<UserEntity> getActiveUsers() {
        return activeUsers;
    }

    public void setActiveUsers(List<UserEntity> activeUsers) {
        this.activeUsers = activeUsers;
    }

    public List<Cluster> getGroupLocation() {
        return groupLocation;
    }

    public void setGroupLocation(List<Cluster> groupLocation) {
        this.groupLocation = groupLocation;
    }

    public ClusterStrategy getStrat() {
        return strat;
    }

    public void setStrat(ClusterStrategy strat) {
        this.strat = strat;
    }

    ClusterStrategy strat;

    public class Cluster {

        private int size;
        private long lat;
        private long lon;

        public Cluster(int size, long lat, long lon) {
            this.size = size;
            this.lat = lat;
            this.lon = lon;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public long getLat() {
            return lat;
        }

        public void setLat(long lat) {
            this.lat = lat;
        }

        public long getLon() {
            return lon;
        }

        public void setLon(long lon) {
            this.lon = lon;
        }
    }

    public class UserLocation {

        private UserEntity user;
        private long lat;
        private long lon;

        public UserLocation(UserEntity user, long lat, long lon) {
            this.user = user;
            this.lat = lat;
            this.lon = lon;
        }

        public UserEntity getUser() {
            return user;
        }

        public void setUser(UserEntity user) {
            this.user = user;
        }

        public long getLat() {
            return lat;
        }

        public void setLat(long lat) {
            this.lat = lat;
        }

        public long getLon() {
            return lon;
        }

        public void setLon(long lon) {
            this.lon = lon;
        }
    }
}
