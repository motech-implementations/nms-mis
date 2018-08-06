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
public class  AnonymousUsersDaoImpl extends AbstractDao<Integer,AnonymousUsers> implements AnonymousUsersDao{

    @Override
    public List<AnonymousUsers> getAnonymousUsers(String forMonth) {
        Criteria criteria = createEntityCriteria().addOrder(Order.asc("lastCalledDate"));
        criteria.add(
                Restrictions.like("forMonth",forMonth)
        );
        return (List<AnonymousUsers>)criteria.list();
    }

    @Override
    public List<AnonymousUsers> getAnonymousUsersCircle(String forMonth,String circleName) {
        Criteria criteria = createEntityCriteria().addOrder(Order.asc("lastCalledDate"));
        criteria.add(Restrictions.and(
                Restrictions.like("forMonth",forMonth),
                Restrictions.eq("circleName",circleName)));
        return (List<AnonymousUsers>)criteria.list();
    }


}

