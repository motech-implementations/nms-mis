package com.beehyv.nmsreporting.model;

import javax.persistence.*;

/**
 * Created by beehyv on 15/3/17.
 */
@Entity
@Table(name="ROLE_PERMISSIONS")
public class Permissions {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="permission_id")
    private Integer permissionId;

    @Column(name="permission_desc")
    private String permission;

    public Integer getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Integer permissionId) {
        this.permissionId = permissionId;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }
}
