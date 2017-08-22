package com.beehyv.nmsreporting.dao;

import com.beehyv.nmsreporting.model.Circle;

/**
 * Created by beehyv on 23/5/17.
 */
public interface CircleDao {

    Circle getByCircleId(Integer circleId);
}
