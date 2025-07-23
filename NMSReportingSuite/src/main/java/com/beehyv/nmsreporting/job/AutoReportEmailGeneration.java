package com.beehyv.nmsreporting.job;

import com.beehyv.nmsreporting.business.AdminService;
import com.beehyv.nmsreporting.business.EmailService;
import com.beehyv.nmsreporting.entity.ReportMessage;
import com.beehyv.nmsreporting.enums.ReportType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.transaction.annotation.Transactional;

import javax.jms.Destination;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Queue;

import static com.beehyv.nmsreporting.enums.ReportType.maCourse;
import static com.beehyv.nmsreporting.utils.Global.isAutoGenerate;

/**
 * Created by beehyv on 31/5/17.
 */

public class AutoReportEmailGeneration {
    @Autowired
    AdminService adminService;

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    EmailService emailService;


    private static final Logger LOGGER = LoggerFactory.getLogger(AutoReportEmailGeneration.class);



    public boolean executeInternal() {
        if (!isAutoGenerate()) {
            LOGGER.info("Auto-generation is disabled. Skipping report generation.");
            return false;
        }
        try {

            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MONTH, -1);
            calendar.set(Calendar.DATE, 1);
            calendar.set(Calendar.MILLISECOND, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            Date fromDate = calendar.getTime();
            calendar.add(Calendar.MONTH, 1);
            Date toDate = calendar.getTime();

            LOGGER.info("Starting report generation preprocessing for period from {} to {}", fromDate, toDate);



            adminService.createFiles(maCourse.getReportType());
            adminService.createFolders(ReportType.maAnonymous.getReportType());
            adminService.createFiles(ReportType.maInactive.getReportType());
            adminService.createFiles(ReportType.lowUsage.getReportType());
            adminService.createFiles(ReportType.selfDeactivated.getReportType());
            adminService.createFiles(ReportType.sixWeeks.getReportType());
            adminService.createFiles(ReportType.lowListenership.getReportType());
            adminService.createFiles(ReportType.motherRejected.getReportType());
            adminService.createFiles(ReportType.childRejected.getReportType());

            LOGGER.info("Preprocessing complete. Files and folders created.");


            publishReportEvent("Mother_Rejected", "mother-rejected", fromDate, toDate, false);
            publishReportEvent("Child_Rejected", "child-rejected", fromDate, toDate, false);
            publishReportEvent("MA_ANONYMOUS", "ma-anonymous-queue", fromDate, toDate, false);
            publishReportEvent("MA_Course_Completion", "ma-course-completion", fromDate, toDate, false);
            publishReportEvent("MA_Inactive", "ma-inactive-reports", fromDate, toDate, false);
            publishReportEvent("KilkariSixWeekNoAnswer", "kilkar-sixWeek-NoAnswer", fromDate, toDate, false);
            publishReportEvent("LowListenershipDeactivation", "low-listenership-deactivation", fromDate, toDate, false);
            publishReportEvent("KilkariSelfDeactivation", "kilkari-self-deactivation", fromDate, toDate, false);
            publishReportEvent("KilkariLowUsage", "kilkari-low-usage", fromDate, toDate, false);

            LOGGER.info("Report generation events published successfully.");
            return true;
        } catch (Exception e) {
            LOGGER.error("Error during report generation processing: ", e);
            return false;
        }
    }


    private void publishReportEvent(String reportType, String queueName, Date fromDate, Date toDate, boolean isWeekly) {
        ReportMessage reportMessage = new ReportMessage(reportType, fromDate, toDate, isWeekly);
        try {
            jmsTemplate.convertAndSend(queueName, reportMessage);
            LOGGER.info("Published report event: {} to queue: {}", reportType, queueName);
        } catch (Exception e) {
            LOGGER.error("Failed to publish report event {} to queue {}: ", reportType, queueName, e);
        }
    }



    public boolean executeWeekly() {
        if (!isAutoGenerate()) {
            LOGGER.info("Auto-generation is disabled. Skipping weekly report generation.");
            return false;
        }
        try {
            Calendar calendar = Calendar.getInstance();
            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
            calendar.add(Calendar.DAY_OF_WEEK, -(dayOfWeek - 1));
            Date weekStartDate = calendar.getTime();

            Date toDate = new Date();

            LOGGER.info("Weekly report generation: weekStartDate = {}, toDate = {}", weekStartDate, toDate);


            adminService.createFiles(ReportType.motherRejected.getReportType());
            adminService.createFiles(ReportType.childRejected.getReportType());

            LOGGER.info("Weekly report generation preprocessing complete. Files and folders created.");
            publishReportEvent("Mother_Rejected", "mother-rejected", weekStartDate, toDate, true);
            publishReportEvent("Child_Rejected", "child-rejected", weekStartDate, toDate, true);

            LOGGER.info("Weekly report generation events published successfully.");
            return true;
        } catch (Exception e) {
            LOGGER.error("Error during weekly report generation processing: ", e);
            return false;
        }
    }

    public HashMap sendFirstMail() {
        HashMap reports = emailService.sendAllMails(ReportType.maAnonymous);
        System.out.println("MA_Anonymous: ");
        System.out.println(reports.toString());
        return reports;
    }

    public HashMap sendSecondMail() {
        HashMap reports = emailService.sendAllMails(ReportType.maCourse);
        System.out.println("MA_Course: ");
        System.out.println(reports.toString());
        return reports;
    }

    public HashMap sendThirdMail() {
        HashMap reports = emailService.sendAllMails(ReportType.maInactive);
        System.out.println("MA_Inactive: ");
        System.out.println(reports.toString());
        return reports;
    }

    public HashMap sendFourthMail() {
        HashMap reports = emailService.sendAllMails(ReportType.sixWeeks);
        System.out.println("Kilkari_SixWeeks: ");
        System.out.println(reports.toString());
        return reports;
    }

    public HashMap sendFifthMail() {
        HashMap reports = emailService.sendAllMails(ReportType.selfDeactivated);
        System.out.println("Kilkari_SelfDeactivated: ");
        System.out.println(reports.toString());
        return reports;
    }

    public HashMap sendSixthMail() {
        HashMap reports = emailService.sendAllMails(ReportType.lowUsage);
        System.out.println("Kilkari_LowUsage: ");
        System.out.println(reports.toString());
        return reports;
    }

    public HashMap sendWeeklyFirstMail() {
        HashMap reports = emailService.sendAllMails(ReportType.childRejected);
        System.out.println("Child Rejected reports: ");
        System.out.println(reports.toString());
        return reports;
    }

    public HashMap sendWeeklySecondMail() {
        HashMap reports = emailService.sendAllMails(ReportType.motherRejected);
        System.out.println("Mother Rejected reports: ");
        System.out.println(reports.toString());
        return reports;
    }

    public HashMap sendWeeklyThirdMail() {
        HashMap reports = emailService.sendAllMails(ReportType.flwRejected);
        System.out.println("Flw Rejected reports: ");
        System.out.println(reports.toString());
        return reports;
    }

    public void test(){
//        System.out.println(new Date());
    }
}
