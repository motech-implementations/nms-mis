package com.beehyv.nmsreporting.dao.impl;

import com.beehyv.nmsreporting.dao.AbstractDao;
import com.beehyv.nmsreporting.dao.StateDao;
import com.beehyv.nmsreporting.model.District;
import com.beehyv.nmsreporting.model.Location;
import com.beehyv.nmsreporting.model.State;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.addAll;

/**
 * Created by beehyv on 4/5/17.
 */
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
    public List<District> getChildLocations(Integer stateId) {
        State staet=getByKey(stateId);
        List<District> childDistricts=new ArrayList<>();
        childDistricts.addAll(staet.getDistricts());
        return childDistricts;
    }

    @Override
    public List<State> getAllStates() {
        Criteria criteria = createEntityCriteria().addOrder(Order.asc("stateId"));
        return (List<State>) criteria.list();
    }

    @Override
    public List<District> getDistricts(int stateId) {
        State staet=getByKey(stateId);
        List<District> childDistricts=new ArrayList<>();
        childDistricts.addAll(staet.getDistricts());
        return childDistricts;
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
