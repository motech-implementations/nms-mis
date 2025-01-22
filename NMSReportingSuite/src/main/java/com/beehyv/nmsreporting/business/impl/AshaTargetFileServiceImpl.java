package com.beehyv.nmsreporting.business.impl;

import com.beehyv.nmsreporting.business.AshaTargetFileService;
import com.beehyv.nmsreporting.business.SmsNotificationService;
import com.beehyv.nmsreporting.dao.AshaBeneficiaryMessageDao;
import com.beehyv.nmsreporting.dao.MACourseAttemptDao;
import com.beehyv.nmsreporting.dao.TargetFileAuditDao;
import com.beehyv.nmsreporting.enums.FileType;
import com.beehyv.nmsreporting.model.AshaBeneficiaryMessage;
import com.beehyv.nmsreporting.model.SmsFileAuditLog;
import org.apache.commons.codec.digest.DigestUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.beehyv.nmsreporting.utils.Global.getPropertyValue;
import static com.beehyv.nmsreporting.utils.Global.retrieveTargetfileLocation;

@Service("smsService")
@Transactional
public class AshaTargetFileServiceImpl implements AshaTargetFileService {

    @Autowired
    AshaBeneficiaryMessageDao ashaBeneficiaryMessageDao;

    @Autowired
    TargetFileAuditDao targetFileAuditDao;

    @Autowired
    SmsNotificationService smsNotificationService;

    @Autowired
    MACourseAttemptDao maCourseAttemptDao;

    private  Logger LOGGER = LoggerFactory.getLogger(AshaTargetFileServiceImpl.class);
    private static final String LOCAL_SMS_FILE_DIR = retrieveTargetfileLocation();
    private static final String SMS_CONTENT_REGISTERED_PREFIX = "sms.default.template.registered";
    private static final String SMS_CONTENT_LOWLISTENERSHIP_PREFIX = "sms.default.template.low_listenership";
    private static final String SMS_CONTENT_ASHACERTIFICATE_PREFIX = "sms.default.asha_certificate_download";
    private static final String SMS_CONTENT_ASHACERTIFICATE_OTP_PREFIX = "sms.default.asha_certificate_otp";
    private static final String registeredTemplateId = getPropertyValue("sms.default.templateId.registered");
    private static final String SMS_CONTENT_CERTIFICATETemplateId_PREFIX = "sms.default.templateId.certificate";
    private static final String SMS_CONTENT_CERTIFICATETemplateId_OTP_PREFIX = getPropertyValue("sms.default.templateId.otp");
    private static final String lowListenershipTemplateId = getPropertyValue("sms.default.templateId.low_listenership");
    private static final String certificateurl = getPropertyValue("sms.asha.certificate.download.url");


    @Override
    public void processTargetFile() {

        try {
            TargetFileNotification targetFileNotification = generateTargetFile();
            if (targetFileNotification != null) {
                smsNotificationService.sendNotificationRequest(targetFileNotification);
            } else {
                LOGGER.error("Failed to generate target file.");
            }
        } catch (Exception e) {
            LOGGER.error("Error processing target file: {}", e.getMessage(), e);
        }

    }

