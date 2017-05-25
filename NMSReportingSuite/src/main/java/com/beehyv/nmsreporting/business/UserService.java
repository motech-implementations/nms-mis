package com.beehyv.nmsreporting.business;

import com.beehyv.nmsreporting.dto.PasswordDto;
import com.beehyv.nmsreporting.model.User;

import java.util.Date;
import java.util.List;
import java.util.Map;

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

    Map<Integer, String> createNewUser(User user);

    Map<Integer, String> updateExistingUser(User user);

    Map<Integer, String> updatePassword(PasswordDto passwordDto);

    void deleteExistingUser(User user);

    boolean isUsernameUnique(String username, Integer userId);
}
