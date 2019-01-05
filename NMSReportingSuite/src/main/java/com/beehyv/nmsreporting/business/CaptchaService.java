package com.beehyv.nmsreporting.business;

import com.beehyv.nmsreporting.model.Captcha;

import java.util.List;

public interface CaptchaService {
    void saveCaptcha(Captcha captcha);
    void deleteCaptcha(Captcha captcha);
    List<Captcha> findBySession(String session);
}
