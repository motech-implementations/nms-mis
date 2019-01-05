package com.beehyv.nmsreporting.dao;

import com.beehyv.nmsreporting.model.Captcha;

import java.util.List;

public interface CaptchaDao {
   public void saveCaptcha(Captcha captcha);
    public void deleteCaptcha(Captcha captcha);
    public List<Captcha> findBySession(String session);
}
