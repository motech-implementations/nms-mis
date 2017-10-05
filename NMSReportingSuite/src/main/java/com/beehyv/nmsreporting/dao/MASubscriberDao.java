package com.beehyv.nmsreporting.dao;

import com.beehyv.nmsreporting.model.AggregateCumulativeMA;

import java.util.Date;
import java.util.List;

/**
 * Created by beehyv on 20/9/17.
 */
public interface MASubscriberDao {

    List<Object> getMASubscriberCountDaily(String locationType, Integer locationId, Date fromDate,Date toDate);

    List<AggregateCumulativeMA> getMASubscriberCountSummary(String locationType, Integer locationId, Date fromDate,Date toDate);

//    List<AggregateCumulativeMA> getMASubscriberCountSummaryEnd(String locationType, Integer locationId, Date fromDate, Date toDate);

}
