package com.beehyv.nmsreporting.business;

import com.beehyv.nmsreporting.entity.EmailInfo;
import com.beehyv.nmsreporting.entity.EmailTest;
import com.beehyv.nmsreporting.enums.ReportType;

import java.util.HashMap;

/**
 * Created by beehyv on 25/5/17.
 */
public interface EmailService {

    public String sendMail(EmailInfo emailInfo);

    public String getBody(String reportName,String place, String monthAndYear,String name);

    public HashMap sendAllMails(ReportType reportType);


    public String sendMailTest(EmailTest emailInfo);

}
