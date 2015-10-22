package com.thoughtworks.assignment.trainroute;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.Boolean;
import java.lang.String;
import java.util.*;

/**
 * This class implements a graph data structure for any list of Train stations.
 * Vertices represent a station and edges represent the distance between the station, note that,
 * this class assumes that the graph data will represent a directed graph.
 * This class is not tested in a multi-threaded environment and may not be thread safe.
 *
 * @author anshumandutta
 * @version 1.0
 * @since 10/13/15
 */
public class RouteMap {

    private static final int INFINITY = 10000;
    private List<Station> stationList;
    private Map<String, Visit> visits;
    private Queue<Station> q;
    private int adjMatrix[][];
    private int stationCount;
    private Station startStation;

    /**
     * Getter method of StartStation attribute
     * @return Station instance
     */
    public Station getStartStation() {
        return startStation;
    }

    /**
     * Setter method for setting startStation attribute
     * @param startStation instance of start station object
     */
    public void setStartStation(Station startStation) {
        this.startStation = startStation;
        initVisits();
    }

    /**
     * Getter method to see all the entered edges
     * @return
     */
    public int[][] getAdjMatrix() {
        return adjMatrix;
    }

    /**
     * This is constructor method for this class.
     *
     * @param inList List of stations/vertices
     */
    public RouteMap(List<Station> inList) {
        stationList = new ArrayList<Station>();
        stationCount = 0;
        int i=0;
        for (Station s:inList) {
            s.setIndexNo(i);
            this.stationList.add(s);
            i++;
            stationCount++;
        }
        initRouteMap();
    }

    public RouteMap(FileReader datFile) throws IOException, TrainRouteException{
        initRouteMap();
        BufferedReader bufferedReader = new BufferedReader(datFile);
        String line;
        int lineNo = 0;
        int noOfStations = 0;
        while ((line = bufferedReader.readLine()) != null) {
            String[] fileData = line.split(",");

            if (fileData.length > 0) {

                if (lineNo == 0) { // first line is station name
                    noOfStations = fileData.length;
                    stationList = new ArrayList<Station>();
                    for (int i = 0; i < fileData.length; i++) {
                        Station s = new Station(fileData[i]);
                        s.setIndexNo(i);
                        stationList.add(s);
                        stationCount++;
                    }
                    initRouteMap();
                } else {
                    if (fileData.length != noOfStations) {
                        throw new TrainRouteException("No of station mismatch to distance", 21006);
                    }
                    for(int i=0;i<fileData.length;i++) {
                        adjMatrix[lineNo-1][i] = Integer.parseInt(fileData[i]);
                    }
                }
            }
            lineNo++;
        }
    }

    /**
     * This method is for adding new station to the graph.
     *
     * @param v New station object
     */
    public void addStation(Station v) {
        v.setIndexNo(stationCount++);
        stationList.add(v);
        initRouteMap();
    }

    /**
     * This method is for adding an edge between two stations.
     *
     * @param node1 index no for source station
     * @param node2 index no for destination station
     * @param edgeVal distance between two station
     */
    public void addEdge(int node1, int node2, int edgeVal) {
        adjMatrix[node1][node2] = edgeVal;
        initRouteMap();
    }


    /**
     * This method is for adding an edge between two stations by using station object.
     *
     * @param source Source Station object
     * @param dest Destination Station object
     * @param distance distance between two station
     * @throws TrainRouteException
     */
    public void addDistance(Station source, Station dest, int distance) throws TrainRouteException {
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
            throw new TrainRouteException("Source station or destination station not found", 21005);
        }

        adjMatrix[sourceIndex][destIndex] = distance;
    }


    /**
     * This method calculates shortest path between two station and prepares a
     * visit data structure holding the route to the shorted path.
     * Method uses Dijkstra's shortest path algorithm to calculate the path.
     * The process Starts with a source node and relaxes the joining nodes by
     * calculating the edge weight (which represent distance here) and updates
     * a Map that holds all the vertices and their last calculated minimum
     * weight.
     * Note that this method assumes that start station is already set by the
     * user of this class.
     */
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

            //Get the nearest station attached to current station, also build a queue hold every station connection to current station
            int minIndex = getMinNode(currStation); // this also sets the queue for the child nodes
            if (minIndex == -1) {
                break;
            }

            int initEdgeWeight = adjMatrix[currStation.getIndexNo()][currStation.getIndexNo()];

            if ((visits.get(currStation.getLabel()) != null)) {
                Visit lastVisit = visits.get(currStation.getLabel());
                if (lastVisit.getEdgeWeight() != INFINITY) {
                    initEdgeWeight = lastVisit.getEdgeWeight(); // Get previous weight if was already visited
                }

            }

            //Iterate over all the attached nodes to current station,
            //e.g for a A->B=7, A->E=10, during first iteration queue q will have B and E in it
            //with minIndex value of B as it is smallest between B and E
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

    /**
     * This method returns the shortest path between two stations
     * @param sourceIndex Source station index no
     * @param destIndex Destination station index no
     * @return A String of station labels followng the shortest path. e.g. A->B->C->#9
     */
    public String getShortestPath(int sourceIndex, int destIndex) {
        return this.getShortestPath(stationList.get(sourceIndex), stationList.get(destIndex));
    }
    /**
     * This method returns the shortest path between two stations.
     * Method uses a Hashmap that is already populated with calculated
     * shortest path for each vertices.
     * Each station from destination is inspected and its previous
     * station is identified and added to a stack until the source station
     * is found. Once done, method simply pops each element from the stack
     * to build the path.
     *
     * Note that method calculatePath() must be called before calling
     * this method.
     *
     * @param source Source Station
     * @param dest Destination Station
     * @return A String of station labels followng the shortest path. e.g. A->B->C->#9
     */
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
        return sb.toString() + "#" + calculatedWeight;
    }

    /**
     * This method calculates the total distance for a list of stations.
     *
     * @param stations List of station
     * @return Total Distance combined for all the stations
     * @throws TrainRouteException 21001, when no station is passed in,
     * 21002 when less than two station provided, 21000, when path is not found
     *
     */
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

    /**
     * This methods returns all the possible station combinations
     * between source and destination station.
     *
     * @param source Source Station
     * @param destination destination Station
     * @return List of station combinations as a list
     */
    public List<List<Station>> getAllStops(Station source, Station destination) {
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

    /**
     * This method returns all station combinations with a defined
     * maximum number of stop
     *
     * @param source Source station
     * @param destination destination station
     * @param maxStops Criteria for maximum number of stop
     * @return List of station combinations as a list
     */
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

    private void initRouteMap() {
        visits = new HashMap<String, Visit>();
        q = new LinkedList<Station>();

        adjMatrix = new int[stationCount][stationCount];

        for (int i = 0; i < stationCount; i++) {
            for (int j = 0; j < stationCount; j++) {
                adjMatrix[i][j] = 0;
            }
        }

        //Reset all visited station
        if (stationList != null) {
            for (Station s : stationList) {
                s.setWasVisited(Boolean.FALSE);
            }
        }
    }

    private void initVisits() {
        visits = new HashMap<String, Visit>();
        q = new LinkedList<Station>();

        //Reset all visited station
        for (Station s:stationList) {
            s.setWasVisited(Boolean.FALSE);
        }
    }

}
