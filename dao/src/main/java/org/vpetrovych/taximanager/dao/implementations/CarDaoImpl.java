package org.vpetrovych.taximanager.dao.implementations;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.vpetrovych.taximanager.dao.interfaces.CarDao;
import org.vpetrovych.taximanager.domain.entities.Car;
import org.vpetrovych.taximanager.domain.entities.User;

import javax.persistence.NonUniqueResultException;
import java.util.List;

@Repository
@Transactional
public class CarDaoImpl extends GenericDaoImpl<Car> implements CarDao {

    public CarDaoImpl() {
        super(Car.class);
    }

    @Override
    public Car findByUser(User user) {
        Assert.notNull(user, "user could not be null");
        Assert.notNull(user.getId(), "user id could not be null");
        List<Car> carList = getEntityManager()
                .createQuery("from " + getClazz().getSimpleName() + " c where c.user = :user", getClazz())
                .setParameter("user", user)
                .getResultList();
        if (carList.isEmpty()) {
            return null;
        } else if (carList.size() == 1) {
            return carList.get(0);
        }
        throw new NonUniqueResultException();
    }
}
