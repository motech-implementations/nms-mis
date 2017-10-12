package com.beehyv.nmsreporting.dao;

import com.beehyv.nmsreporting.model.KilkariCalls;

import java.util.Date;

/**
 * Created by beehyv on 11/10/17.
 */
public interface KilkariCallReportDao {
    KilkariCalls getKilkariCallreport(Integer locationId, String locationType, Date toDate);
}
