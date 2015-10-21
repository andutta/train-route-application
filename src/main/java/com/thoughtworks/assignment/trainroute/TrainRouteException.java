package com.thoughtworks.assignment.trainroute;

/**
 * This class is a used for throwing any exception in the
 * @see com.thoughtworks.assignment.trainroute.RouteMap methods.
 * A new error message and error number can be passed in to the
 * consutructor to represent an exception uniquely
 *
 * @author anshumandutta
 * @version 1.0
 * @since 10/13/15
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
