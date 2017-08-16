package com.beehyv.nmsreporting.dao.impl;

import com.beehyv.nmsreporting.dao.AbstractDao;
import com.beehyv.nmsreporting.dao.MACourseAttemptDao;
import com.beehyv.nmsreporting.enums.AccountStatus;
import com.beehyv.nmsreporting.model.*;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by beehyv on 17/5/17.
 */
@Repository("maCourseAttemptDao")
public class MACourseAttemptDaoImpl extends AbstractDao<Integer, User> implements MACourseAttemptDao {


    @Override
    public List<MACourseFirstCompletion> getSuccessFulCompletion(Date toDate) {
        Criteria criteria = getSession().createCriteria(MACourseFirstCompletion.class);
        criteria.add(Restrictions.lt("firstCompletionDate",toDate));
        return criteria.list();
    }

    @Override
    public List<MACourseFirstCompletion> getSuccessFulCompletionWithStateId(Date toDate, Integer stateId) {
        Criteria criteria = getSession().createCriteria(MACourseFirstCompletion.class);
        criteria.add(Restrictions.lt("firstCompletionDate",toDate))
                .add(Restrictions.eq("stateId",stateId));
        return criteria.list();
    }

    @Override
    public List<MACourseFirstCompletion> getSuccessFulCompletionWithDistrictId(Date toDate, Integer districtId) {
        Criteria criteria = getSession().createCriteria(MACourseFirstCompletion.class);
        criteria.add(Restrictions.lt("firstCompletionDate",toDate))
                .add(Restrictions.eq("districtId",districtId));
        return criteria.list();
    }

    @Override
    public List<MACourseFirstCompletion> getSuccessFulCompletionWithBlockId(Date toDate, Integer blockId) {
        Criteria criteria = getSession().createCriteria(MACourseFirstCompletion.class);
        criteria.add(Restrictions.lt("firstCompletionDate",toDate))
                .add(Restrictions.eq("blockId",blockId));
        return criteria.list();
    }
}
