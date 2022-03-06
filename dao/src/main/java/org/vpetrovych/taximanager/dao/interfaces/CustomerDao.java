package org.vpetrovych.taximanager.dao.interfaces;

import org.vpetrovych.taximanager.domain.entities.Customer;

public interface CustomerDao extends GenericDao<Customer> {

    Customer findByPhone(String phone);

}
