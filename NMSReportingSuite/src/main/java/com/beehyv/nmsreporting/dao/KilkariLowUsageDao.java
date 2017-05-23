package com.beehyv.nmsreporting.dao;

import com.beehyv.nmsreporting.model.KilkariLowUsage;

import java.util.Date;
import java.util.List;

/**
 * Created by beehyv on 23/5/17.
 */
public interface KilkariLowUsageDao {

    public List<KilkariLowUsage> getKilkariLowUsageUsers(Date fromDate, Date toDate);
}
