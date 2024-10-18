package com.beehyv.nmsreporting.dao;

import com.beehyv.nmsreporting.model.ScheduledReportTracker;

import java.util.List;

public interface ScheduledReportTrackerDao {
    public void saveScheduleReportTracker(ScheduledReportTracker scheduledReportTracker);
    public List<ScheduledReportTracker> findByForMonth(String forMonth);
}
