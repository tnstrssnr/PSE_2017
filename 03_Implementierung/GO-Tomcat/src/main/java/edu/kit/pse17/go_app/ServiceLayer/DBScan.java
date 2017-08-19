package edu.kit.pse17.go_app.ServiceLayer;

import java.util.*;


public class DBScan
{

    public static Vector<List> resultList = new Vector<>();

    public static Vector<UserLocation> pointList;

    public static Vector<UserLocation> Neighbours ;

    public DBScan(List<UserLocation> userLocationList) {
        this.pointList = Utility.getList(userLocationList);
    }



    public Vector<List> applyDbscan(int tdistance, int minpt, List<UserLocation> userLocationList)
    {
        resultList.clear();
        pointList.clear();
        Utility.VisitList.clear();
        pointList=Utility.getList(userLocationList);

        int index2 =0;


        while (pointList.size()>index2){
            UserLocation p =pointList.get(index2);
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



                    System.out.println("N"+Neighbours.size());
                    resultList.add(Neighbours);}


            }index2++;
        }
        return resultList;
    }

}

