package com.beehyv.nmsreporting.dao;

import com.beehyv.nmsreporting.model.HealthFacility;

/**
 * Created by beehyv on 23/5/17.
 */
public interface HealthFacilityDao {

    public HealthFacility findByHealthFacilityId(Integer healthFacilityid);

}
