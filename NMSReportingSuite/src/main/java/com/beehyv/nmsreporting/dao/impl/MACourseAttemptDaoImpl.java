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
    public List<MACourseFirstCompletion> getSuccessFulCompletion(String forMonth) {
        Criteria criteria = getSession().createCriteria(MACourseFirstCompletion.class);
        criteria.add(Restrictions.like("forMonth",forMonth))
        .add((Restrictions.not(
                Restrictions.in("districtId", new Integer[] {471,474,483,490})
        )))
        .add(Restrictions.eq("jobStatus","ACTIVE").ignoreCase());
        return criteria.list();
    }

    @Override
    public List<MACourseFirstCompletion> getSuccessFulCompletionWithStateId(String forMonth, Integer stateId) {
        Criteria criteria = getSession().createCriteria(MACourseFirstCompletion.class);
        criteria.add(Restrictions.like("forMonth",forMonth))
                .add(Restrictions.eq("stateId",stateId))
                .add((Restrictions.not(
                        Restrictions.in("districtId", new Integer[] {471,474,483,490})
                )))
        .add(Restrictions.eq("jobStatus","ACTIVE").ignoreCase());
        return criteria.list();
    }

    @Override
    public List<MACourseFirstCompletion> getSuccessFulCompletionWithDistrictId(String forMonth, Integer districtId) {
        Criteria criteria = getSession().createCriteria(MACourseFirstCompletion.class);
        criteria.add(Restrictions.like("forMonth",forMonth))
                .add(Restrictions.eq("districtId",districtId))
                .add(Restrictions.eq("jobStatus","ACTIVE").ignoreCase());
        return criteria.list();
    }

    @Override
    public List<MACourseFirstCompletion> getSuccessFulCompletionWithBlockId(String forMonth, Integer blockId) {
        Criteria criteria = getSession().createCriteria(MACourseFirstCompletion.class);
        criteria.add(Restrictions.like("forMonth",forMonth))
                .add(Restrictions.eq("blockId",blockId))
                .add(Restrictions.eq("jobStatus","ACTIVE").ignoreCase());
        return criteria.list();
    }

    @Override
    public Long getCountForGivenDistrict(String forMonth, Integer districtId) {
        Criteria criteria= getSession().createCriteria(MACourseFirstCompletion.class);
        criteria.add(Restrictions.like("forMonth",forMonth))
                .add(Restrictions.eq("districtId",districtId))
                .add(Restrictions.eq("jobStatus","ACTIVE").ignoreCase())
                .setProjection(Projections.rowCount());

        return (Long) criteria.uniqueResult();

    }

    @Override
    public MACourseFirstCompletion getSuccessFulCompletionByExtrnalFlwId(String forMonth, Long Extr_Flw_Id, Integer state_id){
        Criteria criteria = getSession().createCriteria(MACourseFirstCompletion.class);
        criteria.add(Restrictions.like("forMonth",forMonth))
                .add(Restrictions.eq("externalFlwId",Extr_Flw_Id))
                .add(Restrictions.eq("jobStatus","ACTIVE").ignoreCase())/*
                .add(Restrictions.eq("stateId",state_id))*/;
        return (MACourseFirstCompletion) criteria.uniqueResult();
    }

    @Override
    public void updateMACourseFirstCompletion(MACourseFirstCompletion maCourseFirstCompletion) {

    }

    @Override
    public List<MACourseFirstCompletion> getSuccessFulCompletion(Long msisdn) {
        Criteria criteria = getSession().createCriteria(MACourseFirstCompletion.class);
        criteria.add(Restrictions.eq("msisdn",msisdn));
        return criteria.list();
    }

    @Override
    public List<MACourseFirstCompletion> getSuccessFulCompletionByState(Long msisdn, Integer stateId) {
        Criteria criteria = getSession().createCriteria(MACourseFirstCompletion.class);
        criteria.add(Restrictions.eq("msisdn",msisdn))
                .add(Restrictions.eq("stateId",stateId));
        return criteria.list();
    }

    @Override
    public List<MACourseFirstCompletion> getSuccessFulCompletionByDistrict(Long msisdn, Integer stateId, Integer districtId) {
        Criteria criteria = getSession().createCriteria(MACourseFirstCompletion.class);
        criteria.add(Restrictions.eq("msisdn",msisdn))
                .add(Restrictions.eq("stateId",stateId))
                .add(Restrictions.eq("districtId",districtId));
        return criteria.list();
    }

    @Override
    public List<MACourseFirstCompletion> getSuccessFulCompletionByBlock(Long msisdn, Integer stateId, Integer districtId, Integer blockId) {
        Criteria criteria = getSession().createCriteria(MACourseFirstCompletion.class);
        criteria.add(Restrictions.eq("msisdn",msisdn))
                .add(Restrictions.eq("stateId",stateId))
                .add(Restrictions.eq("districtId",districtId))
                .add(Restrictions.eq("blockId",blockId));
        return criteria.list();
    }

}
