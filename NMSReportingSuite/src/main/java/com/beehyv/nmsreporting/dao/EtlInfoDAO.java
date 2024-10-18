package com.beehyv.nmsreporting.dao;

import com.beehyv.nmsreporting.model.EtlInfoTable;

import java.util.Date;
import java.util.List;

public interface EtlInfoDAO {
    List<EtlInfoTable> fetchETLInfoByJobNames(List<String> etlJobName, Date date);
    List<EtlInfoTable> fetchMonthlyReportsByJobNames(List<String> etlJobName, int currentMonth);
    List<EtlInfoTable> fetchQuarterlyReportsByJobName(List<String> etlJobName);
    List<EtlInfoTable> fetchYearlyReportsByJobNames(List<String> etlJobName);
}
