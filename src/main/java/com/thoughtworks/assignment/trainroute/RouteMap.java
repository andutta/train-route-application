package com.thoughtworks.assignment.trainroute;

import java.lang.Boolean;import java.lang.Integer;import java.lang.String;import java.lang.System;
import java.util.*;

/**
 * Created by anshumandutta on 10/13/15.
 */
public class RouteMap {

    private static final int INFINITY = 10000;
    private List<Station> stationList;
    private Map<String, Visit> visits;
    private Queue<Station> q;
    private int adjMatrix[][];
    private int vertexCount;
    private Station startStation;

    public Station getStartStation() {
        return startStation;
    }

    public void setStartStation(Station startStation) {
        this.startStation = startStation;
    }

    private void initRouteMap() {
        visits = new HashMap<String, Visit>();
        q = new LinkedList<Station>();

        adjMatrix = new int[vertexCount][vertexCount];

        for (int i = 0; i < vertexCount; i++) {
            for (int j = 0; j < vertexCount; j++) {
                adjMatrix[i][j] = 0;
            }
        }

        //Reset all visited station
        for (Station s:stationList) {
            s.setWasVisited(Boolean.FALSE);
        }
    }

    public RouteMap(List<Station> inList) {
        stationList = new ArrayList<Station>();
        vertexCount = 0;
        int i=0;
        for (Station s:inList) {
            s.setIndexNo(i);
            this.stationList.add(s);
            i++;
            vertexCount++;
        }
        initRouteMap();
    }

    public void addStation(Station v) {
        v.setIndexNo(vertexCount++);
        stationList.add(v);
        initRouteMap();
    }

    public void addEdge(int node1, int node2, int edgeVal) {
        adjMatrix[node1][node2] = edgeVal;
        initRouteMap();
    }

    //Ideally this method should throw an Exception if the index is not found
    public boolean addDistance(Station source, Station dest, int distance) {
        int sourceIndex=-1;
        int destIndex=-1;
        for (Station s:stationList) {
            if (s.equals(source)) {
                sourceIndex = s.getIndexNo();
            }

            if (s.equals(dest)) {
                destIndex = s.getIndexNo();
            }

            if (sourceIndex != -1 && destIndex != -1) {
                break;
            }
        }

        if (sourceIndex == -1 || destIndex == -1) {
            return false;
        }

        adjMatrix[sourceIndex][destIndex] = distance;
        return true;
    }


    public void calculatePath() {

        //No calculation to be done is start station not set, ideally should throw exception
        if (startStation == null) {
            return;
        }

        // Source vertex edge weight is set to 0
        visits.put(startStation.getLabel(), new Visit(0, startStation));

        //Initialize all the vertices edge weight is set to infinity from Source
        for (Station v: stationList) {
            if (!startStation.getLabel().equals(v.getLabel())) { // Source vertex
                visits.put(v.getLabel(), new Visit(INFINITY, startStation));
            }
        }

        //Relax nodes
        Station currStation = startStation;
        while (true) {
            int minIndex = getMinNode(currStation); // this also sets the queue for the child nodes
            if (minIndex == -1) {
                break;
            }

            int initEdgeWeight = adjMatrix[currStation.getIndexNo()][currStation.getIndexNo()];

            if ((visits.get(currStation.getLabel()) != null)) {
                Visit lastVisit = visits.get(currStation.getLabel());
                if (lastVisit.getEdgeWeight() != INFINITY) {
                    initEdgeWeight = lastVisit.getEdgeWeight();
                }

            }

            while (!q.isEmpty()) {
                Station relaxNode = q.remove();
                if (visits.get(relaxNode.getLabel()) != null) {
                    Visit visitedRelaxNode = visits.get(relaxNode.getLabel());
                    int edgeWeight = initEdgeWeight +  adjMatrix[currStation.getIndexNo()][relaxNode.getIndexNo()];
                    if (visitedRelaxNode.getEdgeWeight() > edgeWeight) {
                        visits.put(relaxNode.getLabel(), new Visit(edgeWeight, currStation));
                    }
                }
            }

            currStation.setWasVisited(Boolean.TRUE);
            currStation = stationList.get(minIndex);
        }
    }

    public String getShortestPath(Station source, Station dest) {
        Stack<Station> stationStack = new Stack<Station>();
        Station tmpDest = dest;
        Visit tmpVisit = visits.get(tmpDest.getLabel());
        stationStack.push(tmpDest);
        int calculatedWeight = tmpVisit.getEdgeWeight();

        do {

            tmpDest = tmpVisit.getOriginStation();
            tmpVisit = visits.get(tmpDest.getLabel());
            if (!tmpDest.equals(source) && tmpVisit.getOriginStation().equals(tmpDest)) {
                return "Path not found";
            }
            stationStack.push(tmpDest);
        } while(!tmpVisit.getOriginStation().equals(source));

        StringBuilder sb = new StringBuilder();
        sb.append(source.getLabel()).append("->");

        while (!stationStack.isEmpty()) {
            sb.append(stationStack.pop().getLabel()).append("->");
        }
        return sb.toString() + calculatedWeight;
    }

