package com.beehyv.nmsreporting.dao.impl;

import com.beehyv.nmsreporting.dao.AbstractDao;
import com.beehyv.nmsreporting.dao.KilkariUsageMotherDao;
import com.beehyv.nmsreporting.model.KilkariUsageMother;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by beehyv on 27/02/25.
 */
@Repository("usageMotherDao")
public class KilkariUsageMotherDaoImpl extends AbstractDao<Integer, KilkariUsageMother> implements KilkariUsageMotherDao {

    @Override
    public KilkariUsageMother getUsage(Integer locationId, String locationType, Date toDate, String periodType){

        Criteria criteria = createEntityCriteria().addOrder(Order.asc("locationId"));
        criteria.add(Restrictions.and(
                Restrictions.eq("locationId",locationId.longValue()),
                Restrictions.eq("locationType",locationType),
                Restrictions.eq("periodType",periodType),
                Restrictions.eq("date",toDate)
        ));
        List<KilkariUsageMother> result = criteria.list();
        if(result.isEmpty()){
            Long a = (long)0;
            KilkariUsageMother kilkariUsageMother = new KilkariUsageMother(0,locationType,locationId.longValue(),toDate,a,a,a,a,a,a,a,"");
            return kilkariUsageMother;
        }
        KilkariUsageMother kilkariUsageMother =  result.get(0);
        kilkariUsageMother.setCalls_1_25(kilkariUsageMother.getCalls_1_25() == null ? 0 : kilkariUsageMother.getCalls_1_25());
        kilkariUsageMother.setCalls_25_50(kilkariUsageMother.getCalls_25_50() == null ? 0 : kilkariUsageMother.getCalls_25_50());
        kilkariUsageMother.setCalls_50_75(kilkariUsageMother.getCalls_50_75() == null ? 0 : kilkariUsageMother.getCalls_50_75());
        kilkariUsageMother.setCalls_75_100(kilkariUsageMother.getCalls_75_100() == null ? 0 : kilkariUsageMother.getCalls_75_100());
        kilkariUsageMother.setCalledInbox(kilkariUsageMother.getCalledInbox() == null ? 0 : kilkariUsageMother.getCalledInbox());
        kilkariUsageMother.setAnsweredAtleastOneCall(kilkariUsageMother.getAnsweredAtleastOneCall() == null ? 0 : kilkariUsageMother.getAnsweredAtleastOneCall());
        kilkariUsageMother.setTotalBeneficiariesCalled(kilkariUsageMother.getTotalBeneficiariesCalled() == null ? 0 : kilkariUsageMother.getTotalBeneficiariesCalled());
        return kilkariUsageMother;
    }

}
