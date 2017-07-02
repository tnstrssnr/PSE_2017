package edu.kit.pse17.go_app.model;

import android.location.Location;

import java.util.List;

/**
 * Die Objekte der Klasse kapseln die geclusterten GPS-Daten der Go-Teilnehmer
 */

public class GroupLocation {
    
    private class Cluster{

        private Location location;
        private int size;

        public Location getLocation() {
            return location;
        }

        public void setLocation(Location location) {
            this.location = location;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }
    }

    private List<Cluster> cluster;


    public List<Cluster> getCluster() {
        return cluster;
    }

    public void setCluster(List<Cluster> cluster) {
        this.cluster = cluster;
    }
}
