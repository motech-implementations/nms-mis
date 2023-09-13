package com.beehyv.nmsreporting.model;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "login_tracker")
public class LoginTracker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer loginId;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "login_time")
    private Date loginTime;

    @Column(name = "login_successful")
    private boolean loginSuccessful;

    @Column(name = "unique_id")
    private String uniqueId;

    @Column(name = "is_active")
    private boolean isActive;

    public Integer getLoginId() {
        return loginId;
    }

    public void setLoginId(Integer loginId) {
        this.loginId = loginId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public boolean isLoginSuccessful() {
        return loginSuccessful;
    }

    public void setLoginSuccessful(boolean loginSuccessful) {
        this.loginSuccessful = loginSuccessful;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
