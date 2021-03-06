package com.beehyv.nmsreporting.dao.impl;

import com.beehyv.nmsreporting.dao.AbstractDao;
import com.beehyv.nmsreporting.dao.MACourseAttemptDao;
import com.beehyv.nmsreporting.model.MACourseFirstCompletion;
import com.beehyv.nmsreporting.model.User;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * Created by beehyv on 17/5/17.
 */
@Repository("maCourseAttemptDao")
public class MACourseAttemptDaoImpl extends AbstractDao<Integer, User> implements MACourseAttemptDao {

    @Override
    public Long getCountForGivenDistrict(String forMonth, Integer districtId) {
        Criteria criteria= getSession().createCriteria(MACourseFirstCompletion.class);
        criteria.add(Restrictions.like("forMonth",forMonth))
                .add(Restrictions.eq("districtId",districtId))
                .add(Restrictions.eq("jobStatus","ACTIVE").ignoreCase())
                .setProjection(Projections.rowCount());

        return (Long) criteria.uniqueResult();

    }
}
