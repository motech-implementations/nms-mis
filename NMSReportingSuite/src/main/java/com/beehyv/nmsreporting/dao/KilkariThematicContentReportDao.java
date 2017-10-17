package com.beehyv.nmsreporting.dao;


import com.beehyv.nmsreporting.entity.ReportRequest;
import com.beehyv.nmsreporting.model.KilkariThematicContent;

import java.util.Date;
import java.util.List;

/**
 * Created by himanshu on 06/10/17.
 */

public interface KilkariThematicContentReportDao {

    List<KilkariThematicContent> getKilkariThematicContentReportData(Date toDate);

    String getMessageWeekNumber(Integer messageWeekNumber);

    Integer getUniqueBeneficiariesCalled(Date startDate, Date toDate, Integer messageId);
}
