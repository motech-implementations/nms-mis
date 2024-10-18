package com.beehyv.nmsreporting.dao.impl;

import com.beehyv.nmsreporting.dao.AbstractDao;
import com.beehyv.nmsreporting.dao.ScheduledReportTrackerDao;
import com.beehyv.nmsreporting.model.ScheduledReportTracker;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;

@Repository("ScheduledReportTrackerDao")
public class ScheduleReportTrackerDaoImpl extends AbstractDao<Integer, ScheduledReportTracker> implements ScheduledReportTrackerDao {
    @Override
    public void saveScheduleReportTracker(ScheduledReportTracker scheduledReportTracker) {
        persist(scheduledReportTracker);
    }

    @Override
    @Transactional
    public List<ScheduledReportTracker> findByForMonth(String forMonth) {
        Criteria criteria = getSession().createCriteria(ScheduledReportTracker.class);
        criteria.add(Restrictions.eq("forMonth", forMonth));
        return (List<ScheduledReportTracker>) criteria.list();
    }
}
