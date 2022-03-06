package org.vpetrovych.taximanager.dao.implementations;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.vpetrovych.taximanager.dao.interfaces.CustomerDao;
import org.vpetrovych.taximanager.domain.entities.Customer;

import javax.persistence.NonUniqueResultException;
import java.util.List;

@Repository
@Transactional
public class CustomerDaoImpl extends GenericDaoImpl<Customer> implements CustomerDao {

    public CustomerDaoImpl() {
        super(Customer.class);
    }

    @Override
    public Customer findByPhone(String phone) {
        Assert.notNull(phone, "phone could not be null");
        List<Customer> customerList = getEntityManager()
                .createQuery("from " + getClazz().getSimpleName() + " c where c.phone = :phone", getClazz())
                .setParameter("phone", phone)
                .getResultList();
        if (customerList.isEmpty()) {
            return null;
        } else if (customerList.size() == 1) {
            return customerList.get(0);
        }
        throw new NonUniqueResultException();
    }

}
