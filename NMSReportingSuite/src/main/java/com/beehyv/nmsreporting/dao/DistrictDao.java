package com.beehyv.nmsreporting.dao;

import com.beehyv.nmsreporting.model.District;

import java.util.List;

/**
 * Created by beehyv on 4/5/17.
 */
public interface DistrictDao {

    District findByDistrictId(Integer districtId);

    District findByLocationId(Long stateId);


    List<District> findByName(String districtName);

    List<District> getDistrictsOfState(Integer state);

}
