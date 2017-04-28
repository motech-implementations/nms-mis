package com.beehyv.nmsreporting.dao.impl;

import com.beehyv.nmsreporting.dao.AbstractDao;
import com.beehyv.nmsreporting.dao.PermissionsDao;
import com.beehyv.nmsreporting.model.Permissions;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by beehyv on 15/3/17.
 */
@Repository("permissionsDao")
public class PermissionsDaoImpl extends AbstractDao<Integer, Permissions> implements PermissionsDao {
    public Permissions findByPermissionId(Integer permissionId) {
       return getByKey(permissionId);
    }

    public List<Permissions> getAllPermissions() {
        Criteria criteria = createEntityCriteria().addOrder(Order.asc("permissionId"));
        return (List<Permissions>) criteria.list();
    }

    public void savePermission(Permissions permission) {
        persist(permission);
    }

    public void deletePermission(Permissions permission) {
        delete(permission);
    }
}
