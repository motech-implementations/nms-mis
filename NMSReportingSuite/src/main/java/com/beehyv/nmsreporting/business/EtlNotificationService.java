package com.beehyv.nmsreporting.business;

public interface EtlNotificationService {
    void dailyNotifications();
    boolean scheduledNotification() ;
}
