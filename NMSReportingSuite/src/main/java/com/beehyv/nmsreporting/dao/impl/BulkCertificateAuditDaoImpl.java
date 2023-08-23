package com.beehyv.nmsreporting.dao.impl;

import com.beehyv.nmsreporting.dao.AbstractDao;
import com.beehyv.nmsreporting.dao.BulkCertificateAuditDao;
import com.beehyv.nmsreporting.model.BulkCertificateAudit;
import com.beehyv.nmsreporting.model.MACourseFirstCompletion;
import com.beehyv.nmsreporting.model.User;
import org.hibernate.Criteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Repository("bulkCertificateAudit")
@Transactional
public class BulkCertificateAuditDaoImpl extends AbstractDao<Integer, BulkCertificateAudit> implements BulkCertificateAuditDao {


    @Override
    public List<BulkCertificateAudit> findByFileDirectoryAndMonth(List<String> directories) {
        Criteria criteria = getSession().createCriteria(BulkCertificateAudit.class);
        criteria.add(Restrictions.in("fileDirectory", directories));
//        criteria.add(Restrictions.eq("fileDirectory", directory));
        return (List<BulkCertificateAudit>) criteria.list();
    }

    @Override
    public void saveAudit(BulkCertificateAudit bulkCertificateAudit){
        persist(bulkCertificateAudit);
    }

}
