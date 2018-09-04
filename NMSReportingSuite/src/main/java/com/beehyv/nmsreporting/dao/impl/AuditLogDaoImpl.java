package com.beehyv.nmsreporting.dao.impl;

import com.beehyv.nmsreporting.dao.AbstractDao;
import com.beehyv.nmsreporting.dao.AuditLogDao;
import com.beehyv.nmsreporting.dao.LoginTrackerDao;
import com.beehyv.nmsreporting.model.AuditLog;
import org.springframework.stereotype.Repository;

@Repository("auditLogDao")
public class AuditLogDaoImpl extends AbstractDao<Integer, AuditLog> implements AuditLogDao {
    @Override
    public void saveAduitLog(AuditLog auditLog) {
        persist(auditLog);
    }
}
