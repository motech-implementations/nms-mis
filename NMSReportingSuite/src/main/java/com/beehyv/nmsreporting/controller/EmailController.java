package com.beehyv.nmsreporting.controller;

import com.beehyv.nmsreporting.business.EmailService;
import com.beehyv.nmsreporting.business.LocationService;
import com.beehyv.nmsreporting.business.ReportService;
import com.beehyv.nmsreporting.business.UserService;
import com.beehyv.nmsreporting.entity.EmailInfo;
import com.beehyv.nmsreporting.entity.ReportRequest;
import com.beehyv.nmsreporting.enums.AccessLevel;
import com.beehyv.nmsreporting.enums.ReportType;
import com.beehyv.nmsreporting.model.Circle;
import com.beehyv.nmsreporting.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.*;

/**
 * Created by beehyv on 16/5/17.
 */
@Controller
@RequestMapping("/nms/mail")
public class EmailController {

    @Autowired
    UserService userService;
    @Autowired
    ReportService reportService;
    @Autowired
    EmailService emailService;
    @Autowired
    LocationService locationService;

//    @RequestMapping(value = "/input", method = RequestMethod.GET)
//    public @ResponseBody String showForm(ModelMap model) {
//        model.addAttribute("mail", new EmailInfo());
//        return "AttachEmailInput";
//    }

    @RequestMapping(value = "/sendAll/{reportType}", method = RequestMethod.GET)
    public @ResponseBody HashMap sendAllMails(@PathVariable String reportType){
        return emailService.sendAllMails(reportType);
    }

    @RequestMapping(value = "/send", method = RequestMethod.POST)
    public @ResponseBody String send(@RequestBody EmailInfo mailInfo){
        EmailInfo newMail = new EmailInfo();
        newMail.setFrom(mailInfo.getFrom());
        newMail.setTo(mailInfo.getTo());
        Calendar c = Calendar.getInstance();   // this takes current date
        c.add(Calendar.MONTH, -1);
        c.set(Calendar.DATE, 1);
        String fileName = mailInfo.getFileName();
        String pathName = System.getProperty("user.home") + File.separator;
        newMail.setSubject("Mobile Academy "+fileName);
        newMail.setFileName(fileName);
        newMail.setBody(emailService.getBody("Mobile Academy CumulativeInactiveUsers","ODISHA",reportService.getMonthName(c.getTime()),"OdishaNHM"));
        newMail.setRootPath(pathName+fileName);
        return emailService.sendMail(newMail);
    }



    @RequestMapping(value = "/dummy", method = RequestMethod.GET)
    public @ResponseBody EmailInfo dummy(){
        return new EmailInfo();
    }
}
