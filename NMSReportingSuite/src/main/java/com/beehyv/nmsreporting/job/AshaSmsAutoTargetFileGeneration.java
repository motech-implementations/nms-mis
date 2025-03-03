package com.beehyv.nmsreporting.job;

import com.beehyv.nmsreporting.business.AshaTargetFileService;
import com.beehyv.nmsreporting.business.SmsNotificationService;
import com.beehyv.nmsreporting.business.impl.TargetFileNotification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;

import static com.beehyv.nmsreporting.utils.Global.isAutoGenerate;

public class AshaSmsAutoTargetFileGeneration {

    private Logger LOGGER = LoggerFactory.getLogger(AshaSmsAutoTargetFileGeneration.class);

    @Autowired
    SmsNotificationService smsNotificationService;

    @Autowired
    AshaTargetFileService ashaTargetFileService;

    @Autowired
    private JmsTemplate jmsTemplate;

    public void processTargetFile() {

        if (isAutoGenerate()) {
            try {
                // You can publish a simple text message...
                jmsTemplate.convertAndSend("target-file-queue", "PROCESS_TARGET_FILE");

                LOGGER.info("Published target file processing event to 'target-file-queue'.");
            } catch (Exception e) {
                LOGGER.error("Failed to publish target file processing event: {}", e.getMessage(), e);
            }
        }
    }
}
