package com.beehyv.nmsreporting.dao.impl;

import com.beehyv.nmsreporting.dao.AbstractDao;
import com.beehyv.nmsreporting.dao.ContactUsDao;
import com.beehyv.nmsreporting.model.ContactUs;
import org.springframework.stereotype.Repository;

@Repository("ContactUsDao")
public class ContactUsDaoImpl extends AbstractDao<Integer, ContactUs> implements ContactUsDao {

    @Override
    public void saveContactUs(ContactUs contactUs) {
        persist(contactUs);
    }

    @Override
    public ContactUs findByContactUsId(Integer contactUsId) {
        return getByKey(contactUsId);
    }
}
