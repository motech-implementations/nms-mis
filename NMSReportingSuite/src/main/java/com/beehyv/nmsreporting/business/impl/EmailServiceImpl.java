package com.beehyv.nmsreporting.business.impl;

import com.beehyv.nmsreporting.business.*;
import com.beehyv.nmsreporting.entity.EmailInfo;
import com.beehyv.nmsreporting.entity.ReportRequest;
import com.beehyv.nmsreporting.enums.AccessLevel;
import com.beehyv.nmsreporting.enums.ReportType;
import com.beehyv.nmsreporting.model.Circle;
import com.beehyv.nmsreporting.model.EmailTracker;
import com.beehyv.nmsreporting.model.State;
import com.beehyv.nmsreporting.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.ServletContext;
import javax.transaction.Transactional;
import java.util.*;

/**
 * Created by beehyv on 25/5/17.
 */
@Service("emailService")
@Transactional
public class EmailServiceImpl implements EmailService{

    @Autowired
    JavaMailSender mailSender;
    @Autowired
    ServletContext context;

    @Autowired
    UserService userService;
    @Autowired
    ReportService reportService;
    @Autowired
    LocationService locationService;

    @Autowired
    private EmailTrackerService emailTrackerService;
    @Override
    public String sendMail(EmailInfo mailInfo) {
        try {
            final JavaMailSenderImpl ms = (JavaMailSenderImpl) mailSender;
            Properties props = ms.getJavaMailProperties();
            final String username = ms.getUsername();
            final String password = ms.getPassword();
            //need authenticate to server
            Session session = Session.getInstance(props,
                    new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(username, password);
                        }
                    });
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(mailInfo.getFrom()));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mailInfo.getTo()));
            message.setSubject(mailInfo.getSubject(), "UTF-8");
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText(mailInfo.getBody());
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
            messageBodyPart = new MimeBodyPart();
            String filename = mailInfo.getRootPath();
            DataSource source = new FileDataSource(filename);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(mailInfo.getFileName());
            multipart.addBodyPart(messageBodyPart);
            message.setContent(multipart);
            Transport.send(message);
            return "success";
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return "failure";
        }
    }

    @Override
    public String getBody(String reportName,String place, String monthAndYear, String name) {
        String body = "";
        body+= "Dear "+name+",\n\n";
        /*body+= "This is the "+reportName+" Report of "+place+" for the month "+monthAndYear+ ".\n \n";
        body+= "Thank You.\n";
        body+= "NSP Support Team \n \n \n";
        body+= "P.S: This an auto-generated email. Please do not reply";*/
        if(reportName.equalsIgnoreCase(ReportType.maCourse.getReportName())) {
            body+="\tPlease find attached the list of ASHAs who have completed the Mobile Academy course.\n\n" +
                    "This is for your information.\n\n";
        }
        else if(reportName.equalsIgnoreCase(ReportType.maAnonymous.getReportName())) {
            body+="\tPlease find attached the list of anonymous callers to the Mobile Academy course from the telecom " +
                    "circle of " + place + ". We presume that these numbers are used by ASHAs working in your state but " +
                    "have not been registered in RCH Application. Please contact these numbers and if they belong " +
                    "to a registered ASHA in " + place + " then please tell them to either use their registered number " +
                    "to access the Mobile Academy course or register their correct numbers in the RCH Application so " +
                    "that they can access the Mobile Academy course.\n\n" +
                    "This is for your information.\n\n";
        }
        else if(reportName.equalsIgnoreCase(ReportType.maInactive.getReportName())) {
            body+="\tPlease find attached the list of ASHAs who have not yet started the Mobile Academy course.\n\n" +
                    "\tYou are requested to kindly instruct your field level workers and ask them to start accessing " +
                    "the Mobile Academy course and complete the course which has been designed to provide effective " +
                    "training for their operations.\n\n";
        } else if(reportName.equalsIgnoreCase(ReportType.lowUsage.getReportName())) {
            body+="\tPlease find attached the List of Beneficiaries who are listening to less than 25% of content in " +
                    "the last calendar month in the Kilkari system\n\n" +
                    "\tYou are requested to kindly instruct your field level workers to contact the beneficiaries " +
                    "personally and ask them to listen to the Kilkari content for the complete duration of 90 secs. " +
                    "These Kilkari messages contains valuable information on the best practices of health, nutrition " +
                    "and immunizations that they need to follow during their pregnancy period and child care.\n\n";
        } else if(reportName.equalsIgnoreCase(ReportType.selfDeactivated.getReportName())) {
            body+="\tPlease find attached the List of Beneficiaries who have deactivated themselves from the Kilkari " +
                    "system.\n\n" +
                    "\tYou are requested to kindly instruct your field level workers to contact the beneficiaries " +
                    "personally and understand why they have deactivated from the system. If the mobile number belongs " +
                    "to the correct beneficiary then they should be motivated to reactivate their Kilkari Subscription" +
                    " and listen to the Kilkari content for the complete duration of 90 secs. If the mobile number " +
                    "does not belong to the correct beneficiary then ask them to provide their mobile numbers through " +
                    "which they could receive the Kilkari messages and update those mobile numbers in the RCH " +
                    "application. These Kilkari messages contains valuable information on the best practices of health," +
                    " nutrition and immunizations that they need to follow during their pregnancy period and child " +
                    "care.\n\n";

        } else if(reportName.equalsIgnoreCase(ReportType.sixWeeks.getReportName())) {
            body+="\tPlease find attached the following files:\n" +
                    "\tList of Beneficiaries deactivated for not answering any Kilkari calls for 6 consecutive weeks\n\n" +
                    "You are requested to kindly instruct your field level workers to contact the beneficiaries " +
                    "personally and understand why they are not listening to the Kilkari calls. If the mobile number " +
                    "belongs to the correct beneficiary then they should be motivated to reactivate their Kilkari " +
                    "Subscription and listen to the Kilkari content for the complete duration of 90 secs." +
                    " If the mobile number does not belong to the correct beneficiary then ask them to provide their" +
                    " mobile numbers through which they could receive the Kilkari messages and update those mobile" +
                    " numbers in the RCH application. These Kilkari messages contains valuable information on the " +
                    "best practices of health, nutrition and immunizations that they need to follow during their " +
                    "pregnancy period and child care.\n\n";
        }
        body+="Regards\n";
        body+= "NSP Support Team \n \n \n";
        body+= "P.S: This an auto-generated email. Please do not reply";
        return body;
    }

    @Override
    public HashMap<String, String> sendAllMails(ReportType reportType) {
        List<User> users = userService.findAllActiveUsers();
        HashMap<String,String> errorSendingMail = new HashMap<>();
        for(User user: users){
            EmailInfo newMail = new EmailInfo();
            newMail.setFrom("nsp-reports@beehyv.com");
            newMail.setTo(user.getEmailId());
//                for(ReportType reportType: ReportType.values()){
            ReportRequest reportRequest = new ReportRequest();
            Calendar c = Calendar.getInstance();   // this takes current date

            /* if the report is rejection type, we need to find file name with previous sunday date(MM-DD-YY format)
            * else find file with previous month and year(MM-YY format) */

            /* previous sunday */
            if(reportType.getReportType().equals(ReportType.flwRejected.getReportType()) ||
                    reportType.getReportType().equals(ReportType.motherRejected.getReportType()) ||
                    reportType.getReportType().equals(ReportType.childRejected.getReportType())) {
                c.add( Calendar.DAY_OF_WEEK, -(c.get(Calendar.DAY_OF_WEEK)-1));
                reportRequest.setToDate(c.getTime());

            }else {
                /* previous month */
                c.add(Calendar.MONTH, -1);
                c.set(Calendar.DATE, 1);
                reportRequest.setToDate(c.getTime());
            }
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
                        place = getStatesNamesByCircle(circle);
                        newMail.setBody(this.getBody(reportType.getReportName(),place,reportService.getMonthName(c.getTime()),user.getFullName()));
                        newMail.setRootPath(pathName);
                        errorMessage = this.sendMail(newMail);
                        EmailTracker emailTracker=new EmailTracker();
                        emailTracker.setEmailSuccessful(true);
                        if (errorMessage.equalsIgnoreCase("failure")){
                            errorMessage = this.sendMail(newMail);
                        }
                        if (errorMessage.equalsIgnoreCase("failure")){
                            errorMessage = this.sendMail(newMail);
                        }
                        if (errorMessage.equalsIgnoreCase("failure")) {
                            errorSendingMail.put(user.getUsername(), fileName);
                            emailTracker.setEmailSuccessful(false);
                        }
                        emailTracker.setFileName(fileName);
                        emailTracker.setReportType(reportType.getReportName());
                        emailTracker.setTime(new Date());
                        emailTracker.setUserId(user.getUserId());
                        emailTrackerService.saveEmailDeatils(emailTracker);
                    }
                }//else if(user.getAccessLevel().equalsIgnoreCase(AccessLevel.NATIONAL.getAccessLevel())){
