package org.vpetrovych.taximanager.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.vpetrovych.taximanager.domain.entities.Car;
import org.vpetrovych.taximanager.domain.entities.User;
import org.vpetrovych.taximanager.service.interfaces.CarService;
import org.vpetrovych.taximanager.service.interfaces.UserService;

import javax.faces.flow.FlowScoped;

@Component("driverUser")
@Scope(value="flow")
@FlowScoped(value="driver")
public class DriverUser {

    private User user;

    private Car car;

    private boolean showCarForm = false;

    private UserService userService;

    private CarService carService;

    public DriverUser(){}

    @Autowired
    public void setUserService(UserService userService){
        this.userService = userService;
    }

    @Autowired
    public void setCarService(CarService carService){
        this.carService = carService;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public boolean isShowCarForm() {
        return showCarForm;
    }

    public void setShowCarForm(boolean showCarForm) {
        this.showCarForm = showCarForm;
    }

    public void init(){
        user = userService.findByPhone(BeanUtils.getCurrentUser().getUsername());
        car = carService.findByUser(user);
        if(car != null){
            showCarForm = true;
        }else{
            showCarForm = false;
        }
    }

    public void saveUser(){
        userService.updateUser(user);
    }

    public void addCar(){
        car = new Car();
        car.setYear(2000);
        showCarForm = true;
    }

    public void saveCar(){
        if(car.getUser() == null) {
            carService.setDriver(car, BeanUtils.getCurrentUser());
        }
        if(car.getId() == null){
            carService.save(car);
        }else{
            carService.update(car);
        }
    }
}
