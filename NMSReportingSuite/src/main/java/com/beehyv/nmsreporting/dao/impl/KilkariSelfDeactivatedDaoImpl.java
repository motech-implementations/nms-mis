package com.beehyv.nmsreporting.dao.impl;

import com.beehyv.nmsreporting.dao.AbstractDao;
import com.beehyv.nmsreporting.dao.KilkariSelfDeactivatedDao;
import com.beehyv.nmsreporting.model.KilkariSelfDeactivated;
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
@Repository("kilkariSelfDeactivatedDao")
public class KilkariSelfDeactivatedDaoImpl extends AbstractDao<Integer, KilkariSelfDeactivated> implements KilkariSelfDeactivatedDao {
    @Override
    public List<KilkariSelfDeactivated> getSelfDeactivatedUsers(Date fromDate, Date toDate) {
        Criteria criteria = getSession().createCriteria(KilkariSelfDeactivated.class);
        criteria.add(Restrictions.and(
                (Restrictions.not(
                        Restrictions.in("districtId", new Integer[] {471,474,483,490})
                )),
                Restrictions.lt("deactivationDate",toDate),
                Restrictions.ge("deactivationDate",fromDate)
        ));
        criteria.addOrder(Order.asc("deactivationDate"));
        return (List<KilkariSelfDeactivated>) criteria.list();
    }

    @Override
    public List<KilkariSelfDeactivated> getSelfDeactivatedUsersWithStateId(Date fromDate, Date toDate, Integer stateId) {
        Criteria criteria = getSession().createCriteria(KilkariSelfDeactivated.class);
        criteria.add(Restrictions.and(
                (Restrictions.not(
                        Restrictions.in("districtId", new Integer[] {471,474,483,490})
                )),
                Restrictions.lt("deactivationDate",toDate),
                Restrictions.ge("deactivationDate",fromDate)
        ))
                .add(Restrictions.eq("stateId", stateId));
        criteria.addOrder(Order.asc("deactivationDate"));
        return (List<KilkariSelfDeactivated>) criteria.list();
    }

    @Override
    public List<KilkariSelfDeactivated> getSelfDeactivatedUsersWithDistrictId(Date fromDate, Date toDate, Integer districtId) {
        Criteria criteria = getSession().createCriteria(KilkariSelfDeactivated.class);
        criteria.add(Restrictions.and(
                Restrictions.lt("deactivationDate",toDate),
                Restrictions.ge("deactivationDate",fromDate)
        ))
                .add(Restrictions.eq("districtId", districtId));
        criteria.addOrder(Order.asc("deactivationDate"));
        return (List<KilkariSelfDeactivated>) criteria.list();
    }

    @Override
    public List<KilkariSelfDeactivated> getSelfDeactivatedUsersWithBlockId(Date fromDate, Date toDate, Integer blockId) {
        Criteria criteria = getSession().createCriteria(KilkariSelfDeactivated.class);
        criteria.add(Restrictions.and(
                Restrictions.lt("deactivationDate",toDate),
                Restrictions.ge("deactivationDate",fromDate)
        ))
                .add(Restrictions.eq("blockId", blockId));
        criteria.addOrder(Order.asc("deactivationDate"));
        return (List<KilkariSelfDeactivated>) criteria.list();
    }

    @Override
    public Long getCountOfSelfDeactivatedUsers(Date fromDate, Date toDate, Integer districtId) {
        Criteria criteria = getSession().createCriteria(KilkariSelfDeactivated.class);
        criteria.add(Restrictions.and(
                Restrictions.lt("deactivationDate",toDate),
                Restrictions.ge("deactivationDate",fromDate)
        ))
                .add(Restrictions.eq("districtId", districtId))
                .setProjection(Projections.rowCount());

        return (Long) criteria.uniqueResult();
    }
}
