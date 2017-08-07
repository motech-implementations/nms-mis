package com.beehyv.nmsreporting.dao.impl;

import com.beehyv.nmsreporting.dao.AbstractDao;
import com.beehyv.nmsreporting.dao.FlwImportRejectionDao;
import com.beehyv.nmsreporting.model.FlwImportRejection;
import org.hibernate.Criteria;
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
                .add(Restrictions.eq("accepted", true));

        return criteria.list();
    }
}
