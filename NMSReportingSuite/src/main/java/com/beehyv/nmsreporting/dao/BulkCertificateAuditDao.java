package com.beehyv.nmsreporting.dao;


import com.beehyv.nmsreporting.model.BulkCertificateAudit;

import java.util.List;

public interface BulkCertificateAuditDao {
    public List<BulkCertificateAudit> findByFileDirectoryAndMonth(List<String> directories);
    public void saveAudit(BulkCertificateAudit bulkCertificateAudit);
}
