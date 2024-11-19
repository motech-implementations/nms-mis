package com.beehyv.nmsreporting.dao;

import com.beehyv.nmsreporting.model.WhatsAppMessages;

import java.util.Date;

public interface WhatsAppMessagesReportDao {
    public WhatsAppMessages getWhatsAppMessageCounts(Integer locationId, String locationType, Date toDate, String periodType);
}
