package com.beehyv.nmsreporting.dao;

import com.beehyv.nmsreporting.model.District;
import com.beehyv.nmsreporting.model.State;

import java.util.List;

/**
 * Created by beehyv on 4/5/17.
 */
public interface StateDao {

    State findByStateId(Integer stateId);

    List<State> findByName(String stateName);

    List<District> getChildLocations(Integer stateId);

    List<District> getDistricts(int stateId);

    List<State> getAllStates();

    void saveLocation(State state);

    void deleteLocation(State state);
}
