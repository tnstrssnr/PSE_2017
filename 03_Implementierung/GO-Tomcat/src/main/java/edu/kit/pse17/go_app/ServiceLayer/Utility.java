package edu.kit.pse17.go_app.ServiceLayer;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

/**
 * Klasse die Funktionalitäten für den DBSCan beinhaltet.
 */

public class Utility{

    /**
     * Liste die die Locations speichert die besucht wurden.
     */

    public static Vector<UserLocation> VisitList = new Vector<UserLocation>();

    /**
     * Methode, die die Distanz zwischen zwei Locations rausfindet.
     * @param p Erste UserLocation.
     * @param q Zweite UserLocation.
     * @return Die Distanz zwischen ihnen.
     */

    public static double getDistance (UserLocation p, UserLocation q)
    {

        double dx = p.getLat()-q.getLat();

        double dy = p.getLon()-q.getLon();

        double distance = Math.sqrt (dx * dx + dy * dy);

        return distance;

    }


    /**
     * Die Methode findet die Benachbarten "Punkte" einer bestimmten Location anhand des zuvor festgelegten
     * Schwellwerts, beziehungweise maximal Distanz zwischen den Locations.
     * @param p UserLocation, für die die Nachbarn gesucht werden.
     * @param distance Der Schwellwert, beziehungsweise die Distanz die maximal zwischen den Locations liegen darf.
     * @return Eine Vektorliste der UserLocations, die benachbart sind.
     */


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

    /**
     * Die Methode fügt der Liste der besuchten UserLocations eine Location hinzu.
     * @param d Die UserLocation die hinzugefügt werden soll.
     */

    public static void visited(UserLocation d){
        VisitList.add(d);

    }

    /**
     * Die Methode guckt ob eine spezifische UserLocation schon besucht wurde oder nicht
     * (auf der VisitList).
     * @param c UserLocation nach der geschaut wird.
     * @return Gibt True wird falls schon besucht, False falls nicht.
     */

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

    /**
     * Diese Methode führt Vektorlisten zusammen. Dies wird dazu genutzt die Punkte der Cluster zusammenzuführen.
     * @param a Erster Vektor, der zusammengeführt werden soll.
     * @param b Zweiter Vektor, der zusammengeführt werden soll.
     * @return Gibt eine Vektorliste wieder die alle Elemente enthält die die Listen a und b enthielten.
     */

    public static Vector<UserLocation> merge(Vector<UserLocation> a,Vector<UserLocation> b)
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

    /**
     * Die Methode wird dazu genutzt aus einer Liste einen Vektor zu erstellen.
     * @param userLocationList Die Liste die umgewandelt werden soll.
     * @return Die Vektorliste; enthält alle Elemente die userLocationList enthielt.
     */


    public static Vector<UserLocation> getList(List<UserLocation> userLocationList) {

        Vector<UserLocation> newList =new Vector<UserLocation>();
        newList.clear();
        newList.addAll(userLocationList);
        return newList;
    }

    /**
     * Eine Methode, die vergleicht ob zwei Punkte/UserLocations "identisch" sind, beziehungsweise die gleichen Koordinaten haben.
     * @param m Erste UserLocation
     * @param n Zweite UserLocation
     * @return Gibt True wieder falls identisch, False falls nicht.
     */

    public static Boolean equalPoints(UserLocation m , UserLocation n) {
        if((m.getLat()==n.getLat())&&(m.getLon()==n.getLon()))
            return true;
        else
            return false;
    }

}
