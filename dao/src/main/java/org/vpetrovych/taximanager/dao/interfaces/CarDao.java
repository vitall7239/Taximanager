package org.vpetrovych.taximanager.dao.interfaces;

import org.vpetrovych.taximanager.domain.entities.Car;
import org.vpetrovych.taximanager.domain.entities.User;

public interface CarDao extends GenericDao<Car> {

    Car findByUser(User user);

}
