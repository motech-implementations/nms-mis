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
                Restrictions.lt("creationDate",toDate),
                (Restrictions.not(
                        Restrictions.in("districtId", new Integer[] {471,474,483,490})
                )),
                Restrictions.disjunction().add(Restrictions.and(
                        Restrictions.eq("status","ACTIVE").ignoreCase(),
                        Restrictions.gt("courseStartDate",toDate)))
                        .add(Restrictions.eq("status","INACTIVE").ignoreCase()),
                Restrictions.eq("jobStatus","ACTIVE").ignoreCase(),
                Restrictions.eq("designation","ASHA").ignoreCase()
        ));
        return (List<FrontLineWorkers>) criteria.list();
    }

    @Override
    public List<FrontLineWorkers> getInactiveFrontLineWorkersWithStateId(Date toDate, Integer stateId) {
        Criteria criteria = createEntityCriteria().addOrder(Order.asc("creationDate"));
        criteria.add(Restrictions.and(
                Restrictions.lt("creationDate",toDate),
                (Restrictions.not(
                        Restrictions.in("districtId", new Integer[] {471,474,483,490})
                )),
                Restrictions.disjunction().add(Restrictions.and(
                        Restrictions.eq("status","ACTIVE").ignoreCase(),
                        Restrictions.gt("courseStartDate",toDate)))
                        .add(Restrictions.eq("status","INACTIVE").ignoreCase()),
                Restrictions.eq("jobStatus","ACTIVE").ignoreCase(),
                Restrictions.eq("designation","ASHA").ignoreCase(),
                Restrictions.eq("state",stateId)
        ));
        return (List<FrontLineWorkers>) criteria.list();
    }

    @Override
    public List<FrontLineWorkers> getInactiveFrontLineWorkersWithDistrictId(Date toDate, Integer districtId) {
        Criteria criteria = createEntityCriteria().addOrder(Order.asc("creationDate"));
        criteria.add(Restrictions.and(
                Restrictions.lt("creationDate",toDate),
                Restrictions.disjunction().add(Restrictions.and(
                        Restrictions.eq("status","ACTIVE").ignoreCase(),
                        Restrictions.gt("courseStartDate",toDate)))
                        .add(Restrictions.eq("status","INACTIVE").ignoreCase()),
                Restrictions.eq("jobStatus","ACTIVE").ignoreCase(),
                Restrictions.eq("designation","ASHA").ignoreCase(),
                Restrictions.eq("district",districtId)
        ));
        return (List<FrontLineWorkers>) criteria.list();
    }

    @Override
    public List<FrontLineWorkers> getInactiveFrontLineWorkersWithBlockId(Date toDate, Integer blockId) {
        Criteria criteria = createEntityCriteria().addOrder(Order.asc("creationDate"));
        criteria.add(Restrictions.and(
                Restrictions.lt("creationDate",toDate),
                Restrictions.disjunction().add(Restrictions.and(
                        Restrictions.eq("status","ACTIVE").ignoreCase(),
                        Restrictions.gt("courseStartDate",toDate)))
                        .add(Restrictions.eq("status","INACTIVE").ignoreCase()),
                Restrictions.eq("jobStatus","ACTIVE").ignoreCase(),
                Restrictions.eq("designation","ASHA").ignoreCase(),
                Restrictions.eq("block",blockId)
        ));
        return (List<FrontLineWorkers>) criteria.list();
    }

    @Override
    public Long getCountOfInactiveFrontLineWorkersForGivenDistrict(Date toDate, Integer districtId) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.and(
                Restrictions.lt("creationDate",toDate),
                Restrictions.disjunction().add(Restrictions.and(
                        Restrictions.eq("status","ACTIVE").ignoreCase(),
                        Restrictions.gt("courseStartDate",toDate)))
                        .add(Restrictions.eq("status","INACTIVE").ignoreCase()),
                Restrictions.eq("jobStatus","ACTIVE").ignoreCase(),
                Restrictions.eq("designation","ASHA").ignoreCase(),
                Restrictions.eq("district",districtId)
        )).setProjection(Projections.rowCount());
        return (Long) criteria.uniqueResult();
    }

    @Override
    public FrontLineWorkers getINactiveFrontLineWorkerByExternalFlwID(Date toDate, String Ext_Flw_Id){
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.and(
                Restrictions.lt("creationDate",toDate),
                Restrictions.disjunction().add(Restrictions.and(
                        Restrictions.eq("status","ACTIVE").ignoreCase(),
                        Restrictions.gt("courseStartDate",toDate)))
                        .add(Restrictions.eq("status","INACTIVE").ignoreCase()),
                Restrictions.eq("jobStatus","ACTIVE").ignoreCase(),
                Restrictions.eq("designation","ASHA").ignoreCase(),
                Restrictions.eq("externalFlwId",Ext_Flw_Id)
        ));
        return (FrontLineWorkers) criteria.uniqueResult();

    }

    @Override
    public FrontLineWorkers getINactiveFrontLineWorkerByExternalFlwIDAndStateId(Date toDate, String Ext_Flw_Id, Integer stateId) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.and(
                Restrictions.eq("state", stateId),
                Restrictions.eq("externalFlwId",Ext_Flw_Id)
        ));
        return (FrontLineWorkers) criteria.uniqueResult();
    }

    @Override
    public List<FrontLineWorkers> getAllFrontLineWorkers(Date toDate, Integer stateId) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.and(
                Restrictions.lt("creationDate",toDate),
                Restrictions.eq("state", stateId)
        ));
        return (List<FrontLineWorkers>) criteria.list();
    }
}
