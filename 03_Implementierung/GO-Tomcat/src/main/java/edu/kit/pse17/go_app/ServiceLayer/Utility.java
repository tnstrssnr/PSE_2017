package edu.kit.pse17.go_app.ServiceLayer;


import java.util.Iterator;
import java.util.List;
import java.util.Vector;

public class Utility{

    public static Vector<UserLocation> VisitList = new Vector<UserLocation>();

    public static double getDistance (UserLocation p, UserLocation q)
    {

        double dx = p.getLat()-q.getLon();

        double dy = p.getLat()-q.getLon();

        double distance = Math.sqrt (dx * dx + dy * dy);

        return distance;

    }



    /**
     neighbourhood points of any point p
     **/


    public static Vector<UserLocation> getNeighbours(UserLocation p, int distance)
    {
        Vector<UserLocation> neigh =new Vector<UserLocation>();
        Iterator<UserLocation> positions = DBScan.pointList.iterator();
        while(positions.hasNext()){
            UserLocation q = positions.next();
            if(getDistance(p,q)<= distance){
                neigh.add(q);
            }
        }
        return neigh;
    }

    public static void Visited(UserLocation d){
        VisitList.add(d);

    }

    public static boolean isVisited(UserLocation c)
    {
        if (VisitList.contains(c))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public static Vector<UserLocation> Merge(Vector<UserLocation> a,Vector<UserLocation> b)
    {

        Iterator<UserLocation> it5 = b.iterator();
        while(it5.hasNext()){
            UserLocation t = it5.next();
            if (!a.contains(t) ){
                a.add(t);
            }
        }
        return a;
    }



//  Returns PointsList to DBscan.java

    public static Vector<UserLocation> getList(List<UserLocation> userLocationList) {

        Vector<UserLocation> newList =new Vector<UserLocation>();
        newList.clear();
        newList.addAll(userLocationList);
        return newList;
    }

    public static Boolean equalPoints(UserLocation m , UserLocation n) {
        if((m.getLat()==n.getLat())&&(m.getLat()==n.getLon()))
            return true;
        else
            return false;
    }

}
