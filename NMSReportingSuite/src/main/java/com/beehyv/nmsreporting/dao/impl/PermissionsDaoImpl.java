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

    @Override
    public Permissions findByPermissionId(Integer permissionId) {
       return getByKey(permissionId);
    }

    @Override
    public List<Permissions> getAllPermissions() {
        Criteria criteria = createEntityCriteria().addOrder(Order.asc("permissionId"));
        return (List<Permissions>) criteria.list();
    }

    @Override
    public void savePermission(Permissions permission) {
        persist(permission);
    }

    @Override
    public void deletePermission(Permissions permission) {
        delete(permission);
    }
}