    @Override
    public TargetFileNotification generateTargetFile() {
        String templateId;
        int recordsWritten = 0;
        Date todayDate = new Date();
        LOGGER.info("inside target file generation method");
        List<Date> failureDates = getFailureDatesForLastNDays(todayDate);

        if (failureDates == null || failureDates.isEmpty()) {
            logFailure(null, "Failed to fetch failure dates or no failure dates found.");
            LOGGER.info("No failure dates to process.");
            return null;
        }

        LOGGER.info("these are failure dates: {},{}",
                failureDates.isEmpty() ? "No failure dates" : failureDates.get(0),
                failureDates.size() > 1 ? failureDates.get(1) : "N/A");

        List<AshaBeneficiaryMessage> beneficiaries = ashaBeneficiaryMessageDao.fetchMessagesByDateRange(failureDates);
        if (beneficiaries == null || beneficiaries.isEmpty()) {
            logFailure(null, "No records found to process.");
            LOGGER.info("No records found to process.");
            return null;
        }

        String targetFileName = targetFileName(formatDate(todayDate));

        File localSmsFile = localAshaSmsDir();
        File targetFile = new File(localSmsFile, targetFileName);

        try {
            FileOutputStream fos = new FileOutputStream(targetFile);
            OutputStreamWriter writer = new OutputStreamWriter(fos);

            writer.write("MSISDN,State Name,Product Name,Template ID,Message Text\n");

            List<Long> flwIds = new ArrayList<>();
            // Write data
            for (AshaBeneficiaryMessage beneficiary : beneficiaries) {
                try {
                    String msisdn = beneficiary.getAshaContactNumber();
                    String stateName = beneficiary.getStateName();
                    String productName = "";
                    String messageText = "";


                    String languageCode = beneficiary.getLanguage_code();
                    switch (beneficiary.getType()) {
                        case REGISTRATION:
                            templateId = registeredTemplateId;
                            productName = "ASHA engagement with Kilkari Beneficiary";
                            messageText = formatMessage(beneficiary, SMS_CONTENT_REGISTERED_PREFIX, languageCode);
                            break;

                        case LOWLISTENERSHIP:
                            templateId = lowListenershipTemplateId;
                            productName = "ASHA engagement with Kilkari Beneficiary";
                            messageText = formatMessage(beneficiary, SMS_CONTENT_LOWLISTENERSHIP_PREFIX, languageCode);
                            break;

                        case ASHACERTIFICATE:
                            productName = "MA Certificate Download through SMS";
                            messageText = formatCertificateMessage(beneficiary, SMS_CONTENT_ASHACERTIFICATE_PREFIX, languageCode);
//                            String otpMessage = formatCertificateOTPMessage(beneficiary, SMS_CONTENT_ASHACERTIFICATE_OTP_PREFIX, languageCode);
                             templateId = formatTemplateId(languageCode);
                            writeMessage(writer, msisdn, stateName, productName, templateId, messageText);
//                            writeMessage(writer, msisdn, stateName, productName, SMS_CONTENT_CERTIFICATETemplateId_OTP_PREFIX, otpMessage);
                            flwIds.add(beneficiary.getAshaId());
                            recordsWritten ++;
                            continue;

                        default:
                            LOGGER.warn("Unknown message type: {}", beneficiary.getType());
                            continue;
                    }

                    String row = String.format("%s,%s,%s,%s,%s\n", msisdn, stateName, productName, templateId, messageText);
                    writer.write(row);
                    recordsWritten++;
                } catch (Exception e) {
                    LOGGER.error("Error processing message: {}", e.getMessage());
                    logFailure(targetFileName, e.getMessage());
                }
            }
            writer.close();
            fos.close();

            String checksum = checksum(targetFile);
            LOGGER.info("this is check sum: {}",checksum);

            try {
                if(!flwIds.isEmpty()) {
                    maCourseAttemptDao.bulkUpdateMACourseFirstCompletion(flwIds);
                }
            } catch (Exception e) {
                LOGGER.error("Error processing message: {}", e.getMessage());
                e.printStackTrace();
            }
            logSuccess(targetFileName, checksum, recordsWritten);
            return new TargetFileNotification(targetFileName, checksum, recordsWritten);
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            logFailure(targetFileName, e.getMessage());
        }

        return null;

    }

    private void writeMessage(OutputStreamWriter writer, String msisdn, String stateName, String productName, String templateId, String messageText) throws IOException {
        String row = String.format("%s,%s,%s,%s,%s\n", msisdn, stateName, productName, templateId, messageText);
        writer.write(row);
    }

