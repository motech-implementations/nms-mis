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
}
