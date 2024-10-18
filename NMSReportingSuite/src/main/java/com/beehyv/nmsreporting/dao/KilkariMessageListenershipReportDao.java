package com.beehyv.nmsreporting.dao;

/**
 * Created by himanshu on 06/10/17.
 */


import com.beehyv.nmsreporting.entity.KilkariMessageListenershipReportDto;
import com.beehyv.nmsreporting.model.KilkariMessageListenership;
import com.beehyv.nmsreporting.model.KilkariRepeatListenerMonthWise;
import com.beehyv.nmsreporting.model.State;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

public interface KilkariMessageListenershipReportDao {

    KilkariMessageListenership getListenerData(Integer locationId, String locationType, Date date,String periodType);

     Long getTotalAnsweredAtLeastOneCall(Integer locationId, String locationType, Date fromDate, Date toDate, String periodType);


}
