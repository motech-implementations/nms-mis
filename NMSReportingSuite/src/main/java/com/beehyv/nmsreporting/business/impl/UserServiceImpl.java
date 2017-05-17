package com.beehyv.nmsreporting.business.impl;

import com.beehyv.nmsreporting.business.UserService;
import com.beehyv.nmsreporting.dao.*;
import com.beehyv.nmsreporting.enums.AccountStatus;
import com.beehyv.nmsreporting.model.User;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by beehyv on 15/3/17.
 */
@Service("userService")
@Transactional
public class UserServiceImpl implements UserService{
    @Autowired
    private UserDao userDao;

    @Autowired
    private StateDao stateDao;

    @Autowired
    private DistrictDao districtDao;

    @Autowired
    private BlockDao blockDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User findUserByUserId(Integer userId) {
        return userDao.findByUserId(userId);
    }

    @Override
    public User findUserByUsername(String username) {
        return userDao.findByUserName(username);
    }

    @Override
    public User findUserByEmailId(String emailId) {
        return userDao.findByEmailId(emailId);
    }

    @Override
    public User getCurrentUser() {
        final Integer currentUserId = (Integer) SecurityUtils.getSubject().getPrincipal();
        if(currentUserId != null) {
            return findUserByUserId(currentUserId);
        } else {
            return null;
        }
    }

    @Override
    public List<User> findUsersByPhoneNumber(String phoneNumber) {
        return userDao.findByPhoneNumber(phoneNumber);
    }

    @Override
    public List<User> findUsersByCreationDate(Date creationDate) {
        return userDao.findByCreationDate(creationDate);
    }

    @Override
    public List<User> findAllActiveUsers() {
        return userDao.getActiveUsers();
    }

    @Override
    public List<User> findAllActiveUsersByRole(Integer roleId) {
        return userDao.getUsersByRole(roleDao.findByRoleId(roleId));
    }

    @Override
    public List<User> findUsersByAccountStatus(String accountStatus) {
        return userDao.getUsersByAccountStatus(accountStatus);
    }

    @Override
    public List<User> findMyUsers(User currentUser) {

        String accessLevel = "NATIONAL";
        if(accessLevel.equalsIgnoreCase("State")){
            return userDao.getUsersByLocation("stateId", currentUser.getStateId());
        }
        else if(accessLevel.equalsIgnoreCase("District")){
            return userDao.getUsersByLocation("districtId", currentUser.getDistrictId());
        }
        else if(accessLevel.equalsIgnoreCase("Block")){
            return userDao.getUsersByLocation("blockId", currentUser.getBlockId());
        }
        else if(accessLevel.equalsIgnoreCase("National")){
            return userDao.getAllUsers();
        }
        else
            return new ArrayList<User>();
    }

    @Override
    public void createNewUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.saveUser(user);
    }

    @Override
    public void updateExistingUser(User user) {
        User entity = userDao.findByUserId(user.getUserId());
        if(entity != null) {
            entity.setUsername(user.getUsername());
            if(!entity.getPassword().equals(user.getPassword()))
                entity.setPassword(passwordEncoder.encode(user.getPassword()));
            entity.setFullName(user.getFullName());
            entity.setEmailId(user.getEmailId());
            entity.setLocationId(user.getLocationId());
            entity.setAccountStatus(user.getAccountStatus());
            entity.setCreatedByUser(user.getCreatedByUser());
            entity.setCreationDate(user.getCreationDate());
            entity.setPhoneNumber(user.getPhoneNumber());
            entity.setRoleId(user.getRoleId());
        }
    }

    @Override
    public void deleteExistingUser(User user) {
        User entity = userDao.findByUserId(user.getUserId());
        if(entity != null) {
            entity.setAccountStatus(AccountStatus.INACTIVE.getAccountStatus());
        }
    }

    @Override
    public boolean isUsernameUnique(String username, Integer userId) {
        User user = userDao.findByUserName(username);
        return (user == null || ((userId != null) && (user.getUserId() == userId)));
    }
}
