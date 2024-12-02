package com.beehyv.nmsreporting.business;

import com.beehyv.nmsreporting.entity.CourseCompletionDTO;
import com.beehyv.nmsreporting.model.MACourseCompletion;
import com.beehyv.nmsreporting.model.MACourseFirstCompletion;

public interface SmsService {
     String sendSms(MACourseCompletion maCourseCompletion, String template);

     String buildCertificateSMS(CourseCompletionDTO courseCompletionDTO, String message_content);

     String buildOTPSMS(MACourseFirstCompletion maCourseFirstCompletion, String message_content);
}
