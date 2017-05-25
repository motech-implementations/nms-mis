package com.beehyv.nmsreporting.dao;

import com.beehyv.nmsreporting.model.*;

import java.util.List;

/**
 * Created by beehyv on 4/5/17.
 */
public interface DistrictDao {

    District findByDistrictId(Integer districtId);

    List<District> findByName(String districtName);

    List<District> getDistrictsOfState(State state);

    List<Block> getBlocks(int districtId);

    List<Taluka> getTalukas(int districtId);

    List<District> getAllDistricts();

    State getStateOfDistrict(District district);

    Circle getCircleOfDistrict(District district);

    void saveLocation(District district);

    void deleteLocation(District district);
}
