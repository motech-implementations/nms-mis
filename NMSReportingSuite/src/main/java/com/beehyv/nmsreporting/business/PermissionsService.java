package com.beehyv.nmsreporting.business;

import com.beehyv.nmsreporting.model.Permissions;

import java.util.List;

/**
 * Created by beehyv on 15/3/17.
 */
public interface PermissionsService {
    public Permissions findPermissionByPermissionId(Integer permissionId);

    public List<Permissions> getAllPermissions();

    public void createNewPermission(Permissions permission);

    public void updateExistingPermission(Permissions permission);

    public void deleteExistingPermission(Permissions permission);
}
