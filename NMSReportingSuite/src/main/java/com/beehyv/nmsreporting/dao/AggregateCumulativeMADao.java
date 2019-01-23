package com.beehyv.nmsreporting.dao;


import com.beehyv.nmsreporting.model.AggregateCumulativeMA;

import java.util.Date;
import java.util.List;

public interface AggregateCumulativeMADao {


    AggregateCumulativeMA getMACumulativeSummery(Integer locationId, String locationType, Date toDate);

}