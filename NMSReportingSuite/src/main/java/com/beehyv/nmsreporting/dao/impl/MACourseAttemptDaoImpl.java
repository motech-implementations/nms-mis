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
import java.util.List;

/**
 * Created by beehyv on 17/5/17.
 */
@Repository("maCourseAttemptDao")
public class MACourseAttemptDaoImpl extends AbstractDao<Integer, User> implements MACourseAttemptDao {


    @Override
    public List<MACourseFirstCompletion> getSuccessFulCompletion(Date toDate) {
        Criteria criteria = getSession().createCriteria(MACourseFirstCompletion.class);
        criteria.add(Restrictions.lt("firstCompletionDate",toDate))
        .add((Restrictions.not(
                Restrictions.in("districtId", new Integer[] {471,474,483,490})
        )));
        return criteria.list();
    }

    @Override
    public List<MACourseFirstCompletion> getSuccessFulCompletionWithStateId(Date toDate, Integer stateId) {
        Criteria criteria = getSession().createCriteria(MACourseFirstCompletion.class);
        criteria.add(Restrictions.lt("firstCompletionDate",toDate))
                .add(Restrictions.eq("stateId",stateId))
                .add((Restrictions.not(
                        Restrictions.in("districtId", new Integer[] {471,474,483,490})
                )));
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

    @Override
    public Long getCountForGivenDistrict(Date toDate, Integer districtId) {
        Criteria criteria= getSession().createCriteria(MACourseFirstCompletion.class);
        criteria.add(Restrictions.lt("firstCompletionDate",toDate))
                .add(Restrictions.eq("districtId",districtId))
                .setProjection(Projections.rowCount());

        return (Long) criteria.uniqueResult();

    }

    @Override
    public MACourseFirstCompletion getSuccessFulCompletionByExtrnalFlwId(Date toDate, Long Extr_Flw_Id, Integer state_id){
        Criteria criteria = getSession().createCriteria(MACourseFirstCompletion.class);
        criteria.add(Restrictions.lt("firstCompletionDate",toDate))
                .add(Restrictions.eq("externalFlwId",Extr_Flw_Id))/*
                .add(Restrictions.eq("stateId",state_id))*/;
        return (MACourseFirstCompletion) criteria.uniqueResult();
    }

    @Override
    public void updateMACourseFirstCompletion(MACourseFirstCompletion maCourseFirstCompletion) {

    }


}
