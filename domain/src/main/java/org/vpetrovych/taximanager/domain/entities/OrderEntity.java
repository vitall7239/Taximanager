package org.vpetrovych.taximanager.domain.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "OrderEntity")
public class OrderEntity extends AbstractEntity {

    @Column(nullable = false)
    private String addressFrom;

    @Column(nullable = false)
    private String addressTo;

    @Column(nullable = false)
    private Date orderTime;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "driver", foreignKey = @ForeignKey(name = "FK_Order_Driver"), nullable = true)
    private User driver;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "manager", foreignKey = @ForeignKey(name = "FK_Order_Manager"))
    private User manager;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "customer", foreignKey = @ForeignKey(name = "FK_Order_Customer"))
    private Customer customer;

    @Column(nullable = false, unique = false, precision = 19, scale = 2)
    private BigDecimal price;

    @Column(nullable = true, unique = false)
    private String description;

    @Column(nullable = false, unique = false)
    private String status;

    public String getAddressFrom() {
        return addressFrom;
    }

    public void setAddressFrom(String addressFrom) {
        this.addressFrom = addressFrom;
    }

    public String getAddressTo() {
        return addressTo;
    }

    public void setAddressTo(String addressTo) {
        this.addressTo = addressTo;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

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

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        OrderEntity that = (OrderEntity) o;

        return Objects.equals(addressFrom, that.addressFrom) &&
                Objects.equals(addressTo, that.addressTo) &&
                Objects.equals(orderTime, that.orderTime) &&
                Objects.equals(driver, that.driver) &&
                Objects.equals(manager, that.manager) &&
                Objects.equals(customer, that.customer) &&
                Objects.equals(price, that.price) &&
                Objects.equals(description, that.description) &&
                Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        int result = 1;
        result =  31 * result + (addressFrom == null ? 0 : addressFrom.hashCode());
        result =  31 * result + (addressTo == null ? 0 : addressTo.hashCode());
        result =  31 * result + (orderTime == null ? 0 : orderTime.hashCode());
        result =  31 * result + (driver == null ? 0 : driver.hashCode());
        result =  31 * result + (manager == null ? 0 : manager.hashCode());
        result =  31 * result + (customer == null ? 0 : customer.hashCode());
        result =  31 * result + (price == null ? 0 : price.hashCode());
        result =  31 * result + (description == null ? 0 : description.hashCode());
        result =  31 * result + (status == null ? 0 : status.hashCode());
        return result;
    }
}
