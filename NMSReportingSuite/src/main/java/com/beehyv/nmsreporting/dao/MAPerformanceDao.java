package com.beehyv.nmsreporting.dao;

import java.util.Date;
import java.util.List;

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

    Long getAshaRefresherCourseInBetween(Integer locationId, String locationType, Date fromDate, Date toDate);

    Long getAshasCompletedInGivenTime(Integer locationId, String locationType, Date fromDate, Date toDate);

    Long getAshaActivatedInBetween(Integer locationId, String locationType, Date fromDate, Date toDate);

    Long getAshaDeactivatedInBetween(Integer locationId, String locationType, Date fromDate, Date toDate);

    List<Object[]> getPerformanceCount(List<Integer> locationIds, String locationType, Date fromDateTemp, Date toDate);
}
