package com.beehyv.nmsreporting.dao.impl;

import com.beehyv.nmsreporting.dao.AbstractDao;
import com.beehyv.nmsreporting.dao.EmailTrackerDao;
import com.beehyv.nmsreporting.model.EmailTracker;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository("emailTrackerDao")
public class EmailTrackerDaoImpl extends AbstractDao<Integer, EmailTracker> implements EmailTrackerDao {
    @Override
    public void saveEmailTracker(EmailTracker emailTracker) {
        persist(emailTracker);
    }

    @Override
    public List<EmailTracker> getAllFailedEmailsForGivenReportType(String reportType, Date fromDate) {
        Criteria criteria=createEntityCriteria();
        criteria.add(Restrictions.eq("reportType", reportType));
        criteria.add(Restrictions.ge("creationDate", fromDate));

        return criteria.list();
    }
}
