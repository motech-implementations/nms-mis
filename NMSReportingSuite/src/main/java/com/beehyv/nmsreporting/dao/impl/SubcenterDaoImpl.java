package com.beehyv.nmsreporting.dao.impl;

import com.beehyv.nmsreporting.dao.AbstractDao;
import com.beehyv.nmsreporting.dao.SubcenterDao;
import com.beehyv.nmsreporting.model.Subcenter;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by beehyv on 19/9/17.
 */
@Repository("subcenterDao")
public class SubcenterDaoImpl extends AbstractDao<Integer, Subcenter> implements SubcenterDao {

    @Override
    public Subcenter findBySubcenterId(Integer subcenterId) {
        return getByKey(subcenterId);
    }

    @Override
    public Subcenter findByLocationId(Integer locationId) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("locationId", locationId).ignoreCase());
        return (Subcenter) criteria.list().get(0);
    }

    @Override
    public List<Subcenter> findByName(String subcenterName) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("subcenterName", subcenterName).ignoreCase());
        return (List<Subcenter>) criteria.list();
    }

    @Override
    public List<Subcenter> getSubcentersOfBlock(Integer districtId) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("blockOfSubcenter", districtId));
        return (List<Subcenter>) criteria.list();
    }

    @Override
    public Integer getBlockOfSubcenter(Subcenter subcenter) {
        return null;
    }
    
}
