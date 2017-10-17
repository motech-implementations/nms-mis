package com.beehyv.nmsreporting.dao;


import com.beehyv.nmsreporting.entity.KilkariMessageListenershipReportDto;
import com.beehyv.nmsreporting.model.AggregateCumulativeMA;
import com.beehyv.nmsreporting.model.KilkariSubscriber;

import java.util.Date;
import java.util.List;

public interface AggregateKilkariReportsDao {


    KilkariSubscriber getKilkariSubscriberCounts(Integer locationId, String locationType, Date toDate);

}




