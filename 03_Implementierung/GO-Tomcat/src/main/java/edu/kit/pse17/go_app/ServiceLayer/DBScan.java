package edu.kit.pse17.go_app.ServiceLayer;

import java.util.*;

/**
 * Diese Klasse beinhaltet den Clusteringalgorithmus, wobei einige Funktionen/Methoden zur Vereinfachung des Algorithmus
 * in der Utilityklasse liegen.
 *
 * Der Algorithmus macht sich hierzu verschidene Listen/Vektoren zu nutzen.
 *
 * Basiert auf einem Algorithmus von Kanwar Bhajneek.
 *
 */


public class DBScan
{

    /**
     *  Vektor für <UserLocation>-Vektoren: entspricht dem Resultat des eigentlichen Clustering.
     */

    public static Vector<List> resultList = new Vector<>();

    /**
     * Liste der UserLocations die geclustert werden.
     */

    public static Vector<UserLocation> pointList;

    /**
     * Vektor der für UserLocations genutzt wird, die benachbart zu einer bestimmten Userlocation sind
     */

    public static Vector<UserLocation> Neighbours ;



    public DBScan(List<UserLocation> userLocationList) {
        this.pointList = Utility.getList(userLocationList);
    }

    /**
     * Die eigentliche Methode des ClusteringAlgorithmus.
     *
     * @param tdistance Distanz die zwischen Punkten/UserLocations liegen muss um als benachbart zu gelten.
     * @param minpt Mindestanzahl an Punkten um ein Cluster zu bilden.
     * @param userLocationList Die Liste an UserLocation mit der das Clustering betrieben wird.
     * @return Gibt eine Vektorliste mit Vektoren die, die Cluster beinhalten.
     */

    public Vector<List> applyDbscan(double tdistance, int minpt, List<UserLocation> userLocationList)
    {
        resultList.clear();
        pointList.clear();
        Utility.VisitList.clear();
        pointList=Utility.getList(userLocationList);

        int index =0;


        while (pointList.size()>index){
            UserLocation p =pointList.get(index);
            if(!Utility.isVisited(p)){

                Utility.visited(p);

                Neighbours =Utility.getNeighbours(p, tdistance);


                if (Neighbours.size()>=minpt){


                    int ind=0;
                    while(Neighbours.size()>ind){

                        UserLocation r = Neighbours.get(ind);
                        if(!Utility.isVisited(r)){
                            Utility.visited(r);
                            Vector<UserLocation> Neighbours2 = Utility.getNeighbours(r, tdistance);
                            if (Neighbours2.size() >= minpt){
                                Neighbours=Utility.merge(Neighbours, Neighbours2);
                            }
                        } ind++;
                    }



                    //System.out.println("N"+Neighbours.size());
                    resultList.add(Neighbours);}


            }index++;
        }
        return resultList;
    }

}

