package com.beehyv.nmsreporting.dao;

import com.beehyv.nmsreporting.model.District;

import java.util.List;
import java.util.Set;

/**
 * Created by beehyv on 4/5/17.
 */
public interface DistrictDao {

    District findByDistrictId(Integer districtId);

    District findByLocationId(Long stateId);


    List<District> findByName(String districtName);

    List<District> getDistrictsOfState(Integer state);

    List<District> findByIds(Set<Integer> districtIds);

}
