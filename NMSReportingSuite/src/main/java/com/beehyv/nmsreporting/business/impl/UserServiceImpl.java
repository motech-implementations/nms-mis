package com.beehyv.nmsreporting.business.impl;

import com.beehyv.nmsreporting.business.UserService;
import com.beehyv.nmsreporting.dao.LocationDao;
import com.beehyv.nmsreporting.dao.RoleDao;
import com.beehyv.nmsreporting.dao.UserDao;
import com.beehyv.nmsreporting.model.AccountStatus;
import com.beehyv.nmsreporting.model.Location;
import com.beehyv.nmsreporting.model.User;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Date;
import java.util.*;

/**
 * Created by beehyv on 15/3/17.
 */
@Service("userService")
@Transactional
public class UserServiceImpl implements UserService{
    @Autowired
    private UserDao userDao;

    @Autowired
    private LocationDao locationDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User findUserByUserId(Integer userId) {
        return userDao.findByUserId(userId);
    }

    public User findUserByUsername(String username) {
        return userDao.findByUserName(username);
    }

    public User findUserByEmailId(String emailId) {
        return userDao.findByEmailId(emailId);
    }

    public User getCurrentUser() {
        final Integer currentUserId = (Integer) SecurityUtils.getSubject().getPrincipal();
        if(currentUserId != null) {
            return findUserByUserId(currentUserId);
        } else {
            return null;
        }
    }

    public List<User> findUsersByPhoneNumber(String phoneNumber) {
        return userDao.findByPhoneNumber(phoneNumber);
    }

    public List<User> findUsersByLocation(Integer locationId) {
        return userDao.findByLocation(locationDao.findByLocationId(locationId));
    }

    public List<User> findUsersByCreationDate(Date creationDate) {
        return userDao.findByCreationDate(creationDate);
    }

    public List<User> findAllActiveUsers() {
        return userDao.getAllActiveUsers();
    }

    public List<User> findAllActiveUsersByLocation(Integer locationId) {
        List<Location> subLocations = locationDao.getAllSubLocations(locationDao.findByLocationId(locationId));
        List<User> listOfUsersByLocation = new ArrayList<>();

        for(Location location: subLocations) {
            listOfUsersByLocation.addAll(userDao.findByLocation(location));
        }

        Collections.sort(listOfUsersByLocation, new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                return o1.getUserId()-o2.getUserId();
            }
        });
        return listOfUsersByLocation;
    }

    public List<User> findAllActiveUsersByRole(Integer roleId) {
        return userDao.getUsersByRole(roleDao.findByRoleId(roleId));
    }

    public List<User> findUsersByAccountStatus(String accountStatus) {
        return userDao.getUsersByAccountStatus(accountStatus);
    }

    public void createNewUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.saveUser(user);
    }

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

    public void deleteExistingUser(User user) {
        User entity = userDao.findByUserId(user.getUserId());
        if(entity != null) {
            entity.setAccountStatus(AccountStatus.INACTIVE.getAccountStatus());
        }
    }

    public boolean isUsernameUnique(String username, Integer userId) {
        User user = userDao.findByUserName(username);
        return (user == null || ((userId != null) && (user.getUserId() == userId)));
    }
}
