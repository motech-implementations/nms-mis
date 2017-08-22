package com.beehyv.nmsreporting.dao;

import com.beehyv.nmsreporting.model.District;

import java.util.List;

/**
 * Created by beehyv on 4/5/17.
 */
public interface DistrictDao {

    District findByDistrictId(Integer districtId);

    List<District> getDistrictsOfState(Integer state);

}
