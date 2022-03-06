package org.vpetrovych.taximanager.dao.exception;

import java.util.Objects;

public class ErrorObject {

    private String objectName;
    private String objectMessage;

    public ErrorObject(String objectName, String objectMessage) {
        this.objectName = objectName;
        this.objectMessage = objectMessage;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public String getObjectMessage() {
        return objectMessage;
    }

    public void setObjectMessage(String objectMessage) {
        this.objectMessage = objectMessage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ErrorObject)) {
            return false;
        }
        ErrorObject that = (ErrorObject) o;
        return Objects.equals(this.getObjectName(), that.getObjectName()) &&
                Objects.equals(this.getObjectMessage(), that.getObjectMessage());
    }

    @Override
    public int hashCode() {
        int result = 1;
        int hash = 31;
        result = result * hash + (this.getObjectName() != null ? this.getObjectName().hashCode() : 0);
        return result * hash + (this.getObjectMessage() != null ? this.getObjectMessage().hashCode() : 0);
    }
}
