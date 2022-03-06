package org.vpetrovych.taximanager.dao.exception;

import java.util.List;

public class ProcessException extends RuntimeException {

    private List<ErrorObject> errorObjects;

    public List<ErrorObject> getErrorObjects() {
        return errorObjects;
    }

    public ProcessException(List<ErrorObject> errorObjects) {
        this.errorObjects = errorObjects;
    }

    public ProcessException(List<ErrorObject> errorObjects, String message) {
        super(message);
        this.errorObjects = errorObjects;
    }

    public ProcessException(List<ErrorObject> errorObjects, String message, Throwable cause) {
        super(message, cause);
        this.errorObjects = errorObjects;
    }
}
