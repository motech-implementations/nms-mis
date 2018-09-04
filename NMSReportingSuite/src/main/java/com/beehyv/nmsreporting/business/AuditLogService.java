package com.beehyv.nmsreporting.business;

import com.beehyv.nmsreporting.model.AuditLog;
import com.beehyv.nmsreporting.model.User;

import javax.servlet.http.HttpServletRequest;

public interface AuditLogService {

    void saveAuditLog(HttpServletRequest request);
}
