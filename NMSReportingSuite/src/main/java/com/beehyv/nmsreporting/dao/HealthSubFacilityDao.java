package com.beehyv.nmsreporting.dao;

import com.beehyv.nmsreporting.model.HealthSubFacility;

import java.util.List;
import java.util.Set;

/**
 * Created by beehyv on 23/5/17.
 */
public interface HealthSubFacilityDao {

    public HealthSubFacility findByHealthSubFacilityId(Integer healthSubFacilityid);

    public List<HealthSubFacility> findByHealthFacilityId(Integer healthFacilityid);

    public List<HealthSubFacility> getSubcentersOfBlock(Integer blockId);

    public List<HealthSubFacility> findByIds(Set<Integer> healthSubFAcilityIds);


}
