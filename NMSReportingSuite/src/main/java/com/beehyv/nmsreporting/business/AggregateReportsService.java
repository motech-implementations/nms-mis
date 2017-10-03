package com.beehyv.nmsreporting.business;

import com.beehyv.nmsreporting.entity.AggregateCumulativekilkariDto;
import com.beehyv.nmsreporting.entity.ReportRequest;
import com.beehyv.nmsreporting.model.AggregateCumulativeMA;
import com.beehyv.nmsreporting.model.User;

import java.util.Date;
import java.util.List;

/**
 * Created by beehyv on 19/9/17.
 */
public interface AggregateReportsService {

    List<AggregateCumulativeMA> getCumulativeSummaryMAReport(Integer locationId, String locationType, Date toDate);

    List<AggregateCumulativekilkariDto> getKilkariCumulativeSummary(ReportRequest reportRequest, User currentUser);

    }
