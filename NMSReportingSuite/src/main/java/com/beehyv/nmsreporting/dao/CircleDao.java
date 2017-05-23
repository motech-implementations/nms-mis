package com.beehyv.nmsreporting.dao;

import com.beehyv.nmsreporting.model.Circle;

import java.util.List;

/**
 * Created by beehyv on 23/5/17.
 */
public interface CircleDao {

    List<Circle> getAllCircles();
    Circle getByCircleId(Integer circleId);
}
