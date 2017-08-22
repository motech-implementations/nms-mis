package com.beehyv.nmsreporting.dao.impl;

import com.beehyv.nmsreporting.dao.AbstractDao;
import com.beehyv.nmsreporting.dao.KilkariSelfDeactivatedDao;
import com.beehyv.nmsreporting.model.KilkariSelfDeactivated;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * Created by beehyv on 23/5/17.
 */
@Repository("kilkariSelfDeactivatedDao")
public class KilkariSelfDeactivatedDaoImpl extends AbstractDao<Integer, KilkariSelfDeactivated> implements KilkariSelfDeactivatedDao {

    @Override
    public Long getCountOfSelfDeactivatedUsers(Date fromDate, Date toDate, Integer districtId) {
        Criteria criteria = getSession().createCriteria(KilkariSelfDeactivated.class);
        criteria.add(Restrictions.and(
                Restrictions.lt("deactivationDate", toDate),
                Restrictions.ge("deactivationDate", fromDate)
        ))
                .add(Restrictions.eq("districtId", districtId))
                .setProjection(Projections.rowCount());

        return (Long) criteria.uniqueResult();
    }
}
