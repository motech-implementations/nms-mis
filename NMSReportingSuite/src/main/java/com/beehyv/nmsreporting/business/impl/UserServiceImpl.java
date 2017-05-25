package com.beehyv.nmsreporting.business.impl;

import com.beehyv.nmsreporting.business.UserService;
import com.beehyv.nmsreporting.dao.*;
import com.beehyv.nmsreporting.dto.PasswordDto;
import com.beehyv.nmsreporting.enums.AccessLevel;
import com.beehyv.nmsreporting.enums.AccessType;
import com.beehyv.nmsreporting.enums.AccountStatus;
import com.beehyv.nmsreporting.model.User;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

        String accessLevel = currentUser.getAccessLevel();
        if(accessLevel.equalsIgnoreCase(AccessLevel.STATE.getAccessLevel())){
            return userDao.getUsersByLocation("stateId", currentUser.getStateId());
        }
        else if(accessLevel.equalsIgnoreCase(AccessLevel.DISTRICT.getAccessLevel())){
            return userDao.getUsersByLocation("districtId", currentUser.getDistrictId());
        }
        else if(accessLevel.equalsIgnoreCase(AccessLevel.BLOCK.getAccessLevel())){
            return userDao.getUsersByLocation("blockId", currentUser.getBlockId());
        }
        else if(accessLevel.equalsIgnoreCase(AccessLevel.NATIONAL.getAccessLevel())){
            return userDao.getAllUsers();
        }
        else
            return new ArrayList<User>();
    }

    @Override
    public Map<Integer, String> createNewUser(User user) {
        User currentUser = getCurrentUser();
        Integer rowNum = 0;
        Map<Integer, String> responseMap = new HashMap<Integer, String>();

        if (user.getUsername().isEmpty()) {
            String userNameError = "Please specify the username for user";
            responseMap.put(rowNum, userNameError);
            return responseMap;
        }
        if (userDao.findByUserName(user.getUsername()) != null) {
            String userNameError = "Username already exists.";
            responseMap.put(rowNum, userNameError);
            return responseMap;
        }

        String userPhone = user.getPhoneNumber();
        String regexStr1 = "^[0-9]*$";
        String regexStr2 = "^[0-9]{10}$";
        if (userPhone.isEmpty()) {
            String userNameError = "Please specify the phone number for user";
            responseMap.put(rowNum, userNameError);
            return responseMap;
        }
        else if (!(userPhone.matches(regexStr1)) || !(userPhone.matches(regexStr2))) {
            String userNameError = "Please check the format of phone number for user";
            responseMap.put(rowNum, userNameError);
            return responseMap;
        }

        if (user.getEmailId().isEmpty()) {
            String userNameError = "Please specify the Email for user";
            responseMap.put(rowNum, userNameError);
            return responseMap;
        }
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(user.getEmailId());
        if (!matcher.matches()){
            String userNameError = "Please enter the valid Email for user";
            responseMap.put(rowNum, userNameError);
            return responseMap;
        }

//        System.out.println(user.getAccessLevel());
//        System.out.println(AccessLevel.BLOCK.name());
        if (!AccessLevel.isLevel(user.getAccessLevel())) {
            String userNameError = "Please specify the access level for user";
            responseMap.put(rowNum, userNameError);
            return responseMap;
        }

        if(currentUser.getRoleId().getRoleDescription().equalsIgnoreCase(AccessType.USER.getAccessType())) {
            String authorityError = "No authority";
            responseMap.put(rowNum, authorityError);
            return responseMap;
        }

        if(AccessLevel.getLevel(currentUser.getAccessLevel()).ordinal() > AccessLevel.getLevel(user.getAccessLevel()).ordinal()){
            String authorityError = "No authority";
            responseMap.put(rowNum, authorityError);
            return responseMap;
        }


        if (user.getAccessLevel().equalsIgnoreCase(AccessLevel.NATIONAL.getAccessLevel())) {
            if (user.getRoleId().getRoleDescription().equalsIgnoreCase(AccessType.ADMIN.getAccessType())) {
                if (userDao.roleExistsNational(roleDao.findByRoleDescription(AccessType.ADMIN.getAccessType()).get(0))) {
                    String authorityError = "Admin exists at this level and location";
                    responseMap.put(rowNum, authorityError);
                    return responseMap;
                }
            }
            user.setStateId(null);
            user.setDistrictId(null);
            user.setBlockId(null);
        }
        else if (user.getAccessLevel().equalsIgnoreCase(AccessLevel.STATE.getAccessLevel())) {
            if (user.getRoleId().getRoleDescription().equalsIgnoreCase(AccessType.ADMIN.getAccessType())) {
                if (userDao.roleExistsState(roleDao.findByRoleDescription(AccessType.ADMIN.getAccessType()).get(0), user.getStateId())) {
                    String authorityError = "Admin exists at this level and location";
                    responseMap.put(rowNum, authorityError);
                    return responseMap;
                }
            }
            if(user.getStateId() == null) {
                String authorityError = "missing property: state";
                responseMap.put(rowNum, authorityError);
                return responseMap;
            }
            if(currentUser.getStateId() != null && !user.getStateId().equals(currentUser.getStateId())) {
                String authorityError = "invalid property: state";
                responseMap.put(rowNum, authorityError);
                return responseMap;
            }

            user.setDistrictId(null);
            user.setBlockId(null);
        }
        else if (user.getAccessLevel().equalsIgnoreCase(AccessLevel.DISTRICT.getAccessLevel())) {
            if (user.getRoleId().getRoleDescription().equalsIgnoreCase(AccessType.ADMIN.getAccessType())) {
                if (userDao.roleExistsDistrict(roleDao.findByRoleDescription(AccessType.ADMIN.getAccessType()).get(0), user.getDistrictId())) {
                    String authorityError = "Admin exists at this level and location";
                    responseMap.put(rowNum, authorityError);
                    return responseMap;
                }
            }
            if(user.getStateId() == null) {
                String authorityError = "missing property: state";
                responseMap.put(rowNum, authorityError);
                return responseMap;
            }
            if(user.getDistrictId() == null) {
                String authorityError = "missing property: district";
                responseMap.put(rowNum, authorityError);
                return responseMap;
            }
            if(currentUser.getStateId() != null && !user.getStateId().equals(currentUser.getStateId())) {
                String authorityError = "invalid property: state";
                responseMap.put(rowNum, authorityError);
                return responseMap;
            }
            if(currentUser.getDistrictId() != null && !user.getDistrictId().equals(currentUser.getDistrictId())) {
                String authorityError = "invalid property: state";
                responseMap.put(rowNum, authorityError);
                return responseMap;
            }
//            if(!user.getDistrictId().getStateOfDistrict().equals(user.getStateId())) {
//                String authorityError = "invalid property: district";
//                responseMap.put(rowNum, authorityError);
//                return responseMap;
//            }
            user.setBlockId(null);
        }
        else {
            if (user.getRoleId().getRoleDescription().equalsIgnoreCase(AccessType.ADMIN.getAccessType())) {
                String authorityError = "Cannot create admin here";
                responseMap.put(rowNum, authorityError);
                return responseMap;
            }
            if(user.getStateId() == null) {
                String authorityError = "missing property: state";
                responseMap.put(rowNum, authorityError);
                return responseMap;
            }
            if(currentUser.getStateId() != null && !user.getStateId().equals(currentUser.getStateId())) {
                String authorityError = "invalid property: state";
                responseMap.put(rowNum, authorityError);
                return responseMap;
            }
            if(user.getDistrictId() == null) {
                String authorityError = "missing property: district";
                responseMap.put(rowNum, authorityError);
                return responseMap;
            }
            if(currentUser.getDistrictId() != null && !user.getDistrictId().equals(currentUser.getDistrictId())) {
                String authorityError = "invalid property: state";
                responseMap.put(rowNum, authorityError);
                return responseMap;
            }
            if(user.getBlockId() == null) {
                String authorityError = "missing property: block";
                responseMap.put(rowNum, authorityError);
                return responseMap;
            }
//
//            if(!user.getDistrictId().getStateOfDistrict().equals(user.getStateId())) {
//                String authorityError = "invalid property: district";
//                responseMap.put(rowNum, authorityError);
//                return responseMap;
//            }
//            if(!user.getBlockId().getStateOfBlock().equals(user.getStateId())) {
//                String authorityError = "invalid property: block";
//                responseMap.put(rowNum, authorityError);
//                return responseMap;
//            }
        }

        user.setPassword(passwordEncoder.encode(user.getPhoneNumber()));
        user.setCreationDate(new Date());
        user.setCreatedByUser(currentUser);
        user.setAccountStatus(AccountStatus.ACTIVE.getAccountStatus());

        userDao.saveUser(user);

        String authorityError = "user created";
        responseMap.put(rowNum, authorityError);
        return responseMap;
    }

    @Override
    public Map<Integer, String> updateExistingUser(User user) {
        User entity = userDao.findByUserId(user.getUserId());
        Integer rowNum = 0;
        Map<Integer, String> responseMap = new HashMap<Integer, String>();

        User currentUser = getCurrentUser();

        if(user.getCreatedByUser().getUserId() == currentUser.getUserId()){
            String authorityError = "No authority";
            responseMap.put(rowNum, authorityError);
            return responseMap;
        }

        if(currentUser.getRoleId().getRoleDescription().equalsIgnoreCase(AccessType.USER.getAccessType())) {
            String authorityError = "No authority";
            responseMap.put(rowNum, authorityError);
            return responseMap;
        }

        if(entity == null) {
            responseMap.put(rowNum, "invalid user");
            return responseMap;
        }

        if (user.getFullName().isEmpty()) {
            String userNameError = "Please specify the Full name for user";
            responseMap.put(rowNum, userNameError);
            return responseMap;
        }

        String userPhone = user.getPhoneNumber();
        String regexStr1 = "^[0-9]*$";
        String regexStr2 = "^[0-9]{10}$";
        if (userPhone.isEmpty()) {
            String userNameError = "Please specify the phone number for user";
            responseMap.put(rowNum, userNameError);
            return responseMap;
        }
        else if (!(userPhone.matches(regexStr1)) || !(userPhone.matches(regexStr2))) {
            String userNameError = "Please check the format of phone number for user";
            responseMap.put(rowNum, userNameError);
            return responseMap;
        }

        if (user.getEmailId().isEmpty()) {
            String userNameError = "Please specify the Email for user";
            responseMap.put(rowNum, userNameError);
            return responseMap;
        }
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(user.getEmailId());
        if (!matcher.matches()){
            String userNameError = "Please enter the valid Email for user";
            responseMap.put(rowNum, userNameError);
            return responseMap;
        }

        entity.setFullName(user.getFullName());
        entity.setEmailId(user.getEmailId());
        entity.setPhoneNumber(user.getPhoneNumber());

        if (!AccessLevel.isLevel(user.getAccessLevel())) {
            String userNameError = "Please specify the access level for user";
            responseMap.put(rowNum, userNameError);
            return responseMap;
        }

        if(AccessLevel.getLevel(currentUser.getAccessLevel()).ordinal() > AccessLevel.getLevel(user.getAccessLevel()).ordinal()){
            String authorityError = "No authority";
            responseMap.put(rowNum, authorityError);
            return responseMap;
        }

        entity.setRoleId(user.getRoleId());
        entity.setAccessLevel(user.getAccessLevel());

        if (user.getAccessLevel().equalsIgnoreCase(AccessLevel.NATIONAL.getAccessLevel())) {
            if (user.getRoleId().getRoleDescription().equalsIgnoreCase(AccessType.ADMIN.getAccessType())) {
                if (userDao.roleExistsNational(roleDao.findByRoleDescription(AccessType.ADMIN.getAccessType()).get(0))) {
                    String authorityError = "Admin exists at this level and location";
                    responseMap.put(rowNum, authorityError);
                    return responseMap;
                }
            }
            user.setStateId(null);
            user.setDistrictId(null);
            user.setBlockId(null);
        }
        else if (user.getAccessLevel().equalsIgnoreCase(AccessLevel.STATE.getAccessLevel())) {
            if (user.getRoleId().getRoleDescription().equalsIgnoreCase(AccessType.ADMIN.getAccessType())) {
                if (userDao.roleExistsState(roleDao.findByRoleDescription(AccessType.ADMIN.getAccessType()).get(0), user.getStateId())) {
                    String authorityError = "Admin exists at this level and location";
                    responseMap.put(rowNum, authorityError);
                    return responseMap;
                }
            }
            if(user.getStateId() == null) {
                String authorityError = "missing property: state";
                responseMap.put(rowNum, authorityError);
                return responseMap;
            }
            if(currentUser.getStateId() != null && !user.getStateId().equals(currentUser.getStateId())) {
                String authorityError = "invalid property: state";
                responseMap.put(rowNum, authorityError);
                return responseMap;
            }

            user.setDistrictId(null);
            user.setBlockId(null);
        }
        else if (user.getAccessLevel().equalsIgnoreCase(AccessLevel.DISTRICT.getAccessLevel())) {
            if (user.getRoleId().getRoleDescription().equalsIgnoreCase(AccessType.ADMIN.getAccessType())) {
                if (userDao.roleExistsDistrict(roleDao.findByRoleDescription(AccessType.ADMIN.getAccessType()).get(0), user.getDistrictId())) {
                    String authorityError = "Admin exists at this level and location";
                    responseMap.put(rowNum, authorityError);
                    return responseMap;
                }
            }
            if(user.getStateId() == null) {
                String authorityError = "missing property: state";
                responseMap.put(rowNum, authorityError);
                return responseMap;
            }
            if(user.getDistrictId() == null) {
                String authorityError = "missing property: district";
                responseMap.put(rowNum, authorityError);
                return responseMap;
            }
            if(currentUser.getStateId() != null && !user.getStateId().equals(currentUser.getStateId())) {
                String authorityError = "invalid property: state";
                responseMap.put(rowNum, authorityError);
                return responseMap;
            }
            if(currentUser.getDistrictId() != null && !user.getDistrictId().equals(currentUser.getDistrictId())) {
                String authorityError = "invalid property: state";
                responseMap.put(rowNum, authorityError);
                return responseMap;
            }
//            if(!user.getDistrictId().getStateOfDistrict().equals(user.getStateId())) {
//                String authorityError = "invalid property: district";
//                responseMap.put(rowNum, authorityError);
//                return responseMap;
//            }
            user.setBlockId(null);
        }
        else {
            if (user.getRoleId().getRoleDescription().equalsIgnoreCase(AccessType.ADMIN.getAccessType())) {
                String authorityError = "Cannot create admin here";
                responseMap.put(rowNum, authorityError);
                return responseMap;
            }
            if(user.getStateId() == null) {
                String authorityError = "missing property: state";
                responseMap.put(rowNum, authorityError);
                return responseMap;
            }
            if(currentUser.getStateId() != null && !user.getStateId().equals(currentUser.getStateId())) {
                String authorityError = "invalid property: state";
                responseMap.put(rowNum, authorityError);
                return responseMap;
            }
            if(user.getDistrictId() == null) {
                String authorityError = "missing property: district";
                responseMap.put(rowNum, authorityError);
                return responseMap;
            }
            if(currentUser.getDistrictId() != null && !user.getDistrictId().equals(currentUser.getDistrictId())) {
                String authorityError = "invalid property: state";
                responseMap.put(rowNum, authorityError);
                return responseMap;
            }
            if(user.getBlockId() == null) {
                String authorityError = "missing property: block";
                responseMap.put(rowNum, authorityError);
                return responseMap;
            }
//
//            if(!user.getDistrictId().getStateOfDistrict().equals(user.getStateId())) {
//                String authorityError = "invalid property: district";
//                responseMap.put(rowNum, authorityError);
//                return responseMap;
//            }
//            if(!user.getBlockId().getStateOfBlock().equals(user.getStateId())) {
//                String authorityError = "invalid property: block";
//                responseMap.put(rowNum, authorityError);
//                return responseMap;
//            }
        }

        entity.setStateId(user.getStateId());
        entity.setDistrictId(user.getDistrictId());
        entity.setBlockId(user.getBlockId());

        responseMap.put(rowNum, "user updated");
        return responseMap;
    }

    @Override
    public Map<Integer, String> updatePassword(PasswordDto passwordDto) {
        User currentUser = getCurrentUser();
        User entity = userDao.findByUserId(passwordDto.getUserId());

        Integer rowNum = 0;
        Map<Integer, String> responseMap = new HashMap<>();

        if(currentUser.getRoleId().getRoleDescription().equalsIgnoreCase(AccessType.USER.getAccessType())) {
            String authorityError = "No authority";
            responseMap.put(rowNum, authorityError);
            return responseMap;
        }

        if(entity == null) {
            responseMap.put(rowNum, "invalid user");
            return responseMap;
        }

        if(entity.getCreatedByUser().getUserId() == currentUser.getUserId()){
            String authorityError = "No authority";
            responseMap.put(rowNum, authorityError);
            return responseMap;
        }

        entity.setPassword(passwordEncoder.encode(passwordDto.getNewPassword()));

        responseMap.put(rowNum, "password changed");
        return responseMap;
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
