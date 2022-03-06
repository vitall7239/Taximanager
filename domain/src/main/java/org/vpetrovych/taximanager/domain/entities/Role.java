package org.vpetrovych.taximanager.domain.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.Objects;

@Entity
public class Role extends AbstractEntity {

    @Column(nullable = false, unique = true)
    private String name;

    public Role() {
        this(null, null);
    }

    public Role(String name) {
        this(null, name);
    }

    public Role(Long id, String name) {
        this.setId(id);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof Role)) {
            return false;
        }
        Role role = (Role) object;
        return Objects.equals(getName(), role.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }
}
