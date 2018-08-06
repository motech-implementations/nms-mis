package com.beehyv.nmsreporting.business;

import com.beehyv.nmsreporting.entity.MAPerformanceCountsDto;

import java.util.Date;
import java.util.HashMap;

/**
 * Created by beehyv on 25/9/17.
 */
public interface MAPerformanceService {

//    Long getAccessedCount(Integer locationId, String locationType, Date fromDate, Date toDate);
//
//    Long getNotAccessedcount(Integer locationId, String locationType, Date fromDate, Date toDate);
//
//    Integer getAshasFailed(Integer locationId, String locationType, Date fromDate, Date toDate);

    public HashMap<Long,MAPerformanceCountsDto> getMAPerformanceCounts (Integer locationId, String locationType, Date fromDate, Date toDate);

}
