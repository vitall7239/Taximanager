package org.vpetrovych.taximanager.service.interfaces;

import org.springframework.security.core.userdetails.UserDetails;
import org.vpetrovych.taximanager.domain.entities.Car;
import org.vpetrovych.taximanager.domain.entities.User;

public interface CarService {

    Car findByUser(User user);

    void save(Car car);

    void update(Car car);

    void setDriver(Car car, UserDetails userDetails);

}
