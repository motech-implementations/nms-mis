package com.beehyv.nmsreporting.dao;


import com.beehyv.nmsreporting.model.LoginTracker;

import java.util.Date;
import java.util.List;

public interface LoginTrackerDao {

    public void saveLoginDetails(LoginTracker loginTracker);

    public LoginTracker findLoginDetailsById(Integer loginId);

    public void deleteLoginDetails(LoginTracker loginTracker);

    public List<LoginTracker> getAllLoginDetailsForUser(String userId);

    public List<LoginTracker> getAllLoginDetailsByDate(Date fromDate, Date toDate);

    public Date getLastLoginTime(Integer userId);

    public List<LoginTracker> getActiveLoginUsers(Integer userId);

    public void updateLoginDetails(LoginTracker loginTracker);

    public LoginTracker getLoginTrackerByUniqueId(String uniqueId);
}
