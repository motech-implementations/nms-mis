package com.beehyv.nmsreporting.dao.impl;

import com.beehyv.nmsreporting.dao.AbstractDao;
import com.beehyv.nmsreporting.dao.StateDao;
import com.beehyv.nmsreporting.model.State;
import org.springframework.stereotype.Repository;

/**
 * Created by beehyv on 4/5/17.
 */
@Repository("stateDao")
public class StateDaoImpl extends AbstractDao<Integer, State> implements StateDao {

    @Override
    public State findByStateId(Integer stateId) {
        return getByKey(stateId);
    }

}
