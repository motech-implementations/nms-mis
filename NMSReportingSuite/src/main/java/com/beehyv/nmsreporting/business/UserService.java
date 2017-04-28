package com.beehyv.nmsreporting.business;

import com.beehyv.nmsreporting.model.User;

import java.sql.Date;
import java.util.List;

/**
 * Created by beehyv on 14/3/17.
 */
public interface UserService {
    public User findUserByUserId(Integer userId);

    public User findUserByUsername(String username);

    public User getCurrentUser();

    public User findUserByEmailId(String emailId);

    public List<User> findUsersByPhoneNumber(String phoneNumber);

    public List<User> findUsersByLocation(Integer locationId);

    public List<User> findUsersByCreationDate(Date creationDate);

    public List<User> findAllActiveUsers();

    public List<User> findAllActiveUsersByLocation(Integer locationId);

    public List<User> findAllActiveUsersByRole(Integer roleId);

    public List<User> findUsersByAccountStatus(String accountStatus);

    public void createNewUser(User user);

    public void updateExistingUser(User user);

    public void deleteExistingUser(User user);

    public boolean isUsernameUnique(String username, Integer userId);
}
