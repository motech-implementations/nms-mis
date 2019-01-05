package com.beehyv.nmsreporting.entity;

/**
 * Created by beehyv on 8/8/17.
 */
public class ForgotPasswordDto {

    String username;
    String phoneNumber;
    String newPassword;
    String confirmPassword;
    private String cipherTextHexNew;
    private String saltHexNew;
    private String captcha;

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getCipherTextHexNew() {
        return cipherTextHexNew;
    }

    public void setCipherTextHexNew(String cipherTextHexNew) {
        this.cipherTextHexNew = cipherTextHexNew;
    }

    public String getSaltHexNew() {
        return saltHexNew;
    }

    public void setSaltHexNew(String saltHexNew) {
        this.saltHexNew = saltHexNew;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;



    }
}
