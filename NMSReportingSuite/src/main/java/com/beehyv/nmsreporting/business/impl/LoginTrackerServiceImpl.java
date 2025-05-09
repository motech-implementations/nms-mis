package com.beehyv.nmsreporting.business.impl;

import com.beehyv.nmsreporting.business.LoginTrackerService;
import com.beehyv.nmsreporting.dao.LoginTrackerDao;
import com.beehyv.nmsreporting.model.LoginTracker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service("loginTrackerService")
@Transactional
public class LoginTrackerServiceImpl implements LoginTrackerService{

    @Autowired
    LoginTrackerDao loginTrackerDao;

    @Override
    public void saveLoginDetails(LoginTracker loginTracker) {
        loginTrackerDao.saveLoginDetails(loginTracker);
    }

    @Override
    public Date getLastLoginTime(Integer userId) {
        return loginTrackerDao.getLastLoginTime(userId);
    }

    @Override
    public List<LoginTracker> getActiveLoginUsers(Integer userId){
        return loginTrackerDao.getActiveLoginUsers(userId);
    }

    @Override
    public void updateLoginDetails(LoginTracker loginTracker){
        loginTrackerDao.updateLoginDetails(loginTracker);
    }

    public LoginTracker getLoginTrackerByUniqueId(String uniqueId){
        return loginTrackerDao.getLoginTrackerByUniqueId(uniqueId);
    }
}
