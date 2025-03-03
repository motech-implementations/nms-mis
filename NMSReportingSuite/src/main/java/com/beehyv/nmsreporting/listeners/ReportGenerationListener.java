package com.beehyv.nmsreporting.listeners;

import com.beehyv.nmsreporting.business.AshaTargetFileService;
import com.beehyv.nmsreporting.business.SmsNotificationService;
import com.beehyv.nmsreporting.business.impl.TargetFileNotification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.beehyv.nmsreporting.entity.ReportMessage;
import com.beehyv.nmsreporting.business.AdminService;

@Component
public class ReportGenerationListener {

    @Autowired
    private AdminService adminService;

    @Autowired
    SmsNotificationService smsNotificationService;

    @Autowired
    AshaTargetFileService ashaTargetFileService;

    private Logger LOGGER = LoggerFactory.getLogger(ReportGenerationListener.class);

    @JmsListener(destination = "ma-anonymous-queue")
    public void handleMaAnonymousReport(ReportMessage message) {
        try {
            LOGGER.info("Processing MA_ANONYMOUS report: from {} to {}", message.getFromDate(), message.getToDate());
            adminService.getCircleWiseAnonymousFiles(message.getFromDate(), message.getToDate());
            LOGGER.info("MA_ANONYMOUS report generated successfully.");
        } catch (Exception e) {
            LOGGER.error("Error processing MA_ANONYMOUS report: ", e);
        }
    }

    @JmsListener(destination = "ma-course-completion")
    public void handleMaCourseCompletionReport(ReportMessage message) {
        try {
            LOGGER.info("Processing MA_Course_Completion report: toDate {}", message.getToDate());
            adminService.getCumulativeCourseCompletionFiles(message.getToDate());
            LOGGER.info("MA_Course_Completion report generated successfully.");
        } catch (Exception e) {
            LOGGER.error("Error processing MA_Course_Completion report: ", e);
        }
    }

    @JmsListener(destination = "mother-rejected")
    public void handleMotherRejectedReport(ReportMessage message) {
        try {
            LOGGER.info("Processing Mother_Rejected report.");
            adminService.createMotherImportRejectedFiles(1, message.isWeekly());
            LOGGER.info("Mother_Rejected report generated successfully.");
        } catch (Exception e) {
            LOGGER.error("Error processing Mother_Rejected report: ", e);
        }
    }

    @JmsListener(destination = "child-rejected")
    public void handleChildRejectedReport(ReportMessage message) {
        try {
            LOGGER.info("Processing Child_Rejected report.");
            adminService.createChildImportRejectedFiles(1, message.isWeekly());
            LOGGER.info("Child_Rejected report generated successfully.");
        } catch (Exception e) {
            LOGGER.error("Error processing Child_Rejected report: ", e);
        }
    }

    @JmsListener(destination = "ma-inactive-reports")
    public void handleMaInactiveReport(ReportMessage message) {
        try {
            LOGGER.info("Processing MA_Inactive report: toDate {}", message.getToDate());
            adminService.getCumulativeInactiveFiles(message.getToDate());
            LOGGER.info("MA_Inactive report generated successfully.");
        } catch (Exception e) {
            LOGGER.error("Error processing MA_Inactive report: ", e);
        }
    }

    @JmsListener(destination = "kilkar-sixWeek-NoAnswer")
    public void handleKilkariSixWeekNoAnswerReport(ReportMessage message) {
        try {
            LOGGER.info("Processing KilkariSixWeekNoAnswer report: from {} to {}", message.getFromDate(), message.getToDate());
            adminService.porcessKilkariSixWeekNoAnswerFiles(message.getFromDate(), message.getToDate());
            LOGGER.info("KilkariSixWeekNoAnswer report generated successfully.");
        } catch (Exception e) {
            LOGGER.error("Error processing KilkariSixWeekNoAnswer report: ", e);
        }
    }

    @JmsListener(destination = "low-listenership-deactivation")
    public void handleLowListenershipDeactivationReport(ReportMessage message) {
        try {
            LOGGER.info("Processing LowListenershipDeactivation report: from {} to {}", message.getFromDate(), message.getToDate());
            adminService.processKilkariLowListenershipDeactivationFiles(message.getFromDate(), message.getToDate());
            LOGGER.info("LowListenershipDeactivation report generated successfully.");
        } catch (Exception e) {
            LOGGER.error("Error processing LowListenershipDeactivation report: ", e);
        }
    }

    @JmsListener(destination = "kilkari-self-deactivation")
    public void handleKilkariSelfDeactivationReport(ReportMessage message) {
        try {
            LOGGER.info("Processing KilkariSelfDeactivation report: from {} to {}", message.getFromDate(), message.getToDate());
            adminService.getKilkariSelfDeactivationFiles(message.getFromDate(), message.getToDate());
            LOGGER.info("KilkariSelfDeactivation report generated successfully.");
        } catch (Exception e) {
            LOGGER.error("Error processing KilkariSelfDeactivation report: ", e);
        }
    }

    @JmsListener(destination = "kilkari-low-usage")
    public void handleKilkariLowUsageReport(ReportMessage message) {
        try {
            LOGGER.info("Processing KilkariLowUsage report: from {} to {}", message.getFromDate(), message.getToDate());
            adminService.processKilkariLowUsageFiles(message.getFromDate(), message.getToDate());
            LOGGER.info("KilkariLowUsage report generated successfully.");
        } catch (Exception e) {
            LOGGER.error("Error processing KilkariLowUsage report: ", e);
        }
    }


    @JmsListener(destination = "target-file-queue")
    public void handleTargetFileMessage(String message) {
        LOGGER.info("Received target file processing event: {}", message);
        try {
            TargetFileNotification targetFileNotification = ashaTargetFileService.generateTargetFile();
            if (targetFileNotification != null) {
                smsNotificationService.sendNotificationRequest(targetFileNotification);
            } else {
                LOGGER.error("Failed to generate target file.");
            }
        } catch (Exception e) {
            LOGGER.error("Error processing target file: {}", e.getMessage(), e);
        }
    }
}
