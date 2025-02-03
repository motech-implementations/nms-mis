package com.beehyv.nmsreporting.dao;

import com.beehyv.nmsreporting.model.KilkariUsageChild;

import java.util.Date;

/**
 * Created by beehyv on 27/01/25.
 */
public interface KilkariUsageChildDao {

    KilkariUsageChild getUsage(Integer locationId, String locationType, Date toDate, String periodType);

}
