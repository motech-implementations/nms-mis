package com.beehyv.nmsreporting.model;

import javax.persistence.*;
import javax.validation.constraints.Pattern;

@Entity
@Table(name="captcha")
public class Captcha {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private Integer captchaId;

    @Column(name="session")
    private String session;

    @Column(name="captchacode")
    private String captchaCode;

    public Captcha(String session, String captchaCode) {
        this.session = session;
        this.captchaCode = captchaCode;
    }

    public Captcha() {
    }

    public Integer getCaptchaId() {
        return captchaId;
    }

    public void setCaptchaId(Integer captchaId) {
        this.captchaId = captchaId;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public String getCaptchaCode() {
        return captchaCode;
    }

    public void setCaptchaCode(String captchaCode) {
        this.captchaCode = captchaCode;
    }
}
