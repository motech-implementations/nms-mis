package com.beehyv.nmsreporting.dao;

import com.beehyv.nmsreporting.model.District;
import com.beehyv.nmsreporting.model.Location;
import com.beehyv.nmsreporting.model.State;

import java.util.List;

/**
 * Created by beehyv on 4/5/17.
 */
public interface StateDao {

    public State findByStateId(Integer stateId);

    public List<State> findByName(String stateName);

    public List<District> getChildLocations(Integer stateId);

    public List<State> getAllStates();
    public List<District> getDistricts(int stateId);

    public void saveLocation(State state);

    public void deleteLocation(State state);
}
