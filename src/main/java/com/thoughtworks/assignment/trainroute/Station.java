package com.thoughtworks.assignment.trainroute;

import java.lang.Boolean;import java.lang.String;

/**
 * This class represent a station or node in the Route graph.
 * NOte two stations cannot have the same label.
 *
 * @author anshumandutta
 * @version 1.0
 * @since 10/13/15
 */
public class Station {
    private String label;
    private Boolean wasVisited;
    private int indexNo;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Boolean getWasVisited() {
        return wasVisited;
    }

    public void setWasVisited(Boolean wasVisited) {
        this.wasVisited = wasVisited;
    }

    public Station(String label, int indexNo) {
        this.label = label;
        this.indexNo = indexNo;
        this.wasVisited = Boolean.FALSE;
    }

    public Station(String label) {
        this.label = label;
        this.wasVisited = Boolean.FALSE;
    }

    public int getIndexNo() {
        return indexNo;
    }

    public void setIndexNo(int indexNo) {
        this.indexNo = indexNo;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        Station station = (Station) o;
        if (station.label == null) {
            return false;
        }

        if (!station.label.equals(this.label)) {
            return false;
        }
        return true;
    }
}
