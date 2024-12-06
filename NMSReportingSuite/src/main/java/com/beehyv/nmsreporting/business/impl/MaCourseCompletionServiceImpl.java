package com.beehyv.nmsreporting.business.impl;

import com.beehyv.nmsreporting.business.MACourseCompletionService;
import com.beehyv.nmsreporting.business.SmsService;
import com.beehyv.nmsreporting.dao.MACourseCompletionDao;
import com.beehyv.nmsreporting.entity.CourseCompletionDTO;
import com.beehyv.nmsreporting.model.MACourseCompletion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.beehyv.nmsreporting.utils.Global.retrieveAshaCertificateDownloadPageUrl;
import static com.beehyv.nmsreporting.utils.Global.retrieveAshaCourseCompletionMessage;

@Service
public class MaCourseCompletionServiceImpl implements MACourseCompletionService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MaCourseCompletionServiceImpl.class);

    @Autowired
    private MACourseCompletionDao maCourseCompletionDao;

    @Autowired
    private SmsService smsService;

    public void dailyJobForMACourseCompletion() {
        // Define the batch size
        int batchSize = 10000;
        List<CourseCompletionDTO> pendingNotifications;

        do {
            // Fetch the next batch of records
            pendingNotifications = maCourseCompletionDao.findBySentNotificationIsFalseAndHasPassed( batchSize);
            LOGGER.info("Initiating SMS sending for {} records", pendingNotifications.size());

            // Process the current batch
            int count = 0;
            for (CourseCompletionDTO courseCompletionDTO : pendingNotifications) {
                try {
                    LOGGER.info("Processing record no.: {}", ++count);
                    processCourseCompletionNotification(courseCompletionDTO);
                } catch (Exception e) {
                    LOGGER.error("Failed to send SMS for FLW ID: {}, Error: {}", courseCompletionDTO.getFlwId(), e.getMessage(), e);
                }
            }

        } while (!pendingNotifications.isEmpty());
    }

    private void processCourseCompletionNotification(CourseCompletionDTO courseCompletionDTO) throws Exception {
        String messageContent = buildCompletionMessage(courseCompletionDTO);
        String template = smsService.buildCertificateSMS(courseCompletionDTO, messageContent);

        if (template != null) {
            MACourseCompletion maCourseCompletion = maCourseCompletionDao.getAshaById(courseCompletionDTO.getId());
            if (maCourseCompletion != null) {
                smsService.sendSms(maCourseCompletion, template);
                LOGGER.info("SMS sent successfully for FLW ID: {}", courseCompletionDTO.getFlwId());
            } else {
                LOGGER.warn("No MACourseCompletion found for FLW ID: {}", courseCompletionDTO.getFlwId());
            }
        } else {
            LOGGER.warn("SMS template is null for FLW ID: {}", courseCompletionDTO.getFlwId());
        }
    }

    private String buildCompletionMessage(CourseCompletionDTO courseCompletionDTO) {
        long languageId = courseCompletionDTO.getLanguageId();
        String messageTemplate = retrieveAshaCourseCompletionMessage(languageId);
        return messageTemplate.replace("<CertificateLink>", retrieveAshaCertificateDownloadPageUrl());
    }

}
