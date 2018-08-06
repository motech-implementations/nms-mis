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
                .add((Restrictions.not(
                        Restrictions.in("districtId", new Integer[] {471,474,483,490})
                )))
                .add(Restrictions.disjunction()
                .add(Restrictions.eq("rejectionReason","DUPLICATE_MOBILE_NUMBER_IN_DATASET"))
                .add(Restrictions.eq("rejectionReason","MOBILE_NUMBER_ALREADY_SUBSCRIBED"))
                .add(Restrictions.eq("rejectionReason","MOBILE_NUMBER_EMPTY_OR_WRONG_FORMAT"))
                .add(Restrictions.eq("rejectionReason","INVALID_CASE_NO"))
                );

        return criteria.list();
    }

    @Override
    public List<MotherImportRejection> getAllRejectedMotherImportRecordsWithStateId(Date fromDate, Date toDate, Integer stateId) {
        Criteria criteria=createEntityCriteria();
        criteria.add(Restrictions.lt("modificationDate", toDate))
                .add(Restrictions.ge("modificationDate", fromDate))
                .add(Restrictions.eq("accepted", false))
                .add((Restrictions.not(
                        Restrictions.in("districtId", new Integer[] {471,474,483,490})
                )))
                .add(Restrictions.eq("stateId", stateId))
                .add(Restrictions.disjunction()
                .add(Restrictions.eq("rejectionReason","DUPLICATE_MOBILE_NUMBER_IN_DATASET"))
                .add(Restrictions.eq("rejectionReason","MOBILE_NUMBER_ALREADY_SUBSCRIBED"))
                .add(Restrictions.eq("rejectionReason","MOBILE_NUMBER_EMPTY_OR_WRONG_FORMAT"))
                .add(Restrictions.eq("rejectionReason","INVALID_CASE_NO"))
                );

        return criteria.list();
    }

    @Override
    public List<MotherImportRejection> getAllRejectedMotherImportRecordsWithDistrictId(Date fromDate, Date toDate, Integer districtId) {
        Criteria criteria=createEntityCriteria();
        criteria.add(Restrictions.lt("modificationDate", toDate))
                .add(Restrictions.ge("modificationDate", fromDate))
                .add(Restrictions.eq("accepted", false))
                .add(Restrictions.eq("districtId", districtId))
                .add(Restrictions.disjunction()
                .add(Restrictions.eq("rejectionReason","DUPLICATE_MOBILE_NUMBER_IN_DATASET"))
                .add(Restrictions.eq("rejectionReason","MOBILE_NUMBER_ALREADY_SUBSCRIBED"))
                .add(Restrictions.eq("rejectionReason","MOBILE_NUMBER_EMPTY_OR_WRONG_FORMAT"))
                .add(Restrictions.eq("rejectionReason","INVALID_CASE_NO"))
                );

        return criteria.list();
    }

    @Override
    public List<MotherImportRejection> getAllRejectedMotherImportRecordsWithBlockId(Date fromDate, Date toDate, Integer blockId) {
        Criteria criteria=createEntityCriteria();
        criteria.add(Restrictions.lt("modificationDate", toDate))
                .add(Restrictions.ge("modificationDate", fromDate))
                .add(Restrictions.eq("accepted", false))
                .add(Restrictions.eq("healthBlockId", blockId))
                .add(Restrictions.disjunction()
                .add(Restrictions.eq("rejectionReason","DUPLICATE_MOBILE_NUMBER_IN_DATASET"))
                .add(Restrictions.eq("rejectionReason","MOBILE_NUMBER_ALREADY_SUBSCRIBED"))
                .add(Restrictions.eq("rejectionReason","MOBILE_NUMBER_EMPTY_OR_WRONG_FORMAT"))
                .add(Restrictions.eq("rejectionReason","INVALID_CASE_NO"))
                );

        return criteria.list();
    }

    @Override
    public Long getCountOFRejectedMotherImportRecordsWithDistrictId(Date fromDate, Date toDate, Integer districtId) {
        Criteria criteria=createEntityCriteria();
        criteria.add(Restrictions.lt("modificationDate", toDate))
                .add(Restrictions.ge("modificationDate", fromDate))
                .add(Restrictions.eq("accepted", false))
                .add(Restrictions.eq("districtId", districtId))
                .add(Restrictions.disjunction()
                .add(Restrictions.eq("rejectionReason","DUPLICATE_MOBILE_NUMBER_IN_DATASET"))
                .add(Restrictions.eq("rejectionReason","MOBILE_NUMBER_ALREADY_SUBSCRIBED"))
                .add(Restrictions.eq("rejectionReason","MOBILE_NUMBER_EMPTY_OR_WRONG_FORMAT"))
                .add(Restrictions.eq("rejectionReason","INVALID_CASE_NO")))
                .setProjection(Projections.rowCount());

        return (Long) criteria.uniqueResult();

    }

}
