package com.beehyv.nmsreporting.dao;

import com.beehyv.nmsreporting.model.StateCircle;

import java.util.List;

/**
 * Created by beehyv on 25/5/17.
 */
public interface StateCircleDao {

    List<StateCircle> getCirclesByState(Integer stateId);

}
