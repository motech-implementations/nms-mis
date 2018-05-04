package com.beehyv.nmsreporting.dao;

import com.beehyv.nmsreporting.model.KilkariThematicContent;

import java.util.Date;
import java.util.Map;

public interface KilkariThematicContentWeekDao {

    Map<String,KilkariThematicContent> getKilkariThematicContentReportData(Integer locationId, String locationType, Date date );
}
