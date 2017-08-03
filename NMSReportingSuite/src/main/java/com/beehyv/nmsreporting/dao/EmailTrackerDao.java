package com.beehyv.nmsreporting.dao;

import com.beehyv.nmsreporting.model.EmailTracker;

import java.util.Date;
import java.util.List;

public interface EmailTrackerDao {

    void saveEmailTracker(EmailTracker emailTracker);

    List<EmailTracker> getAllFailedEmailsForGivenReportType(String reportType, Date fromDate);
}
