package com.beehyv.nmsreporting.dao.impl;

import com.beehyv.nmsreporting.dao.AbstractDao;
import com.beehyv.nmsreporting.dao.HealthFacilityDao;
import com.beehyv.nmsreporting.model.HealthFacility;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * Created by beehyv on 23/5/17.
 */
@Repository("healthFacilityDao")
public class HealthFacilityDaoImpl extends AbstractDao<Integer, HealthFacility> implements HealthFacilityDao {
    @Override
    public HealthFacility findByHealthFacilityId(Integer healthFacilityid) {
        return getByKey(healthFacilityid);
    }

    @Override
    public List<HealthFacility> findByHealthBlockId(Integer healthBlockid) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("blockOfHealthFacility", healthBlockid));
        return (List<HealthFacility>) criteria.list();
    }

    @Override
    public List<HealthFacility> findByIds(Set<Integer> healthFacilityIds) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.in("healthFacilityId", healthFacilityIds));
        return (List<HealthFacility>) criteria.list();
    }
}
