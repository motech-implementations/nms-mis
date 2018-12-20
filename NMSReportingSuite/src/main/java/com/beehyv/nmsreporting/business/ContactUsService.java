package com.beehyv.nmsreporting.business;

import com.beehyv.nmsreporting.model.ContactUs;

public interface ContactUsService {
    ContactUs findByContactUsId(Integer contactUsId);
    void saveContactUS(ContactUs contactUs);
}
