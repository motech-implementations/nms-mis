package com.beehyv.nmsreporting.dao.impl;

import com.beehyv.nmsreporting.dao.AbstractDao;
import com.beehyv.nmsreporting.dao.ChildImportRejectionDao;
import com.beehyv.nmsreporting.model.ChildImportRejection;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository("childImportRejectionDao")
public class ChildImportRejectionDaoImpl extends AbstractDao<Long, ChildImportRejection> implements ChildImportRejectionDao{
    @Override
    public List<ChildImportRejection> getRejectedChildRecords(Date fromDate, Date toDate) {

        Criteria criteria=createEntityCriteria();
        criteria.add(Restrictions.lt("modificationDate",toDate))
                .add(Restrictions.ge("modificationDate",fromDate))
                .add(Restrictions.eq("accepted",false))
                .add(Restrictions.eq("rejectionReason","DUPLICATE_MOBILE_NUMBER_IN_DATASET"))
                .add(Restrictions.eq("rejectionReason","MOBILE_NUMBER_ALREADY_SUBSCRIBED"))
                .add(Restrictions.eq("rejectionReason","MOBILE_NUMBER_EMPTY_OR_WRONG_FORMAT"))
                .add(Restrictions.eq("rejectionReason","ALREADY_LINKED_WITH_A_DIFFERENT_MOTHER_ID"));

        return criteria.list();
    }

    @Override
    public List<ChildImportRejection> getRejectedChildRecordsWithStateId(Date fromDate, Date toDate, Integer stateId) {

        Criteria criteria=createEntityCriteria();
        criteria.add(Restrictions.lt("modificationDate",toDate))
                .add(Restrictions.ge("modificationDate",fromDate))
                .add(Restrictions.eq("accepted",false))
                .add(Restrictions.eq("stateId",stateId))
                .add(Restrictions.eq("rejectionReason","DUPLICATE_MOBILE_NUMBER_IN_DATASET"))
                .add(Restrictions.eq("rejectionReason","MOBILE_NUMBER_ALREADY_SUBSCRIBED"))
                .add(Restrictions.eq("rejectionReason","MOBILE_NUMBER_EMPTY_OR_WRONG_FORMAT"))
                .add(Restrictions.eq("rejectionReason","ALREADY_LINKED_WITH_A_DIFFERENT_MOTHER_ID"));
        return criteria.list();
    }

    @Override
    public List<ChildImportRejection> getRejectedChildRecordsWithDistrictId(Date fromDate,Date toDate, Integer districtId) {

        Criteria criteria=createEntityCriteria();
        criteria.add(Restrictions.lt("modificationDate",toDate))
                .add(Restrictions.ge("modificationDate",fromDate))
                .add(Restrictions.eq("accepted",false))
                .add(Restrictions.eq("districtId",districtId))
                .add(Restrictions.eq("rejectionReason","DUPLICATE_MOBILE_NUMBER_IN_DATASET"))
                .add(Restrictions.eq("rejectionReason","MOBILE_NUMBER_ALREADY_SUBSCRIBED"))
                .add(Restrictions.eq("rejectionReason","MOBILE_NUMBER_EMPTY_OR_WRONG_FORMAT"))
                .add(Restrictions.eq("rejectionReason","ALREADY_LINKED_WITH_A_DIFFERENT_MOTHER_ID"));
        return criteria.list();
    }

    @Override
    public List<ChildImportRejection> getRejectedChildRecordsWithBlockId(Date fromDate, Date toDate, Integer blockId) {

        Criteria criteria=createEntityCriteria();
        criteria.add(Restrictions.lt("modificationDate",toDate))
                .add(Restrictions.ge("modificationDate",fromDate))
                .add(Restrictions.eq("accepted",false))
                .add(Restrictions.eq("healthBlockId",blockId))
                .add(Restrictions.eq("rejectionReason","DUPLICATE_MOBILE_NUMBER_IN_DATASET"))
                .add(Restrictions.eq("rejectionReason","MOBILE_NUMBER_ALREADY_SUBSCRIBED"))
                .add(Restrictions.eq("rejectionReason","MOBILE_NUMBER_EMPTY_OR_WRONG_FORMAT"))
                .add(Restrictions.eq("rejectionReason","ALREADY_LINKED_WITH_A_DIFFERENT_MOTHER_ID"));
        return criteria.list();
    }

    @Override
    public Long getCountOfRejectedChildRecords(Date fromDate, Date toDate, Integer districtId) {
        Criteria criteria=createEntityCriteria();
        criteria.add(Restrictions.lt("modificationDate",toDate))
                .add(Restrictions.ge("modificationDate",fromDate))
                .add(Restrictions.eq("accepted",false))
                .add(Restrictions.eq("districtId",districtId))
                .add(Restrictions.eq("rejectionReason","DUPLICATE_MOBILE_NUMBER_IN_DATASET"))
                .add(Restrictions.eq("rejectionReason","MOBILE_NUMBER_ALREADY_SUBSCRIBED"))
                .add(Restrictions.eq("rejectionReason","MOBILE_NUMBER_EMPTY_OR_WRONG_FORMAT"))
                .add(Restrictions.eq("rejectionReason","ALREADY_LINKED_WITH_A_DIFFERENT_MOTHER_ID"))
                .setProjection(Projections.rowCount());

        return (Long) criteria.uniqueResult();
    }
}
