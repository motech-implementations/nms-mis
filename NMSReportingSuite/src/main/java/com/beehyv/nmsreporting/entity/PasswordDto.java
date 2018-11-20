package com.beehyv.nmsreporting.entity;

/**
 * Created by beehyv on 25/5/17.
 */
public class PasswordDto {

    private Integer userId;

    private String oldPassword;

    private String newPassword;

    private String cipherTextHexOld;

    private String saltHexOld;

    private String cipherTextHexNew;

    private String saltHexNew;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getCipherTextHexOld() {
        return cipherTextHexOld;
    }

    public void setCipherTextHexOld(String cipherTextHexOld) {
        this.cipherTextHexOld = cipherTextHexOld;
    }

    public String getSaltHexOld() {
        return saltHexOld;
    }

    public void setSaltHexOld(String saltHexOld) {
        this.saltHexOld = saltHexOld;
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
}
