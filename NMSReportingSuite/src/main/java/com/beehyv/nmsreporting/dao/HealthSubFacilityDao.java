package com.beehyv.nmsreporting.dao;

import com.beehyv.nmsreporting.model.HealthSubFacility;

import java.util.List;

/**
 * Created by beehyv on 23/5/17.
 */
public interface HealthSubFacilityDao {

    public HealthSubFacility findByHealthSubFacilityId(Integer healthSubFacilityid);

    public List<HealthSubFacility> findByHealthFacilityId(Integer healthFacilityid);

    public List<HealthSubFacility> getSubcentersOfBlock(Integer blockId);


}
