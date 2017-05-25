package com.beehyv.nmsreporting.business;

import com.beehyv.nmsreporting.entity.EmailInfo;
import com.beehyv.nmsreporting.entity.ReportRequest;

/**
 * Created by beehyv on 25/5/17.
 */
public interface EmailService {

    public String sendMail(EmailInfo emailInfo);

}
