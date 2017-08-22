package com.beehyv.nmsreporting.dao.impl;

import com.beehyv.nmsreporting.dao.AbstractDao;
import com.beehyv.nmsreporting.dao.FlwImportRejectionDao;
import com.beehyv.nmsreporting.model.FlwImportRejection;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository("flwImportRejectionDao")
public class FlwImportRejectionDaoImpl extends AbstractDao<Long, FlwImportRejection> implements FlwImportRejectionDao {
    @Override
    public List<FlwImportRejection> getAllRejectedFlwImportRecords(Date toDate) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.lt("modificationDate", toDate))
                .add(Restrictions.eq("accepted", false));

        return criteria.list();
    }

    @Override
    public List<FlwImportRejection> getAllRejectedFlwImportRecordsWithStateId(Date toDate, Integer stateId) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.lt("modificationDate", toDate))
                .add(Restrictions.eq("accepted", false))
                .add(Restrictions.eq("stateId", stateId));
        return criteria.list();
    }
    @Override
    public List<FlwImportRejection> getAllRejectedFlwImportRecordsWithDistrictId(Date toDate, Integer districtId) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.lt("modificationDate", toDate))
                .add(Restrictions.eq("accepted", false))
                .add(Restrictions.eq("districtId", districtId));

        return criteria.list();
    }

    @Override
    public List<FlwImportRejection> getAllRejectedFlwImportRecordsWithBlockId(Date toDate, Integer blockId) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.lt("modificationDate", toDate))
                .add(Restrictions.eq("accepted", false))
                .add(Restrictions.eq("blockId", blockId));

        return criteria.list();
    }

    @Override
    public Long getCountOfFlwRejectedRecordsForDistrict(Date toDate, Integer districtId) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.lt("modificationDate", toDate))
                .add(Restrictions.eq("accepted", false))
                .add(Restrictions.eq("districtId", districtId))
                .setProjection(Projections.rowCount());

        return (Long) criteria.uniqueResult();
    }
}
