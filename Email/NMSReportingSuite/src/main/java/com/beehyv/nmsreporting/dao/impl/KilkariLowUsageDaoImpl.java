package com.beehyv.nmsreporting.dao.impl;

import com.beehyv.nmsreporting.dao.AbstractDao;
import com.beehyv.nmsreporting.dao.KilkariLowUsageDao;
import com.beehyv.nmsreporting.model.KilkariLowUsage;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * Created by beehyv on 23/5/17.
 */
@Repository("kilkariLowUsageDao")
public class KilkariLowUsageDaoImpl extends AbstractDao<Integer, KilkariLowUsage> implements KilkariLowUsageDao {

    @Override
    public Long getCountOfLowUsageUsersForGivenDistrict(String month, Integer districtId) {
        Criteria criteria = getSession().createCriteria(KilkariLowUsage.class);
        criteria.add(
                Restrictions.like("forMonth", month)
        )
                .add(Restrictions.eq("districtId", districtId))
                .setProjection(Projections.rowCount());


        return (Long) criteria.uniqueResult();
    }


}
