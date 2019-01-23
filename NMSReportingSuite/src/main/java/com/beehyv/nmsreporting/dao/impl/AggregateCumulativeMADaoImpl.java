package com.beehyv.nmsreporting.dao.impl;

import com.beehyv.nmsreporting.dao.AbstractDao;
import com.beehyv.nmsreporting.dao.AggregateCumulativeMADao;
import com.beehyv.nmsreporting.model.AggregateCumulativeMA;
import com.beehyv.nmsreporting.model.Block;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
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
                
                AggregateCumulativeMA aggregateCumulativeMA1 = (AggregateCumulativeMA)criteria.list().get(0);
//                aggregateCumulativeMA1.setAshasRejected(aggregateCumulativeMA1.getAshasRejected() == null?0:aggregateCumulativeMA1.getAshasRejected());
                aggregateCumulativeMA1.setAshasStarted(aggregateCumulativeMA1.getAshasStarted() == null?0:aggregateCumulativeMA1.getAshasStarted());
                aggregateCumulativeMA1.setAshasRegistered(aggregateCumulativeMA1.getAshasRegistered() == null?0:aggregateCumulativeMA1.getAshasRegistered());
                aggregateCumulativeMA1.setAshasCompleted(aggregateCumulativeMA1.getAshasCompleted() == null?0:aggregateCumulativeMA1.getAshasCompleted());
                aggregateCumulativeMA1.setAshasFailed(aggregateCumulativeMA1.getAshasFailed() == null?0:aggregateCumulativeMA1.getAshasFailed());
                aggregateCumulativeMA1.setAshasNotStarted(aggregateCumulativeMA1.getAshasNotStarted() == null?0:aggregateCumulativeMA1.getAshasNotStarted());


                Query query = getSession().createSQLQuery("select count(*) from flw_import_rejection f  " +
                        "where f.type = 'ASHA' and f.accepted = 0 and f.creation_date < :toDate and f.state_id = :locationId");

                query.setParameter("toDate",toDate);
                query.setParameter("locationId",locationId);


                aggregateCumulativeMA1.setAshasRejected(((BigInteger) query.uniqueResult()).intValue());

                   return aggregateCumulativeMA1;
            };
        }
