package com.beehyv.nmsreporting.controller;

import com.beehyv.nmsreporting.dao.MACourseAttemptDao;
import com.beehyv.nmsreporting.dao.MACourseCompletionDao;
import com.beehyv.nmsreporting.entity.SmsStatusRequest;
import com.beehyv.nmsreporting.model.MACourseCompletion;
import com.beehyv.nmsreporting.model.MACourseFirstCompletion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import static java.lang.Long.parseLong;

@Controller
@RequestMapping("/nms/deliveryNotification")
public class SmsDeliveryNotificationController {

    @Autowired
    MACourseCompletionDao maCourseCompletionDao;

    @Autowired
    private MACourseAttemptDao maCourseAttemptDao;

    @RequestMapping(value = "/otp", method = RequestMethod.POST)
    public HttpStatus otpSMSDeliveryNotification(@RequestBody SmsStatusRequest smsStatusRequest) {
        String status = smsStatusRequest.getRequestData().getDeliveryInfoNotitification()
                .getDeliveryInfo().getDeliveryStatus().toString();
        Long phone_no = parseLong(smsStatusRequest.getRequestData().getDeliveryInfoNotitification()
                .getDeliveryInfo().getAddress());
        if(status != null && status.contains("DeliveredToTerminal")) {
            MACourseFirstCompletion maCourseFirstCompletion = maCourseAttemptDao.getMACourseFirstCompletionByMobileNo(phone_no);
            maCourseFirstCompletion.setLastDeliveryStatus("OTP Sent to Asha");

            TimeZone timeZone = TimeZone.getTimeZone("Asia/Kolkata");
            Calendar calendar = Calendar.getInstance(timeZone);
            Date now = calendar.getTime();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            dateFormat.setTimeZone(timeZone);
            String formattedDate = dateFormat.format(now);

            Date date;

            try {
                date = dateFormat.parse(formattedDate);
                maCourseFirstCompletion.setLastModifiedDate(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            maCourseAttemptDao.updateMACourseFirstCompletion(maCourseFirstCompletion);
        }
        return HttpStatus.OK ;
    }

    @RequestMapping(value = "/certificateLink",method = RequestMethod.POST)
    public HttpStatus certificateLinkSMSDeliveryNotification(@RequestBody SmsStatusRequest smsStatusRequest) {
        String status = smsStatusRequest.getRequestData().getDeliveryInfoNotitification()
                .getDeliveryInfo().getDeliveryStatus().toString();
        Long phone_no = parseLong(smsStatusRequest.getRequestData().getDeliveryInfoNotitification()
                .getDeliveryInfo().getAddress());
        if(status != null && status.contains("DeliveredToTerminal")) {
            MACourseFirstCompletion maCourseFirstCompletion = maCourseAttemptDao.getMACourseFirstCompletionByMobileNo(phone_no);
            maCourseFirstCompletion.setLastDeliveryStatus("Certificate Link Sent to Asha");

            TimeZone timeZone = TimeZone.getTimeZone("Asia/Kolkata");
            Calendar calendar = Calendar.getInstance(timeZone);
            Date now = calendar.getTime();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            dateFormat.setTimeZone(timeZone);
            String formattedDate = dateFormat.format(now);

            Date date;

            try {
                // Parse the formatted date string back into a Date object
                date = dateFormat.parse(formattedDate);
                maCourseFirstCompletion.setLastModifiedDate(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            maCourseAttemptDao.updateMACourseFirstCompletion(maCourseFirstCompletion);
        }
        return HttpStatus.OK ;
    }
}
