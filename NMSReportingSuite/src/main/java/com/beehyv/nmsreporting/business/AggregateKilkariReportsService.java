package com.beehyv.nmsreporting.business;

/**
 * Created by himanshu on 06/10/17.
 */

import com.beehyv.nmsreporting.entity.AggregateKilkariReportsDto;
import com.beehyv.nmsreporting.entity.ReportRequest;
import com.beehyv.nmsreporting.model.KilkariSubscriber;

import java.util.Date;
import java.util.List;

public interface AggregateKilkariReportsService {

//    List<KilkariSubscriber> getKilkariSubscriberCount(Integer locationId, String locationType, Date toDate);
    AggregateKilkariReportsDto getKilkariSubscriberCountReport(ReportRequest reportRequest);
}