//                            reportRequest.setCircleId(0);
//                            pathName = reportService.getReportPathName(reportRequest).get(1);
//                            fileName = reportService.getReportPathName(reportRequest).get(0);
//                            newMail.setSubject(fileName);
//                            newMail.setFileName(fileName);
//                            place = "NATIONAL";
//                            newMail.setBody(emailService.getBody(reportName,place,reportService.getMonthName(c.getTime()),user.getFullName()));
//                            newMail.setRootPath(pathName);
//                            errorMessage = emailService.sendMail(newMail);
//                            if (errorMessage.equalsIgnoreCase("failure"))
//                                errorSendingMail.put(user.getUsername(),fileName);
//                        } else {
//                            reportRequest.setCircleId(locationService.findDistrictById(user.getDistrictId()).getCircleOfDistrict());
//                            pathName = reportService.getReportPathName(reportRequest).get(1);
//                            fileName = reportService.getReportPathName(reportRequest).get(0);
//                            newMail.setSubject(fileName);
//                            newMail.setFileName(fileName);
//                            Circle circle = reportService.getUserCircles(user).get(0);
//                            place = circle.getCircleName()+" Circle";
//                            newMail.setBody(emailService.getBody(reportName,place,reportService.getMonthName(c.getTime()),user.getFullName()));
//                            newMail.setRootPath(pathName);
//                            errorMessage = emailService.sendMail(newMail);
//                            if (errorMessage.equalsIgnoreCase("failure"))
//                                errorSendingMail.put(user.getUsername(),fileName);
//                        }
            } else {
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
                     if(reportType.getReportType().equals(ReportType.motherRejected.getReportType()) ||
                        reportType.getReportType().equals(ReportType.childRejected.getReportType())) {

                        reportRequest.setReportType(ReportType.childRejected.getReportType());
                        List<String> file1Details=reportService.getReportPathName(reportRequest);
                        reportRequest.setReportType(ReportType.motherRejected.getReportType());
                        String file1Name=file1Details.get(0);
                        String file1Path=file1Details.get(1);
                        List<String> file2Details=reportService.getReportPathName(reportRequest);
                         String file2Name=file2Details.get(0);
                         String file2Path=file2Details.get(1);
                        newMail.setFileName(file1Name);
                        newMail.setFileName2(file2Name);
                        newMail.setRootPath(file1Path);
                        newMail.setRootPathForFile2(file2Path);
                        newMail.setSubject("Rejected Mother and child Records for " +reportService.getDateMonthYear(c.getTime()));

                        //getBody should change

                        newMail.setBody(this.getBody(reportType.getReportName(), place, reportService.getDateMonthYear(c.getTime()), user.getFullName()));
                        // email for district users
                        if (user.getDistrictId() != null) {
                             //errorMessage=this.sendMailWithMultipleAttachments(newMail);
                             EmailTracker emailTracker = new EmailTracker();
                             emailTracker.setEmailSuccessful(true);
                             emailTracker.setFileName(file1Name);
                             emailTracker.setReportType(ReportType.motherRejected.getReportName());
                             emailTracker.setTime(new Date());
                             emailTracker.setUserId(user.getUserId());
                             EmailTracker emailTracker1 = new EmailTracker();
                             emailTracker1.setEmailSuccessful(true);
                             emailTracker1.setFileName(file2Name);
                             emailTracker1.setReportType(ReportType.childRejected.getReportName());
                             emailTracker1.setTime(new Date());
                             emailTracker1.setUserId(user.getUserId());
                             if (errorMessage.equalsIgnoreCase("failure")) {
                                 //errorMessage = this.sendMail(newMail);
                             }
                             if (errorMessage.equalsIgnoreCase("failure")) {
                                 //errorMessage = this.sendMail(newMail);
                             }
                             if (errorMessage.equalsIgnoreCase("failure")) {
                                 emailTracker.setEmailSuccessful(false);
                                 emailTracker1.setEmailSuccessful(false);
                             }
                             emailTrackerService.saveEmailDeatils(emailTracker);
                             emailTrackerService.saveEmailDeatils(emailTracker1);
                         }else if(user.getStateId()!=null){
                            sendMailWithStatistics(newMail);
                         }

                     }else {
                         List<String> fileDetails=reportService.getReportPathName(reportRequest);
                         pathName = fileDetails.get(1);
                         fileName = fileDetails.get(0);
                         newMail.setSubject(fileName);
                         newMail.setFileName(fileName);
                         newMail.setBody(this.getBody(reportType.getReportName(), place, reportService.getMonthName(c.getTime()), user.getFullName()));
                         newMail.setRootPath(pathName);
                         if (user.getDistrictId() != null) {
                             errorMessage = this.sendMail(newMail);
                             EmailTracker emailTracker = new EmailTracker();
                             emailTracker.setEmailSuccessful(true);
                             emailTracker.setFileName(fileName);
                             emailTracker.setReportType(reportType.getReportName());
                             emailTracker.setTime(new Date());
                             emailTracker.setUserId(user.getUserId());
                             if (errorMessage.equalsIgnoreCase("failure")) {
                                 errorMessage = this.sendMail(newMail);
                             }
                             if (errorMessage.equalsIgnoreCase("failure")) {
                                 errorMessage = this.sendMail(newMail);
                             }
                             if (errorMessage.equalsIgnoreCase("failure")) {
                                 emailTracker.setEmailSuccessful(false);
                             }
                             emailTrackerService.saveEmailDeatils(emailTracker);

                         } else {
                             errorMessage = "success";
                         }
                         if (errorMessage.equalsIgnoreCase("failure"))
                             errorSendingMail.put(user.getUsername(), fileName);
                     }
                }
