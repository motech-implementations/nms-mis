package com.beehyv.nmsreporting.dao.impl;

import com.beehyv.nmsreporting.dao.AbstractDao;
import com.beehyv.nmsreporting.dao.FlwImportRejectionDao;
import com.beehyv.nmsreporting.model.FlwImportRejection;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository("flwImportRejectionDao")
public class FlwImportRejectionDaoImpl extends AbstractDao<Long, FlwImportRejection> implements FlwImportRejectionDao {

    @Override
    public Long getCountOfFlwRejectedRecordsForDistrict(Date fromDate, Date toDate, Integer districtId) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.lt("modificationDate", toDate))
                .add(Restrictions.ge("modificationDate", fromDate))
                .add(Restrictions.eq("accepted", false))
                .add(Restrictions.eq("districtId", districtId))
                .add(Restrictions.ne("rejectionReason","UPDATED_RECORD_ALREADY_EXISTS"))
                .add(Restrictions.ne("rejectionReason","FLW_TYPE_NOT_ASHA"))
                .add(Restrictions.ne("rejectionReason","FLW_IMPORT_ERROR"))
                .add(Restrictions.ne("rejectionReason","GF_STATUS_INACTIVE"))
                .setProjection(Projections.rowCount());

        return (Long) criteria.uniqueResult();
    }
}
