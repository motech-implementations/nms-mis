package com.beehyv.nmsreporting.dao.impl;

import com.beehyv.nmsreporting.dao.AbstractDao;
import com.beehyv.nmsreporting.dao.FrontLineWorkersDao;
import com.beehyv.nmsreporting.model.FrontLineWorkers;
import com.beehyv.nmsreporting.model.User;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by beehyv on 23/5/17.
 */
@Repository("frontLineWorkersDao")
public class FrontLineWorkersDaoImpl extends AbstractDao<Integer,FrontLineWorkers> implements FrontLineWorkersDao {

    @Override
    public List<FrontLineWorkers> getInactiveFrontLineWorkers(Date toDate) {
        Criteria criteria = createEntityCriteria().addOrder(Order.asc("creationDate"));
        criteria.add(Restrictions.and(
                Restrictions.eq("status","INACTIVE").ignoreCase(),
                Restrictions.lt("lastModifiedDate",toDate),
                Restrictions.ne("jobStatus","INACTIVE").ignoreCase(),
                Restrictions.eq("designation","ASHA").ignoreCase()
        ));
        return (List<FrontLineWorkers>) criteria.list();
    }

    @Override
    public List<FrontLineWorkers> getInactiveFrontLineWorkersWithStateId(Date toDate, Integer stateId) {
        Criteria criteria = createEntityCriteria().addOrder(Order.asc("creationDate"));
        criteria.add(Restrictions.and(
                Restrictions.eq("status","INACTIVE").ignoreCase(),
                Restrictions.lt("lastModifiedDate",toDate),
                Restrictions.ne("jobStatus","INACTIVE").ignoreCase(),
                Restrictions.eq("designation","ASHA").ignoreCase(),
                Restrictions.eq("stateId","stateId").ignoreCase()
        ));
        return (List<FrontLineWorkers>) criteria.list();
    }

    @Override
    public List<FrontLineWorkers> getInactiveFrontLineWorkersWithDistrictId(Date toDate, Integer districtId) {
        Criteria criteria = createEntityCriteria().addOrder(Order.asc("creationDate"));
        criteria.add(Restrictions.and(
                Restrictions.eq("status","INACTIVE").ignoreCase(),
                Restrictions.lt("lastModifiedDate",toDate),
                Restrictions.ne("jobStatus","INACTIVE").ignoreCase(),
                Restrictions.eq("designation","ASHA").ignoreCase(),
                Restrictions.eq("districtId","districtId").ignoreCase()
        ));
        return (List<FrontLineWorkers>) criteria.list();
    }

    @Override
    public List<FrontLineWorkers> getInactiveFrontLineWorkersWithBlockId(Date toDate, Integer blockId) {
        Criteria criteria = createEntityCriteria().addOrder(Order.asc("creationDate"));
        criteria.add(Restrictions.and(
                Restrictions.eq("status","INACTIVE").ignoreCase(),
                Restrictions.lt("lastModifiedDate",toDate),
                Restrictions.ne("jobStatus","INACTIVE").ignoreCase(),
                Restrictions.eq("designation","ASHA").ignoreCase(),
                Restrictions.eq("blockId","blockId").ignoreCase()
        ));
        return (List<FrontLineWorkers>) criteria.list();
    }
}
