package com.beehyv.nmsreporting.dao.impl;

import com.beehyv.nmsreporting.dao.AbstractDao;
import com.beehyv.nmsreporting.dao.KilkariSixWeeksNoAnswerDao;
import com.beehyv.nmsreporting.model.KilkariManualDeactivations;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * Created by beehyv on 23/5/17.
 */
@Repository("kilkariSixWeeksNoAnswerDao")
public class KilkariSixWeeksNoAnswerDaoImpl extends AbstractDao<Integer, KilkariManualDeactivations> implements KilkariSixWeeksNoAnswerDao {

    @Override
    public Long getCountOfDeactivatedForDistrict(Date fromDate, Date toDate, Integer districtId) {
        Criteria criteria = getSession().createCriteria(KilkariManualDeactivations.class);
        criteria.add(Restrictions.and(
                Restrictions.lt("deactivationDate", toDate),
                Restrictions.ge("deactivationDate", fromDate),
                Restrictions.eq("deactivationReason", "WEEKLY_CALLS_NOT_ANSWERED")
        ))
                .add(Restrictions.eq("districtId", districtId))
                .setProjection(Projections.rowCount());

        return (Long) criteria.uniqueResult();
    }

    @Override
    public Long getCountOfLowListenershipUsersForDistrict(Date fromDate, Date toDate, Integer districtId) {
        Criteria criteria = getSession().createCriteria(KilkariManualDeactivations.class);
        criteria.add(Restrictions.and(
                Restrictions.lt("deactivationDate", toDate),
                Restrictions.ge("deactivationDate", fromDate),
                Restrictions.eq("deactivationReason", "LOW_LISTENERSHIP")
        ))
                .add(Restrictions.eq("districtId", districtId))
                .setProjection(Projections.rowCount());

        return (Long) criteria.uniqueResult();
    }
}
