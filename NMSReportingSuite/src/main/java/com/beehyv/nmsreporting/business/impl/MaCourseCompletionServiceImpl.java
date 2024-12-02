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
        List<CourseCompletionDTO> list = maCourseCompletionDao.findBySentNotificationIsFalseAndHasPassed();
        LOGGER.info("initiating sending sms");
        int count = 0;
        for (CourseCompletionDTO courseCompletionDTO : list) {
            try {
                LOGGER.info("sms initialised for record no.: {}", count);
                count++;
                String language = courseCompletionDTO.getLanguageId();
                int languageId = (language != null) ? Integer.parseInt(language) : 2;
                // int languageId = Integer.parseInt(courseCompletionDTO.getLanguageId());
                String messageContent = retrieveAshaCourseCompletionMessage(languageId)
                        .replace("<CertificateLink>", retrieveAshaCertificateDownloadPageUrl());
                String template = smsService.buildCertificateSMS(courseCompletionDTO, messageContent);
                if (template != null) {
                    // Get the corresponding MACourseCompletion entity from your DB or service layer
                    MACourseCompletion maCourseCompletion = maCourseCompletionDao.getAshaById(courseCompletionDTO.getId());
                    if (maCourseCompletion != null) {
                        smsService.sendSms(maCourseCompletion, template); // Pass the entity instead of the DTO
                        LOGGER.info("sms sent ");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                Long flwid = courseCompletionDTO.getFlwId();
                LOGGER.info("cant send sms for flw Id : {}", flwid);
            }
        }
    }
}
