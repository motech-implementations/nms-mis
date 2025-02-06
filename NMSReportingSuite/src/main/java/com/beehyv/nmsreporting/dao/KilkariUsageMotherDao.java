package com.beehyv.nmsreporting.dao;

import com.beehyv.nmsreporting.model.KilkariUsageMother;

import java.util.Date;

/**
 * Created by beehyv on 27/01/25.
 */
public interface KilkariUsageMotherDao {

    KilkariUsageMother getUsage(Integer locationId, String locationType, Date toDate, String periodType);

}
