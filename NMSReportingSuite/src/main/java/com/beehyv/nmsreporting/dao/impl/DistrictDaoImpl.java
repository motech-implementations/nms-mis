package com.beehyv.nmsreporting.dao.impl;

import com.beehyv.nmsreporting.dao.AbstractDao;
import com.beehyv.nmsreporting.dao.DistrictDao;
import com.beehyv.nmsreporting.model.*;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by beehyv on 4/5/17.
 */
@Repository("districtDao")
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

    public List<District> getDistrictsOfState(State state) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("stateOfDistrict", state));
        return (List<District>) criteria.list();
    }

    @Override
    public List<District> getAllDistricts() {
        Criteria criteria = createEntityCriteria();
        return (List<District>) criteria.list();
    }

    @Override
    public State getStateOfDistrict(District district) {
        return district.getStateOfDistrict();
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
