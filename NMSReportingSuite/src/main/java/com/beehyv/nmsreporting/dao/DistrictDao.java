package com.beehyv.nmsreporting.dao;

import com.beehyv.nmsreporting.model.*;

import java.util.List;

/**
 * Created by beehyv on 4/5/17.
 */
public interface DistrictDao {

    public District findByDistrictId(Integer districtId);

    public List<District> findByName(String districtName);


    public List<Block> getBlocks(int districtId);
    public List<Taluka> getTalukas(int districtId);

    public void saveLocation(District district);

    public void deleteLocation(District district);
}
