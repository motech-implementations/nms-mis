package com.beehyv.nmsreporting.dao;

import com.beehyv.nmsreporting.model.Role;

import java.util.List;

/**
 * Created by beehyv on 15/3/17.
 */
public interface RoleDao {
    public Role findByRoleId(Integer roleId);
    public List<Role> findByRoleDescription(String role);
    public List<Role> getAllRoles();

    public void saveRole(Role role);

    public void deleteRole(Role role);
}
