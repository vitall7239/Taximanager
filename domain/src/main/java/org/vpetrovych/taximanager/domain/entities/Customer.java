package org.vpetrovych.taximanager.domain.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.Objects;

@Entity
public class Customer extends AbstractEntity {

    @Column(nullable = false, unique = true)
    String phone;

    @Column(nullable = false)
    String name;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Customer)) {
            return false;
        }
        Customer customer = (Customer) o;
        return Objects.equals(phone, customer.phone);
    }

    @Override
    public int hashCode() {
        return 31 + (phone == null ? 0 : phone.hashCode());
    }
}
