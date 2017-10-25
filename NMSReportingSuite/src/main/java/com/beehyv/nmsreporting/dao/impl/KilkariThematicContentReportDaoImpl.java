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
    public List<KilkariThematicContent> getKilkariThematicContentReportData(Date toDate){

        List<KilkariThematicContent> kilkariThematicContentData = new ArrayList<>();
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.and(
                Restrictions.eq("date",toDate)
        ));

        kilkariThematicContentData = (List<KilkariThematicContent>)criteria.list();
        return (List<KilkariThematicContent>)kilkariThematicContentData;
    }

    @Override
    public String getMessageWeekNumber(Integer messageWeekNumber){
        Query query = getSession().createSQLQuery("select md.week_id from message_duration md where md.id = :id")
                      .setParameter("id",messageWeekNumber);

        Object result = query.uniqueResult();
        return (String)result;

    }

    @Override
    public Integer getUniqueBeneficiariesCalled(Date startDate, Date toDate, Integer messageId){
        Query query = getSession().createSQLQuery("select count(DISTINCT bcm.pregnancy_id) from beneficiary_call_measure bcm where bcm.modificationDate >= :startDate and bcm.modificationDate <= :endDate and bcm.campaign_id = :messageId")
                        .setParameter("startDate",startDate)
                        .setParameter("endDate",toDate)
                        .setParameter("messageId",messageId);
        BigInteger result = (BigInteger) query.uniqueResult();
        return result.intValue();
    }
}
