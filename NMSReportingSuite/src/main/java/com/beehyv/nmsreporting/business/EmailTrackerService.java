package com.beehyv.nmsreporting.business;

import com.beehyv.nmsreporting.model.EmailTracker;

import java.util.Date;
import java.util.List;

public interface EmailTrackerService {

    void saveEmailDeatils(EmailTracker emailTracker);

    List<EmailTracker> getAllFailedEmailsOfGivenReportTypeFromGivenDate(String reportType, Date fromDate);
}
