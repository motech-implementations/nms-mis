package com.beehyv.nmsreporting.dao;

import com.beehyv.nmsreporting.model.State;

import java.util.List;

/**
 * Created by beehyv on 4/5/17.
 */
public interface StateDao {

    State findByStateId(Integer stateId);

    State findByLocationId(Long stateId);

    List<State> findByName(String stateName);

    List<State> getAllStates();

    List<State> getStatesByServiceType(String type);
}