//          }
        }
        return errorSendingMail;
    }

    private String sendMailWithStatistics(EmailInfo emailInfo) {
        try {
            final JavaMailSenderImpl ms = (JavaMailSenderImpl) mailSender;
            Properties props = ms.getJavaMailProperties();
            final String username = ms.getUsername();
            final String password = ms.getPassword();
            //need authenticate to server
            Session session = Session.getInstance(props,
                    new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(username, password);
                        }
                    });
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(emailInfo.getFrom()));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailInfo.getTo()));
            message.setSubject(emailInfo.getSubject(),"UTF-8");
            BodyPart messageBodyPart = new MimeBodyPart();
            String text=
                    "Hi , please find attachment <body><br><br><table width='100%' border='1' align='center'>"
                            + "<tr align='center'>"
                            + "<td><b>Product Name <b></td>"
                            + "<td><b>Count<b></td>"
                            + "</tr>";
            text=text+"<tr align='center'>"+"<td>" + "ok" + "</td>"
                    + "<td>" + "yes" + "</td>"+"</tr></body>";

            Multipart multipart = new MimeMultipart();

            messageBodyPart = new MimeBodyPart();
            message.setContent(text,"text/html; charset=utf-8");
            Transport.send(message);
            return "success";
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return "failure";
        }
    }

    private String sendMailWithMultipleAttachments(EmailInfo mailInfo) {
        try {
            final JavaMailSenderImpl ms = (JavaMailSenderImpl) mailSender;
            Properties props = ms.getJavaMailProperties();
            final String username = ms.getUsername();
            final String password = ms.getPassword();
            //need authenticate to server
            Session session = Session.getInstance(props,
                    new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(username, password);
                        }
                    });
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(mailInfo.getFrom()));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mailInfo.getTo()));
            message.setSubject(mailInfo.getSubject(),"UTF-8");
            /*BodyPart messageBodyPart = new MimeBodyPart();
            String text=
                    "Hi , please find attachment <body><br><br><table width='100%' border='1' align='center'>"
                            + "<tr align='center'>"
                            + "<td><b>Product Name <b></td>"
                            + "<td><b>Count<b></td>"
                            + "</tr>";
            text=text+"<tr align='center'>"+"<td>" + "ok" + "</td>"
                    + "<td>" + "yes" + "</td>"+"</tr></body>";

            Multipart multipart = new MimeMultipart();

            //multipart.addBodyPart(messageBodyPart);
            messageBodyPart = new MimeBodyPart();
            List<String> fileNames=new ArrayList<>();
            fileNames.add("/home/beehyv/Documents/Reports/Mother_Import_Rejects/Mother_Import_Rejects_NATIONAL_06_08_17.xlsx");
            fileNames.add("/home/beehyv/Documents/Reports/Child_Import_Rejects/Child_Import_Rejects_NATIONAL_06_08_17.xlsx");*/
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText(mailInfo.getBody());
            Multipart multipart = new MimeMultipart();
            //multipart.addBodyPart(messageBodyPart);
            List<String> filePaths=new ArrayList<>();
            filePaths.add(mailInfo.getRootPath());
            filePaths.add(mailInfo.getRootPathForFile2());
            addAttachment(multipart,filePaths.get(0), mailInfo.getFileName());
            addAttachment(multipart,filePaths.get(1), mailInfo.getFileName2());
            //message.setContent(mailInfo.getBody(),"text/html; charset=utf-8");
            message.setContent(multipart);
            Transport.send(message);
            return "success";
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return "failure";
        }
    }
    private static void addAttachment(Multipart multipart, String filePath, String fileName )
    {
        DataSource source = new FileDataSource(filePath);
        BodyPart messageBodyPart = new MimeBodyPart();

        try {
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(fileName);
            multipart.addBodyPart(messageBodyPart);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }

    private String getStatesNamesByCircle(Circle circle){
        List<State> states = locationService.getStatesOfCircle(circle);
        String placeNames = "";
        if(states.size()>1){
            placeNames += states.get(0).getStateName() + " and " + states.get(1).getStateName() + " regions";
        } else {
            placeNames += states.get(0).getStateName() + " region";
        }
        return placeNames;
    }
}
