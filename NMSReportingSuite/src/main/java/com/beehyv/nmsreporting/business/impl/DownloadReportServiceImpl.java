package com.beehyv.nmsreporting.business.impl;

import com.beehyv.nmsreporting.business.DownloadReportActivityService;
import com.beehyv.nmsreporting.dao.DownloadReportActivityDao;
import com.beehyv.nmsreporting.model.DownloadReportActivity;
import com.beehyv.nmsreporting.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;


@Service
@Transactional
public class DownloadReportServiceImpl implements DownloadReportActivityService {

    @Autowired
    private DownloadReportActivityDao downloadReportActivityDao;


    @Override
    public void saveReportActivity(DownloadReportActivity downloadReportActivity){
        downloadReportActivityDao.saveDownloadReportActivity(downloadReportActivity);
    }

    @Override
    public void recordDownloadActivity(String username, String reportName, Integer userId) {
        DownloadReportActivity activity = new DownloadReportActivity();
        activity.setUserName(username);
        activity.setReportName(reportName);
        activity.setUserId(userId);
        activity.setDownloadTime(new Date());

        downloadReportActivityDao.saveDownloadReportActivity(activity);
    }
}
