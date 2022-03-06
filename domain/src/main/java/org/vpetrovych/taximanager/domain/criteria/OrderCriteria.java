package org.vpetrovych.taximanager.domain.criteria;

import org.vpetrovych.taximanager.domain.entities.User;

import java.util.List;

public class OrderCriteria {

    private User driver;

    private User manager;

    private String order;

    private List<String> statuses;

    public User getDriver() {
        return driver;
    }

    public void setDriver(User driver) {
        this.driver = driver;
    }

    public User getManager() {
        return manager;
    }

    public void setManager(User manager) {
        this.manager = manager;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public List<String> getStatuses() {
        return statuses;
    }

    public void setStatuses(List<String> statuses) {
        this.statuses = statuses;
    }
}
