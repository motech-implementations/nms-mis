package com.beehyv.nmsreporting.business;

import com.beehyv.nmsreporting.entity.EmailInfo;
import com.beehyv.nmsreporting.entity.ReportRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by beehyv on 25/5/17.
 */
public interface EmailService {

    public String sendMail(EmailInfo emailInfo);

    public String getBody(String reportName,String place, String monthAndYear,String name);

    public HashMap sendAllMails(String reportName);

}
