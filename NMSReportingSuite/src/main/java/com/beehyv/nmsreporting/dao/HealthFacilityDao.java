package com.beehyv.nmsreporting.dao;

import com.beehyv.nmsreporting.model.HealthFacility;

import java.util.List;
import java.util.Set;

/**
 * Created by beehyv on 23/5/17.
 */
public interface HealthFacilityDao {

    public HealthFacility findByHealthFacilityId(Integer healthFacilityid);

    public List<HealthFacility> findByHealthBlockId(Integer healthBlockid);

    public List<HealthFacility> findByIds(Set<Integer> healthFacilityIds);

}
