package com.beehyv.nmsreporting.dao.impl;

import com.beehyv.nmsreporting.dao.AbstractDao;
import com.beehyv.nmsreporting.dao.TargetFileAuditDao;
import com.beehyv.nmsreporting.model.SmsFileAuditLog;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Repository("smsFileAuditLogDao")
public class TargetFileAuditDaoImpl extends AbstractDao<Integer, SmsFileAuditLog> implements TargetFileAuditDao {
    private Logger LOGGER = LoggerFactory.getLogger(TargetFileAuditDaoImpl.class);


    @Override
    public void saveSmsFileduitLog(SmsFileAuditLog smsFileAuditLog){
        persist(smsFileAuditLog);
    }

//    @Override
//    public boolean hasFailureOnDate(Date date) {
//        Session session = getSession();
//        String sql = "SELECT * FROM sms_audit_log WHERE success_status = :successStatus  AND DATE(created_at) = :date";
//        SQLQuery query = session.createSQLQuery(sql);
//        query.addEntity(SmsFileAuditLog.class);
//        query.setParameter("successStatus", false);
//        query.setParameter("date", new java.sql.Date(date.getTime()));
//
//        return !query.list().isEmpty();
//    }

    @Override
    @Transactional
    public boolean hasFailureOnDate(Date date) {
        if (date == null) {
            throw new IllegalArgumentException("Date cannot be null");
        }

        // Set date range for the specific day.
        Calendar startOfDay = Calendar.getInstance();
        startOfDay.setTime(date);
        startOfDay.set(Calendar.HOUR_OF_DAY, 0);
        startOfDay.set(Calendar.MINUTE, 0);
        startOfDay.set(Calendar.SECOND, 0);
        startOfDay.set(Calendar.MILLISECOND, 0);

        Calendar endOfDay = (Calendar) startOfDay.clone();
        endOfDay.add(Calendar.DAY_OF_MONTH, 1);

        // Build criteria for all audit logs on the given day regardless of successStatus.
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.ge("createdAt", startOfDay.getTime()));
        criteria.add(Restrictions.lt("createdAt", endOfDay.getTime()));

        List<SmsFileAuditLog> logs = criteria.list();

        // If no logs exist for that day, there's no failure.
        if (logs == null || logs.isEmpty()) {
            return true;
        }

        // If any log on that day indicates success
        // then we return false because the day is not considered as having a failure.
        for (SmsFileAuditLog log : logs) {
            if (Boolean.TRUE.equals(log.getSuccessStatus())) {
                return false;
            }
        }

        // If all entries are failures then we return true.
        return true;
    }




}
