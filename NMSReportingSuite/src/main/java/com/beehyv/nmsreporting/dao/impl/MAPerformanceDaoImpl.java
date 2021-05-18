package com.beehyv.nmsreporting.dao.impl;

import com.beehyv.nmsreporting.dao.AbstractDao;
import com.beehyv.nmsreporting.dao.MAPerformanceDao;
import com.beehyv.nmsreporting.model.FrontLineWorkers;
import com.beehyv.nmsreporting.model.User;
import net.sf.ehcache.hibernate.HibernateUtil;
import org.hibernate.*;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.beehyv.nmsreporting.utils.ServiceFunctions.dateAdder;

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
//        fromDate = dateAdder(fromDate,1);
        if(locationType.equalsIgnoreCase("state")) {
            Query query = getSession().createSQLQuery("select count(*) from front_line_worker f  " +
                    "where f.flw_id not in  (select distinct m.flw_id  from ma_course_completion m  where m.has_passed = 1 and m.creationDate < :fromDate)  " +
                    "and f.flw_id not in  (select distinct m1.flw_id from ma_call_detail_measure m1 where m1.start_time between :fromDate and :toDate) " +
                    "and f.flw_id in (select distinct m2.flw_id from ma_call_detail_measure m2 where m2.start_time < :fromDate) " +
                    "and f.flw_designation = 'ASHA' and f.flw_status = 'ACTIVE' and f.job_status = 'ACTIVE' and f.state_id = :locationId");
            query.setParameter("fromDate",fromDate);
            query.setParameter("toDate",toDate);
            query.setParameter("locationId",locationId);
            return ((BigInteger) query.uniqueResult()).longValue();
        }
        if(locationType.equalsIgnoreCase("district")){
            Query query = getSession().createSQLQuery("select count(*) from front_line_worker f  " +
                    "where f.flw_id not in  (select distinct m.flw_id  from ma_course_completion m  where m.has_passed = 1 and m.creationDate < :fromDate)  " +
                    "and f.flw_id not in  (select distinct m1.flw_id from ma_call_detail_measure m1 where m1.start_time between :fromDate and :toDate) " +
                    "and f.flw_id in (select distinct m2.flw_id from ma_call_detail_measure m2 where m2.start_time < :fromDate) " +
                    "and f.flw_designation = 'ASHA' and f.flw_status = 'ACTIVE' and f.job_status = 'ACTIVE' and f.district_id = :locationId");
            query.setParameter("fromDate",fromDate);
            query.setParameter("toDate",toDate);
            query.setParameter("locationId",locationId);
            return ((BigInteger) query.uniqueResult()).longValue();
        }
        if(locationType.equalsIgnoreCase("block")){
            Query query = getSession().createSQLQuery("select count(*) from front_line_worker f  " +
                    "where f.flw_id not in  (select distinct m.flw_id  from ma_course_completion m  where m.has_passed = 1 and m.creationDate < :fromDate)  " +
                    "and f.flw_id not in  (select distinct m1.flw_id from ma_call_detail_measure m1 where m1.start_time between :fromDate and :toDate) " +
                    "and f.flw_id in (select distinct m2.flw_id from ma_call_detail_measure m2 where m2.start_time < :fromDate) " +
                    "and f.flw_designation = 'ASHA' and f.flw_status = 'ACTIVE' and f.job_status = 'ACTIVE' and f.block_id = :locationId");
            query.setParameter("fromDate",fromDate);
            query.setParameter("toDate",toDate);
            query.setParameter("locationId",locationId);
            return ((BigInteger) query.uniqueResult()).longValue();
        }
        if(locationType.equalsIgnoreCase("subcenter")) {
            Query query = getSession().createSQLQuery("select count(*) from front_line_worker f  " +
                    "where f.flw_id not in  (select distinct m.flw_id  from ma_course_completion m  where m.has_passed = 1 and m.creationDate < :fromDate)  " +
                    "and f.flw_id not in  (select distinct m1.flw_id from ma_call_detail_measure m1 where m1.start_time between :fromDate and :toDate) " +
                    "and f.flw_id in (select distinct m2.flw_id from ma_call_detail_measure m2 where m2.start_time < :fromDate) " +
                    "and f.flw_designation = 'ASHA' and f.flw_status = 'ACTIVE' and f.job_status = 'ACTIVE' and f.healthsubfacility_id = :locationId");
            query.setParameter("fromDate",fromDate);
            query.setParameter("toDate",toDate);
            query.setParameter("locationId",locationId);
            return ((BigInteger) query.uniqueResult()).longValue();
        }

        return (long)0;
    }

    @Override
    public Long accessedAtLeastOnce(Integer locationId, String locationType, Date fromDate, Date toDate){
//        fromDate = dateAdder(fromDate,1);
        if(locationType.equalsIgnoreCase("state")) {
            Query query = getSession().createSQLQuery("select count(*) from front_line_worker f  " +
                    "where f.flw_id not in  (select distinct m.flw_id  from ma_course_completion m  where m.has_passed = 1 and m.creationDate < :toDate)  " +
                    "and f.flw_id in  (select distinct m1.flw_id from ma_call_detail_measure m1 where m1.start_time between :fromDate and :toDate) " +
                    "and f.flw_id in (select distinct m2.flw_id from ma_call_detail_measure m2 where m2.start_time < :fromDate) " +
                    "and f.flw_designation = 'ASHA' and f.flw_status = 'ACTIVE' and f.job_status = 'ACTIVE' and f.state_id = :locationId");
            query.setParameter("fromDate",fromDate);
            query.setParameter("toDate",toDate);
            query.setParameter("locationId",locationId);
            return ((BigInteger) query.uniqueResult()).longValue();
        }
        if(locationType.equalsIgnoreCase("district")){
            Query query = getSession().createSQLQuery("select count(*) from front_line_worker f  " +
                    "where f.flw_id not in  (select distinct m.flw_id  from ma_course_completion m  where m.has_passed = 1 and m.creationDate < :toDate)  " +
                    "and f.flw_id in  (select distinct m1.flw_id from ma_call_detail_measure m1 where m1.start_time between :fromDate and :toDate) " +
                    "and f.flw_id in (select distinct m2.flw_id from ma_call_detail_measure m2 where m2.start_time < :fromDate) " +
                    "and f.flw_designation = 'ASHA' and f.flw_status = 'ACTIVE' and f.job_status = 'ACTIVE' and f.district_id = :locationId");
            query.setParameter("fromDate",fromDate);
            query.setParameter("toDate",toDate);
            query.setParameter("locationId",locationId);
            return ((BigInteger) query.uniqueResult()).longValue();
        }
        if(locationType.equalsIgnoreCase("block")){
            Query query = getSession().createSQLQuery("select count(*) from front_line_worker f  " +
                    "where f.flw_id not in  (select distinct m.flw_id  from ma_course_completion m  where m.has_passed = 1 and m.creationDate < :toDate)  " +
                    "and f.flw_id in  (select distinct m1.flw_id from ma_call_detail_measure m1 where m1.start_time between :fromDate and :toDate) " +
                    "and f.flw_id in (select distinct m2.flw_id from ma_call_detail_measure m2 where m2.start_time < :fromDate) " +
                    "and f.flw_designation = 'ASHA' and f.flw_status = 'ACTIVE' and f.job_status = 'ACTIVE' and f.block_id = :locationId");
            query.setParameter("fromDate",fromDate);
            query.setParameter("toDate",toDate);
            query.setParameter("locationId",locationId);
            return ((BigInteger) query.uniqueResult()).longValue();
        }
        if(locationType.equalsIgnoreCase("subcenter")) {
            Query query = getSession().createSQLQuery("select count(*) from front_line_worker f  " +
                    "where f.flw_id not in  (select distinct m.flw_id  from ma_course_completion m  where m.has_passed = 1 and m.creationDate < :toDate)  " +
                    "and f.flw_id in  (select distinct m1.flw_id from ma_call_detail_measure m1 where m1.start_time between :fromDate and :toDate) " +
                    "and f.flw_id in (select distinct m2.flw_id from ma_call_detail_measure m2 where m2.start_time < :fromDate) " +
                    "and f.flw_designation = 'ASHA' and f.flw_status = 'ACTIVE' and f.job_status = 'ACTIVE' and f.healthsubfacility_id = :locationId");
            query.setParameter("fromDate",fromDate);
            query.setParameter("toDate",toDate);
            query.setParameter("locationId",locationId);
            return ((BigInteger) query.uniqueResult()).longValue();

        }

        return (long)0;
    }

    @Override
    public Integer getAshasFailed(Integer locationId, String locationType, Date fromDate, Date toDate){
//        fromDate = dateAdder(fromDate,1);
        if(locationType.equalsIgnoreCase("state")) {
            Query query = getSession().createSQLQuery("select count(distinct f.flw_id) from front_line_worker f where f.flw_designation = 'ASHA' and f.state_id = :locationId AND "+"f.flw_id in (select distinct m.flw_id from ma_course_completion m where m.has_passed = 0 and (m.modificationdate between :fromDate"+" AND :toDate"+")) and  f.flw_id not in (select distinct m1.flw_id from ma_course_completion m1 where m1.has_passed = 1 and m1.modificationdate < :toDate"+")");
            query.setParameter("fromDate",fromDate);
            query.setParameter("toDate",toDate);
            query.setParameter("locationId",locationId);
            return ((BigInteger) query.uniqueResult()).intValue();
        }
        if(locationType.equalsIgnoreCase("district")){
            Query query = getSession().createSQLQuery("select count(distinct f.flw_id) from front_line_worker f where f.flw_designation = 'ASHA' and f.district_id = :locationId AND "+"f.flw_id in (select distinct m.flw_id from ma_course_completion m where m.has_passed = 0 and (m.modificationdate between :fromDate"+" AND :toDate"+")) and  f.flw_id not in (select distinct m1.flw_id from ma_course_completion m1 where m1.has_passed = 1 and m1.modificationdate < :toDate"+")");
            query.setParameter("fromDate",fromDate);
            query.setParameter("toDate",toDate);
            query.setParameter("locationId",locationId);
            return ((BigInteger) query.uniqueResult()).intValue();
        }
        if(locationType.equalsIgnoreCase("block")){
            Query query = getSession().createSQLQuery("select count(distinct f.flw_id) from front_line_worker f where f.flw_designation = 'ASHA' and f.block_id = :locationId AND "+"f.flw_id in (select distinct m.flw_id from ma_course_completion m where m.has_passed = 0 and (m.modificationdate between :fromDate"+" AND :toDate"+")) and  f.flw_id not in (select distinct m1.flw_id from ma_course_completion m1 where m1.has_passed = 1 and m1.modificationdate < :toDate"+")");
            query.setParameter("fromDate",fromDate);
            query.setParameter("toDate",toDate);
            query.setParameter("locationId",locationId);
            return ((BigInteger) query.uniqueResult()).intValue();
        }
        if(locationType.equalsIgnoreCase("subcenter")) {
            Query query = getSession().createSQLQuery("select count(distinct f.flw_id) from front_line_worker f where f.flw_designation = 'ASHA' and f.healthsubfacility_id = :locationId AND "+"f.flw_id in (select distinct m.flw_id from ma_course_completion m where m.has_passed = 0 and (m.modificationdate between :fromDate"+" AND :toDate"+")) and  f.flw_id not in (select distinct m1.flw_id from ma_course_completion m1 where m1.has_passed = 1 and m1.modificationdate < :toDate"+")");
            query.setParameter("fromDate",fromDate);
            query.setParameter("toDate",toDate);
            query.setParameter("locationId",locationId);
            return ((BigInteger) query.uniqueResult()).intValue();

        }

        return 0;
    }

    @Override
    public Long getAshaActivated(Integer locationId, String locationType, Date toDate) {

        System.out.println("--------------================================----------------------");
        System.out.println();System.out.println();System.out.println();System.out.println();System.out.println();System.out.println(locationId+"  "+locationType );
        if(locationType.equalsIgnoreCase("state")) {


            Query query2 = getSession().createSQLQuery("select count(distinct f.flw_id) from front_line_worker f where f.flw_designation = 'ASHA' and f.state_id = :locationId AND "+"f.job_status = 'ACTIVE' and f.modificationdate < :toDate ");
//            query2.setParameter("fromDate",fromDate);
            query2.setParameter("toDate",toDate);
            query2.setParameter("locationId",locationId);
            return ((BigInteger) query2.uniqueResult()).longValue();

        }
        if(locationType.equalsIgnoreCase("district")){
            Query query2 = getSession().createSQLQuery("select count(distinct f.flw_id) from front_line_worker f where f.flw_designation = 'ASHA' and f.district_id = :locationId AND "+"f.job_status = 'ACTIVE' and f.modificationdate < :toDate ");
//            query2.setParameter("fromDate",fromDate);
            query2.setParameter("toDate",toDate);
            query2.setParameter("locationId",locationId);
            return ((BigInteger) query2.uniqueResult()).longValue();
        }
        if(locationType.equalsIgnoreCase("block")){

            Query query2 = getSession().createSQLQuery("select count(distinct f.flw_id) from front_line_worker f where f.flw_designation = 'ASHA' and f.block_id = :locationId AND "+"f.job_status = 'ACTIVE' and f.modificationdate < :toDate ");
//            query2.setParameter("fromDate",fromDate);
            query2.setParameter("toDate",toDate);
            query2.setParameter("locationId",locationId);
            return ((BigInteger) query2.uniqueResult()).longValue();
        }
        if(locationType.equalsIgnoreCase("subcenter")) {

            Query query2 = getSession().createSQLQuery("select count(distinct f.flw_id) from front_line_worker f where f.flw_designation = 'ASHA' and f.healthsubfacility_id = :locationId AND "+"f.job_status = 'ACTIVE' and f.modificationdate < :toDate ");
//            query2.setParameter("fromDate",fromDate);
            query2.setParameter("toDate",toDate);
            query2.setParameter("locationId",locationId);
            return ((BigInteger) query2.uniqueResult()).longValue();

        }

        return 0l;
    }

    @Override
    public Long getAshaDeactivated(Integer locationId, String locationType, Date toDate) {
        if(locationType.equalsIgnoreCase("state")) {
            Query query2 = getSession().createSQLQuery("select count(distinct f.flw_id) from front_line_worker f where f.flw_designation = 'ASHA' and f.state_id = :locationId AND "+"f.job_status = 'INACTIVE' and f.modificationdate < :toDate ");
//            query2.setParameter("fromDate",fromDate);
            query2.setParameter("toDate",toDate);
            query2.setParameter("locationId",locationId);
            return ((BigInteger) query2.uniqueResult()).longValue();

        }
        if(locationType.equalsIgnoreCase("district")){
            Query query2 = getSession().createSQLQuery("select count(distinct f.flw_id) from front_line_worker f where f.flw_designation = 'ASHA' and f.district_id = :locationId AND "+"f.job_status = 'INACTIVE' and f.modificationdate < :toDate ");
//            query2.setParameter("fromDate",fromDate);
            query2.setParameter("toDate",toDate);
            query2.setParameter("locationId",locationId);
            return ((BigInteger) query2.uniqueResult()).longValue();

        }
        if(locationType.equalsIgnoreCase("block")){

            Query query2 = getSession().createSQLQuery("select count(distinct f.flw_id) from front_line_worker f where f.flw_designation = 'ASHA' and f.block_id = :locationId AND "+"f.job_status = 'INACTIVE' and f.modificationdate < :toDate ");
//            query2.setParameter("fromDate",fromDate);
            query2.setParameter("toDate",toDate);
            query2.setParameter("locationId",locationId);
            return ((BigInteger) query2.uniqueResult()).longValue();

        }
        if(locationType.equalsIgnoreCase("subcenter")) {
            Query query2 = getSession().createSQLQuery("select count(distinct f.flw_id) from front_line_worker f where f.flw_designation = 'ASHA' and f.healthsubfacility_id = :locationId AND "+"f.job_status = 'INACTIVE' and f.modificationdate < :toDate ");
//            query2.setParameter("fromDate",fromDate);
            query2.setParameter("toDate",toDate);
            query2.setParameter("locationId",locationId);
            return ((BigInteger) query2.uniqueResult()).longValue();
        }
        return 0l;
    }

    @Override
    public Long getAshaRefresherCourse(Integer locationId, String locationType) {
       // number of asha doing refresher course
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DATE, -7);
        Date fromDate = cal.getTime();
       Date toDate = new Date();
        if(locationType.equalsIgnoreCase("state")) {
//            Query query1 =  getSession().createSQLQuery("select r.flw_id from (select distinct f.flw_id from front_line_worker f  join ma_course_completion ma on f.flw_id = ma.flw_id  where f.flw_status= 'ACTIVE' and f.state_id=:locationId ) as r group by r.flw_id having count(*)>0");
            Query query1 =  getSession().createSQLQuery("select distinct f.flw_id from front_line_worker as f join ma_call_detail_measure as ma on ma.flw_id=f.flw_id and  f.flw_status= 'ACTIVE' and f.state_id= :locationId and ma.end_time between :fromDate and :toDate");
            query1.setParameter("locationId",locationId);
            query1.setParameter("fromDate",fromDate);
            query1.setParameter("toDate",toDate);
            List<Integer> flwList= query1.list();

            System.out.println(flwList.size());
            for(int i=0;i<flwList.size();i++){
                System.out.println("element: "+flwList.get(i));
            }
            if(flwList.size()!=0) {
                Query query2 = getSession().createSQLQuery("\n" +
                        "select count(distinct t.ma_flw) from (select m.flw_id as ma_flw,min(m.modificationdate) as ma_modificationDate from ma_course_completion as m where m.flw_id in :flw group by m.flw_id ) t" +
                        "  join   (select flw_id,max(end_time) as call_end_time from ma_call_detail_measure where flw_id in :flw  group by flw_id )t2 on t2.flw_id=t.ma_flw where t.ma_modificationdate<t2.call_end_time");
//                query2.setParameter("fromDate", fromDate);
//                query2.setParameter("toDate", toDate);
//            query2.setParameter("locationId",locationId);
                query2.setParameterList("flw", flwList);
                return ((BigInteger) query2.uniqueResult()).longValue();
            }
        }
        if(locationType.equalsIgnoreCase("district")){
            Query query1 =  getSession().createSQLQuery("select distinct f.flw_id from front_line_worker as f join ma_call_detail_measure as ma on ma.flw_id=f.flw_id and  f.flw_status= 'ACTIVE' and f.district_id= :locationId and ma.end_time between :fromDate and :toDate");
            query1.setParameter("locationId",locationId);
            query1.setParameter("fromDate",fromDate);
            query1.setParameter("toDate",toDate);
            List<Integer> flwList= query1.list();
            if(flwList.size()!=0) {
                Query query2 = getSession().createSQLQuery("\n" +
                        "select count(distinct t.ma_flw) from (select m.flw_id as ma_flw,min(m.modificationdate) as ma_modificationDate from ma_course_completion as m where m.flw_id in :flw group by m.flw_id ) t" +
                        "  join   (select flw_id,max(end_time) as call_end_time from ma_call_detail_measure where flw_id in :flw  group by flw_id )t2 on t2.flw_id=t.ma_flw where t.ma_modificationdate<t2.call_end_time");
               // query2.setParameter("fromDate", fromDate);
                //query2.setParameter("toDate", toDate);
//            query2.setParameter("locationId",locationId);
                query2.setParameterList("flw", flwList);
                return ((BigInteger) query2.uniqueResult()).longValue();
            }

        }
        if(locationType.equalsIgnoreCase("block")){

//            Query query1 =  getSession().createSQLQuery("select r.flw_id from (select distinct f.flw_id from front_line_worker f join ma_course_completion ma on f.flw_id = ma.flw_id  where f.flw_status= 'ACTIVE' and f.block_id=:locationId ) as r group by r.flw_id having count(*)>0");
            Query query1 =  getSession().createSQLQuery("select distinct f.flw_id from front_line_worker as f join ma_call_detail_measure as ma on ma.flw_id=f.flw_id and  f.flw_status= 'ACTIVE' and f.state_id= :locationId and ma.end_time between :fromDate and :toDate");
            query1.setParameter("locationId",locationId);
            query1.setParameter("fromDate",fromDate);
            query1.setParameter("toDate",toDate);
            List<Integer> flwList= query1.list();

            if(flwList.size()!=0) {
                Query query2 = getSession().createSQLQuery("\n" +
                        "select count(distinct t.ma_flw) from (select m.flw_id as ma_flw,min(m.modificationdate) as ma_modificationDate from ma_course_completion as m where m.flw_id in :flw group by m.flw_id ) t" +
                        "  join   (select flw_id,max(end_time) as call_end_time from ma_call_detail_measure where flw_id in :flw  group by flw_id )t2 on t2.flw_id=t.ma_flw where t.ma_modificationdate<t2.call_end_time");
               // query2.setParameter("fromDate", fromDate);
               // query2.setParameter("toDate", toDate);
//            query2.setParameter("locationId",locationId);
                query2.setParameterList("flw", flwList);
                return ((BigInteger) query2.uniqueResult()).longValue();
            }
        }
        if(locationType.equalsIgnoreCase("subcenter")) {
//            Query query1 =  getSession().createSQLQuery("select r.flw_id from (select distinct f.flw_id from front_line_worker f  join ma_course_completion ma on f.flw_id = ma.flw_id  where f.flw_status= 'ACTIVE' and f.healthsubfacility_id=:locationId ) as r group by r.flw_id having count(*)>0");
            Query query1 =  getSession().createSQLQuery("select distinct f.flw_id from front_line_worker as f join ma_call_detail_measure as ma on ma.flw_id=f.flw_id and  f.flw_status= 'ACTIVE' and f.state_id= :locationId and ma.end_time between :fromDate and :toDate");
            query1.setParameter("locationId",locationId);
            query1.setParameter("fromDate",fromDate);
            query1.setParameter("toDate",toDate);
            List<Integer> flwList= query1.list();
            if(flwList.size()!=0) {
                Query query2 = getSession().createSQLQuery("\n" +
                        "select count(distinct t.ma_flw) from (select m.flw_id as ma_flw,min(m.modificationdate) as ma_modificationDate from ma_course_completion as m where m.flw_id in :flw group by m.flw_id ) t" +
                        "  join   (select flw_id,max(end_time) as call_end_time from ma_call_detail_measure where flw_id in :flw  group by flw_id )t2 on t2.flw_id=t.ma_flw where t.ma_modificationdate<t2.call_end_time");
                //query2.setParameter("fromDate", fromDate);
               // query2.setParameter("toDate", toDate);
//            query2.setParameter("locationId",locationId);
                query2.setParameterList("flw", flwList);
                return ((BigInteger) query2.uniqueResult()).longValue();
            }
        }
        return 0l;
    }
}
