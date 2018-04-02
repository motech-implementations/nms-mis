package com.beehyv.nmsreporting.dao.impl;

import com.beehyv.nmsreporting.dao.AbstractDao;
import com.beehyv.nmsreporting.dao.MotherImportRejectionDao;
import com.beehyv.nmsreporting.model.MotherImportRejection;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository("motherImportRejectionDao")
public class MotherImportRejectionDaoImpl extends AbstractDao<Long, MotherImportRejection> implements MotherImportRejectionDao {

    @Override
    public Long getCountOFRejectedMotherImportRecordsWithDistrictId(Date fromDate, Date toDate, Integer districtId) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.lt("modificationDate", toDate))
                .add(Restrictions.ge("modificationDate", fromDate))
                .add(Restrictions.eq("accepted", false))
                .add(Restrictions.eq("districtId", districtId))
                .add(Restrictions.eq("rejectionReason","DUPLICATE_MOBILE_NUMBER_IN_DATASET"))
                .add(Restrictions.eq("rejectionReason","MOBILE_NUMBER_ALREADY_SUBSCRIBED"))
                .add(Restrictions.eq("rejectionReason","MOBILE_NUMBER_EMPTY_OR_WRONG_FORMAT"))
                .add(Restrictions.eq("rejectionReason","INVALID_CASE_NO"))
                .setProjection(Projections.rowCount());

        return (Long) criteria.uniqueResult();

    }

}
