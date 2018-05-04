package com.beehyv.nmsreporting.dao.impl;

import com.beehyv.nmsreporting.dao.AbstractDao;
import com.beehyv.nmsreporting.dao.KilkariUsageDao;
import com.beehyv.nmsreporting.dao.KilkariUsageWeekDao;
import com.beehyv.nmsreporting.model.KilkariUsage;
import com.beehyv.nmsreporting.model.KilkariUsageWeekly;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository("usageWeekDao")
public class KilkariUsageWeekDaoImpl extends AbstractDao<Integer,KilkariUsageWeekly> implements KilkariUsageWeekDao {

    @Override
    public KilkariUsage getUsage(Integer locationId, String locationType, Date toDate){

        Criteria criteria = createEntityCriteria().addOrder(Order.asc("locationId"));
        criteria.add(Restrictions.and(
                Restrictions.eq("locationId",locationId.longValue()),
                Restrictions.eq("locationType",locationType),
                Restrictions.eq("date",toDate)
        ));
        List<KilkariUsageWeekly> result = criteria.list();
        if(result.isEmpty()){
            Long a = (long)0;
            KilkariUsage kilkariUsage = new KilkariUsage(0,locationType,locationId.longValue(),toDate,a,a,a,a,a);
            return kilkariUsage;
        }
        KilkariUsageWeekly kilkariUsageWeekly =  result.get(0);
        KilkariUsage kilkariUsage = new KilkariUsage();

        kilkariUsage.setId(kilkariUsageWeekly.getId());
        kilkariUsage.setLocationId(kilkariUsageWeekly.getLocationId());
        kilkariUsage.setLocationType(kilkariUsageWeekly.getLocationType());
        kilkariUsage.setDate(kilkariUsageWeekly.getDate());
        kilkariUsage.setCalls_1_25(kilkariUsageWeekly.getCalls_1_25() == null ? 0 : kilkariUsageWeekly.getCalls_1_25());
        kilkariUsage.setCalls_25_50(kilkariUsageWeekly.getCalls_25_50() == null ? 0 : kilkariUsageWeekly.getCalls_25_50());
        kilkariUsage.setCalls_50_75(kilkariUsageWeekly.getCalls_50_75() == null ? 0 : kilkariUsageWeekly.getCalls_50_75());
        kilkariUsage.setCalls_75_100(kilkariUsageWeekly.getCalls_75_100() == null ? 0 : kilkariUsageWeekly.getCalls_75_100());
        kilkariUsage.setCalledInbox(kilkariUsageWeekly.getCalledInbox() == null ? 0 : kilkariUsageWeekly.getCalledInbox());
        return kilkariUsage;
    }
}

