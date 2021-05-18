package com.beehyv.nmsreporting.dao;

import java.util.Date;

/**
 * Created by beehyv on 22/9/17.
 */
public interface MAPerformanceDao {
    Long accessedAtLeastOnce(Integer locationId, String locationType, Date fromDate, Date toDate);

    Long accessedNotOnce(Integer locationId, String locationType, Date fromDate, Date toDate);

    Integer getAshasFailed(Integer locationId, String locationType, Date fromDate, Date toDate);

    Long getAshaActivated(Integer locationId, String locationType, Date toDate);

    Long getAshaDeactivated(Integer locationId, String locationType, Date toDate);

    Long getAshaRefresherCourse(Integer locationId, String locationType);
}
