package com.thoughworks.assignment.trainroute;

import com.thoughtworks.assignment.trainroute.RouteMap;
import com.thoughtworks.assignment.trainroute.Station;
import com.thoughtworks.assignment.trainroute.TrainRouteException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by adutta on 10/20/15.
 */

public class RouteMapTest {

    private RouteMap routeMap;
    private List<Station> stationList;
    private Station A;
    private Station B;
    private Station C;
    private Station D;
    private Station E;

    @Before
    public void initTest() {
        stationList = new ArrayList<Station>();

        A = new Station("A");
        B = new Station("B");
        C = new Station("C");
        D = new Station("D");
        E = new Station("E");
        stationList.add(A);stationList.add(B);stationList.add(C);stationList.add(D);stationList.add(E);
        routeMap = new RouteMap(stationList);
        dataset1();
    }

    @Test
    public void testCase_1() {
        List<Station> stations = new ArrayList<Station>();
        stations.add(A);
        stations.add(B);
        stations.add(C);
        try {
            int tripVal = routeMap.tripDistance(stations);
            Assert.assertEquals(9, tripVal);
        } catch (TrainRouteException e) {
            Assert.fail();
        }
    }

    @Test
    public void testCase_2() {
        List<Station> stations = new ArrayList<Station>();
        stations.add(A);
        stations.add(D);
        try {
            int tripVal = routeMap.tripDistance(stations);
            Assert.assertEquals(5, tripVal);
        } catch (TrainRouteException e) {
            Assert.fail();
        }
    }

    @Test
    public void testCase_3() {
        List<Station> stations = new ArrayList<Station>();
        stations.add(A);
        stations.add(D);
        stations.add(C);
        try {
            int tripVal = routeMap.tripDistance(stations);
            Assert.assertEquals(13, tripVal);
        } catch (TrainRouteException e) {
            Assert.fail();
        }
    }

    @Test
    public void testCase_4() {
        List<Station> stations = new ArrayList<Station>();
        stations.add(A);
        stations.add(E);
        stations.add(B);
        stations.add(C);
        stations.add(D);
        try {
            int tripVal = routeMap.tripDistance(stations);
            Assert.assertEquals(22, tripVal);
        } catch (TrainRouteException e) {
            Assert.fail();
        }
    }

    @Test
    public void testCase_5() {
        List<Station> stations = new ArrayList<Station>();
        stations.add(A);
        stations.add(E);
        stations.add(D);
        try {
            routeMap.tripDistance(stations);
            Assert.fail();
        } catch (TrainRouteException e) {
            Assert.assertEquals(21000, e.getErrorNumber());
        }
    }

    @Test
    public void getStopsTestCount_C_C() {
        List<List<Station>> data = routeMap.getStops(C, C);
        Assert.assertEquals(2, data.size());
    }

    @Test
    public void getStopsTestCount_C_C_MaxStop3() {
        List<List<Station>> data = routeMap.getStops(C, C, 3);
        Assert.assertEquals(2, data.size());
    }

    @Test
    public void getStopTest_C_C() {
        List<List<Station>> data = routeMap.getStops(C, C, 3);
        HashMap<String, String> result = new HashMap<String, String>();
        for (List<Station> stations:data) {
            StringBuilder sb = new StringBuilder();
            for (Station station:stations) {
                sb.append(station.getLabel()).append("-");
            }
            result.put(sb.toString(),sb.toString());
        }

        Assert.assertTrue(result.containsKey("D-C-"));
        Assert.assertTrue(result.containsKey("E-B-C-"));
    }

    @Test
    public void getStopTest_A_C() {
        List<List<Station>> data = routeMap.getAllStops(A, C);
        Assert.assertEquals(3, data.size());
    }

    @Test
    public void getShortestPath_A_C() {
        routeMap.setStartStation(A);
        routeMap.calculatePath();
        String path = routeMap.getShortestPath(A, C);
        //Assert.assertEquals("", path);
    }

    @Test
    public void getShortestPath_B_B() {
        routeMap.setStartStation(B);
        routeMap.calculatePath();
        String path = routeMap.getShortestPath(B, B);
        //Assert.assertEquals("", path);
    }

    private void dataset1() {
        try {
            routeMap.addDistance(A, B, 5);
            routeMap.addDistance(A, D, 5);
            routeMap.addDistance(A, E, 7);
            //routeMap.addDistance(E, A, 7);
            routeMap.addDistance(B, C, 4);
            routeMap.addDistance(C, D, 8);
            routeMap.addDistance(D, C, 8);
            routeMap.addDistance(C, E, 2);
            routeMap.addDistance(D, E, 6);
            routeMap.addDistance(E, B, 3);
        } catch (TrainRouteException te) {
            Assert.fail(te.getErrorMessage());
        }
    }

}
