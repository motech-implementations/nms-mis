package com.beehyv.nmsreporting.dao.impl;

import com.beehyv.nmsreporting.dao.AbstractDao;
import com.beehyv.nmsreporting.dao.KilkariSubscriberReportDao;
import com.beehyv.nmsreporting.model.KilkariRepeatListenerMonthWise;
import com.beehyv.nmsreporting.model.KilkariSubscriber;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository("kilkariSubscriberReportDao")
public class KilkariSubscriberReportDaoImpl extends AbstractDao<Integer,KilkariSubscriber> implements KilkariSubscriberReportDao {
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
            KilkariSubscriber kilkariSubscriber = new KilkariSubscriber(0,locationType,locationId.longValue(),toDate,0,0,0,0,0);
            return kilkariSubscriber;
        }

        KilkariSubscriber kilkariSubscriber = result.get(0);
        kilkariSubscriber.setTotalBeneficiaryRecordsRejected(kilkariSubscriber.getTotalBeneficiaryRecordsRejected() == null ? 0 : kilkariSubscriber.getTotalBeneficiaryRecordsRejected());
        kilkariSubscriber.setRecordsRejectedButEligible(kilkariSubscriber.getRecordsRejectedButEligible() == null ? 0 : kilkariSubscriber.getRecordsRejectedButEligible());
        kilkariSubscriber.setTotalSubscriptionsCompleted(kilkariSubscriber.getTotalSubscriptionsCompleted() == null ? 0 : kilkariSubscriber.getTotalSubscriptionsCompleted());
        kilkariSubscriber.setRecordsRejectedDuplicateMobileNumbers(kilkariSubscriber.getRecordsRejectedDuplicateMobileNumbers() == null ? 0 : kilkariSubscriber.getRecordsRejectedDuplicateMobileNumbers());
        kilkariSubscriber.setTotalSubscriptions(kilkariSubscriber.getTotalSubscriptions() == null ? 0 : kilkariSubscriber.getTotalSubscriptions());
        return kilkariSubscriber;
    }

}
