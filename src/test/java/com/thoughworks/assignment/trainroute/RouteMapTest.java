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
 * This class is Unit Test class for
 * @see com.thoughtworks.assignment.trainroute.RouteMap
 *
 * @author anshumandutta
 * @version 1.0
 * @since 10/13/15
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

    /**
     * 1. The distance of the route A-B-C
     */
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

    /**
     * 2. The distance of the route A-D
     */
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

    /**
     * 3. The distance of the route A-D-C
     */
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

    /**
     * 4. The distance of the route A-E-B-C-D
     */
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

    /**
     * 5. The distance of the route A-E-D
     */
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

    /**
     * The number of trips starting at C and ending at C with any number of stop.
     */
    @Test
    public void getAllStopsTestCount_C_C() {
        List<List<Station>> data = routeMap.getAllStops(C, C);
        Assert.assertEquals(2, data.size());
    }

    /**
     * 6. The number of trips starting at C and ending at C with a maximum of 3 stops.
     */
    @Test
    public void getStopsTestCount_C_C_MaxStop3() {
        List<List<Station>> data = routeMap.getStops(C, C, 3);
        Assert.assertEquals(2, data.size());
    }

    /**
     * 6. The number of trips starting at C and ending at C with a maximum of 3 stops.
     * As per sample data there are two such trips: C-D-C (2 stops). and C-E-B-C (3 stops).
     * Note that while asserting source station is ignored.
     */
    /**
     * Below test is same for -
     * 10.The number of different routes from C to C with a distance of less than 30.
     * In the sample data, the trips are: CDC, CEBC, CEBCDC, CDCEBC, CDEBC, CEBCEBC, CEBCEBCEBC.
     *
     * CDC and CEBC are valid values, I am not sure how would the rest of it qualify for a valid path,
     * considering C is already reached after B.
     */
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

    /**
     * 7. The number of trips starting at A and ending at C with exactly 4 stops.
     * In the sample data below, there are three such trips: A to C (via B,C,D); A to C (via D,C,D); and A to C (via D,E,B).
     *
     * I didn't understand this test case, A to C should be A-B-C, destination C is already reached.
     * Why would the path continue to D and then to C again.
     */
    @Test
    public void getStopTest_A_C() {
        List<List<Station>> data = routeMap.getAllStops(A, C);
        Assert.assertEquals(3, data.size());
    }

    /**
     * 8. The length of the shortest route (in terms of distance to travel) from A to C
     */
    @Test
    public void getShortestPath_A_C() {
        routeMap.setStartStation(A);
        routeMap.calculatePath();
        String path = routeMap.getShortestPath(A, C);
        Assert.assertEquals(9, Integer.parseInt(path.substring(path.indexOf("#")+1)));
    }

    /**
     * 9. The length of the shortest route (in terms of distance to travel) from B to B.
     *
     * Shortest path from B to B should be 0, not sure why the expectation is 9.
     */
    @Test
    public void getShortestPath_B_B() {
        routeMap.setStartStation(B);
        routeMap.calculatePath();
        String path = routeMap.getShortestPath(B, B);
        Assert.assertEquals(0, Integer.parseInt(path.substring(path.indexOf("#")+1)));
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
