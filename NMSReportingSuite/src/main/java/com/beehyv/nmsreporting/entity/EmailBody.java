package com.beehyv.nmsreporting.entity;

/**
 * Created by beehyv on 13/8/18.
 */
public class EmailBody {

    private String body;

    private String email;

    private String name;

    private String phoneNo;

    private String subject;

    private String cipherTextHex;

    private String saltHex;

    private String captcha;
    private String captchaResponse;

    public String getCaptchaResponse() {
        return captchaResponse;
    }

    public void setCaptchaResponse(String captchaResponse) {
        this.captchaResponse = captchaResponse;
    }

    public String getCipherTextHex() {
        return cipherTextHex;
    }

    public void setCipherTextHex(String cipherTextHex) {
        this.cipherTextHex = cipherTextHex;
    }

    public String getSaltHex() {
        return saltHex;
    }

    public void setSaltHex(String saltHex) {
        this.saltHex = saltHex;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }




    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
