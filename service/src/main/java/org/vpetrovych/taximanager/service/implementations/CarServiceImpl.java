package org.vpetrovych.taximanager.service.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.vpetrovych.taximanager.dao.interfaces.CarDao;
import org.vpetrovych.taximanager.dao.interfaces.UserDao;
import org.vpetrovych.taximanager.domain.entities.Car;
import org.vpetrovych.taximanager.domain.entities.User;
import org.vpetrovych.taximanager.service.interfaces.CarService;

public class CarServiceImpl implements CarService {

    private UserDao userDao;

    private CarDao carDao;

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Autowired
    public void setCarDao(CarDao carDao) {
        this.carDao = carDao;
    }

    @Override
    public Car findByUser(User user) {
        return carDao.findByUser(user);
    }

    @Override
    public void save(Car car) {
        carDao.save(car);
    }

    @Override
    public void update(Car car) {
        carDao.update(car);
    }

    @Override
    public void setDriver(Car car, UserDetails userDetails) {
        User driver = userDao.findByPhone(userDetails.getUsername());
        car.setUser(driver);
    }
}
