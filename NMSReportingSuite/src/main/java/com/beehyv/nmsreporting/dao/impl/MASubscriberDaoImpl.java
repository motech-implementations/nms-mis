package com.beehyv.nmsreporting.dao.impl;

import com.beehyv.nmsreporting.dao.AbstractDao;
import com.beehyv.nmsreporting.dao.MASubscriberDao;
import com.beehyv.nmsreporting.model.AggregateCumulativeMA;
import com.beehyv.nmsreporting.model.AggregateDailyMA;
import com.beehyv.nmsreporting.model.User;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

/**
 * Created by beehyv on 20/9/17.
 */
@Repository("maSubscriberDao")
public class MASubscriberDaoImpl extends AbstractDao<Integer, User>  implements MASubscriberDao {
    @Override
    public List<Object> getMASubscriberCountDaily(String locationType, Integer locationId, Date fromDate, Date toDate){

        Criteria criteria = getSession().createCriteria(AggregateDailyMA.class);

        return null;
    }

    @Override
    public List<AggregateCumulativeMA> getMASubscriberCountSummary(String locationType, Integer locationId, Date fromDate, Date toDate){

        Criteria criteria = getSession().createCriteria(AggregateCumulativeMA.class);

        if(locationId == 0){
            criteria.add(Restrictions.and(
                    Restrictions.or(Restrictions.eq("date",fromDate),Restrictions.eq("date",toDate)),
                    Restrictions.eq("locationType",locationType)
            ));
            return (List<AggregateCumulativeMA>) criteria.list();
        }
        else{
            criteria.add(Restrictions.and(
                    Restrictions.eq("locationId",locationId.longValue()),
                    Restrictions.eq("locationType",locationType),
                    Restrictions.or(Restrictions.eq("date",fromDate),Restrictions.eq("date",toDate))
            ));
            return (List<AggregateCumulativeMA>) criteria.list();
        }
    }

    @Override
    public Integer getRejectedAshas(Integer locationId, String locationType, Date fromDate, Date toDate) {
        if(locationType.equalsIgnoreCase("state")) {
            Query query = getSession().createSQLQuery("select \n" +
                    "'SUBCENTRE' as location_type,\n" +
                    "r.subcentre_id as location_id,\n" +
                    "count(*) as rejected\n" +
                    "from flw_import_rejection r \n" +
                    "where r.creation_date between '2017-09-01 20:20:01'and '2018-09-01 20:20:01' and r.accepted = 0 and r.district_id = :locationId and" +
                    "r.type = 'ASHA' and r.rejection_reason not in ('UPDATED_RECORD_ALREADY_EXISTS','FLW_TYPE_NOT_ASHA','FLW_IMPORT_ERROR','GF_STATUS_INACTIVE')\n" +
                    "group by r.subcentre_id;");
            query.setParameter("fromDate",fromDate);
            query.setParameter("toDate",toDate);
            query.setParameter("locationId",locationId);
            return ((BigInteger) query.uniqueResult()).intValue();
        }
        if(locationType.equalsIgnoreCase("district")){
            Query query = getSession().createSQLQuery("select count(distinct f.flw_id) from front_line_worker f where " +
                    "f.flw_designation = 'ASHA' and f.district_id = :locationId AND "+"f.flw_id in (select distinct m.flw_id from ma_course_completion m where" +
                    " m.has_passed = 0 and (m.modificationdate between :fromDate"+" AND :toDate"+")) and  f.flw_id not in (select distinct m1.flw_id from ma_course_completion m1 " +
                    "where m1.has_passed = 1 and m1.modificationdate < :toDate"+")");
            query.setParameter("fromDate",fromDate);
            query.setParameter("toDate",toDate);
            query.setParameter("locationId",locationId);
            return ((BigInteger) query.uniqueResult()).intValue();
        }
        if(locationType.equalsIgnoreCase("block")){
            Query query = getSession().createSQLQuery("select count(distinct f.flw_id) from front_line_worker f where " +
                    "f.flw_designation = 'ASHA' and f.block_id = :locationId AND "+"f.flw_id in (select distinct m.flw_id from ma_course_completion m where" +
                    " m.has_passed = 0 and (m.modificationdate between :fromDate"+" AND :toDate"+")) and  " +
                    "f.flw_id not in (select distinct m1.flw_id from ma_course_completion m1 where m1.has_passed = 1 and m1.modificationdate < :toDate"+")");
            query.setParameter("fromDate",fromDate);
            query.setParameter("toDate",toDate);
            query.setParameter("locationId",locationId);
            return ((BigInteger) query.uniqueResult()).intValue();
        }
        if(locationType.equalsIgnoreCase("subcenter")) {
            Query query = getSession().createSQLQuery("select count(distinct f.flw_id) from front_line_worker f where" +
                    " f.flw_designation = 'ASHA' and f.healthsubfacility_id = :locationId AND "+"f.flw_id in (select distinct m.flw_id from ma_course_completion m where" +
                    " m.has_passed = 0 and (m.modificationdate between :fromDate"+" AND :toDate"+")) and  " +
                    "f.flw_id not in (select distinct m1.flw_id from ma_course_completion m1 where m1.has_passed = 1 and m1.modificationdate < :toDate"+")");
            query.setParameter("fromDate",fromDate);
            query.setParameter("toDate",toDate);
            query.setParameter("locationId",locationId);
            return ((BigInteger) query.uniqueResult()).intValue();

        }

        return 0;
    }

//    @Override
//    public List<AggregateCumulativeMA> getMASubscriberCountSummaryEnd(String locationType, Integer locationId, Date fromDate, Date toDate){
//
//        Criteria criteria = getSession().createCriteria(AggregateCumulativeMA.class);
//
//        if(locationId == 0){
//            criteria.add(Restrictions.and(
//                    Restrictions.eq("date",toDate),
//                    Restrictions.eq("locationType",locationType)
//            ));
//            return (List<AggregateCumulativeMA>) criteria.list();
//        }
//        else{
//            criteria.add(Restrictions.and(
//                    Restrictions.eq("locationId",locationId.longValue()),
//                    Restrictions.eq("locationType",locationType),
//                    Restrictions.eq("date",toDate)
//            ));
//            return (List<AggregateCumulativeMA>) criteria.list();
//        }
//
//    }
}
