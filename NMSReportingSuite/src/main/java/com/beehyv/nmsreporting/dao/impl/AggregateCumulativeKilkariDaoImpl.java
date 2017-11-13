package com.beehyv.nmsreporting.dao.impl;

import com.beehyv.nmsreporting.dao.AbstractDao;
import com.beehyv.nmsreporting.dao.AggregateCumulativekilkariDao;
import com.beehyv.nmsreporting.model.AggregateCumulativeKilkari;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * Created by beehyv on 3/10/17.
 */
@Repository("aggregateCumulativeKilkariDao")
public class AggregateCumulativeKilkariDaoImpl extends AbstractDao<Integer,AggregateCumulativeKilkari> implements AggregateCumulativekilkariDao{

    public AggregateCumulativeKilkari getKilkariCumulativeSummary(Integer locationId, String locationType, Date toDate){

        Criteria criteria = createEntityCriteria().addOrder(Order.asc("locationId"));
        criteria.add(Restrictions.and(
                Restrictions.eq("locationId",locationId.longValue()),
                Restrictions.eq("locationType",locationType),
                Restrictions.eq("date",toDate)
        ));
        if(criteria.list().isEmpty()){
            Long a = (long)0;
            AggregateCumulativeKilkari aggregateCumulativeKilkari = new AggregateCumulativeKilkari(0,locationType,locationId.longValue(),toDate,a,a,a);
            return aggregateCumulativeKilkari;
        }

        AggregateCumulativeKilkari aggregateCumulativeKilkari = (AggregateCumulativeKilkari)criteria.list().get(0);
        aggregateCumulativeKilkari.setBillableMinutes(aggregateCumulativeKilkari.getBillableMinutes() == null ? 0 : aggregateCumulativeKilkari.getBillableMinutes());
        aggregateCumulativeKilkari.setSuccessfulCalls(aggregateCumulativeKilkari.getSuccessfulCalls() == null ? 0 : aggregateCumulativeKilkari.getSuccessfulCalls());
        aggregateCumulativeKilkari.setUniqueBeneficiaries(aggregateCumulativeKilkari.getUniqueBeneficiaries() == null ? 0 : aggregateCumulativeKilkari.getUniqueBeneficiaries());

        return aggregateCumulativeKilkari;

    }



}
