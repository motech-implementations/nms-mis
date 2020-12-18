package com.beehyv.nmsreporting.controller;

import com.beehyv.nmsreporting.business.EmailService;
import com.beehyv.nmsreporting.business.ReportService;
import com.beehyv.nmsreporting.entity.EmailInfo;
import com.beehyv.nmsreporting.enums.ReportType;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.InputStreamReader;
import java.net.URL;
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
    public @ResponseBody
    String sendPassword(@PathVariable String encoded) throws Exception {
        byte[] decoded = Base64.decodeBase64(encoded);
        String token = new String(decoded, "UTF-8");
        String[] tokenItems = token.split("\\|\\|");
    //changed the 'from' emailId from beehyv domain address to govt domain address
        String email = tokenItems[0];
        String password = tokenItems[1];
        String type = tokenItems[2];
        String subject = "Password for MIS Portal";
        String line = "As per your request, your password has been reset to:";
        if(type.equals("new")) line = "The password for your newly created MIS account is:";
        else if(type.equals("admin")) line = "The password for your MIS account has been reset by the admin to:";
        String message = "\"Dear user,<br/><br/><p>"+line+"<b>" +
               password +
              "</b></p><br/><p>Once you login to the MIS portal with the above password, the system will direct you to change the default password as it is mandatory.</p><br/>" +
             "<p>Thanks,</p>" +"<p>NSP Support</p>\"";
        String command = "/opt/sendEmail/sendEmail -f motechnagios@ggn.rcil.gov.in -s email.ggn.rcil.gov.in -t "+
                email+" -o \"message-content-type=html\" -m "+message+" -u "+subject;
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command("bash","-c",command);
        processBuilder.inheritIO();
        try {
            Process process = processBuilder.start();
            int exitVal = process.waitFor();
            if(exitVal==0){
                return "success";
            }else{
                return "failure";
            }
        }catch (InterruptedException e){return "failure";}
    }

    @RequestMapping(value = "/sendCaptcha/{captchaResponse}", method = RequestMethod.GET)
    public @ResponseBody
    String sendCaptcha(@PathVariable String captchaResponse) throws Exception {
        return emailService.sendCaptcha(captchaResponse);
    }
}
