package com.thoughtworks.assignment;

import com.thoughtworks.assignment.trainroute.Station;
import com.thoughtworks.assignment.trainroute.RouteMap;
import com.thoughtworks.assignment.trainroute.TrainRouteException;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by anshumandutta on 10/19/15.
 */
public class TrainRouteApp {

    public static void main(String[] args) {

        Station A = new Station("A",0);
        Station B = new Station("B",1);
        Station C = new Station("C",2);
        Station D = new Station("D",3);
        Station E = new Station("E",4);
        //stationList.add(A);stationList.add(B);stationList.add(C);stationList.add(D);stationList.add(E);
        List<Station> stationList = new ArrayList<Station>();
        stationList.add(A);stationList.add(B);stationList.add(C);stationList.add(D);stationList.add(E);
        RouteMap routeMap = new RouteMap(stationList);

        try {

            //Prepare data set
            routeMap.addDistance(A, B, 5);
            routeMap.addDistance(A, D, 5);
            routeMap.addDistance(A, E, 7);
            routeMap.addDistance(E, A, 7);
            routeMap.addDistance(B, C, 4);
            routeMap.addDistance(C, D, 8);
            routeMap.addDistance(D, C, 8);
            routeMap.addDistance(C, E, 2);
            routeMap.addDistance(D, E, 6);
            routeMap.addDistance(E, B, 3);

            List<Station> stations = new ArrayList<Station>();
            stations.add(A);
            stations.add(B);
            stations.add(C);
            logStmt("----------------- Assignment test start -------------------");
            logStmt("1. The distance of the route A-B-C.");
            logStmt("Result - " + routeMap.tripDistance(stations));
            stations = null;

            stations = new ArrayList<Station>();
            stations.add(A);
            stations.add(D);
            logStmt("2. The distance of the route A-D");
            logStmt("Result - " + routeMap.tripDistance(stations));
            stations = null;

            stations = new ArrayList<Station>();
            stations.add(A);
            stations.add(D);
            stations.add(C);
            logStmt("3. The distance of the route A-D-C");
            logStmt("Result - " + routeMap.tripDistance(stations));
            stations = null;

            stations = new ArrayList<Station>();
            stations.add(A);
            stations.add(E);
            stations.add(B);
            stations.add(C);
            stations.add(D);
            logStmt("4. The distance of the route A-E-B-C-D");
            logStmt("Result - " + routeMap.tripDistance(stations));
            stations = null;

            stations = new ArrayList<Station>();
            stations.add(A);
            stations.add(E);

            stations.add(D);
            logStmt("5. The distance of the route A-E-D");
            try {
                logStmt("Result - " + routeMap.tripDistance(stations));
            } catch (TrainRouteException e) {
                logStmt("Result - " + e.getErrorMessage() + "_" + e.getErrorNumber());
            }
            stations = null;

            logStmt("6. The number of trips starting at C and ending at C with a maximum of 3 stops. There are two such trips: C-D-C (2 stops). and C-E-B-C (3 stops).");
            List<List<Station>> data = routeMap.getAllStops(C, C);
            logStmt("Result - " + "No of Stops " + data.size());

            logStmt("(Not understood) 7. The number of trips starting at A and ending at C with exactly 4 stops.  In the sample data below, there are three such trips: A to C (via B,C,D); A to C (via D,C,D); and A to C (via D,E,B)." + "" +
                    "** I didn't understand this test case, A to C should be A-B-C, destination C is already reached.Why would the path continue to D and then to C again.");

            logStmt("8. The length of the shortest route (in terms of distance to travel) from A to C.");
            routeMap.setStartStation(A);
            routeMap.calculatePath();
            logStmt("Result - " + routeMap.getShortestPath(A, C));

            logStmt("9. The length of the shortest route (in terms of distance to travel) from B to B.");
            routeMap.setStartStation(B);
            routeMap.calculatePath();
            logStmt("Result - " + routeMap.getShortestPath(B, B));

            logStmt("(Not understood) 10.The number of different routes from C to C with a distance of less than 30.  In the sample data, the trips are: CDC, CEBC, CEBCDC, CDCEBC, CDEBC, CEBCEBC, CEBCEBCEBC." + "" +
                    "** CDC and CEBC are valid values, I am not sure how would the rest of it qualify for a valid path, considering C is already reached after B");
            logStmt("----------------- Assignment Tests done -------------------");


        } catch (Exception te) {
            //Doing nothing
            te.printStackTrace();
        }

    }

    private static void logStmt(Object message) {
        System.out.println(message);
    }

}
