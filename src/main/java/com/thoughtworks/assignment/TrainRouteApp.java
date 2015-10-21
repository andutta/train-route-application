package com.thoughtworks.assignment;

import com.thoughtworks.assignment.trainroute.Station;
import com.thoughtworks.assignment.trainroute.RouteMap;
import com.thoughtworks.assignment.trainroute.TrainRouteException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anshumandutta on 10/19/15.
 */
public class TrainRouteApp {

    public static void main(String[] args) {
        List<Station> stationList = new ArrayList<Station>();

        //Index for each station is entered as unique value in sequence
        Station A =  new Station("A");
        Station B = new Station("B");
        Station C = new Station("C");
        Station D = new Station("D");
        Station E = new Station("E");
        Station F = new Station("F");
        stationList.add(A);
        stationList.add(B);
        stationList.add(C);
        stationList.add(D);
        stationList.add(E);
        stationList.add(F);

        RouteMap routeMap = new RouteMap(stationList);

        /* Set 1 */
//        routeMap.addDistance(A, B, 4);
//        routeMap.addDistance(A, E, 6);
//        routeMap.addDistance(B, C, 5);
//        routeMap.addDistance(C, D, 3);
//        routeMap.addDistance(D, E, 2);
//        routeMap.addDistance(E, D, 3);
//        routeMap.addDistance(E, F, 2);
//        routeMap.addDistance(F, C, 7);


        /* Set 2 */
//        routeMap.addDistance(A, B, 2);
//        routeMap.addDistance(A, D, 5);
//        routeMap.addDistance(B, C, 3);
//        routeMap.addDistance(B, D, 2);
//        routeMap.addDistance(C, D, 4);
//        routeMap.addDistance(D, E, 4);
//        routeMap.addDistance(D, F, 2);

        /* Set 3 */
//        routeMap.addDistance(A, B, 3);
//        routeMap.addDistance(B, C, 4);
//        routeMap.addDistance(C, D, 2);
//        routeMap.addDistance(C, F, 3);
//        routeMap.addDistance(D, E, 5);
//        routeMap.addDistance(E, F, 6);

        routeMap.setStartStation(A);
        routeMap.calculatePath();

//        System.out.println(routeMap.getShortestPath(A, F));
//        System.out.println(routeMap.getShortestPath(A, C));
//        System.out.println(routeMap.getShortestPath(A, D));
//        System.out.println(routeMap.getShortestPath(A, B));
        System.out.println(routeMap.getShortestPath(A, F));
        System.out.println(routeMap.getShortestPath(E, C));

        Station D1 = new Station("D1");
        routeMap.addStation(D1);
//        routeMap.addDistance(A, B, 3);
//        routeMap.addDistance(B, C, 4);
//        routeMap.addDistance(C, D, 2);
//        routeMap.addDistance(D, E, 5);
//        routeMap.addDistance(E, F, 6);
//
//        routeMap.addDistance(D, D1, 1);
//        routeMap.addDistance(D1, F, 3);
        routeMap.calculatePath();

        System.out.println(routeMap.getShortestPath(A, F));

        List<Station> pathFor = new ArrayList<Station>();
        pathFor.add(A);
        pathFor.add(B);
        pathFor.add(D);
        try {
            System.out.println(routeMap.tripDistance(pathFor));
        } catch (TrainRouteException e) {
            System.out.println(e.getErrorMessage());
        }
        System.out.println("Done");
    }
    
}
