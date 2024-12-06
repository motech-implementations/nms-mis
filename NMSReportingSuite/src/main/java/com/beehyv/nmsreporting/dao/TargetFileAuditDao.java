package com.beehyv.nmsreporting.dao;

import com.beehyv.nmsreporting.model.SmsFileAuditLog;

import java.util.Date;

public interface TargetFileAuditDao {

    void saveSmsFileduitLog(SmsFileAuditLog smsFileAuditLog);

    boolean hasFailureOnDate(Date date);
}
