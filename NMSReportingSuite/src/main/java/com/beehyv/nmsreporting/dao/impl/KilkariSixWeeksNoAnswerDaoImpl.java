package com.beehyv.nmsreporting.dao.impl;

import com.beehyv.nmsreporting.dao.AbstractDao;
import com.beehyv.nmsreporting.dao.KilkariSixWeeksNoAnswerDao;
import com.beehyv.nmsreporting.model.KilkariManualDeactivations;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by beehyv on 23/5/17.
 */
@Repository("kilkariSixWeeksNoAnswerDao")
public class KilkariSixWeeksNoAnswerDaoImpl extends AbstractDao<Integer, KilkariManualDeactivations> implements KilkariSixWeeksNoAnswerDao {
    @Override
    public List<KilkariManualDeactivations> getKilkariUsers(Date fromDate, Date toDate) {
        Criteria criteria = getSession().createCriteria(KilkariManualDeactivations.class);
        criteria.add(Restrictions.and(
                (Restrictions.not(
                        Restrictions.in("districtId", new Integer[] {471,474,483,490})
                )),
                Restrictions.lt("deactivationDate",toDate),
                Restrictions.ge("deactivationDate",fromDate),
                Restrictions.eq("deactivationReason","WEEKLY_CALLS_NOT_ANSWERED")
        ));
        criteria.addOrder(Order.asc("deactivationDate"));
        return (List<KilkariManualDeactivations>) criteria.list();
    }

    @Override
    public List<KilkariManualDeactivations> getKilkariUsersWithStateId(Date fromDate, Date toDate, Integer stateId) {
        Criteria criteria = getSession().createCriteria(KilkariManualDeactivations.class);
        criteria.add(Restrictions.and(
                (Restrictions.not(
                        Restrictions.in("districtId", new Integer[] {471,474,483,490})
                )),
                Restrictions.lt("deactivationDate",toDate),
                Restrictions.ge("deactivationDate",fromDate),
                Restrictions.eq("deactivationReason","WEEKLY_CALLS_NOT_ANSWERED")
        ))
                .add(Restrictions.eq("stateId",stateId));
        criteria.addOrder(Order.asc("deactivationDate"));
        return (List<KilkariManualDeactivations>) criteria.list();
    }

    @Override
    public List<KilkariManualDeactivations> getKilkariUsersWithDistrictId(Date fromDate, Date toDate, Integer districtId) {
        Criteria criteria = getSession().createCriteria(KilkariManualDeactivations.class);
        criteria.add(Restrictions.and(
                Restrictions.lt("deactivationDate",toDate),
                Restrictions.ge("deactivationDate",fromDate),
                Restrictions.eq("deactivationReason","WEEKLY_CALLS_NOT_ANSWERED")
        ))
                .add(Restrictions.eq("districtId",districtId));
        criteria.addOrder(Order.asc("deactivationDate"));
        return (List<KilkariManualDeactivations>) criteria.list();
    }

    @Override
    public List<KilkariManualDeactivations> getKilkariUsersWithBlockId(Date fromDate, Date toDate, Integer blockId) {
        Criteria criteria = getSession().createCriteria(KilkariManualDeactivations.class);
        criteria.add(Restrictions.and(
                Restrictions.lt("deactivationDate",toDate),
                Restrictions.ge("deactivationDate",fromDate),
                Restrictions.eq("deactivationReason","WEEKLY_CALLS_NOT_ANSWERED")
        ))
                .add(Restrictions.eq("blockId",blockId));
        criteria.addOrder(Order.asc("deactivationDate"));
        return (List<KilkariManualDeactivations>) criteria.list();
    }

    @Override
    public Long getCountOfDeactivatedForDistrict(Date fromDate, Date toDate, Integer districtId) {
        Criteria criteria = getSession().createCriteria(KilkariManualDeactivations.class);
        criteria.add(Restrictions.and(
                Restrictions.lt("deactivationDate",toDate),
                Restrictions.ge("deactivationDate",fromDate),
                Restrictions.eq("deactivationReason","WEEKLY_CALLS_NOT_ANSWERED")
        ))
                .add(Restrictions.eq("districtId",districtId))
                .setProjection(Projections.rowCount());

        return (Long) criteria.uniqueResult();
    }


    @Override
    public List<KilkariManualDeactivations> getLowListenershipUsers(Date fromDate, Date toDate) {
        Criteria criteria = getSession().createCriteria(KilkariManualDeactivations.class);
        criteria.add(Restrictions.and(
                (Restrictions.not(
                        Restrictions.in("districtId", new Integer[] {471,474,483,490})
                )),
                Restrictions.lt("deactivationDate",toDate),
                Restrictions.ge("deactivationDate",fromDate),
                Restrictions.eq("deactivationReason","LOW_LISTENERSHIP")
        ));
        criteria.addOrder(Order.asc("deactivationDate"));
        return (List<KilkariManualDeactivations>) criteria.list();
    }

    @Override
    public List<KilkariManualDeactivations> getLowListenershipUsersWithStateId(Date fromDate, Date toDate, Integer stateId) {
        Criteria criteria = getSession().createCriteria(KilkariManualDeactivations.class);
        criteria.add(Restrictions.and(
                (Restrictions.not(
                        Restrictions.in("districtId", new Integer[] {471,474,483,490})
                )),
                Restrictions.lt("deactivationDate",toDate),
                Restrictions.ge("deactivationDate",fromDate),
                Restrictions.eq("deactivationReason","LOW_LISTENERSHIP")
        ))
                .add(Restrictions.eq("stateId",stateId));
        criteria.addOrder(Order.asc("deactivationDate"));
        return (List<KilkariManualDeactivations>) criteria.list();
    }

    @Override
    public List<KilkariManualDeactivations> getLowListenershipUsersWithDistrictId(Date fromDate, Date toDate, Integer districtId) {
        Criteria criteria = getSession().createCriteria(KilkariManualDeactivations.class);
        criteria.add(Restrictions.and(
                Restrictions.lt("deactivationDate",toDate),
                Restrictions.ge("deactivationDate",fromDate),
                Restrictions.eq("deactivationReason","LOW_LISTENERSHIP")
        ))
                .add(Restrictions.eq("districtId",districtId));
        criteria.addOrder(Order.asc("deactivationDate"));
        return (List<KilkariManualDeactivations>) criteria.list();
    }

    @Override
    public List<KilkariManualDeactivations> getLowListenershipUsersWithBlockId(Date fromDate, Date toDate, Integer blockId) {
        Criteria criteria = getSession().createCriteria(KilkariManualDeactivations.class);
        criteria.add(Restrictions.and(
                Restrictions.lt("deactivationDate",toDate),
                Restrictions.ge("deactivationDate",fromDate),
                Restrictions.eq("deactivationReason","LOW_LISTENERSHIP")
        ))
                .add(Restrictions.eq("blockId",blockId));
        criteria.addOrder(Order.asc("deactivationDate"));
        return (List<KilkariManualDeactivations>) criteria.list();
    }

    @Override
    public Long getCountOfLowListenershipUsersForDistrict(Date fromDate, Date toDate, Integer districtId) {
        Criteria criteria = getSession().createCriteria(KilkariManualDeactivations.class);
        criteria.add(Restrictions.and(
                Restrictions.lt("deactivationDate",toDate),
                Restrictions.ge("deactivationDate",fromDate),
                Restrictions.eq("deactivationReason","LOW_LISTENERSHIP")
        ))
                .add(Restrictions.eq("districtId",districtId))
                .setProjection(Projections.rowCount());

        return (Long) criteria.uniqueResult();
    }
}
