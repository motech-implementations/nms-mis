package com.beehyv.nmsreporting.dao;

import com.beehyv.nmsreporting.model.State;

/**
 * Created by beehyv on 4/5/17.
 */
public interface StateDao {

    State findByStateId(Integer stateId);

}
