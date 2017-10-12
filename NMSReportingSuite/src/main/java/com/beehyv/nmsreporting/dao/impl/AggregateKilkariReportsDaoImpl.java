package com.beehyv.nmsreporting.dao.impl;

import com.beehyv.nmsreporting.dao.AbstractDao;
import com.beehyv.nmsreporting.dao.AggregateKilkariReportsDao;
import com.beehyv.nmsreporting.model.AggregateCumulativeMA;
import com.beehyv.nmsreporting.model.KilkariSubscriber;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository("aggregateKilkariReportsDao")
public class AggregateKilkariReportsDaoImpl extends AbstractDao<Integer,KilkariSubscriber> implements AggregateKilkariReportsDao {
    @Override
    public KilkariSubscriber getKilkariSubscriberCounts(Integer locationId, String locationType, Date toDate){


        Criteria criteria = createEntityCriteria().addOrder(Order.asc("locationId"));
        criteria.add(Restrictions.and(
                Restrictions.eq("locationId",locationId.longValue()),
                Restrictions.eq("locationType",locationType),
                Restrictions.eq("date",toDate)
        ));

        List<KilkariSubscriber> result = criteria.list();

        if(result.isEmpty()){
            KilkariSubscriber kilkariSubscriber = new KilkariSubscriber(0,locationType,locationId.longValue(),toDate,0,0,0,0,0,0);
            return kilkariSubscriber;
        }
        return result.get(0);
    }
}
