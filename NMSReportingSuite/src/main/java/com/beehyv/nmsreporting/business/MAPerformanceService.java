package com.beehyv.nmsreporting.business;

import java.util.Date;

/**
 * Created by beehyv on 25/9/17.
 */
public interface MAPerformanceService {

    Long getAccessedCount(Integer locationId, String locationType, Date fromDate, Date toDate);

    Long getNotAccessedcount(Integer locationId, String locationType, Date fromDate, Date toDate);

}
