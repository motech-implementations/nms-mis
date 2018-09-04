package com.beehyv.nmsreporting.dao;

import com.beehyv.nmsreporting.model.AuditLog;

public interface AuditLogDao {

    void saveAduitLog(AuditLog auditLog);
}
