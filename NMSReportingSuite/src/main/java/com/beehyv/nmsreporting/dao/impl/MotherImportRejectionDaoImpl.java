package com.beehyv.nmsreporting.dao.impl;

import com.beehyv.nmsreporting.dao.AbstractDao;
import com.beehyv.nmsreporting.dao.MotherImportRejectionDao;
import com.beehyv.nmsreporting.model.MotherImportRejection;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository("motherImportRejectionDao")
public class MotherImportRejectionDaoImpl extends AbstractDao<Long, MotherImportRejection> implements MotherImportRejectionDao {
    @Override
    public List<MotherImportRejection> getAllRejectedMotherImportRecords(Date fromDate, Date toDate) {
        Criteria criteria=createEntityCriteria();
        criteria.add(Restrictions.lt("modificationDate", toDate))
                .add(Restrictions.ge("modificationDate", fromDate))
                .add(Restrictions.eq("accepted", false))
                .add(Restrictions.ne("rejectionReason","INVALID_LMP_DATE"))
                .add(Restrictions.ne("rejectionReason","UPDATED_RECORD_ALREADY_EXISTS"));

        return criteria.list();
    }

    @Override
    public List<MotherImportRejection> getAllRejectedMotherImportRecordsWithStateId(Date fromDate, Date toDate, Integer stateId) {
        Criteria criteria=createEntityCriteria();
        criteria.add(Restrictions.lt("modificationDate", toDate))
                .add(Restrictions.ge("modificationDate", fromDate))
                .add(Restrictions.eq("accepted", false))
                .add(Restrictions.eq("stateId", stateId))
                .add(Restrictions.ne("rejectionReason","INVALID_LMP_DATE"))
                .add(Restrictions.ne("rejectionReason","UPDATED_RECORD_ALREADY_EXISTS"));

        return criteria.list();
    }

    @Override
    public List<MotherImportRejection> getAllRejectedMotherImportRecordsWithDistrictId(Date fromDate, Date toDate, Integer districtId) {
        Criteria criteria=createEntityCriteria();
        criteria.add(Restrictions.lt("modificationDate", toDate))
                .add(Restrictions.ge("modificationDate", fromDate))
                .add(Restrictions.eq("accepted", false))
                .add(Restrictions.eq("districtId", districtId))
                .add(Restrictions.ne("rejectionReason","INVALID_LMP_DATE"))
                .add(Restrictions.ne("rejectionReason","ACTIVE_CHILD_PRESENT"))
                .add(Restrictions.ne("rejectionReason","ABORT_STILLBIRTH_DEATH"))
                .add(Restrictions.ne("rejectionReason","UPDATED_RECORD_ALREADY_EXISTS"));

        return criteria.list();
    }

    @Override
    public List<MotherImportRejection> getAllRejectedMotherImportRecordsWithBlockId(Date fromDate, Date toDate, Integer blockId) {
        Criteria criteria=createEntityCriteria();
        criteria.add(Restrictions.lt("modificationDate", toDate))
                .add(Restrictions.ge("modificationDate", fromDate))
                .add(Restrictions.eq("accepted", false))
                .add(Restrictions.eq("healthBlockId", blockId))
                .add(Restrictions.ne("rejectionReason","INVALID_LMP_DATE"))
                .add(Restrictions.ne("rejectionReason","ACTIVE_CHILD_PRESENT"))
                .add(Restrictions.ne("rejectionReason","ABORT_STILLBIRTH_DEATH"))
                .add(Restrictions.ne("rejectionReason","UPDATED_RECORD_ALREADY_EXISTS"));

        return criteria.list();
    }

    @Override
    public Long getCountOFRejectedMotherImportRecordsWithDistrictId(Date fromDate, Date toDate, Integer districtId) {
        Criteria criteria=createEntityCriteria();
        criteria.add(Restrictions.lt("modificationDate", toDate))
                .add(Restrictions.ge("modificationDate", fromDate))
                .add(Restrictions.eq("accepted", false))
                .add(Restrictions.eq("districtId", districtId))
                .add(Restrictions.ne("rejectionReason","INVALID_LMP_DATE"))
                .add(Restrictions.ne("rejectionReason","ACTIVE_CHILD_PRESENT"))
                .add(Restrictions.ne("rejectionReason","ABORT_STILLBIRTH_DEATH"))
                .add(Restrictions.ne("rejectionReason","UPDATED_RECORD_ALREADY_EXISTS"))
                .setProjection(Projections.rowCount());

        return (Long) criteria.uniqueResult();

    }

}
