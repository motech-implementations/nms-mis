package com.beehyv.nmsreporting.dao.impl;

import com.beehyv.nmsreporting.dao.AbstractDao;
import com.beehyv.nmsreporting.dao.TalukaDao;
import com.beehyv.nmsreporting.model.District;
import com.beehyv.nmsreporting.model.Location;
import com.beehyv.nmsreporting.model.State;
import com.beehyv.nmsreporting.model.Taluka;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

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
    public List<Taluka> getTalukasOfDistrict(District district) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("districtOfTaluka", district));
        return (List<Taluka>) criteria.list();
    }

    @Override
    public List<Taluka> getTalukasOfState(State state) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("stateOfTaluka", state));
        return (List<Taluka>) criteria.list();
    }

    @Override
    public List<Taluka> getAllTalukas() {
        Criteria criteria = createEntityCriteria();
        return (List<Taluka>) criteria.list();
    }

    @Override
    public void saveTaluka(Taluka taluka) {
        persist(taluka);
    }

    @Override
    public void deleteTaluka(Taluka taluka) {
        delete(taluka);
    }
}
