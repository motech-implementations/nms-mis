package com.beehyv.nmsreporting.dao.impl;

import com.beehyv.nmsreporting.dao.AbstractDao;
import com.beehyv.nmsreporting.dao.HealthSubFacilityDao;
import com.beehyv.nmsreporting.model.HealthSubFacility;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by beehyv on 23/5/17.
 */
@Repository("healthSubFacilityDao")
public class HealthSubFacilityDaoImpl extends AbstractDao<Integer, HealthSubFacility> implements HealthSubFacilityDao{
    @Override
    public HealthSubFacility findByHealthSubFacilityId(Integer healthSubFacilityid) {
        return getByKey(healthSubFacilityid);
    }

    @Override
    public List<HealthSubFacility> findByHealthFacilityId(Integer healthFacilityid) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("healthFacilityOfHealthSubFacility", healthFacilityid));
        return (List<HealthSubFacility>) criteria.list();
    }

    @Override
    public List<HealthSubFacility> getSubcentersOfBlock(Integer blockId) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("blockOfhealthSubFacility", blockId));
        return (List<HealthSubFacility>) criteria.list();
    }

}
