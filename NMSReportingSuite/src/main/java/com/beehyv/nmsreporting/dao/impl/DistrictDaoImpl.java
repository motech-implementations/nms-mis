package com.beehyv.nmsreporting.dao.impl;

import com.beehyv.nmsreporting.dao.AbstractDao;
import com.beehyv.nmsreporting.dao.DistrictDao;
import com.beehyv.nmsreporting.model.District;
import com.beehyv.nmsreporting.model.Location;

import java.util.List;

/**
 * Created by beehyv on 4/5/17.
 */
public class DistrictDaoImpl extends AbstractDao<Integer, District> implements DistrictDao {
    @Override
    public District findByDistrictId(Integer districtId) {
        return getByKey(districtId);
    }

    @Override
    public List<District> findByName(String districtName) {
        return null;
    }

    @Override
    public List<District> getTalukas(int districtId) {
        return null;
    }

    @Override
    public void saveLocation(District district) {

    }

    @Override
    public void deleteLocation(District district) {

    }
}
