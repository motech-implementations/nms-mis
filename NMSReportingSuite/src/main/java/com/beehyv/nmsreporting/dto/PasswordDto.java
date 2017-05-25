package com.beehyv.nmsreporting.dto;

/**
 * Created by beehyv on 25/5/17.
 */
public class PasswordDto {

    private int userId;

    private String newPassword;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
