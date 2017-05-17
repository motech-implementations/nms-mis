package com.beehyv.nmsreporting.dao;

import com.beehyv.nmsreporting.model.District;
import com.beehyv.nmsreporting.model.Role;
import com.beehyv.nmsreporting.model.User;

import java.util.Date;
import java.util.List;

/**
 * Created by beehyv on 14/3/17.
 */
public interface UserDao {
    User findByUserId(Integer userId);

    User findByUserName(String username);

    User findByEmailId(String emailId);

    List<User> findByPhoneNumber(String phoneNumber);

    List<User> findByCreationDate(Date creationDate);

    List<User> getActiveUsers();

    List<User> getAllUsers();

    List<User> getUsersByAccountStatus(String accountStatus);

    List<User> getUsersByRole(Role roleId);

    <E> List<User> getUsersByLocation(String propertyName, E location);

    void saveUser(User user);

    boolean isAdminCreated(District districtId);
}
