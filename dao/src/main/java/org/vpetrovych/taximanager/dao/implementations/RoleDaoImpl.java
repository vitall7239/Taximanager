package org.vpetrovych.taximanager.dao.implementations;

import org.springframework.transaction.annotation.Transactional;
import org.vpetrovych.taximanager.dao.interfaces.RoleDao;
import org.vpetrovych.taximanager.domain.entities.Role;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import javax.persistence.NonUniqueResultException;
import java.util.List;

@Repository
@Transactional
public class RoleDaoImpl extends GenericDaoImpl<Role> implements RoleDao {

    public RoleDaoImpl() {
        super(Role.class);
    }

    @Override
    public Role findByName(String name) {
        Assert.notNull(name, "name could not be empty");
        List<Role> resultList = getEntityManager()
                .createQuery(" from " + getClazz().getSimpleName() + " r  where r.name = :name ", getClazz())
                .setParameter("name", name)
                .getResultList();
        if (resultList.isEmpty()) {
            return null;
        } else if (resultList.size() == 1) {
            return resultList.get(0);
        }
        throw new NonUniqueResultException();
    }
}
