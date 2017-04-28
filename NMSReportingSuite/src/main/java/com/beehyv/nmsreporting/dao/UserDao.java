package com.beehyv.nmsreporting.dao;

import com.beehyv.nmsreporting.model.Location;
import com.beehyv.nmsreporting.model.Role;
import com.beehyv.nmsreporting.model.User;

import java.sql.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by beehyv on 14/3/17.
 */
public interface UserDao {
    public User findByUserId(Integer userId);

    public User findByUserName(String username);

    public User findByEmailId(String emailId);

    public List<User> findByPhoneNumber(String phoneNumber);

    public List<User> findByLocation(Location locationId);

    public List<User> findByCreationDate(Date creationDate);

    public List<User> getAllActiveUsers();

    public List<User> getUsersByAccountStatus(String accountStatus);

    public List<User> getUsersByRole(Role roleId);

    public void saveUser(User user);

    public boolean isAdminCreated (Location districtId,Role roleId);
}
