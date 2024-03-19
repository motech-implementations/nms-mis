package com.beehyv.nmsreporting.dao;

import com.beehyv.nmsreporting.model.WhatsAppSubscribers;

import java.util.Date;

public interface WhatsAppSubscribersReportDao {
    public WhatsAppSubscribers getWhatsAppSubscriberCounts(Integer locationId, String locationType, Date toDate, String periodType);
}
