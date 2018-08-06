package com.beehyv.nmsreporting.dao.impl;

import com.beehyv.nmsreporting.dao.AbstractDao;
import com.beehyv.nmsreporting.dao.DistrictDao;
import com.beehyv.nmsreporting.model.District;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by beehyv on 4/5/17.
 */
@Repository("districtDao")
public class DistrictDaoImpl extends AbstractDao<Integer, District> implements DistrictDao {
    @Override
    public District findByDistrictId(Integer districtId) throws NullPointerException{
        return getByKey(districtId);
    }

    @Override
    public District findByLocationId(Long locationId) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("locationId", locationId).ignoreCase());
        return (District) criteria.list().get(0);
    }
    @Override
    public List<District> findByName(String districtName) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("districtName", districtName).ignoreCase());
        return (List<District>) criteria.list();

    }

    public List<District> getDistrictsOfState(Integer stateId) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.and(Restrictions.eq("stateOfDistrict", stateId),
                (Restrictions.not(
                        Restrictions.in("districtId", new Integer[] {471,474,483,490})
                ))));
        return (List<District>) criteria.list();
    }

}
