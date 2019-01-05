package com.beehyv.nmsreporting.business.impl;


import com.beehyv.nmsreporting.business.CaptchaService;
import com.beehyv.nmsreporting.dao.CaptchaDao;
import com.beehyv.nmsreporting.model.Captcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("captchaService")
@Transactional
public class CaptchaServiceImpl implements CaptchaService {

    @Autowired
    CaptchaDao captchaDao;
    @Override
    public void saveCaptcha(Captcha captcha){
        captchaDao.saveCaptcha(captcha);
    }

    @Override
    public void deleteCaptcha(Captcha captcha){
        captchaDao.deleteCaptcha(captcha);
    }
    @Override
    public List<Captcha> findBySession(String session){
        return captchaDao.findBySession(session);
    }
}
