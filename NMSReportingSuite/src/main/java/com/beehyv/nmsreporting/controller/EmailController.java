package com.beehyv.nmsreporting.controller;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.ServletContext;
import javax.xml.ws.RequestWrapper;

import com.beehyv.nmsreporting.business.EmailService;
import com.beehyv.nmsreporting.business.ReportService;
import com.beehyv.nmsreporting.business.UserService;
import com.beehyv.nmsreporting.dao.UserDao;
import com.beehyv.nmsreporting.entity.EmailInfo;
import com.beehyv.nmsreporting.entity.ReportRequest;
import com.beehyv.nmsreporting.enums.AccessLevel;
import com.beehyv.nmsreporting.enums.ReportType;
import com.beehyv.nmsreporting.model.StateCircle;
import com.beehyv.nmsreporting.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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
    ServletContext context;
    @Autowired
    JavaMailSender mailSender;
    @Autowired
    UserService userService;
    @Autowired
    ReportService reportService;
    @Autowired
    EmailService emailService;

//    @RequestMapping(value = "/input", method = RequestMethod.GET)
//    public @ResponseBody String showForm(ModelMap model) {
//        model.addAttribute("mail", new EmailInfo());
//        return "AttachEmailInput";
//    }

    @RequestMapping(value = "/sendAll", method = RequestMethod.GET)
    public @ResponseBody List<String> sendAllMails(){
        List<User> users = userService.findAllActiveUsers();
        List<String> errorSendingMail = new ArrayList<>();
        for(User user: users){
            EmailInfo newMail = new EmailInfo();
            newMail.setFrom("Beehyv");
            newMail.setTo(user.getEmailId());
                for(ReportType reportType: ReportType.values()){
                    ReportRequest reportRequest = new ReportRequest();
                    Calendar c = Calendar.getInstance();   // this takes current date
                    c.set(Calendar.DAY_OF_MONTH, 1);
                    reportRequest.setToDate(c.getTime());
                    reportRequest.setReportType(reportType.getReportType());
                    String pathName = "",fileName = "",errorMessage = "";
                    List<StateCircle> stateCircleList = new ArrayList<>();
                    if(reportType.getReportType().equalsIgnoreCase(ReportType.maAnonymous.getReportType())){
//
//                        if (user.getStateId() == null) {
//                            reportRequest.setStateId(0);
//                            reportRequest.setCircleId(0);
//                        }
//                        else
//                            reportRequest.setStateId(user.getStateId().getStateId());
//                        if (user.getDistrictId() == null) {
//                            reportRequest.setDistrictId(0);
//                        }
//                        else
//                            reportRequest.setDistrictId(user.getDistrictId().getDistrictId());
//                        if (user.getBlockId() == null)
//                            reportRequest.setBlockId(0);
//                        else
//                            reportRequest.setBlockId(user.getBlockId().getBlockId());
//
////                        stateCircleList = reportService.getCirclesByState(user.getStateId().getStateId());
//
                    }else {
                        if (user.getStateId() == null)
                            reportRequest.setStateId(0);
                        else
                            reportRequest.setStateId(user.getStateId().getStateId());
                        if (user.getDistrictId() == null)
                            reportRequest.setDistrictId(0);
                        else
                            reportRequest.setDistrictId(user.getDistrictId().getDistrictId());
                        if (user.getBlockId() == null)
                            reportRequest.setBlockId(0);
                        else
                            reportRequest.setBlockId(user.getBlockId().getBlockId());
                        pathName = reportService.getReportPathName(reportRequest).get(1);
                        fileName = reportService.getReportPathName(reportRequest).get(0);

                        newMail.setSubject(fileName);
                        newMail.setFileName(fileName);
                        newMail.setRootPath(pathName);
                        errorMessage = emailService.sendMail(newMail);
                        if (errorMessage.equalsIgnoreCase("failure"))
                            errorSendingMail.add(user.getFullName() + "_" + fileName);
                    }
            }
        }
        return errorSendingMail;
    }



//    @RequestMapping(value = "/dummy", method = RequestMethod.GET)
//    public @ResponseBody EmailInfo dummy(){
//        return new EmailInfo();
//    }
}
