package com.thoughtworks.assignment.trainroute;

/**
 * Created by anshumandutta on 10/19/15.
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
