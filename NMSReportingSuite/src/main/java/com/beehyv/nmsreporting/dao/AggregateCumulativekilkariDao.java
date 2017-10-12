package com.beehyv.nmsreporting.dao;

import com.beehyv.nmsreporting.model.AggregateCumulativeKilkari;

import java.util.Date;

/**
 * Created by beehyv on 3/10/17.
 */
public interface AggregateCumulativekilkariDao {

    AggregateCumulativeKilkari getKilkariCumulativeSummary(Integer locationId, String locationType, Date toDate);
}
