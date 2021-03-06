package com.beehyv.nmsreporting.business.impl;

import com.beehyv.nmsreporting.business.LocationService;
import com.beehyv.nmsreporting.dao.BlockDao;
import com.beehyv.nmsreporting.dao.DistrictDao;
import com.beehyv.nmsreporting.dao.StateCircleDao;
import com.beehyv.nmsreporting.dao.StateDao;
import com.beehyv.nmsreporting.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by beehyv on 15/3/17.
 */
@Service("locationService")
@Transactional
public class LocationServiceImpl implements LocationService {
    @Autowired
    private StateDao stateDao;

    @Autowired
    private DistrictDao districtDao;

    @Autowired
    private BlockDao blockDao;

    @Autowired
    private StateCircleDao stateCircleDao;

    @Override
    public State findStateById(Integer stateId) {
        return stateDao.findByStateId(stateId);
    }

    @Override
    public District findDistrictById(Integer districtId) throws NullPointerException {
        return districtDao.findByDistrictId(districtId);
    }

    @Override
    public Block findBlockById(Integer blockId) {
        return blockDao.findByblockId(blockId);
    }

    @Override
    public List<State> getStatesOfCircle(Circle circle) {
        List<StateCircle> stateCircleList = stateCircleDao.getRelByCircleId(circle.getCircleId());
        List<State> states = new ArrayList<>();
        for (StateCircle stateCircle : stateCircleList) {
            states.add(stateDao.findByStateId(stateCircle.getStateId()));
        }
        return states;
    }

}
