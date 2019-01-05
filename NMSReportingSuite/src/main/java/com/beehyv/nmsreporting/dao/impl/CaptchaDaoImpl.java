package com.beehyv.nmsreporting.dao.impl;

import com.beehyv.nmsreporting.dao.AbstractDao;
import com.beehyv.nmsreporting.dao.CaptchaDao;
import com.beehyv.nmsreporting.model.Captcha;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("captchaDao")
public class CaptchaDaoImpl extends AbstractDao<Integer, Captcha> implements CaptchaDao {
    public void saveCaptcha(Captcha captcha){
        persist(captcha);
    }

    public void deleteCaptcha(Captcha captcha){
        delete(captcha);
    }
    public List<Captcha> findBySession(String session){
        Criteria criteria=createEntityCriteria();
        criteria.add(Restrictions.eq("session", session));

        return criteria.list();
    }
}
