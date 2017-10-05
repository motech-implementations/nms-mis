package com.beehyv.nmsreporting.dao.impl;

import com.beehyv.nmsreporting.dao.AbstractDao;
import com.beehyv.nmsreporting.dao.AggregateCumulativeMADao;
import com.beehyv.nmsreporting.model.AggregateCumulativeMA;
import com.beehyv.nmsreporting.model.Block;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository("aggregateCumulativeMADao")
public class  AggregateCumulativeMADaoImpl extends AbstractDao<Integer,AggregateCumulativeMA> implements AggregateCumulativeMADao
        {
            @Override
            public AggregateCumulativeMA getMACumulativeSummery(Integer locationId, String locationType, Date toDate){


                   Criteria criteria = createEntityCriteria().addOrder(Order.asc("locationId"));
                   criteria.add(Restrictions.and(
                           Restrictions.eq("locationId",locationId.longValue()),
                           Restrictions.eq("locationType",locationType),
                           Restrictions.eq("date",toDate)
                   ));
                if(criteria.list().isEmpty()){
                    AggregateCumulativeMA aggregateCumulativeMA = new AggregateCumulativeMA(0,locationType,locationId.longValue(),toDate,0,0,0,0,0,0);
                    return aggregateCumulativeMA;
                }
                   return (AggregateCumulativeMA)criteria.list().get(0);


            };
        }
