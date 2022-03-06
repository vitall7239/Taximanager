package org.vpetrovych.taximanager.domain.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.util.Objects;

@Entity
public class Car extends AbstractEntity {

    @Column(nullable = false)
    private String mark;

    @Column(nullable = false)
    private String model;

    @Column(nullable = false)
    private String color;

    @Column(nullable = false)
    private int year;

    @Column(nullable = false, unique = true)
    private String number;

    @Column(nullable = false, unique = true)
    private String driverNumber;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_User_Car"))
    private User user;

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDriverNumber() {
        return driverNumber;
    }

    public void setDriverNumber(String driverNumber) {
        this.driverNumber = driverNumber;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Car)) return false;

        Car car = (Car) o;

        return Objects.equals(year, car.year) &&
                Objects.equals(mark, car.mark) &&
                Objects.equals(model, car.model) &&
                Objects.equals(color, car.color) &&
                Objects.equals(number, car.number) &&
                Objects.equals(driverNumber, car.driverNumber) &&
                Objects.equals(user, car.user);
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = 31 * result + (mark == null ? 0 : mark.hashCode());
        result = 31 * result + (model == null ? 0 : model.hashCode());
        result = 31 * result + (color == null ? 0 : color.hashCode());
        result = 31 * result + year;
        result = 31 * result + (number == null ? 0 : number.hashCode());
        result = 31 * result + (driverNumber == null ? 0 : driverNumber.hashCode());
        return 31 * result + (user == null ? 0 : user.hashCode());
    }
}
