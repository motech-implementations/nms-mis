package com.beehyv.nmsreporting.dao.impl;

import com.beehyv.nmsreporting.dao.AbstractDao;
import com.beehyv.nmsreporting.dao.FrontLineWorkersDao;
import com.beehyv.nmsreporting.model.FrontLineWorkers;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * Created by beehyv on 23/5/17.
 */
@Repository("frontLineWorkersDao")
public class FrontLineWorkersDaoImpl extends AbstractDao<Integer, FrontLineWorkers> implements FrontLineWorkersDao {

    @Override
    public Long getCountOfInactiveFrontLineWorkersForGivenDistrict(Date toDate, Integer districtId) {
        Criteria criteria = createEntityCriteria().addOrder(Order.asc("creationDate"));
        criteria.add(Restrictions.and(
                Restrictions.eq("status", "INACTIVE").ignoreCase(),
                Restrictions.lt("lastModifiedDate", toDate),
                Restrictions.ne("jobStatus", "INACTIVE").ignoreCase(),
                Restrictions.eq("designation", "ASHA").ignoreCase(),
                Restrictions.eq("district", districtId)
        )).setProjection(Projections.rowCount());
        return (Long) criteria.uniqueResult();
    }
}
