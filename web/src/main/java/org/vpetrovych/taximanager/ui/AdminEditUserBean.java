package org.vpetrovych.taximanager.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.vpetrovych.taximanager.domain.entities.Car;
import org.vpetrovych.taximanager.domain.entities.User;
import org.vpetrovych.taximanager.service.interfaces.CarService;
import org.vpetrovych.taximanager.service.interfaces.UserService;

import javax.faces.flow.FlowScoped;

@Component("adminEditUserBean")
@Scope(value="flow")
@FlowScoped(value="admin")
public class AdminEditUserBean {

    private User user;

    private Car car;

    private boolean showCarForm = false;

    private UserService userService;

    private CarService carService;

    private String password;

    private String role;

    public AdminEditUserBean(){}

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void init(Long id ){
        password = "";
        user = userService.findById(id);
        car = carService.findByUser(user);
        role = user.getRole().getName();
        if(car != null){
            showCarForm = true;
        }else{
            showCarForm = false;
        }
    }

    public void saveUser(){
        if(!role.equals(user.getRole().getName())){
            user = userService.updateRole(user, role);
        }
        if(!password.equals("")){
            userService.updatePassword(user, password);
        }else {
            userService.updateUser(user);
        }
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
