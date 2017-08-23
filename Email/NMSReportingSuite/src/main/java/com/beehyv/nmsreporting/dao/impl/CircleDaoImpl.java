package com.beehyv.nmsreporting.dao.impl;

import com.beehyv.nmsreporting.dao.AbstractDao;
import com.beehyv.nmsreporting.dao.CircleDao;
import com.beehyv.nmsreporting.model.Circle;
import org.springframework.stereotype.Repository;

/**
 * Created by beehyv on 23/5/17.
 */
@Repository("circleDao")
public class CircleDaoImpl extends AbstractDao<Integer, Circle> implements CircleDao {

    @Override
    public Circle getByCircleId(Integer circleId) {
        return getByKey(circleId);
    }
}
