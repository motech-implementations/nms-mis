package com.beehyv.nmsreporting.dao.impl;

import com.beehyv.nmsreporting.dao.AbstractDao;
import com.beehyv.nmsreporting.dao.CircleDao;
import com.beehyv.nmsreporting.model.Circle;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by beehyv on 23/5/17.
 */
@Repository("circleDao")
public class CircleDaoImpl extends AbstractDao<Integer,Circle> implements CircleDao{

    @Override
    public List<Circle> getAllCircles() {
        Criteria criteria = createEntityCriteria().addOrder(Order.asc("circleName"));
        return (List<Circle>) criteria.list();
    }

    @Override
    public Circle getByCircleId(Integer locId) {
        return getByKey(locId);
    }
}
