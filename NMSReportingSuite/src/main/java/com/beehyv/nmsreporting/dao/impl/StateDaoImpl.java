package com.beehyv.nmsreporting.dao.impl;

import com.beehyv.nmsreporting.dao.AbstractDao;
import com.beehyv.nmsreporting.dao.StateDao;
import com.beehyv.nmsreporting.model.State;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by beehyv on 4/5/17.
 */
@Repository("stateDao")
public class StateDaoImpl extends AbstractDao<Integer, State> implements StateDao {


    @Override
    public State findByStateId(Integer stateId) {
        return getByKey(stateId);
    }

    @Override
    public List<State> findByName(String stateName) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("stateName", stateName).ignoreCase());
        return (List<State>) criteria.list();

    }

    @Override
    public List<State> getAllStates() {
        Criteria criteria = createEntityCriteria();
        return (List<State>) criteria.list();
    }

    @Override
    public void saveLocation(State state) {
        persist(state);
    }

    @Override
    public void deleteLocation(State state) {
        delete(state);
    }
}
