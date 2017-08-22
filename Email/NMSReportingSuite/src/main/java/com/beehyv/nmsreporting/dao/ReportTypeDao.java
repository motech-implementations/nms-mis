package com.beehyv.nmsreporting.dao;

import com.beehyv.nmsreporting.enums.ReportType;

/**
 * Created by beehyv on 26/5/17.
 */
public interface ReportTypeDao {

    ReportType getReportTypeByName(String reportName);

}
