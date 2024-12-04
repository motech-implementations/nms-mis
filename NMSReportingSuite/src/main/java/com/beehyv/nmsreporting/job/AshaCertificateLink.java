package com.beehyv.nmsreporting.job;

import com.beehyv.nmsreporting.business.MACourseCompletionService;
import com.beehyv.nmsreporting.business.impl.MaCourseCompletionServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import static com.beehyv.nmsreporting.utils.Global.isAutoGenerate;


public class AshaCertificateLink {
    private static final Logger LOGGER = LoggerFactory.getLogger(AshaCertificateLink.class);

    @Autowired
    MACourseCompletionService maCourseCompletionService;

    public boolean executeSendSMSForCertificate(){
        if (isAutoGenerate()){
            try {
                maCourseCompletionService.dailyJobForMACourseCompletion();
                LOGGER.info("Course SMS Scheduler has been successfully Completed");
            } catch (Exception e) {
                LOGGER.error("Error occurred while processing MA course completion scheduled SMS: ", e);
            }
            return true;
        }
        return false;
    }
}
