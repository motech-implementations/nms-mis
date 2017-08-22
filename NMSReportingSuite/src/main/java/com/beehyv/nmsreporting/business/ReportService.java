package com.beehyv.nmsreporting.business;

import com.beehyv.nmsreporting.entity.ReportRequest;
import com.beehyv.nmsreporting.enums.ReportType;
import com.beehyv.nmsreporting.model.Circle;
import com.beehyv.nmsreporting.model.User;

import java.util.Date;
import java.util.List;

/**
 * Created by beehyv on 25/5/17.
 */
public interface ReportService {

    List<String> getReportPathName(ReportRequest reportRequest);

//    List<StateCircle> getRelByStateId(Integer stateId);

    List<Circle> getUserCircles(User user);

    ReportType getReportTypeByName(String reportName);

    String getMonthName(Date toDate);

    String getDateMonthYear(Date toDate);


}
