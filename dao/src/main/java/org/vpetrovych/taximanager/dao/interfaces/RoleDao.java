package org.vpetrovych.taximanager.dao.interfaces;

import org.vpetrovych.taximanager.domain.entities.Role;

public interface RoleDao extends GenericDao<Role> {

    Role findByName(String name);

}
