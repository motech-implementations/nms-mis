package com.beehyv.nmsreporting.dao.impl;

import com.beehyv.nmsreporting.dao.AbstractDao;
import com.beehyv.nmsreporting.dao.AggCumulativeBeneficiaryComplDao;
import com.beehyv.nmsreporting.model.AggregateCumulativeBeneficiaryCompletion;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by beehyv on 6/10/17.
 */
@Repository("aggCumulativeBeneficiaryComplDao")
public class AggCumulativeBeneficiaryComplDaoImpl extends AbstractDao<Integer,AggregateCumulativeBeneficiaryCompletion> implements AggCumulativeBeneficiaryComplDao
    {
        public AggregateCumulativeBeneficiaryCompletion getBeneficiaryCompletion(Integer locationId,String locationType,Date date, String periodType) {
            Criteria criteria = createEntityCriteria().addOrder(Order.asc("locationId"));
            criteria.add(Restrictions.and(
                    Restrictions.eq("locationId", locationId.longValue()),
                    Restrictions.eq("locationType", locationType),
                    Restrictions.eq("date", date),
                    Restrictions.eq("periodType",periodType)
            ));
            List<AggregateCumulativeBeneficiaryCompletion> result = criteria.list();
            if (result.isEmpty()) {
                Long a = (long) 0;
                AggregateCumulativeBeneficiaryCompletion aggregateCumulativeBeneficiaryCompletion = new AggregateCumulativeBeneficiaryCompletion(0, locationType, locationId.longValue(), date, a, a, a, a, a, 0.00, "");
                return aggregateCumulativeBeneficiaryCompletion;
            }
            AggregateCumulativeBeneficiaryCompletion aggregateCumulativeBeneficiaryCompletion = result.get(0);
            aggregateCumulativeBeneficiaryCompletion.setCalls_1_25(aggregateCumulativeBeneficiaryCompletion.getCalls_1_25() == null ? 0 : aggregateCumulativeBeneficiaryCompletion.getCalls_1_25());
            aggregateCumulativeBeneficiaryCompletion.setTotalAge(aggregateCumulativeBeneficiaryCompletion.getTotalAge() == null ? 0 : aggregateCumulativeBeneficiaryCompletion.getTotalAge());
            aggregateCumulativeBeneficiaryCompletion.setCalls_25_50(aggregateCumulativeBeneficiaryCompletion.getCalls_25_50() == null ? 0 : aggregateCumulativeBeneficiaryCompletion.getCalls_25_50());
            aggregateCumulativeBeneficiaryCompletion.setCalls_50_75(aggregateCumulativeBeneficiaryCompletion.getCalls_50_75() == null ? 0 : aggregateCumulativeBeneficiaryCompletion.getCalls_50_75());
            aggregateCumulativeBeneficiaryCompletion.setCalls_75_100(aggregateCumulativeBeneficiaryCompletion.getCalls_75_100() == null ? 0 : aggregateCumulativeBeneficiaryCompletion.getCalls_75_100());
            aggregateCumulativeBeneficiaryCompletion.setCompletedBeneficiaries(aggregateCumulativeBeneficiaryCompletion.getCompletedBeneficiaries() == null ? 0 : aggregateCumulativeBeneficiaryCompletion.getCompletedBeneficiaries());
            return aggregateCumulativeBeneficiaryCompletion;
        }
    }
