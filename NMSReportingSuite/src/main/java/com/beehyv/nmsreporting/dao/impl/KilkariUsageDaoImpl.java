package com.beehyv.nmsreporting.dao.impl;

import com.beehyv.nmsreporting.dao.AbstractDao;
import com.beehyv.nmsreporting.dao.KilkariUsageDao;
import com.beehyv.nmsreporting.model.KilkariUsage;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by beehyv on 9/10/17.
 */
@Repository("usageDao")
public class KilkariUsageDaoImpl extends AbstractDao<Integer,KilkariUsage> implements KilkariUsageDao {

    @Override
    public KilkariUsage getUsage(Integer locationId, String locationType, Date toDate,String periodType){

        Criteria criteria = createEntityCriteria().addOrder(Order.asc("locationId"));
        criteria.add(Restrictions.and(
                Restrictions.eq("locationId",locationId.longValue()),
                Restrictions.eq("locationType",locationType),
                Restrictions.eq("periodType",periodType),
                Restrictions.eq("date",toDate)
        ));
        List<KilkariUsage> result = criteria.list();
        if(result.isEmpty()){
            Long a = (long)0;
            KilkariUsage kilkariUsage = new KilkariUsage(0,locationType,locationId.longValue(),toDate,a,a,a,a,a,"");
            return kilkariUsage;
        }
        KilkariUsage kilkariUsage =  result.get(0);
        kilkariUsage.setCalls_1_25(kilkariUsage.getCalls_1_25() == null ? 0 : kilkariUsage.getCalls_1_25());
        kilkariUsage.setCalls_25_50(kilkariUsage.getCalls_25_50() == null ? 0 : kilkariUsage.getCalls_25_50());
        kilkariUsage.setCalls_50_75(kilkariUsage.getCalls_50_75() == null ? 0 : kilkariUsage.getCalls_50_75());
        kilkariUsage.setCalls_75_100(kilkariUsage.getCalls_75_100() == null ? 0 : kilkariUsage.getCalls_75_100());
        kilkariUsage.setCalledInbox(kilkariUsage.getCalledInbox() == null ? 0 : kilkariUsage.getCalledInbox());
        return kilkariUsage;
    }
}
