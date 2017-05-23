package com.beehyv.nmsreporting.dao.impl;

import com.beehyv.nmsreporting.dao.AbstractDao;
import com.beehyv.nmsreporting.dao.HealthFacilityDao;
import com.beehyv.nmsreporting.model.HealthFacility;
import org.springframework.stereotype.Repository;

/**
 * Created by beehyv on 23/5/17.
 */
@Repository("healthFacilityDao")
public class HealthFacilityDaoImpl extends AbstractDao<Integer, HealthFacility> implements HealthFacilityDao {
    @Override
    public HealthFacility findByHealthFacilityId(Integer healthFacilityid) {
        return getByKey(healthFacilityid);
    }
}
