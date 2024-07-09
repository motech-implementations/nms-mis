package com.beehyv.nmsreporting.business;

import com.beehyv.nmsreporting.model.DownloadReportActivity;

public interface DownloadReportActivityService {


    void saveReportActivity(DownloadReportActivity downloadReportActivity);

    void recordDownloadActivity(String username, String reportName,Integer userId);
}
