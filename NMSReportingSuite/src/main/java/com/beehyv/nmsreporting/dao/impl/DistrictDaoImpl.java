package com.beehyv.nmsreporting.dao.impl;

import com.beehyv.nmsreporting.dao.AbstractDao;
import com.beehyv.nmsreporting.dao.DistrictDao;
import com.beehyv.nmsreporting.model.*;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import java.util.ArrayList;
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
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("districtName", districtName).ignoreCase());
        return (List<District>) criteria.list();

    }

    @Override
    public List<Block> getBlocks(int districtId) {
        District district=getByKey(districtId);
        List<Block> childDistricts=new ArrayList<>();
        childDistricts.addAll(district.getBlocks());
        return childDistricts;
    }

    @Override
    public List<Taluka> getTalukas(int districtId) {
        return null;
    }

    @Override
    public void saveLocation(District district) {
        persist(district);
    }

    @Override
    public void deleteLocation(District district) {
        delete(district);
    }
}
