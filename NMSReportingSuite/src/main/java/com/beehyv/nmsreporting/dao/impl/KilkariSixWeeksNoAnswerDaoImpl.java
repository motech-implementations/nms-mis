package com.beehyv.nmsreporting.dao.impl;

import com.beehyv.nmsreporting.dao.AbstractDao;
import com.beehyv.nmsreporting.dao.KilkariSixWeeksNoAnswerDao;
import com.beehyv.nmsreporting.model.KilkariDeactivationOther;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by beehyv on 23/5/17.
 */
@Repository("kilkariSixWeeksNoAnswerDao")
public class KilkariSixWeeksNoAnswerDaoImpl extends AbstractDao<Integer, KilkariDeactivationOther> implements KilkariSixWeeksNoAnswerDao {
    @Override
    public List<KilkariDeactivationOther> getKilkariUsers(Date fromDate, Date toDate) {
        Criteria criteria = getSession().createCriteria(KilkariDeactivationOther.class);
        criteria.add(Restrictions.and(
                Restrictions.lt("deactivationDate",toDate),
                Restrictions.ge("deactivationDate",fromDate),
                Restrictions.eq("deactivationReason","WEEKLY_CALLS_NOT_ANSWERED")
        ));
        criteria.addOrder(Order.asc("deactivationDate"));
        return (List<KilkariDeactivationOther>) criteria.list();
    }

    @Override
    public List<KilkariDeactivationOther> getKilkariUsersWithStateId(Date fromDate, Date toDate, Integer stateId) {
        Criteria criteria = getSession().createCriteria(KilkariDeactivationOther.class);
        criteria.add(Restrictions.and(
                Restrictions.lt("deactivationDate",toDate),
                Restrictions.ge("deactivationDate",fromDate),
                Restrictions.eq("deactivationReason","WEEKLY_CALLS_NOT_ANSWERED")
        ))
                .add(Restrictions.eq("stateId",stateId));
        criteria.addOrder(Order.asc("deactivationDate"));
        return (List<KilkariDeactivationOther>) criteria.list();
    }

    @Override
    public List<KilkariDeactivationOther> getKilkariUsersWithDistrictId(Date fromDate, Date toDate, Integer districtId) {
        Criteria criteria = getSession().createCriteria(KilkariDeactivationOther.class);
        criteria.add(Restrictions.and(
                Restrictions.lt("deactivationDate",toDate),
                Restrictions.ge("deactivationDate",fromDate),
                Restrictions.eq("deactivationReason","WEEKLY_CALLS_NOT_ANSWERED")
        ))
                .add(Restrictions.eq("districtId",districtId));
        criteria.addOrder(Order.asc("deactivationDate"));
        return (List<KilkariDeactivationOther>) criteria.list();
    }

    @Override
    public List<KilkariDeactivationOther> getKilkariUsersWithBlockId(Date fromDate, Date toDate, Integer blockId) {
        Criteria criteria = getSession().createCriteria(KilkariDeactivationOther.class);
        criteria.add(Restrictions.and(
                Restrictions.lt("deactivationDate",toDate),
                Restrictions.ge("deactivationDate",fromDate),
                Restrictions.eq("deactivationReason","WEEKLY_CALLS_NOT_ANSWERED")
        ))
                .add(Restrictions.eq("blockId",blockId));
        criteria.addOrder(Order.asc("deactivationDate"));
        return (List<KilkariDeactivationOther>) criteria.list();
    }


    @Override
    public List<KilkariDeactivationOther> getLowListenershipUsers(Date fromDate, Date toDate) {
        Criteria criteria = getSession().createCriteria(KilkariDeactivationOther.class);
        criteria.add(Restrictions.and(
                Restrictions.lt("deactivationDate",toDate),
                Restrictions.ge("deactivationDate",fromDate),
                Restrictions.eq("deactivationReason","LOW_LISTENERSHIP")
        ));
        criteria.addOrder(Order.asc("deactivationDate"));
        return (List<KilkariDeactivationOther>) criteria.list();
    }

    @Override
    public List<KilkariDeactivationOther> getLowListenershipUsersWithStateId(Date fromDate, Date toDate, Integer stateId) {
        Criteria criteria = getSession().createCriteria(KilkariDeactivationOther.class);
        criteria.add(Restrictions.and(
                Restrictions.lt("deactivationDate",toDate),
                Restrictions.ge("deactivationDate",fromDate),
                Restrictions.eq("deactivationReason","LOW_LISTENERSHIP")
        ))
                .add(Restrictions.eq("stateId",stateId));
        criteria.addOrder(Order.asc("deactivationDate"));
        return (List<KilkariDeactivationOther>) criteria.list();
    }

    @Override
    public List<KilkariDeactivationOther> getLowListenershipUsersWithDistrictId(Date fromDate, Date toDate, Integer districtId) {
        Criteria criteria = getSession().createCriteria(KilkariDeactivationOther.class);
        criteria.add(Restrictions.and(
                Restrictions.lt("deactivationDate",toDate),
                Restrictions.ge("deactivationDate",fromDate),
                Restrictions.eq("deactivationReason","LOW_LISTENERSHIP")
        ))
                .add(Restrictions.eq("districtId",districtId));
        criteria.addOrder(Order.asc("deactivationDate"));
        return (List<KilkariDeactivationOther>) criteria.list();
    }

    @Override
    public List<KilkariDeactivationOther> getLowListenershipUsersWithBlockId(Date fromDate, Date toDate, Integer blockId) {
        Criteria criteria = getSession().createCriteria(KilkariDeactivationOther.class);
        criteria.add(Restrictions.and(
                Restrictions.lt("deactivationDate",toDate),
                Restrictions.ge("deactivationDate",fromDate),
                Restrictions.eq("deactivationReason","LOW_LISTENERSHIP")
        ))
                .add(Restrictions.eq("blockId",blockId));
        criteria.addOrder(Order.asc("deactivationDate"));
        return (List<KilkariDeactivationOther>) criteria.list();
    }
}
