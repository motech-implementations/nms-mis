package com.beehyv.nmsreporting.dao;

import com.beehyv.nmsreporting.model.WhatsAppReports;

import java.util.Date;

public interface WhatsAppReportsDao {
    public WhatsAppReports getWhatsAppReportCounts(Integer locationId, String locationType, Date toDate, String periodType);
}
