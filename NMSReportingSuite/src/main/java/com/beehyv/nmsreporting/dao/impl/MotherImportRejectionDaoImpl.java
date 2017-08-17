package com.beehyv.nmsreporting.dao.impl;

import com.beehyv.nmsreporting.dao.AbstractDao;
import com.beehyv.nmsreporting.dao.MotherImportRejectionDao;
import com.beehyv.nmsreporting.model.MotherImportRejection;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository("motherImportRejectionDao")
public class MotherImportRejectionDaoImpl extends AbstractDao<Long, MotherImportRejection> implements MotherImportRejectionDao {
    @Override
    public List<MotherImportRejection> getAllRejectedMotherImportRecords(Date toDate) {
        Criteria criteria=createEntityCriteria();
        criteria.add(Restrictions.lt("modificationDate", toDate))
                .add(Restrictions.eq("accepted", false));

        return criteria.list();
    }

    @Override
    public List<MotherImportRejection> getAllRejectedMotherImportRecordsWithStateId(Date toDate, Integer stateId) {
        Criteria criteria=createEntityCriteria();
        criteria.add(Restrictions.lt("modificationDate", toDate))
                .add(Restrictions.eq("accepted", false))
                .add(Restrictions.eq("stateId", stateId));

        return criteria.list();
    }

    @Override
    public List<MotherImportRejection> getAllRejectedMotherImportRecordsWithDistrictId(Date toDate, Integer districtId) {
        Criteria criteria=createEntityCriteria();
        criteria.add(Restrictions.lt("modificationDate", toDate))
                .add(Restrictions.eq("accepted", false))
                .add(Restrictions.eq("districtId", districtId));

        return criteria.list();
    }

    @Override
    public List<MotherImportRejection> getAllRejectedMotherImportRecordsWithBlockId(Date toDate, Integer blockId) {
        Criteria criteria=createEntityCriteria();
        criteria.add(Restrictions.lt("modificationDate", toDate))
                .add(Restrictions.eq("accepted", false))
                .add(Restrictions.eq("blockId", blockId));

        return criteria.list();
    }

}
