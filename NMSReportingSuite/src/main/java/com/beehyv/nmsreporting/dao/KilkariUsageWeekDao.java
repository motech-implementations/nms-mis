package com.beehyv.nmsreporting.dao;

import com.beehyv.nmsreporting.model.KilkariUsage;

import java.util.Date;

public interface KilkariUsageWeekDao {
    KilkariUsage getUsage(Integer locationId, String locationType, Date toDate);
}
