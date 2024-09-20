package com.beehyv.nmsreporting.dao.impl;


import com.beehyv.nmsreporting.dao.AbstractDao;
import com.beehyv.nmsreporting.dao.KilkariThematicContentReportDao;
import com.beehyv.nmsreporting.entity.AggregateKilkariReportsDto;
import com.beehyv.nmsreporting.entity.ReportRequest;
import com.beehyv.nmsreporting.model.AggregateCumulativeKilkari;
import com.beehyv.nmsreporting.model.KilkariThematicContent;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

/**
 * Created by himanshu on 06/10/17.
 */

@Repository("kilkariThematicContentReportDao")
public class KilkariThematicContentReportDaoImpl extends AbstractDao<Integer,KilkariThematicContent> implements KilkariThematicContentReportDao{

    @Override
    public Map<String,KilkariThematicContent> getKilkariThematicContentReportData(Integer locationId, String locationType, Date date, String periodType){

        KilkariThematicContent kilkariThematicContent;
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.and(
                Restrictions.eq("locationId",locationId.longValue()),
                Restrictions.eq("locationType",locationType),
                Restrictions.eq("periodType",periodType),
                Restrictions.eq("date", date)
              //  Restrictions.eq("messageWeekNumber", week_id)
        ));

        List<KilkariThematicContent> result = (List<KilkariThematicContent>)criteria.list();
        Double d = new Double(0.00);
        Map<String,KilkariThematicContent> resultMap =  new HashMap<>();
        if(result.isEmpty()){
            kilkariThematicContent = new KilkariThematicContent(0,date,"",(long)0,(long)0,d);
            resultMap.put("",kilkariThematicContent);
            return resultMap;
        }
        for(int i=0;i<72;i++){
            KilkariThematicContent kilkariThematicContent1= new KilkariThematicContent();
            if(i<result.size()) {
                kilkariThematicContent1 = result.get(i);
                kilkariThematicContent1.setCallsAnswered(kilkariThematicContent1.getCallsAnswered() == null ? 0 : kilkariThematicContent1.getCallsAnswered());
                kilkariThematicContent1.setMinutesConsumed(kilkariThematicContent1.getMinutesConsumed() == null ? d : kilkariThematicContent1.getMinutesConsumed());
                kilkariThematicContent1.setUniqueBeneficiariesCalled(kilkariThematicContent1.getUniqueBeneficiariesCalled() == null ? 0 : kilkariThematicContent1.getUniqueBeneficiariesCalled());
            }
            resultMap.put(kilkariThematicContent1.getMessageWeekNumber(),kilkariThematicContent1);
        }
        return resultMap;
    }

    @Override
    public String getMessageWeekNumber(Integer messageWeekNumber){
        Query query = getSession().createSQLQuery("select md.week_id from message_durations md where md.id = :id")
                      .setParameter("id",messageWeekNumber);

        Object result = query.uniqueResult();
        if(result == null){
            return "";
        }
        String week[] = result.toString().split("_");
        return week[0];
    }

    @Override
    public Long getUniqueBeneficiariesCalled(Date startDate, Date toDate, String messageId){
        Query query = getSession().createSQLQuery("select count(DISTINCT bcm.pregnancy_id) from beneficiary_call_measure bcm left join message_durations md on md.id = bcm.campaign_id where bcm.modificationDate >= :startDate and bcm.modificationDate <= :endDate and bcm.call_source = 'OBD' and substring_index(md.week_id,'_',1) = :messageId")
                        .setParameter("startDate",startDate)
                        .setParameter("endDate",toDate)
                        .setParameter("messageId",messageId);
        BigInteger result = (BigInteger) query.uniqueResult();
        if(result == null){
            return (long)0;
        }
        return result.longValue();
    }

    @Override
    public String getMostHeardCallWeek(Integer locationId, String locationType, Date startDate, Date endDate, String periodType) {
        String sql = "SELECT message_week_number " +
                "FROM agg_kilkari_thematic_content " +
                "WHERE location_id = :locationId " +
                "  AND location_type = :locationType " +
                "  AND date BETWEEN :startDate AND :endDate " +
                "  AND message_week_number <> 'w1' " +
                "  AND period_type = :periodType " +
                "GROUP BY message_week_number " +
                "ORDER BY SUM(minutes_consumed) DESC " +
                "LIMIT 1";

        Query query = getSession().createSQLQuery(sql);
        query.setParameter("locationId", locationId);
        query.setParameter("locationType", locationType);
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);
        query.setParameter("periodType", periodType);

        Object result = query.uniqueResult();
        return result != null ? result.toString() : null;
    }

    @Override
    public String getLeastHeardCallWeek(Integer locationId, String locationType, Date startDate, Date endDate, String periodType) {
        String sql = "SELECT message_week_number " +
                "FROM agg_kilkari_thematic_content " +
                "WHERE location_id = :locationId " +
                "  AND location_type = :locationType " +
                "  AND date BETWEEN :startDate AND :endDate " +
                "  AND message_week_number <> 'w1' " +
                "  AND period_type = :periodType " +
                "GROUP BY message_week_number " +
                "ORDER BY SUM(minutes_consumed) ASC " +
                "LIMIT 1";

        Query query = getSession().createSQLQuery(sql);
        query.setParameter("locationId", locationId);
        query.setParameter("locationType", locationType);
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);
        query.setParameter("periodType", periodType);

        Object result = query.uniqueResult();
        return result != null ? result.toString() : null;
    }

    @Override
    public double getAverageDurationOfCalls(Integer locationId, String locationType, Date startDate, Date endDate, String periodType) {
        String sql = "SELECT SUM(minutes_consumed) / NULLIF(SUM(calls_answered), 0) AS average_duration_of_calls " +
                "FROM agg_kilkari_thematic_content " +
                "WHERE location_id = :locationId " +
                "AND location_type = :locationType " +
                "AND date BETWEEN :startDate AND :endDate " +
                "AND period_type = :periodType";

        Query query = getSession().createSQLQuery(sql)
                .setParameter("locationId", locationId)
                .setParameter("locationType", locationType)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .setParameter("periodType", periodType);

        Object result = query.uniqueResult();
        return result != null ? ((Number) result).doubleValue() : 0.0;
    }

}
