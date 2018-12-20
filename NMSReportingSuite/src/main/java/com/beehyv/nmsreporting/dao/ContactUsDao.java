package com.beehyv.nmsreporting.dao;


import com.beehyv.nmsreporting.model.ContactUs;

public interface ContactUsDao {

    void saveContactUs(ContactUs contactUs);
    ContactUs findByContactUsId(Integer contactUsId);
}
