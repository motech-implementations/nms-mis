package com.beehyv.nmsreporting.job;

import com.beehyv.nmsreporting.business.MACourseCompletionService;
import com.beehyv.nmsreporting.business.impl.MaCourseCompletionServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


public class AshaCertificateLink {
    private static final Logger LOGGER = LoggerFactory.getLogger(AshaCertificateLink.class);

    @Autowired
    MACourseCompletionService maCourseCompletionService;

    public void executeSendSMSForCertificate(){
        maCourseCompletionService.dailyJobForMACourseCompletion();
        LOGGER.info("It working fine");
    }
}
