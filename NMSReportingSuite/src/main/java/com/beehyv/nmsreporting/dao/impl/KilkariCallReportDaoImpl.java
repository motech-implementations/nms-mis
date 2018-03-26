package com.beehyv.nmsreporting.dao.impl;

import com.beehyv.nmsreporting.dao.AbstractDao;
import com.beehyv.nmsreporting.dao.KilkariCallReportDao;
import com.beehyv.nmsreporting.model.KilkariCalls;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by beehyv on 11/10/17.
 */
@Repository("kilkariCallReportDao")
public class KilkariCallReportDaoImpl extends AbstractDao<Integer,KilkariCalls> implements KilkariCallReportDao {

    @Override
    public  KilkariCalls getKilkariCallreport(Integer locationId, String locationType, Date toDate){


        Criteria criteria = createEntityCriteria().addOrder(Order.asc("locationId"));
        criteria.add(Restrictions.and(
                Restrictions.eq("locationId",locationId.longValue()),
                Restrictions.eq("locationType",locationType),
                Restrictions.eq("date",toDate)
        ));

        List<KilkariCalls> result = criteria.list();
        if(result.isEmpty()){
            Long a=(long)0;
            KilkariCalls kilkariCalls = new KilkariCalls(0,locationType,locationId.longValue(),toDate,a,a,a,a,a,a,0.00,a,a);
            return kilkariCalls;
        }

        KilkariCalls kilkariCalls = result.get(0);
        kilkariCalls.setBillableMinutes(kilkariCalls.getBillableMinutes() == null ? 0 : kilkariCalls.getBillableMinutes());
        kilkariCalls.setContent_1_25(kilkariCalls.getContent_1_25() == null ? 0 : kilkariCalls.getContent_1_25());
        kilkariCalls.setContent_25_50(kilkariCalls.getContent_25_50() == null ? 0 : kilkariCalls.getContent_25_50());
        kilkariCalls.setContent_50_75(kilkariCalls.getContent_50_75() == null ? 0 : kilkariCalls.getContent_50_75());
        kilkariCalls.setContent_75_100(kilkariCalls.getContent_75_100() == null ? 0 : kilkariCalls.getContent_75_100());
        kilkariCalls.setCallsAttempted(kilkariCalls.getCallsAttempted() == null ? 0 : kilkariCalls.getCallsAttempted());
        kilkariCalls.setCallsToInbox(kilkariCalls.getCallsToInbox() == null ? 0 : kilkariCalls.getCallsToInbox());
        kilkariCalls.setSuccessfulCalls(kilkariCalls.getSuccessfulCalls() == null ? 0 : kilkariCalls.getSuccessfulCalls());
        kilkariCalls.setUniqueBeneficiaries(kilkariCalls.getUniqueBeneficiaries()== null? 0 : kilkariCalls.getUniqueBeneficiaries());
        return kilkariCalls;
    }
}

