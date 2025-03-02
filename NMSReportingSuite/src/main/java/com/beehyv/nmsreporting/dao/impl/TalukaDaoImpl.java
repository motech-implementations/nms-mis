package com.beehyv.nmsreporting.dao.impl;

import com.beehyv.nmsreporting.dao.AbstractDao;
import com.beehyv.nmsreporting.dao.TalukaDao;
import com.beehyv.nmsreporting.model.Taluka;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * Created by beehyv on 4/5/17.
 */
@Repository("talukaDao")
public class TalukaDaoImpl extends AbstractDao<Integer, Taluka> implements TalukaDao {
    @Override
    public Taluka findByTalukaId(Integer talukaId) {
        return getByKey(talukaId);
    }

    @Override
    public List<Taluka> findByName(String talukaName) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("talukaName", talukaName).ignoreCase());
        return (List<Taluka>) criteria.list();
    }

    @Override
    public List<Taluka> findByIds(Set<Integer> talukaIds) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.in("talukaId", talukaIds));
        return (List<Taluka>) criteria.list();
    }
}
