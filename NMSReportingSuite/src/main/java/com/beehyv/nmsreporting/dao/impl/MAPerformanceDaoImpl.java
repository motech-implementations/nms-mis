package com.beehyv.nmsreporting.dao.impl;

import com.beehyv.nmsreporting.dao.AbstractDao;
import com.beehyv.nmsreporting.dao.MAPerformanceDao;
import com.beehyv.nmsreporting.model.FrontLineWorkers;
import com.beehyv.nmsreporting.model.User;
import net.sf.ehcache.hibernate.HibernateUtil;
import org.hibernate.*;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by beehyv on 22/9/17.
 */
@Repository("maPerformanceDao")
public class MAPerformanceDaoImpl extends AbstractDao<Integer, User> implements MAPerformanceDao {

    @Override
    public Long accessedNotOnce(Integer locationId, String locationType, Date fromDate, Date toDate){

//        Criteria criteria = getSession().createCriteria(FrontLineWorkers.class);
//        List<FrontLineWorkers> frontLineWorkersList = new ArrayList<>();
//
//        if(locationType.equalsIgnoreCase("State")){
//            criteria.add(Restrictions.lt("state",locationId));
//            frontLineWorkersList = criteria.list();
//        }
//        if(locationType.equalsIgnoreCase("District")){
//            criteria.add(Restrictions.lt("district",locationId));
//            frontLineWorkersList = criteria.list();
//        }
//        if(locationType.equalsIgnoreCase("Block")){
//            criteria.add(Restrictions.lt("block",locationId));
//            frontLineWorkersList = criteria.list();
//        }
//        if(locationType.equalsIgnoreCase("Subcenter")){
//            criteria.add(Restrictions.lt("subcenter",locationId));
//            frontLineWorkersList = criteria.list();
//        }
//
//        Query query = getSession().createSQLQuery("select f from FrontLineWorkers f where f.flwId in (select m.flwId from MACallDetailMeasure m where m.startTime between "+fromDate+" AND "+toDate+" ) AND f.state= "+locationId+
//        "AND NOT IN (select c.flwId from MACourseFirstCompletion c where c.firstCompletionDate <= "+fromDate);
//

//        Query query = getSession().createSQLQuery("select f from FrontLineWorkers f where f.flwId in (select DISTINCT m.flwId from MACallDetailMeasure m where m.startTime between "+fromDate+" AND "+toDate+" ) AND f.state= "+locationId+
//                "AND f.courseStartDate <"+ fromDate +" AND (firstCompletionDate > "+fromDate+" OR firstCompletionDate is NULL)");
        if(locationType.equalsIgnoreCase("state")) {
            Query query = getSession().createQuery("select f from FrontLineWorkers f where f.flwId NOT IN (select DISTINCT m.flwId from MACallDetailMeasure m where m.startTime between :fromDate"+"  AND  :toDate"+"   ) AND f.state = :locationId"+" AND f.courseStartDate < :fromDate"+" AND (f.firstCompletionDate > :fromDate"+"  OR f.firstCompletionDate is NULL)");
            query.setParameter("fromDate",fromDate);
            query.setParameter("toDate",toDate);
            query.setParameter("locationId",locationId);
            return (long)query.list().size();
        }
        if(locationType.equalsIgnoreCase("district")){
            Query query = getSession().createQuery("select f from FrontLineWorkers f where f.flwId NOT IN (select DISTINCT m.flwId from MACallDetailMeasure m where m.startTime between :fromDate"+"  AND  :toDate"+"   ) AND f.district = :locationId"+" AND f.courseStartDate < :fromDate"+" AND (f.firstCompletionDate > :fromDate"+"  OR f.firstCompletionDate is NULL)");
            query.setParameter("fromDate",fromDate);
            query.setParameter("toDate",toDate);
            query.setParameter("locationId",locationId);
            return (long)query.list().size();
        }
        if(locationType.equalsIgnoreCase("block")){
            Query query = getSession().createQuery("select f from FrontLineWorkers f where f.flwId NOT IN (select DISTINCT m.flwId from MACallDetailMeasure m where m.startTime between :fromDate"+"  AND  :toDate"+"   ) AND f.block = :locationId"+" AND f.courseStartDate < :fromDate"+" AND (f.firstCompletionDate > :fromDate"+"  OR f.firstCompletionDate is NULL)");
            query.setParameter("fromDate",fromDate);
            query.setParameter("toDate",toDate);
            query.setParameter("locationId",locationId);
            return (long)query.list().size();
        }
        if(locationType.equalsIgnoreCase("subcenter")) {
            Query query = getSession().createQuery("select f from FrontLineWorkers f where f.flwId NOT IN (select DISTINCT m.flwId from MACallDetailMeasure m where m.startTime between :fromDate"+"  AND  :toDate"+"   ) AND f.subcenter = :locationId"+" AND f.courseStartDate < :fromDate"+" AND (f.firstCompletionDate > :fromDate"+"  OR f.firstCompletionDate is NULL)");
            query.setParameter("fromDate",fromDate);
            query.setParameter("toDate",toDate);
            query.setParameter("locationId",locationId);
            return (long)query.list().size();
        }

        return null;
    }

