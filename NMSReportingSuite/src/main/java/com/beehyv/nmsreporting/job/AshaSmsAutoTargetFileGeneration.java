package com.beehyv.nmsreporting.job;

import com.beehyv.nmsreporting.business.AshaTargetFileService;
import com.beehyv.nmsreporting.business.SmsNotificationService;
import com.beehyv.nmsreporting.business.impl.TargetFileNotification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class AshaSmsAutoTargetFileGeneration {

    private Logger LOGGER = LoggerFactory.getLogger(AshaSmsAutoTargetFileGeneration.class);

    @Autowired
    SmsNotificationService smsNotificationService;

    @Autowired
    AshaTargetFileService ashaTargetFileService;

    public void processTargetFile() {

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
