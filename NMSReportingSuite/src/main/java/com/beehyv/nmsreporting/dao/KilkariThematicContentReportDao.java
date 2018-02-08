package com.beehyv.nmsreporting.dao;


import com.beehyv.nmsreporting.entity.ReportRequest;
import com.beehyv.nmsreporting.model.KilkariThematicContent;

import java.util.Date;
import java.util.List;

/**
 * Created by himanshu on 06/10/17.
 */

public interface KilkariThematicContentReportDao {

    KilkariThematicContent getKilkariThematicContentReportData(Integer locationId,String locationType,Date date, String week_id);

    String getMessageWeekNumber(Integer messageWeekNumber);

    Long getUniqueBeneficiariesCalled(Date startDate, Date toDate, String messageId);
}
