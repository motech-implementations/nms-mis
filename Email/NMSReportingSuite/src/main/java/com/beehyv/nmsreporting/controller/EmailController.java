package com.beehyv.nmsreporting.controller;

import com.beehyv.nmsreporting.business.EmailService;
import com.beehyv.nmsreporting.business.ReportService;
import com.beehyv.nmsreporting.entity.EmailInfo;
import com.beehyv.nmsreporting.enums.ReportType;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.Calendar;
import java.util.HashMap;

/**
 * Created by beehyv on 16/5/17.
 */
@Controller
@RequestMapping("/nms/mail")
public class EmailController {

    @Autowired
    ReportService reportService;
    @Autowired
    EmailService emailService;

//    @RequestMapping(value = "/sendAll/{reportEnum}", method = RequestMethod.GET)
//    public @ResponseBody HashMap sendAllMails(@PathVariable String reportEnum){
//        ReportType reportType = reportService.getReportTypeByName(reportEnum);
//        return emailService.sendAllMails(reportType);
//    }
//
//    @RequestMapping(value = "/send", method = RequestMethod.POST)
//    public @ResponseBody String send(@RequestBody EmailInfo mailInfo){
//        EmailInfo newMail = new EmailInfo();
//        newMail.setFrom(mailInfo.getFrom());
//        newMail.setTo(mailInfo.getTo());
//        Calendar c = Calendar.getInstance();   // this takes current date
//        c.add(Calendar.MONTH, -1);
//        c.set(Calendar.DATE, 1);
//        String fileName = mailInfo.getFileName();
//        String pathName = System.getProperty("user.home") + File.separator;
//        newMail.setSubject(mailInfo.getSubject());
//        newMail.setFileName(fileName);
//        newMail.setBody(mailInfo.getBody());
//        newMail.setRootPath(pathName + fileName);
//        return emailService.sendMail(newMail);
//    }

    @RequestMapping(value = "/sendPassword/{encoded}", method = RequestMethod.GET)
    public @ResponseBody String sendPassword(@PathVariable String encoded) throws Exception{
        byte[] decoded = Base64.decodeBase64(encoded);
        String token = new String(decoded, "UTF-8");
        String[] tokenItems = token.split("\\|\\|");

        String email = tokenItems[0];
        String password = tokenItems[1];
        EmailInfo newMail = new EmailInfo();
        newMail.setFrom("nsp-reports@beehyv.com");
        newMail.setTo(email);
        Calendar c = Calendar.getInstance();   // this takes current date
        c.add(Calendar.MONTH, -1);
        c.set(Calendar.DATE, 1);
        newMail.setSubject("Reset Password for MIS Portal");
        newMail.setBody("Dear user,<br/><br/><p>As per your request, your password has been reset to: <b>" +
                        password +
                        "</b></p><br/><p>Once you login to the MIS portal with the above password, the system will direct you to change the default password as it is mandatory.</p><br/>" +
                        "<p>Thanks,</p>" +
                        "<p>NSP Support</p>");
       return emailService.sendMailPassword(newMail);
    }
}
