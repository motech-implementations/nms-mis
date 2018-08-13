package com.beehyv.nmsreporting.controller;

import com.beehyv.nmsreporting.business.EmailService;
import com.beehyv.nmsreporting.business.LocationService;
import com.beehyv.nmsreporting.business.ReportService;
import com.beehyv.nmsreporting.business.UserService;
import com.beehyv.nmsreporting.entity.EmailBody;
import com.beehyv.nmsreporting.entity.EmailInfo;
import com.beehyv.nmsreporting.entity.EmailTest;
import com.beehyv.nmsreporting.enums.ReportType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.util.Calendar;
import java.util.HashMap;

import static com.beehyv.nmsreporting.utils.Global.retrieveUiAddress;

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

    private static final String feedbackBody = "Received your feedback. Tahnking you sending feedback";

    private static final String feedbackSubject = "Feedback Received";

    @RequestMapping(value = "/sendAll/{reportEnum}", method = RequestMethod.GET)
    public @ResponseBody HashMap sendAllMails(@PathVariable String reportEnum){
        ReportType reportType = reportService.getReportTypeByName(reportEnum);
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
        newMail.setSubject(mailInfo.getSubject());
        newMail.setFileName(fileName);
        newMail.setBody(mailInfo.getBody());
        newMail.setRootPath(pathName+fileName);
        return emailService.sendMail(newMail);
    }

    @RequestMapping(value = "/sendFeedback", method = RequestMethod.POST)
        public @ResponseBody String sendFeedback(@RequestBody EmailBody mailInfo){
        EmailTest newMail = new EmailTest();
        newMail.setFrom("nsp-reports@beehyv.com");
        newMail.setTo(mailInfo.getEmail());
        Calendar c = Calendar.getInstance();   // this takes current date
        c.add(Calendar.MONTH, -1);
        c.set(Calendar.DATE, 1);
        newMail.setSubject("Feedback Received");
        newMail.setBody(emailService.getBody("FeedBack",mailInfo.getName() ));
        return emailService.sendMailTest(newMail);
   }

    @RequestMapping(value = "/sendEmailForContactUs", method = RequestMethod.POST)
    public @ResponseBody String sendEmailForContactUs(@RequestBody EmailBody mailInfo){
        EmailTest newMail = new EmailTest();
        newMail.setFrom("nsp-reports@beehyv.com");
        newMail.setTo(mailInfo.getEmail());
        Calendar c = Calendar.getInstance();   // this takes current date
        c.add(Calendar.MONTH, -1);
        c.set(Calendar.DATE, 1);
        newMail.setSubject("Message Received");
        newMail.setBody(emailService.getBody("ContactUs",mailInfo.getName() ));
        return emailService.sendMailTest(newMail);
    }

//    @RequestMapping(value = "/sendFeedback1", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
//    public ModelAndView send(Model model, @ModelAttribute EmailTest mailInfo){
//        EmailTest newMail = new EmailTest();
//        newMail.setFrom("nsp-reports@beehyv.com");
//        newMail.setTo(mailInfo.getTo());
//        Calendar c = Calendar.getInstance();   // this takes current date
//        c.add(Calendar.MONTH, -1);
//        c.set(Calendar.DATE, 1);
//        newMail.setSubject(mailInfo.getSubject());
//        newMail.setBody(mailInfo.getBody());
//        emailService.sendMailTest(newMail);
//        return new ModelAndView("redirect:"+ retrieveUiAddress() +"feedbackResponse");
//    }
//
//
//    @RequestMapping(value = "/sendEmailForContactUs1", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
//    public ModelAndView sendEmailForContactUs(Model model, @ModelAttribute EmailTest mailInfo){
//        EmailTest newMail = new EmailTest();
//        newMail.setFrom("nsp-reports@beehyv.com");
//        newMail.setTo(mailInfo.getTo());
//        Calendar c = Calendar.getInstance();   // this takes current date
//        c.add(Calendar.MONTH, -1);
//        c.set(Calendar.DATE, 1);
//        newMail.setSubject("Subject for Contact");
//        newMail.setBody(mailInfo.getBody());
//        emailService.sendMailTest(newMail);
//        return new ModelAndView("redirect:"+ retrieveUiAddress() +"contactUsResponse");
//    }

    /*@RequestMapping(value = "/test", method =RequestMethod.GET )
    @ResponseBody
        public String dummyTest() {
            //return emailService.dummyEmail();
        }*/


}
