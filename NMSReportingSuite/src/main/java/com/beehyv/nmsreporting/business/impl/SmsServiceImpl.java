package com.beehyv.nmsreporting.business.impl;

import com.beehyv.nmsreporting.business.SmsService;
import com.beehyv.nmsreporting.dao.FrontLineWorkersDao;
import com.beehyv.nmsreporting.dao.MACourseCompletionDao;
import com.beehyv.nmsreporting.entity.CourseCompletionDTO;
import com.beehyv.nmsreporting.model.MACourseCompletion;
import com.beehyv.nmsreporting.model.MACourseFirstCompletion;
import org.apache.commons.io.FileUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
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


    private String sms_template_id = retrieveTemplateId();
    private String sms_entity_id = retrieveEntityId();
    private String sms_telemarketer_id = retrieveTelemarketerId();
    private String senderId = retrieveSenderId();
    private String authKey = retrieveAuthKey();
    private String endpoint = retrieveSMSEndPoint();


    private static final Logger LOGGER = LoggerFactory.getLogger(SmsServiceImpl.class);

    public String sendSms(MACourseCompletion maCourseCompletion, String template) {
        LOGGER.info("template {}", template);


        endpoint = endpoint.replace("senderId", senderId);

        HttpPost httprequest = new HttpPost(endpoint);
        httprequest.setHeader("Content-type", "application/json");
        httprequest.setHeader("Key", authKey);

        try {
            httprequest.setEntity(new StringEntity(template));
            LOGGER.info("entity set");
            LOGGER.info("printing the url");
            LOGGER.info(httprequest.getURI().toString());

            String str = EntityUtils.toString((httprequest.getEntity()));
            LOGGER.info("printing the entity set in request");
            LOGGER.info(str);
        } catch (IOException ue) {
            LOGGER.error("Unable to build sms request");
            return null;
        }

        HttpResponse response = null;
       /* HttpClient client = HttpClientBuilder.create().build();



        try {
            LOGGER.info("now executing the request");
            response = client.execute(httprequest);
            LOGGER.info("request executed");
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        /*if ((response.getStatusLine()).toString().contains("success")) {*/
        if(true){
            TimeZone timeZone = TimeZone.getTimeZone("Asia/Kolkata");
            Calendar calendar = Calendar.getInstance(timeZone);

            // Format the date directly in the required format
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            dateFormat.setTimeZone(timeZone);
            Date date;
            try {
                // Parse the formatted date string into a Date object
               date = dateFormat.parse(dateFormat.format(calendar.getTime()));
                maCourseCompletion.setLastModifiedDate(date);
            } catch (ParseException pe) {
                pe.printStackTrace();
                maCourseCompletion.setSentNotification(true);
                maCourseCompletionDao.updateMACourseCompletion(maCourseCompletion);

                return "message sent but modification date can not be updated";
            }
            maCourseCompletion.setLastModifiedDate(date);
            maCourseCompletion.setSentNotification(true);

            maCourseCompletionDao.updateMACourseCompletion(maCourseCompletion);
        }
        return "success";
    }


    public String buildCertificateSMS(CourseCompletionDTO courseCompletionDTO, String message_content) {
        long phone_no;
        LOGGER.info("building sms for : {}", courseCompletionDTO.getFlwId());
        try {
            phone_no = courseCompletionDTO.getMobileNumber();
        } catch (NullPointerException nullPointerException) {
            LOGGER.info("phone no. does not exists for  : {}", courseCompletionDTO.getFlwId());
            nullPointerException.printStackTrace();
            return null;
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
        LOGGER.info("phone_no: {}", phone_no);
        LOGGER.info("message_content: {}", message_content);
        String template = null;

        try {
            template = FileUtils.readFileToString(new File("../webapps/NMSReportingSuite/WEB-INF/classes/smsTemplate.json"), StandardCharsets.UTF_8);

        } catch (Exception e) {
            e.printStackTrace();
        }

        String callbackEndpoint =  retrieveAshaSMSCallBackEndPoint("certificateLink");

        template = template.replace("<phoneNumber>", String.valueOf(phone_no));
        template = template.replace("<senderId>", senderId);
        template = template.replace("<messageContent>", message_content);
        template = template.replace("<notificationUrl>", callbackEndpoint==null?"0":"1");
        template = template.replace("<smsTemplateId>", sms_template_id);
        template = template.replace("<smsEntityId>", sms_entity_id);
        template = template.replace("<smsTelemarketerId>", sms_telemarketer_id);
        return template;
    }

    public String buildOTPSMS(MACourseFirstCompletion maCourseFirstCompletion, String message_content) {
        long phone_no;
        LOGGER.info("building sms for : {}", maCourseFirstCompletion.getFlwId());
        try {
            phone_no = maCourseCompletionDao.getAshaPhoneNo(maCourseFirstCompletion.getFlwId());
        } catch (NullPointerException nullPointerException) {
            nullPointerException.printStackTrace();
            return null;
        }
        LOGGER.info("phone_no: {}", phone_no);
        LOGGER.info("message_content: {}", message_content);
        String template = null;

        try {
            template = FileUtils.readFileToString(new File("../webapps/NMSReportingSuite/WEB-INF/classes/smsTemplate.json"), StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String callbackEndpoint =  retrieveAshaSMSCallBackEndPoint("OTP");

        template = template.replace("<phoneNumber>", String.valueOf(phone_no));
        template = template.replace("<senderId>", senderId);
        template = template.replace("<messageContent>", message_content);
        template = template.replace("<notificationUrl>", callbackEndpoint);
        template = template.replace("<smsTemplateId>", sms_template_id);
        template = template.replace("<smsEntityId>", sms_entity_id);
        template = template.replace("<smsTelemarketerId>", sms_telemarketer_id);
        return template;
    }

}
