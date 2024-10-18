package com.beehyv.nmsreporting.dao;


import com.beehyv.nmsreporting.entity.ReportRequest;
import com.beehyv.nmsreporting.model.KilkariThematicContent;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by himanshu on 06/10/17.
 */

public interface KilkariThematicContentReportDao {

    Map<String,KilkariThematicContent> getKilkariThematicContentReportData(Integer locationId, String locationType, Date date,String periodType );

    String getMessageWeekNumber(Integer messageWeekNumber);

    Long getUniqueBeneficiariesCalled(Date startDate, Date toDate, String messageId);

    String getMostHeardCallWeek(Integer locationId, String locationType, Date startDate, Date endDate, String periodType);

    String getLeastHeardCallWeek(Integer locationId, String locationType, Date startDate, Date endDate, String periodType);

    double getAverageDurationOfCalls(Integer locationId, String locationType, Date startDate, Date endDate, String periodType);
}
