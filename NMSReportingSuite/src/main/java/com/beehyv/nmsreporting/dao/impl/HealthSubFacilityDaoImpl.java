package com.beehyv.nmsreporting.dao.impl;

import com.beehyv.nmsreporting.dao.AbstractDao;
import com.beehyv.nmsreporting.dao.HealthSubFacilityDao;
import com.beehyv.nmsreporting.model.HealthFacility;
import com.beehyv.nmsreporting.model.HealthSubFacility;
import org.springframework.stereotype.Repository;

/**
 * Created by beehyv on 23/5/17.
 */
@Repository("healthSubFacilityDao")
public class HealthSubFacilityDaoImpl extends AbstractDao<Integer, HealthSubFacility> implements HealthSubFacilityDao{
    @Override
    public HealthSubFacility findByHealthSubFacilityId(Integer healthSubFacilityid) {
        return getByKey(healthSubFacilityid);
    }
}
