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
    public District findByDistrictId(Integer districtId) throws NullPointerException {
        return getByKey(districtId);
    }

    public List<District> getDistrictsOfState(Integer stateId) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("stateOfDistrict", stateId));
        return (List<District>) criteria.list();
    }

}
