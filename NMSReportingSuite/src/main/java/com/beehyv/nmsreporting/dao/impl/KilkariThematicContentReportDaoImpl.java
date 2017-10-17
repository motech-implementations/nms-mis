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
        Criteria criteria = createEntityCriteria()
                .setProjection(Projections.projectionList()
                .add(Projections.property("id"))
                .add(Projections.property("theme"))
                .add(Projections.property("messageWeekNumber"))
                .add(Projections.property("callsAnswered"))
                .add(Projections.property("minutesConsumed")));

        criteria.add(Restrictions.and(
                Restrictions.eq("date",toDate)
        ));

        kilkariThematicContentData = criteria.list();
        return kilkariThematicContentData;
    }

    @Override
    public String getMessageWeekNumber(Integer messageWeekNumber){
        Query query = getSession().createSQLQuery("select md.week_id from message_duration md where md.id = :id")
                      .setParameter("id",messageWeekNumber);

        List<Object> result = query.list();
        return (String)result.get(0);

    }

    @Override
    public Integer getUniqueBeneficiariesCalled(Date startDate, Date toDate, Integer messageId){
        Query query = getSession().createSQLQuery("select count(DISTINCT bcm.pregnancy_id) from beneficiary_call_measure bcm where bcm.modificationDate >= :startDate: and bcm.modificationDate <= :endDate: and bcm.campaign_id = :messageId")
                        .setParameter("startDate",startDate)
                        .setParameter("endDate",toDate)
                        .setParameter("messageId",messageId);
        List<Object> result = query.list();
        return (Integer)result.get(0);
    }



}
