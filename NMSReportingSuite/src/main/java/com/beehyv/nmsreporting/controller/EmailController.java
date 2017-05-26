package com.beehyv.nmsreporting.controller;

import javax.servlet.ServletContext;

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
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    @Autowired
    LocationService locationService;

//    @RequestMapping(value = "/input", method = RequestMethod.GET)
//    public @ResponseBody String showForm(ModelMap model) {
//        model.addAttribute("mail", new EmailInfo());
//        return "AttachEmailInput";
//    }

    @RequestMapping(value = "/sendAll", method = RequestMethod.GET)
    public @ResponseBody HashMap sendAllMails(){
        List<User> users = userService.findAllActiveUsers();
        HashMap<String,String> errorSendingMail = new HashMap<>();
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
                    List<Circle> stateCircleList = new ArrayList<>();
                    if(reportType.getReportType().equalsIgnoreCase(ReportType.maAnonymous.getReportType())){
                        if(user.getAccessLevel().equalsIgnoreCase(AccessLevel.STATE.getAccessLevel())){
                            stateCircleList = reportService.getUserCircles(user);
                            for(Circle circle: stateCircleList){
                                reportRequest.setCircleId(circle.getCircleId());
                                pathName = reportService.getReportPathName(reportRequest).get(1);
                                fileName = reportService.getReportPathName(reportRequest).get(0);
                                newMail.setSubject(fileName);
                                newMail.setFileName(fileName);
                                newMail.setRootPath(pathName);
                                errorMessage = emailService.sendMail(newMail);
                                if (errorMessage.equalsIgnoreCase("failure"))
                                    errorSendingMail.put(user.getEmailId(),fileName);
                            }
                        }else if(user.getAccessLevel().equalsIgnoreCase(AccessLevel.NATIONAL.getAccessLevel())){
                            reportRequest.setCircleId(0);
                            pathName = reportService.getReportPathName(reportRequest).get(1);
                            fileName = reportService.getReportPathName(reportRequest).get(0);
                            newMail.setSubject(fileName);
                            newMail.setFileName(fileName);
                            newMail.setRootPath(pathName);
                            errorMessage = emailService.sendMail(newMail);
                            if (errorMessage.equalsIgnoreCase("failure"))
                                errorSendingMail.put(user.getEmailId(),fileName);
                        } else {
                            reportRequest.setCircleId(user.getDistrictId().getCircleOfDistrict().getCircleId());
                            pathName = reportService.getReportPathName(reportRequest).get(1);
                            fileName = reportService.getReportPathName(reportRequest).get(0);
                            newMail.setSubject(fileName);
                            newMail.setFileName(fileName);
                            newMail.setRootPath(pathName);
                            errorMessage = emailService.sendMail(newMail);
                            if (errorMessage.equalsIgnoreCase("failure"))
                                errorSendingMail.put(user.getEmailId(),fileName);
                        }
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
                            errorSendingMail.put(user.getEmailId(),fileName);
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
