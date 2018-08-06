package com.beehyv.nmsreporting.dao.impl;

import com.beehyv.nmsreporting.dao.AbstractDao;
import com.beehyv.nmsreporting.dao.KilkariLowUsageDao;
import com.beehyv.nmsreporting.model.KilkariLowUsage;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by beehyv on 23/5/17.
 */
@Repository("kilkariLowUsageDao")
public class KilkariLowUsageDaoImpl extends AbstractDao<Integer, KilkariLowUsage> implements KilkariLowUsageDao {
    @Override
    public List<KilkariLowUsage> getKilkariLowUsageUsers(String forMonth) {
        Criteria criteria = getSession().createCriteria(KilkariLowUsage.class);
        criteria.add(
                Restrictions.like("forMonth",forMonth)
        )
                .add((Restrictions.not(
                        Restrictions.in("districtId", new Integer[] {471,474,483,490})
                )));
        criteria.addOrder(Order.asc("modificationDate"));
        return (List<KilkariLowUsage>) criteria.list();
    }

    @Override
    public List<KilkariLowUsage> getKilkariLowUsageUsersWithStateId(String forMonth, Integer stateId) {
        Criteria criteria = getSession().createCriteria(KilkariLowUsage.class);
        criteria.add(
                Restrictions.like("forMonth",forMonth)
        )
                .add(Restrictions.eq("stateId",stateId))
                .add((Restrictions.not(
                        Restrictions.in("districtId", new Integer[] {471,474,483,490})
                )));
        criteria.addOrder(Order.asc("modificationDate"));
        return (List<KilkariLowUsage>) criteria.list();
    }

    @Override
    public List<KilkariLowUsage> getKilkariLowUsageUsersWithDistrictId(String forMonth, Integer districtId) {
        Criteria criteria = getSession().createCriteria(KilkariLowUsage.class);
        criteria.add(
                Restrictions.like("forMonth",forMonth)
        )
                .add(Restrictions.eq("districtId",districtId));
        criteria.addOrder(Order.asc("modificationDate"));
        return (List<KilkariLowUsage>) criteria.list();
    }

    @Override
    public List<KilkariLowUsage> getKilkariLowUsageUsersWithBlockId(String forMonth, Integer blockId) {
        Criteria criteria = getSession().createCriteria(KilkariLowUsage.class);
        criteria.add(
                Restrictions.like("forMonth",forMonth)
        )
                .add(Restrictions.eq("blockId",blockId));
        criteria.addOrder(Order.asc("modificationDate"));
        return (List<KilkariLowUsage>) criteria.list();
    }

    @Override
    public Long getCountOfLowUsageUsersForGivenDistrict(String month, Integer districtId) {
        Criteria criteria = getSession().createCriteria(KilkariLowUsage.class);
        criteria.add(
                Restrictions.like("forMonth",month)
        )
                .add(Restrictions.eq("districtId",districtId))
                .setProjection(Projections.rowCount());


        return (Long) criteria.uniqueResult();
    }


}
