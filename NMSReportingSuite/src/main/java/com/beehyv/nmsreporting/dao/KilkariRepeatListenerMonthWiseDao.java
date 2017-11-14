package com.beehyv.nmsreporting.dao;

import com.beehyv.nmsreporting.model.KilkariRepeatListenerMonthWise;

import java.util.Date;
import java.util.List;

/**
 * Created by himanshu on 06/10/17.
 */


public interface KilkariRepeatListenerMonthWiseDao {

    KilkariRepeatListenerMonthWise getListenerData(Date date);
}
