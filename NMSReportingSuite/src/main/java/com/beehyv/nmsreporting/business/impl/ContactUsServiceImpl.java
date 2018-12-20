package com.beehyv.nmsreporting.business.impl;

import com.beehyv.nmsreporting.business.ContactUsService;
import com.beehyv.nmsreporting.dao.ContactUsDao;
import com.beehyv.nmsreporting.model.ContactUs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service("ContactUsService")
@Transactional
public class ContactUsServiceImpl  implements ContactUsService {
    @Autowired
    private ContactUsDao contactUsDao;

    @Override
    public  void saveContactUS(ContactUs contactUs){
        contactUsDao.saveContactUs(contactUs);
    }
    @Override
    public ContactUs findByContactUsId(Integer contactUsId){
        return contactUsDao.findByContactUsId(contactUsId);
    }

}
