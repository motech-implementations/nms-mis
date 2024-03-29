package com.beehyv.nmsreporting.model;

import javax.persistence.*;

@Entity
@Table(name="USER_ROLE")
public class Role {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="role_id")
	private Integer roleId;
	
	@Column(name="role_desc")
	private String roleDescription;

	@ManyToOne
	@JoinColumn(name="permission_id")
	private Permissions permissionId;

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getRoleDescription() {
		return roleDescription;
	}

	public void setRoleDescription(String roleDescription) {
		this.roleDescription = roleDescription;
	}

	public Permissions getPermissionId() {
		return permissionId;
	}

	public void setPermissionId(Permissions permissionId) {
		this.permissionId = permissionId;
	}
}
