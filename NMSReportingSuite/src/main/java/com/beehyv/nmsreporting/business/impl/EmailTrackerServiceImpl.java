package com.beehyv.nmsreporting.business.impl;

import com.beehyv.nmsreporting.business.EmailTrackerService;
import com.beehyv.nmsreporting.dao.EmailTrackerDao;
import com.beehyv.nmsreporting.model.EmailTracker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service("emailTrackerSe")
public class EmailTrackerServiceImpl implements EmailTrackerService {

    @Autowired
    private EmailTrackerDao emailTrackerDao;

    @Override
    public void saveEmailDeatils(EmailTracker emailTracker) {
        emailTrackerDao.saveEmailTracker(emailTracker);
    }

    @Override
    public List<EmailTracker> getAllFailedEmailsOfGivenReportTypeFromGivenDate(String reportType, Date fromDate) {
        return emailTrackerDao.getAllFailedEmailsForGivenReportType(reportType, fromDate);
    }
}
