package com.beehyv.nmsreporting.dao.impl;

import com.beehyv.nmsreporting.dao.AbstractDao;
import com.beehyv.nmsreporting.dao.ChildImportRejectionDao;
import com.beehyv.nmsreporting.model.ChildImportRejection;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository("childImportRejectionDao")
public class ChildImportRejectionDaoImpl extends AbstractDao<Long, ChildImportRejection> implements ChildImportRejectionDao{
    @Override
    public List<ChildImportRejection> getRejectedChildRecords(Date toDate) {

        Criteria criteria=createEntityCriteria();
        criteria.add(Restrictions.lt("modificationDate",toDate));
        criteria.add(Restrictions.ge("accepted",false));

        return criteria.list();
    }
}
