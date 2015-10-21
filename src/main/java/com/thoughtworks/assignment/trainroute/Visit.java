package com.thoughtworks.assignment.trainroute;

/**
 * This class represent a station that has been visited
 * while calculating the shortest path algorithm. An instance
 * of this class is created and added/updated to a HashMap
 * when edge weight is calculated.
 *
 * @author anshumandutta
 * @version 1.0
 * @since 10/13/15
 */
public class Visit {
    private Integer edgeWeight;
    private Station originStation;

    public Visit(Integer edgeWeight, Station originStation) {
        this.edgeWeight = edgeWeight;
        this.originStation = originStation;
    }

    public Integer getEdgeWeight() {
        return edgeWeight;
    }

    public void setEdgeWeight(Integer edgeWeight) {
        this.edgeWeight = edgeWeight;
    }

    public Station getOriginStation() {
        return originStation;
    }

    public void setOriginStation(Station originStation) {
        this.originStation = originStation;
    }
}
