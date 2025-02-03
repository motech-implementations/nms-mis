package com.beehyv.nmsreporting.dao.impl;

import com.beehyv.nmsreporting.dao.AbstractDao;
import com.beehyv.nmsreporting.dao.KilkariUsageChildDao;

import com.beehyv.nmsreporting.model.KilkariUsageChild;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by beehyv on 27/02/25.
 */
@Repository("usageChildDao")
public class KilkariUsageChildDaoImpl extends AbstractDao<Integer, KilkariUsageChild> implements KilkariUsageChildDao {

    @Override
    public KilkariUsageChild getUsage(Integer locationId, String locationType, Date toDate, String periodType){

        Criteria criteria = createEntityCriteria().addOrder(Order.asc("locationId"));
        criteria.add(Restrictions.and(
                Restrictions.eq("locationId",locationId.longValue()),
                Restrictions.eq("locationType",locationType),
                Restrictions.eq("periodType",periodType),
                Restrictions.eq("date",toDate)
        ));
        List<KilkariUsageChild> result = criteria.list();
        if(result.isEmpty()){
            Long a = (long)0;
            KilkariUsageChild kilkariUsageChild = new KilkariUsageChild(0,locationType,locationId.longValue(),toDate,a,a,a,a,a,a,a,"");
            return kilkariUsageChild;
        }
        KilkariUsageChild kilkariUsageChild =  result.get(0);
        kilkariUsageChild.setCalls_1_25(kilkariUsageChild.getCalls_1_25() == null ? 0 : kilkariUsageChild.getCalls_1_25());
        kilkariUsageChild.setCalls_25_50(kilkariUsageChild.getCalls_25_50() == null ? 0 : kilkariUsageChild.getCalls_25_50());
        kilkariUsageChild.setCalls_50_75(kilkariUsageChild.getCalls_50_75() == null ? 0 : kilkariUsageChild.getCalls_50_75());
        kilkariUsageChild.setCalls_75_100(kilkariUsageChild.getCalls_75_100() == null ? 0 : kilkariUsageChild.getCalls_75_100());
        kilkariUsageChild.setCalledInbox(kilkariUsageChild.getCalledInbox() == null ? 0 : kilkariUsageChild.getCalledInbox());
        kilkariUsageChild.setAnsweredAtleastOneCall(kilkariUsageChild.getAnsweredAtleastOneCall() == null ? 0 : kilkariUsageChild.getAnsweredAtleastOneCall());
        kilkariUsageChild.setTotalBeneficiariesCalled(kilkariUsageChild.getTotalBeneficiariesCalled() == null ? 0 : kilkariUsageChild.getTotalBeneficiariesCalled());
        return kilkariUsageChild;
    }
}