    @Override
    public Long accessedAtLeastOnce(Integer locationId, String locationType, Date fromDate, Date toDate){

        if(locationType.equalsIgnoreCase("state")) {
            Query query = getSession().createQuery("select f from FrontLineWorkers f where f.flwId IN (select DISTINCT m.flwId from MACallDetailMeasure m where m.startTime between :fromDate"+"  AND  :toDate"+"   ) AND f.state = :locationId"+" AND f.courseStartDate < :fromDate"+" AND (f.firstCompletionDate > :fromDate"+"  OR f.firstCompletionDate is NULL)");
            query.setParameter("fromDate",fromDate);
            query.setParameter("toDate",toDate);
            query.setParameter("locationId",locationId);
            return (long)query.list().size();
        }
        if(locationType.equalsIgnoreCase("district")){
            Query query = getSession().createQuery("select f from FrontLineWorkers f where f.flwId IN (select DISTINCT m.flwId from MACallDetailMeasure m where m.startTime between :fromDate"+"  AND  :toDate"+"   ) AND f.district = :locationId"+" AND f.courseStartDate < :fromDate"+" AND (f.firstCompletionDate > :fromDate"+"  OR f.firstCompletionDate is NULL)");
            query.setParameter("fromDate",fromDate);
            query.setParameter("toDate",toDate);
            query.setParameter("locationId",locationId);
            return (long)query.list().size();
        }
        if(locationType.equalsIgnoreCase("block")){
            Query query = getSession().createQuery("select f from FrontLineWorkers f where f.flwId IN (select DISTINCT m.flwId from MACallDetailMeasure m where m.startTime between :fromDate"+"  AND  :toDate"+"   ) AND f.block = :locationId"+" AND f.courseStartDate < :fromDate"+" AND (f.firstCompletionDate > :fromDate"+"  OR f.firstCompletionDate is NULL)");
            query.setParameter("fromDate",fromDate);
            query.setParameter("toDate",toDate);
            query.setParameter("locationId",locationId);
            return (long)query.list().size();
        }
        if(locationType.equalsIgnoreCase("subcenter")) {
            Query query = getSession().createQuery("select f from FrontLineWorkers f where f.flwId IN (select DISTINCT m.flwId from MACallDetailMeasure m where m.startTime between :fromDate"+"  AND  :toDate"+"   ) AND f.subcenter = :locationId"+" AND f.courseStartDate < :fromDate"+" AND (f.firstCompletionDate > :fromDate"+"  OR f.firstCompletionDate is NULL)");
            query.setParameter("fromDate",fromDate);
            query.setParameter("toDate",toDate);
            query.setParameter("locationId",locationId);
            return (long)query.list().size();
        }

        return null;
    }

}
