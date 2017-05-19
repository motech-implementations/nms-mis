package com.beehyv.nmsreporting.dao;

import com.beehyv.nmsreporting.model.Permissions;

import java.util.List;

/**
 * Created by beehyv on 15/3/17.
 */
public interface PermissionsDao {

    Permissions findByPermissionId(Integer permissionId);

    List<Permissions> getAllPermissions();

    void savePermission(Permissions permission);

    void deletePermission(Permissions permission);
}
