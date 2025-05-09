package com.beehyv.nmsreporting.business;

import com.beehyv.nmsreporting.model.LoginTracker;

import java.util.Date;
import java.util.List;

public interface LoginTrackerService {

    void saveLoginDetails(LoginTracker loginTracker);

    Date getLastLoginTime(Integer userId);

    List<LoginTracker> getActiveLoginUsers(Integer userId);

    void  updateLoginDetails(LoginTracker loginTracker);

    LoginTracker getLoginTrackerByUniqueId(String uniqueId);
}
