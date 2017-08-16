package com.beehyv.nmsreporting.dao.impl;

import com.beehyv.nmsreporting.dao.AbstractDao;
import com.beehyv.nmsreporting.dao.KilkariSixWeeksNoAnswerDao;
import com.beehyv.nmsreporting.model.KilkariSixWeeksNoAnswer;
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
public class KilkariSixWeeksNoAnswerDaoImpl extends AbstractDao<Integer, KilkariSixWeeksNoAnswer> implements KilkariSixWeeksNoAnswerDao {
    @Override
    public List<KilkariSixWeeksNoAnswer> getKilkariUsers(Date fromDate, Date toDate) {
        Criteria criteria = getSession().createCriteria(KilkariSixWeeksNoAnswer.class);
        criteria.add(Restrictions.and(
                Restrictions.lt("deactivationDate",toDate),
                Restrictions.ge("deactivationDate",fromDate)
        ));
        criteria.addOrder(Order.asc("deactivationDate"));
        return (List<KilkariSixWeeksNoAnswer>) criteria.list();
    }

    @Override
    public List<KilkariSixWeeksNoAnswer> getKilkariUsersWithStateId(Date fromDate, Date toDate, Integer stateId) {
        Criteria criteria = getSession().createCriteria(KilkariSixWeeksNoAnswer.class);
        criteria.add(Restrictions.and(
                Restrictions.lt("deactivationDate",toDate),
                Restrictions.ge("deactivationDate",fromDate)
        ))
                .add(Restrictions.eq("stateId",stateId));
        criteria.addOrder(Order.asc("deactivationDate"));
        return (List<KilkariSixWeeksNoAnswer>) criteria.list();
    }

    @Override
    public List<KilkariSixWeeksNoAnswer> getKilkariUsersWithDistrictId(Date fromDate, Date toDate, Integer districtId) {
        Criteria criteria = getSession().createCriteria(KilkariSixWeeksNoAnswer.class);
        criteria.add(Restrictions.and(
                Restrictions.lt("deactivationDate",toDate),
                Restrictions.ge("deactivationDate",fromDate)
        ))
                .add(Restrictions.eq("districtId",districtId));
        criteria.addOrder(Order.asc("deactivationDate"));
        return (List<KilkariSixWeeksNoAnswer>) criteria.list();
    }

    @Override
    public List<KilkariSixWeeksNoAnswer> getKilkariUsersWithBlockId(Date fromDate, Date toDate, Integer blockId) {
        Criteria criteria = getSession().createCriteria(KilkariSixWeeksNoAnswer.class);
        criteria.add(Restrictions.and(
                Restrictions.lt("deactivationDate",toDate),
                Restrictions.ge("deactivationDate",fromDate)
        ))
                .add(Restrictions.eq("blockId",blockId));
        criteria.addOrder(Order.asc("deactivationDate"));
        return (List<KilkariSixWeeksNoAnswer>) criteria.list();
    }
}
