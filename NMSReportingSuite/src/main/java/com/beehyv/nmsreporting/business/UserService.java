package com.beehyv.nmsreporting.business;

import com.beehyv.nmsreporting.model.User;

import java.util.Date;
import java.util.List;

/**
 * Created by beehyv on 14/3/17.
 */
public interface UserService {
    User findUserByUserId(Integer userId);

    User findUserByUsername(String username);

    User getCurrentUser();

    User findUserByEmailId(String emailId);

    List<User> findUsersByPhoneNumber(String phoneNumber);

    List<User> findUsersByCreationDate(Date creationDate);

    List<User> findAllActiveUsers();

    List<User> findAllActiveUsersByRole(Integer roleId);

    List<User> findUsersByAccountStatus(String accountStatus);

    List<User> findMyUsers(User currentUser);

    void createNewUser(User user);

    void updateExistingUser(User user);

    void deleteExistingUser(User user);

    boolean isUsernameUnique(String username, Integer userId);
}