    private int getMinNode(Station source) {
        int retIndex = -1;
        if (source.getWasVisited()) {
            return retIndex;
        }
        int minValue = INFINITY;
        for (int i=0;i<adjMatrix[source.getIndexNo()].length;i++) {
            if (adjMatrix[source.getIndexNo()][i] > 0) {
                if (minValue > adjMatrix[source.getIndexNo()][i]) {
                    minValue = adjMatrix[source.getIndexNo()][i];
                    retIndex = i;
                }
                q.add(stationList.get(i));
            }
        }

        return retIndex;
    }

    private Station getUnvisitedChild(Station v) {
        int data[] = adjMatrix[v.getIndexNo()];
        if (data != null ) {
            for (int i=0;i<data.length;i++) {
                if (data[i] > 0) {
                    Station child = stationList.get(i);
                    if (!child.getWasVisited()) {
                        return child;
                    }
                }

            }
        }

        return null;
    }

    public int tripDistance(List<Station> stations) throws TrainRouteException{
        if (stations == null) {
            throw new TrainRouteException("No station provided to calculate distance", 21001);
        }

        if (stations.size() == 1) {
            throw new TrainRouteException("Atleast two stations must be provided", 21002);
        }

        Station source = stations.get(0);
        int edgeWeight = 0;
        Station prevStation = source;
        for (Station s:stations) {
            if (!s.equals(source)) {
                if (adjMatrix[prevStation.getIndexNo()][s.getIndexNo()] != 0) {
                    edgeWeight = edgeWeight + adjMatrix[prevStation.getIndexNo()][s.getIndexNo()];
                    prevStation = s;
                } else {
                    throw new TrainRouteException("Path not found", 21000);
                }
            }
        }
        return edgeWeight;
    }

    public List<List<Station>> getStops(Station source, Station destination) {
        List<List<Station>> retList =  new ArrayList<List<Station>>();
        Queue<Station> stationQ = new LinkedList<Station>();

        for (int i=0;i<adjMatrix[source.getIndexNo()].length;i++) {
            if (adjMatrix[source.getIndexNo()][i] > 0) {
                stationQ.add(stationList.get(i));
            }
        }

        while (!stationQ.isEmpty()) {
            Station curr = stationQ.remove();
            List<Station> stops = new ArrayList<Station>();
            isDestinationFound(stops, curr, destination);
            retList.add(stops);
        }
        return retList;
    }

    public List<List<Station>> getStops(Station source, Station destination, int maxStops) {
        List<List<Station>> retList =  new ArrayList<List<Station>>();
        Queue<Station> stationQ = new LinkedList<Station>();

        for (int i=0;i<adjMatrix[source.getIndexNo()].length;i++) {
            if (adjMatrix[source.getIndexNo()][i] > 0) {
                stationQ.add(stationList.get(i));
            }
        }

        while (!stationQ.isEmpty()) {
            Station curr = stationQ.remove();
            List<Station> stops = new ArrayList<Station>();
            isDestinationFound(stops, curr, destination);
            if (stops.size() <= maxStops) {
                retList.add(stops);
            }
        }
        return retList;
    }

    private void isDestinationFound(List<Station> stationStops, Station currStation, Station dest) {
        stationStops.add(currStation);
        if(currStation.equals(dest)) {
            return;
        }

        for (int i=0;i<adjMatrix[currStation.getIndexNo()].length;i++) {
            if (adjMatrix[currStation.getIndexNo()][i] > 0) {
                isDestinationFound(stationStops, stationList.get(i), dest);
                if (stationList.get(i).equals(dest)) {
                    return;
                }
            }
        }
    }

    public void BFS(Station root) {
        Queue q = new LinkedList();
        root.setWasVisited(Boolean.TRUE);
        q.add(root);
        printNode(root);

        while(!q.isEmpty()) {
            Station vN = (Station) q.remove();
            Station child = null;
            while ((child = getUnvisitedChild(vN)) != null) {
                child.setWasVisited(Boolean.TRUE);
                printNode(vN, child);
                q.add(child);
            }
        }
    }

    private void printNode(Station v) {
        System.out.print(v.getLabel() + " -> ");
    }

    private void printNode(Station s, Station d) {
        System.out.println(s.getLabel() + " -> " + d.getLabel());
    }

}
