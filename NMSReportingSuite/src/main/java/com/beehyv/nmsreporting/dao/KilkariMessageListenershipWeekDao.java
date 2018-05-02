package com.beehyv.nmsreporting.dao;


import com.beehyv.nmsreporting.model.KilkariMessageListenership;
import java.util.Date;

public interface KilkariMessageListenershipWeekDao {

    KilkariMessageListenership getListenerData(Integer locationId, String locationType, Date date);
}
