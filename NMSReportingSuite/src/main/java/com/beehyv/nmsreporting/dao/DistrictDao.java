package com.beehyv.nmsreporting.dao;

import com.beehyv.nmsreporting.model.District;
import com.beehyv.nmsreporting.model.Location;
import com.beehyv.nmsreporting.model.State;

import java.util.List;

/**
 * Created by beehyv on 4/5/17.
 */
public interface DistrictDao {

    public District findByDistrictId(Integer districtId);

    public List<District> findByName(String districtName);

    public List<District> getDistrictsOfState(State state);

    public List<District> getAllDistricts();

    public State getStateOfDistrict(District district);

    public void saveLocation(District district);

    public void deleteLocation(District district);
}
