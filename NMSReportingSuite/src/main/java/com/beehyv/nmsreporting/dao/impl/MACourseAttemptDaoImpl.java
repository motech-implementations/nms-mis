package com.beehyv.nmsreporting.dao.impl;

import com.beehyv.nmsreporting.dao.AbstractDao;
import com.beehyv.nmsreporting.dao.MACourseAttemptDao;
import com.beehyv.nmsreporting.enums.AccountStatus;
import com.beehyv.nmsreporting.model.FrontLineWorkers;
import com.beehyv.nmsreporting.model.MACourseCompletion;
import com.beehyv.nmsreporting.model.User;
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
    public List<FrontLineWorkers> getSuccessFulFirstCompletion(Date fromDate, Date toDate) {
        Criteria criteria = getSession().createCriteria(MACourseCompletion.class);
        criteria.add(Restrictions.and(Restrictions.between("lastModifiedDate",fromDate,toDate),
                Restrictions.eq("passed",true)));
        List<FrontLineWorkers> successFullFlws=new ArrayList<>();
        List<MACourseCompletion> successFullCompletion=(List<MACourseCompletion>)criteria.list();
        for(MACourseCompletion maCourseCompletion:successFullCompletion){
            Criteria criteria1 = getSession().createCriteria(MACourseCompletion.class).addOrder(Order.asc("lastModifiedDate"));
            criteria1.add(Restrictions.and(Restrictions.eq("flwId",maCourseCompletion.getFlwId()),
                    Restrictions.eq("passed",true)));
            MACourseCompletion firstCompletion=(MACourseCompletion)criteria.list().get(0);
            if(firstCompletion.getLastModifiedDate().after(fromDate)&&firstCompletion.getLastModifiedDate().before(toDate)) {
                Criteria criteria2 = getSession().createCriteria(FrontLineWorkers.class);
                criteria2.add(Restrictions.eq("id", firstCompletion.getFlwId()));

                successFullFlws.add((FrontLineWorkers) criteria2.list().get(0));
            }
        }
        return successFullFlws;
    }

    @Override
    public Date getFirstCompletionDate(Integer flwId) {
        Criteria criteria = createEntityCriteria().addOrder(Order.asc("lastModifiedDate"));
        criteria.add(Restrictions.and(Restrictions.eq("flwId",flwId),
                Restrictions.eq("passed",true)));
        MACourseCompletion firstCompletion=(MACourseCompletion)criteria.list().get(0);

            return firstCompletion.getLastModifiedDate();


    }
}
