package com.beehyv.nmsreporting.business;

import com.beehyv.nmsreporting.business.impl.TargetFileNotification;
import org.apache.http.client.methods.HttpPost;

public interface SmsNotificationService {

    void sendNotificationRequest(TargetFileNotification tfn);

}
