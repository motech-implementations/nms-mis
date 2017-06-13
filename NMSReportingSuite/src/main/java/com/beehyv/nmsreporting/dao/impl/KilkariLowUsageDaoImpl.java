package com.beehyv.nmsreporting.dao.impl;

import com.beehyv.nmsreporting.dao.AbstractDao;
import com.beehyv.nmsreporting.dao.KilkariLowUsageDao;
import com.beehyv.nmsreporting.model.KilkariLowUsage;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.Date;
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
        );
        criteria.addOrder(Order.asc("modificationDate"));
        return (List<KilkariLowUsage>) criteria.list();
    }
}
