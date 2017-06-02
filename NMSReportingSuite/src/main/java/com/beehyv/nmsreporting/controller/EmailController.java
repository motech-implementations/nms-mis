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
    public @ResponseBody HashMap sendAllMails(@PathVariable String reportName){
        List<User> users = userService.findAllActiveUsers();
        HashMap<String,String> errorSendingMail = new HashMap<>();
        for(User user: users){
            EmailInfo newMail = new EmailInfo();
            newMail.setFrom("Beehyv");
            newMail.setTo(user.getEmailId());
//                for(ReportType reportType: ReportType.values()){
                    ReportType reportType = reportService.getReportTypeByName(reportName);
                    ReportRequest reportRequest = new ReportRequest();
                    Calendar c = Calendar.getInstance();   // this takes current date
                    c.add(Calendar.MONTH, -1);
                    c.set(Calendar.DATE, 1);
                    reportRequest.setToDate(c.getTime());
                    reportRequest.setReportType(reportType.getReportType());
                    String pathName = "",fileName = "",errorMessage = "",place = "";
                    if(reportType.getReportType().equalsIgnoreCase(ReportType.maAnonymous.getReportType())){
                        if(user.getAccessLevel().equalsIgnoreCase(AccessLevel.STATE.getAccessLevel())){
                            List<Circle> stateCircleList = reportService.getUserCircles(user);
                            for(Circle circle: stateCircleList){
                                reportRequest.setCircleId(circle.getCircleId());
                                pathName = reportService.getReportPathName(reportRequest).get(1);
                                fileName = reportService.getReportPathName(reportRequest).get(0);
                                newMail.setSubject(fileName);
                                newMail.setFileName(fileName);
                                place = circle.getCircleName()+" Circle";
                                newMail.setBody(emailService.getBody(reportName,place,reportService.getMonthYear(c.getTime()),user.getFullName()));
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
                            place = "NATIONAL";
                            newMail.setBody(emailService.getBody(reportName,place,reportService.getMonthYear(c.getTime()),user.getFullName()));
                            newMail.setRootPath(pathName);
                            errorMessage = emailService.sendMail(newMail);
                            if (errorMessage.equalsIgnoreCase("failure"))
                                errorSendingMail.put(user.getEmailId(),fileName);
                        } else {
                            reportRequest.setCircleId(locationService.findDistrictById(user.getDistrictId()).getCircleOfDistrict());
                            pathName = reportService.getReportPathName(reportRequest).get(1);
                            fileName = reportService.getReportPathName(reportRequest).get(0);
                            newMail.setSubject(fileName);
                            newMail.setFileName(fileName);
                            Circle circle = reportService.getUserCircles(user).get(0);
                            place = circle.getCircleName()+" Circle";
                            newMail.setBody(emailService.getBody(reportName,place,reportService.getMonthYear(c.getTime()),user.getFullName()));
                            newMail.setRootPath(pathName);
                            errorMessage = emailService.sendMail(newMail);
                            if (errorMessage.equalsIgnoreCase("failure"))
                                errorSendingMail.put(user.getEmailId(),fileName);
                        }
                    }else {
                        place = "NATIONAL";
                        if (user.getStateId() == null)
                            reportRequest.setStateId(0);
                        else {
                            reportRequest.setStateId(user.getStateId());
                            place = locationService.findStateById(user.getStateId()).getStateName()+" State";
                        }
                        if (user.getDistrictId() == null)
                            reportRequest.setDistrictId(0);
                        else {
                            reportRequest.setDistrictId(user.getDistrictId());
                            place = locationService.findDistrictById(user.getDistrictId()).getDistrictName()+" District";
                        }
                        if (user.getBlockId() == null)
                            reportRequest.setBlockId(0);
                        else {
                            reportRequest.setBlockId(user.getBlockId());
                            place = locationService.findBlockById(user.getBlockId()).getBlockName()+" Block";
                        }
                        pathName = reportService.getReportPathName(reportRequest).get(1);
                        fileName = reportService.getReportPathName(reportRequest).get(0);
                        newMail.setSubject(fileName);
                        newMail.setFileName(fileName);
                        newMail.setBody(emailService.getBody(reportName,place,reportService.getMonthYear(c.getTime()),user.getFullName()));
                        newMail.setRootPath(pathName);
                        errorMessage = emailService.sendMail(newMail);
                        if (errorMessage.equalsIgnoreCase("failure"))
                            errorSendingMail.put(user.getEmailId(),fileName);
                    }
//            }
        }
        return errorSendingMail;
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
        newMail.setBody(emailService.getBody("Mobile Academy CumulativeInactiveUsers","ODISHA",reportService.getMonthYear(c.getTime()),"OdishaNHM"));
        newMail.setRootPath(pathName+fileName);
        return emailService.sendMail(newMail);
    }



    @RequestMapping(value = "/dummy", method = RequestMethod.GET)
    public @ResponseBody EmailInfo dummy(){
        return new EmailInfo();
    }
}
