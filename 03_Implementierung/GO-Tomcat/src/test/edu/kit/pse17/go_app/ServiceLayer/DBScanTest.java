package edu.kit.pse17.go_app.ServiceLayer;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import static org.junit.Assert.*;

/**
 * Klasse getestet in Zusammenhang mit Utility-Klasse, da sämtliche Resultate und Funktionen mit Methoden von Utility Klasse gegeben sind.
 * Essentiell wäre ein Mocking hier nur eine Wiedergabe von dem vom Mocking gewünschten Wiedergabewert, was wiederrum sinnlos wäre.
 * Diese Testklasse testet nun das Zusammenspiel der einzelnen Methoden und den richtigen Ablauf des eignetlichen Clusterings.
 *
 */

public class DBScanTest {

    private DBScan newScan;
    private List<UserLocation> userLocationList;
    private UserLocation loc1;
    private UserLocation loc2;
    private UserLocation loc3;
    private UserLocation loc4;



    @Before
    public void setUp() throws Exception {

        this.loc1 = new UserLocation("loc1", 4, 4);
        this.loc2 = new UserLocation("loc2", 5, 5);
        this.loc3 = new UserLocation("loc3", 6, 6);
        this.loc4 = new UserLocation("loc4", 7, 7);

        this.userLocationList = new ArrayList<>();


    }

    @Test
    public void applyDbscan() throws Exception {

        userLocationList.add(loc1);
        userLocationList.add(loc2);
        userLocationList.add(loc3);
        userLocationList.add(loc4);

        Vector<UserLocation> testUserList = new Vector<UserLocation>();
        testUserList.addAll(userLocationList);

        newScan = new DBScan(userLocationList);

        Vector<List> testResults = newScan.applyDbscan(3, 3, testUserList);

        assertEquals(testResults.size(), 1);
        assertEquals(testResults.get(0).get(0), loc1);
        assertEquals(testResults.get(0).get(1), loc2);
        assertEquals(testResults.get(0).get(2), loc3);
        assertEquals(testResults.get(0).get(3), loc4);
    }


}