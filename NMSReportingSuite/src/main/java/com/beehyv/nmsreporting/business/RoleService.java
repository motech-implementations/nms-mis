package com.beehyv.nmsreporting.business;

import com.beehyv.nmsreporting.model.Role;

import java.util.List;

/**
 * Created by beehyv on 15/3/17.
 */
public interface RoleService {
    public Role findRoleByRoleId(Integer roleId);

    public List<Role> getAllRoles();

    public void createNewRole(Role role);

    public void updateExistingRole(Role role);

    public void deleteExistingRole(Role role);
}
