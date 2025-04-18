package com.beehyv.nmsreporting.model;

import com.beehyv.nmsreporting.enums.AccountStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Entity
@Table(name="USER_DETAILS")
public class User {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="user_id")
	private Integer userId;

	@Column(name="username")
	@Pattern(regexp="[A-Za-z0-9]+")
	private String username;

	@JsonIgnore
	@Column(name="password")
	private String password;

	@Column(name="full_name")
	@Pattern(regexp="[A-Za-z ]+")
	private String fullName;

	@Column(name="phone_no")
	private String phoneNumber;

	@Column(name="email_id")
	private String emailId;

//	@ManyToOne
//	@JoinColumn(name="location",insertable = false,updatable = false)
//	private Location locationId;

	@Column(name="state",columnDefinition = "TINYINT")
	private Integer stateId;

	@Column(name="district", columnDefinition = "SMALLINT")
	private Integer districtId;

	@Column(name="healthblock", columnDefinition = "INT")
	private Integer blockId;

	@Column(name="creation_date")
	private Date creationDate;

	@Column(name = "last_password_change_date")
	private Date lastPasswordChangeDate;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="created_by_user")
	private User createdByUser;

//	@OneToMany(mappedBy="createdByUser")
//	@LazyCollection(LazyCollectionOption.FALSE)
//	@JsonIgnore
//	private Set<User> createdUsers = new HashSet<>();

	@Column(name="role_id")
	private Integer roleId;

	@Column(name="account_status")
	private String accountStatus = AccountStatus.ACTIVE.getAccountStatus();

	@Column(name="access_level")
	private  String accessLevel;

	@Column(name = "state_name")
	private  String stateName;

	@Column(name = "district_name")
	private String districtName;

	@Column(name = "block_name")
	private String blockName;

	@Column(name = "role_name")
	private String roleName;

	@Column(name = "isLoggedOnce")
	private Boolean loggedAtLeastOnce = false;

	@Column(name = "isPasswordDefault")
	private Boolean isDefault;

	private Integer unSuccessfulAttempts = 0;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public Integer getStateId() {
		return stateId;
	}

	public void setStateId(Integer stateId) {
		this.stateId = stateId;
	}

	public Integer getDistrictId() {
		return districtId;
	}

	public void setDistrictId(Integer districtId) {
		this.districtId = districtId;
	}

	public Integer getBlockId() {
		return blockId;
	}

	public void setBlockId(Integer blockId) {
		this.blockId = blockId;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public User getCreatedByUser() {
		return createdByUser;
	}

	public void setCreatedByUser(User createdByUser) {
		this.createdByUser = createdByUser;
	}

//	public Set<User> getCreatedUsers() {
//		return createdUsers;
//	}
//
//	public void setCreatedUsers(Set<User> createdUsers) {
//		this.createdUsers = createdUsers;
//	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}

	public String getAccessLevel() {
		return accessLevel;
	}

	public void setAccessLevel(String accessLevel) {
		this.accessLevel = accessLevel;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public String getDistrictName() {
		return districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	public String getBlockName() {
		return blockName;
	}

	public void setBlockName(String blockName) {
		this.blockName = blockName;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Date getLastPasswordChangeDate() {
		return lastPasswordChangeDate;
	}

	public void setLastPasswordChangeDate(Date lastPasswordChangeDate) {
		this.lastPasswordChangeDate = lastPasswordChangeDate;
	}

	public Boolean getLoggedAtLeastOnce() {
		return loggedAtLeastOnce;
	}

	public void setLoggedAtLeastOnce(Boolean loggedAtLeastOnce) {
		this.loggedAtLeastOnce = loggedAtLeastOnce;
	}

	public Boolean getDefault() {
		return isDefault;
	}

	public void setDefault(Boolean aDefault) {
		isDefault = aDefault;
	}

	public Integer getUnSuccessfulAttempts() {
		return unSuccessfulAttempts;
	}

	public void setUnSuccessfulAttempts(Integer unSuccessfulAttempts) {
		this.unSuccessfulAttempts = unSuccessfulAttempts;
	}
}