    private String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return sdf.format(date);
    }

    private String getOtp(String flwId) {
        return flwId != null && flwId.length() >= 3 ? flwId.substring(flwId.length()-6) : "000";
    }

    private String targetFileName(String timestamp) {
        return String.format("asha_sms_target_file_%s.csv", timestamp);
    }


    private File localAshaSmsDir() {
        return new File(LOCAL_SMS_FILE_DIR);
    }

    private String formatMessage(AshaBeneficiaryMessage record, String baseTemplateKey,String languageCode) {

        String languageSpecificKey = String.format("%s.%s",baseTemplateKey,languageCode);
        String template = getPropertyValue(languageSpecificKey);

        if (template == null) {
            LOGGER.warn("Template not found for key: {}, falling back to default template.", languageSpecificKey);
            template = getPropertyValue(baseTemplateKey);
        }
        String formattedMessage = template.replace("{#var1#}", record.getBeneficiaryName())
                .replace("{#var2#}", record.getBeneficiaryRchId())
                .replace("{#var3#}", record.getBeneficiaryContactNumber());

        return "\"" + formattedMessage + "\"";
    }

    private String formatCertificateOTPMessage(AshaBeneficiaryMessage record,String baseTemplateKey, String languageCode) {
        String languageSpecificKey = String.format("%s.%s",baseTemplateKey, languageCode);

        String template = getPropertyValue(languageSpecificKey);
        if (template == null) {
            LOGGER.warn("Template not found for key: {}, falling back to default template.", languageSpecificKey);
            template = getPropertyValue(baseTemplateKey);
        }
        LOGGER.info("Using ASHA Certificate template for key {}: {}", languageSpecificKey, template);

        return "\"" + template.replace("{#var1#}", getOtp(String.valueOf(record.getAshaId()))) + "\"";
    }

    private String formatTemplateId(String languageCode) {
        String languageSpecificKey = String.format("%s.%s", AshaTargetFileServiceImpl.SMS_CONTENT_CERTIFICATETemplateId_PREFIX, languageCode);
        LOGGER.info("Fetching template for key: {}", languageSpecificKey);

        String template = getPropertyValue(languageSpecificKey);
        if (template == null) {
            LOGGER.warn("Template not found for key: {}, falling back to default template.", languageSpecificKey);
            template = getPropertyValue(AshaTargetFileServiceImpl.SMS_CONTENT_CERTIFICATETemplateId_PREFIX);
        }

        if (template == null) {
            LOGGER.error("No template found for both specific and default keys.");
            throw new IllegalStateException("Template ID not found for language code: " + languageCode);
        }

        LOGGER.info("Using ASHA Certificate template for key {}: {}", languageSpecificKey, template);
        return template;
    }



    private String formatCertificateMessage(AshaBeneficiaryMessage record,String baseTemplateKey, String languageCode) {
        String languageSpecificKey = String.format("%s.%s",baseTemplateKey, languageCode);

        String template = getPropertyValue(languageSpecificKey);
        if (template == null) {
            LOGGER.warn("Template not found for key: {}, falling back to default template.", languageSpecificKey);
            template = getPropertyValue(baseTemplateKey);
        }
        LOGGER.info("Using ASHA Certificate template for key {}: {}", languageSpecificKey, template);

        return "\"" + template.replace("{#var1#}",record.getAshaContactNumber()).replace("<CertificateLink>",certificateurl) + "\"";
    }

    public static String checksum(File file) throws IOException {
        return DigestUtils.md5Hex(new FileInputStream(file));
    }

    private void logSuccess(String fileName, String checksum, int recordCount) {
        SmsFileAuditLog smsFileAuditLog = new SmsFileAuditLog(FileType.SMS_TARGET_FILE, fileName, null, checksum, recordCount, true,new Date(),new Date());
        targetFileAuditDao.saveSmsFileduitLog(smsFileAuditLog);
        LOGGER.info("Successfully processed file: {}", fileName);
    }

    private void logFailure(String fileName, String errorMessage) {
        SmsFileAuditLog smsFileAuditLog = new SmsFileAuditLog(FileType.SMS_TARGET_FILE, fileName, errorMessage, null, 0, false,new Date(),new Date());
        targetFileAuditDao.saveSmsFileduitLog(smsFileAuditLog);
    }

    private List<Date> getFailureDatesForLastNDays(Date today) {
        LOGGER.error("inside getfailure date method");
        List<Date> failureDates = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        for (int i = 1; i < 3; i++) {
            Date dateToCheck = DateTime.now().minusDays(i).toDate();

            boolean hasFailure = false;
            try {
                LOGGER.error("calling hasfailure");
                hasFailure = targetFileAuditDao.hasFailureOnDate(dateToCheck);
                LOGGER.error("this is response from hasfailure method: {},{}",hasFailure,dateToCheck);
            } catch (Exception e) {
                LOGGER.error("Error checking failure on date: " + dateToCheck, e);
            }

            if (hasFailure) {
                try {
                    String formattedDate = dateFormat.format(dateToCheck);
                    failureDates.add(dateFormat.parse(formattedDate));
                } catch (ParseException e) {
                    LOGGER.error("Error formatting date: " + dateToCheck, e);
                }
            } else {
                break;
            }
        }

        try {
            String formattedToday = dateFormat.format(today);
            failureDates.add(dateFormat.parse(formattedToday));
        } catch (ParseException e) {
            LOGGER.error("Error formatting today's date: " + today, e);
        }

        return failureDates;
    }

}
