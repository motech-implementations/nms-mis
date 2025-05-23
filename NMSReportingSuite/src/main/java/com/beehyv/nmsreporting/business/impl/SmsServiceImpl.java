package com.beehyv.nmsreporting.business.impl;

import com.beehyv.nmsreporting.business.SmsService;
import com.beehyv.nmsreporting.dao.FrontLineWorkersDao;
import com.beehyv.nmsreporting.dao.MACourseCompletionDao;
import com.beehyv.nmsreporting.entity.CourseCompletionDTO;
import com.beehyv.nmsreporting.model.MACourseCompletion;
import com.beehyv.nmsreporting.model.MACourseFirstCompletion;
import org.apache.commons.io.FileUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import static com.beehyv.nmsreporting.utils.Global.*;


@Service
@PropertySource("classpath:sms.properties")
public class SmsServiceImpl implements SmsService {
    @Autowired
    private MACourseCompletionDao maCourseCompletionDao;

    @Autowired
    private FrontLineWorkersDao frontLineWorkersDao;

    public void setMACourseCompletionDao(MACourseCompletionDao maCourseCompletionDao) {
        this.maCourseCompletionDao = maCourseCompletionDao;
    }


    private String sms_otp_template_id = getProperty("sms.otp.templateId.default");
    private String sms_entity_id = getProperty("sms.entityId.default");
    private String sms_telemarketer_id = getProperty("sms.telemarketerId.default");
    private String senderId = getProperty("senderid");
    private String authKey = getProperty("sms.authentication.key");
    private String endpoint = getProperty("endpoint");


    private static final Logger LOGGER = LoggerFactory.getLogger(SmsServiceImpl.class);

    public String sendSms(MACourseCompletion maCourseCompletion, String template) {
        LOGGER.info("template {}", template);

        String resolvedEndpoint = endpoint.replace("senderId", senderId);
        RequestConfig config = RequestConfig.custom()
                .setConnectTimeout(10000)
                .setSocketTimeout(10000)
                .build();

        // Create HTTP POST request
        HttpPost httpRequest = new HttpPost(resolvedEndpoint);
        httpRequest.setHeader("Content-type", "application/json");
        httpRequest.setHeader("Key", authKey);

        try {
            // Set request entity
            StringEntity entity = new StringEntity(template, StandardCharsets.UTF_8);
            entity.setContentType("application/json");
            httpRequest.setEntity(entity);
            httpRequest.setHeader("Content-Type", "application/json");

            LOGGER.info("Entity set for request");
            LOGGER.info("Request URL: {}", httpRequest.getURI().toString());
            LOGGER.info("Request entity: {}", EntityUtils.toString(httpRequest.getEntity()));
        } catch (IOException e) {
            LOGGER.error("Unable to build SMS request", e);
            return "Unable to send";
        }

        // Set up connection pooling and use it to create an HttpClient
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(20);
        cm.setDefaultMaxPerRoute(2);

        try (CloseableHttpClient client = HttpClientBuilder.create()
                .setDefaultRequestConfig(config)
                .setConnectionManager(cm)
                .build()) {
            HttpResponse response = client.execute(httpRequest);
            LOGGER.info("Request executed, status: {}", response.getStatusLine());
            LOGGER.info("Request executed successfully");

            // Process response
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_CREATED || response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                // No need to change the modification date as it is not serving any purpose and also it is effecting the ETL process
                LOGGER.info("SMS sent successfully");
            maCourseCompletion.setScheduleMessageSent(true);
            maCourseCompletionDao.updateMACourseCompletion(maCourseCompletion);
            }
            return "success";
        } catch (IOException e) {
            LOGGER.info(e.getMessage());
            LOGGER.error("Unable to send SMS : Error during HTTP request execution");
            return "Unable to send SMS";
        }
    }



    public String buildCertificateSMS(CourseCompletionDTO courseCompletionDTO, String messageContent) {
        LOGGER.info("Building SMS for FLW ID: {}", courseCompletionDTO.getFlwId());

        long phoneNo;
        try {
            phoneNo = courseCompletionDTO.getMobileNumber();
        } catch (NullPointerException e) {
            LOGGER.info("Phone number does not exist for FLW ID: {}", courseCompletionDTO.getFlwId());
            return null;
        } catch (Exception e) {
            LOGGER.error("Unexpected error while fetching phone number for FLW ID: {}", courseCompletionDTO.getFlwId());
            return null;
        }

        LOGGER.info("Phone number: {}", phoneNo);
        LOGGER.info("Message content: {}", messageContent);

        String template;
        try {
            template = FileUtils.readFileToString(
                    new File("../webapps/NMSReportingSuite/WEB-INF/classes/smsTemplate.json"),
                    StandardCharsets.UTF_8
            );
        } catch (IOException e) {
            LOGGER.error("Error reading SMS template file");
            return null;
        }

        String callbackEndpoint = retrieveAshaSMSCallBackEndPoint("certificateLink");
        if (callbackEndpoint == null) {
            callbackEndpoint = "0";
        } else {
            callbackEndpoint = "1";
        }

        // Replace placeholders in the template
        try {
            String templateId = retrieveAshaCourseCompletionTemplateId(courseCompletionDTO.getLanguageId());
            template = template
                    .replace("<phoneNumber>", String.valueOf(phoneNo))
                    .replace("<senderId>", senderId)
                    .replace("<messageContent>", messageContent)
                    .replace("<notificationUrl>", callbackEndpoint)
                    .replace("<smsTemplateId>", templateId)
                    .replace("<smsEntityId>", sms_entity_id)
                    .replace("<smsTelemarketerId>", sms_telemarketer_id)
                    .replace("<correlationId>", DateTime.now().toString())
                    .replace("<messageType>", "4");
        } catch (Exception e) {
            LOGGER.error("Error replacing placeholders in SMS template");
            return null;
        }

        return template;
    }

    public String buildOTPSMS(MACourseFirstCompletion maCourseFirstCompletion, String messageContent, long languageId) {
        long phoneNumber;
        String template = null;

        LOGGER.info("Building SMS for FLW ID: {}", maCourseFirstCompletion.getFlwId());

        // Fetch phone number
        try {
            phoneNumber = maCourseCompletionDao.getAshaPhoneNo(maCourseFirstCompletion.getFlwId());
        } catch (NullPointerException e) {
            LOGGER.error("Phone number not found for FLW ID: {}", maCourseFirstCompletion.getFlwId());
            return null;
        }

        LOGGER.info("Phone number: {}", phoneNumber);
        LOGGER.info("Message content: {}", messageContent);

        // Read SMS template
        try {
            template = FileUtils.readFileToString(
                    new File("../webapps/NMSReportingSuite/WEB-INF/classes/smsTemplate.json"),
                    StandardCharsets.UTF_8
            );
        } catch (IOException e) {
            LOGGER.error("Error reading SMS template file");
            return null;
        }

        // Populate SMS template
        try {
            String callbackEndpoint = retrieveAshaSMSCallBackEndPoint("OTP");
            template = template.replace("<phoneNumber>", String.valueOf(phoneNumber))
                    .replace("<senderId>", senderId)
                    .replace("<messageContent>", messageContent)
                    .replace("<notificationUrl>", callbackEndpoint)
                    .replace("<smsTemplateId>", sms_otp_template_id)
                    .replace("<smsEntityId>", sms_entity_id)
                    .replace("<smsTelemarketerId>", sms_telemarketer_id)
                    .replace("<correlationId>", DateTime.now().toString())
                    .replace("<messageType>", "");
        } catch (Exception e) {
            LOGGER.error("Error populating SMS template.");
            return null;
        }

        return template;
    }

}
