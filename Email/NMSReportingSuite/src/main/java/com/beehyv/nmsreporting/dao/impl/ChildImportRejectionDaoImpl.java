package com.beehyv.nmsreporting.dao.impl;

import com.beehyv.nmsreporting.dao.AbstractDao;
import com.beehyv.nmsreporting.dao.ChildImportRejectionDao;
import com.beehyv.nmsreporting.model.ChildImportRejection;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository("childImportRejectionDao")
public class ChildImportRejectionDaoImpl extends AbstractDao<Long, ChildImportRejection> implements ChildImportRejectionDao {

    @Override
    public Long getCountOfRejectedChildRecords(Date toDate, Integer districtId) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.lt("modificationDate", toDate))
                .add(Restrictions.ge("accepted", false))
                .add(Restrictions.eq("districtId", districtId))
                .add(Restrictions.ne("rejectionReason", "INVALID_DOB"))
                .setProjection(Projections.rowCount());

        return (Long) criteria.uniqueResult();
    }
}
