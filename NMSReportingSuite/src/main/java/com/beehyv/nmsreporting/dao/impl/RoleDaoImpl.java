package com.beehyv.nmsreporting.dao.impl;

import com.beehyv.nmsreporting.dao.AbstractDao;
import com.beehyv.nmsreporting.dao.RoleDao;
import com.beehyv.nmsreporting.model.Role;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by beehyv on 15/3/17.
 */
@Repository("roleDao")
public class RoleDaoImpl extends AbstractDao<Integer, Role> implements RoleDao {
    public Role findByRoleId(Integer roleId) {
        return getByKey(roleId);
    }

    @Override
    public List<Role> findByRoleDescription(String role) {
//        Criteria criteria = createEntityCriteria();
//        criteria.add(Restrictions.like("role_desc", role));
//        return criteria.list();

        Query query = getSession().createQuery("from Role where role_desc = :role ");
        query.setParameter("role", role);
        return query.list();
    }

    public List<Role> getAllRoles() {
        Criteria criteria = createEntityCriteria().addOrder(Order.asc("roleId"));
        return (List<Role>) criteria.list();
    }

    public void saveRole(Role role) {
        persist(role);
    }

    public void deleteRole(Role role) {
        delete(role);
    }
}
