package com.thoughtworks.assignment.trainroute;

/**
 * Created by adutta on 10/20/15.
 */
public class TrainRouteException extends Exception {
    private String errorMessage;
    private int errorNumber;

    public TrainRouteException(String errorMessage, int errorNumber) {
        this.errorMessage = errorMessage;
        this.errorNumber = errorNumber;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public int getErrorNumber() {
        return errorNumber;
    }

    public void setErrorNumber(int errorNumber) {
        this.errorNumber = errorNumber;
    }
}
