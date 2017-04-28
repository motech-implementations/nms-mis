package com.beehyv.nmsreporting.dao;

import com.beehyv.nmsreporting.model.Permissions;

import java.util.List;

/**
 * Created by beehyv on 15/3/17.
 */
public interface PermissionsDao {
    public Permissions findByPermissionId(Integer permissionId);

    public List<Permissions> getAllPermissions();

    public void savePermission(Permissions permission);

    public void deletePermission(Permissions permission);
}
