package com.beehyv.nmsreporting.dao.impl;

import com.beehyv.nmsreporting.dao.AbstractDao;
import com.beehyv.nmsreporting.dao.MACourseCompletionDao;
import com.beehyv.nmsreporting.entity.CourseCompletionDTO;
import com.beehyv.nmsreporting.model.MACourseCompletion;
import org.hibernate.*;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.IntegerType;
import org.hibernate.type.LongType;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository("maCourseCompletionDao")
@Transactional
public class MACourseCompletionDaoImpl extends AbstractDao<Long,MACourseCompletion> implements MACourseCompletionDao {

    @Override
    public List<CourseCompletionDTO> findBySentNotificationIsFalseAndHasPassed() {

        Session session = getSession();

        //TODO -- LIMIT NEED TO BE REMOVED BEFORE PRODUCTION
        // Create the native SQL query with a JOIN
        String sql = "SELECT ma.id as Id, ma.flw_id as flwId , ma.score as score, ma.has_passed as passed, ma.chapter_wise_score as chapterWiseScore, ma.last_delivery_status as lastDeliveryStatus, ma.sent_notification as sentNotification, ma.modificationdate as lastModifiedDate, ma.schedule_message_sent as scheduleMessageSent, flw.flw_msisdn AS mobileNumber, flw.language as languageId" +
                "  FROM ma_course_completion ma " +  "INNER JOIN front_line_worker flw ON ma.flw_id = flw.flw_id WHERE ma.schedule_message_sent = 0 AND ma.has_passed = 1 LIMIT 10";

        SQLQuery query = session.createSQLQuery(sql);

        List<Object[]> results = query.list();

        List<CourseCompletionDTO> courseCompletionDTOs = new ArrayList<>();
        for (Object[] row : results) {
            MACourseCompletion macc = new MACourseCompletion();
            BigInteger bigIntegerId = (BigInteger) row[0];
            Long id = bigIntegerId.longValue();
            macc.setId(id);
            BigInteger bigIntegerFlwId = (BigInteger) row[1];
            Long flwId = bigIntegerFlwId.longValue();
            macc.setFlwId(flwId);
            macc.setScore((Integer) row[2]);
            macc.setPassed((Boolean) row[3]);
            macc.setChapterWiseScore((String) row[4]);
            macc.setLastDeliveryStatus((String) row[5]);
            macc.setSentNotification((Boolean) row[6]);
            macc.setLastModifiedDate((Date) row[7]);
            macc.setScheduleMessageSent((Boolean) row[8]);

            BigInteger bigMobile = (BigInteger) row[9];
            Long mobile = bigMobile.longValue();
            String languageId = (String) row[10];

            CourseCompletionDTO dto = new CourseCompletionDTO(macc, mobile, languageId);
            courseCompletionDTOs.add(dto);
        }

        return courseCompletionDTOs;

    }
    public Date getLastEtlTimeForTableId(int tableId) {
        SQLQuery query = getSession().createSQLQuery("SELECT last_etl_time FROM ETL_info_table WHERE table_id = :tableId"); // table id is 21 for MACourseCompletion
        query.setParameter("tableId", tableId);
        Date lastEtlTime = (Date) query.uniqueResult();
        return lastEtlTime;
    }

    public MACourseCompletion getAshaByFLWId(long flwId ) {
        Criteria criteria = getSession().createCriteria(MACourseCompletion.class);
        criteria.add(Restrictions.eq("flwId", flwId));
        criteria.addOrder(Order.desc("Id"));
        criteria.setMaxResults(1);
        return (MACourseCompletion) criteria.uniqueResult();
    }

    @Override
    public MACourseCompletion getAshaById(long id ) {
        Criteria criteria = getSession().createCriteria(MACourseCompletion.class);
        criteria.add(Restrictions.eq("Id", id));
        MACourseCompletion result = (MACourseCompletion) criteria.uniqueResult();
        return result;
    }

    public long getAshaPhoneNo(long flwId) {
        SQLQuery query = getSession().createSQLQuery("SELECT flw_msisdn FROM front_line_worker WHERE flw_id = :flwId")
                .addScalar("flw_msisdn", LongType.INSTANCE);

        query.setParameter("flwId", flwId);
        long phone_no = (long) query.uniqueResult();
        return phone_no;
    }
    public int getAshaLanguageId(long flwId) {
        SQLQuery query = getSession().createSQLQuery("SELECT language_id FROM front_line_worker WHERE flw_id = :flwId")
                .addScalar("language_id", IntegerType.INSTANCE);

        query.setParameter("flwId", flwId);
        Object resultObject = query.uniqueResult();
        if(resultObject == null){
            return -1;
        }
        int languageId = (int) resultObject;
        return languageId;
    }
    public void updateMACourseCompletion(MACourseCompletion maCourseCompletion) {
        update(maCourseCompletion);
    }

    public void updateLastEtlTimeForTableId(int tableId) {
        Date dt = getLastEtlTimeForTableId(tableId);
        Transaction tx = null;
        try {
            tx = getSession().getTransaction();
            if (tx != null && tx.isActive()) {
                tx.commit();
                tx = getSession().beginTransaction();
            } else {
                tx = getSession().beginTransaction();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        SQLQuery query = getSession().createSQLQuery("UPDATE ETL_info_table\n" +
                "SET last_etl_time = (DATE_ADD(:dt, INTERVAL 1 DAY)) \n" +
                "WHERE table_id = :table_id");
        query.setParameter("table_id", tableId);
        query.setParameter("dt", dt);
        try {
            query.executeUpdate();
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


