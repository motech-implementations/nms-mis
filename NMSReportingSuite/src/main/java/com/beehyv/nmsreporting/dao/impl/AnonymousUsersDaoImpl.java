package com.beehyv.nmsreporting.dao.impl;

import com.beehyv.nmsreporting.dao.AbstractDao;
import com.beehyv.nmsreporting.dao.AnonymousUsersDao;
import com.beehyv.nmsreporting.model.AnonymousUsers;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by beehyv on 23/5/17.
 */
@Repository("anonymousUsersDao")
public class AnonymousUsersDaoImpl extends AbstractDao<Integer,AnonymousUsers> implements AnonymousUsersDao{

    @Override
    public List<AnonymousUsers> getAnonymousUsers(Date toDate) {
        Criteria criteria = createEntityCriteria().addOrder(Order.asc("lastCalledDate"));
        criteria.add(Restrictions.le("lastCalledDate",toDate));
        return (List<AnonymousUsers>)criteria.list();
    }

    @Override
    public List<AnonymousUsers> getAnonymousUsersCircle(Date toDate,Integer circleId) {
        Criteria criteria = createEntityCriteria().addOrder(Order.asc("lastCalledDate"));
        criteria.add(Restrictions.and(Restrictions.le("lastCalledDate",toDate),Restrictions.eq("circleId",circleId)));
        return (List<AnonymousUsers>)criteria.list();
    }
}
