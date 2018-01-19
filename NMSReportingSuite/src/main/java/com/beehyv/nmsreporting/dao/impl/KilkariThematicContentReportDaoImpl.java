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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by himanshu on 06/10/17.
 */

@Repository("kilkariThematicContentReportDao")
public class KilkariThematicContentReportDaoImpl extends AbstractDao<Integer,KilkariThematicContent> implements KilkariThematicContentReportDao{

    @Override
    public KilkariThematicContent getKilkariThematicContentReportData(Date date, String week_id){

        KilkariThematicContent kilkariThematicContent;
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.and(
                Restrictions.eq("date", date),
                Restrictions.eq("messageWeekNumber", week_id)
        ));

        List<KilkariThematicContent> result = (List<KilkariThematicContent>)criteria.list();
        BigDecimal d = new BigDecimal(0.00);
        if(result.isEmpty()){
            kilkariThematicContent = new KilkariThematicContent(0,date,"",week_id,(long)0,(long)0,d);
            return kilkariThematicContent;
        }
        kilkariThematicContent = result.get(0);
        kilkariThematicContent.setCallsAnswered(kilkariThematicContent.getCallsAnswered() == null ? 0 : kilkariThematicContent.getCallsAnswered());
        kilkariThematicContent.setMinutesConsumed(kilkariThematicContent.getMinutesConsumed() == null ? d : kilkariThematicContent.getMinutesConsumed());
        kilkariThematicContent.setUniqueBeneficiariesCalled(kilkariThematicContent.getUniqueBeneficiariesCalled() == null ? 0 : kilkariThematicContent.getUniqueBeneficiariesCalled());
        kilkariThematicContent.setTheme(kilkariThematicContent.getTheme() == null ? "" : kilkariThematicContent.getTheme());
        return kilkariThematicContent;
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
}
