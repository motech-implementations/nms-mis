package com.beehyv.nmsreporting.dao;

import com.beehyv.nmsreporting.model.KilkariUsage;

import java.util.Date;
import java.util.List;

/**
 * Created by beehyv on 9/10/17.
 */
public interface KilkariUsageDao {

    KilkariUsage getUsage(Integer locationId, String locationType, Date toDate);
}
