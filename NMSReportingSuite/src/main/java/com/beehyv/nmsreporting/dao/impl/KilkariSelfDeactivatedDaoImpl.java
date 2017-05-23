package com.beehyv.nmsreporting.dao.impl;

import com.beehyv.nmsreporting.dao.AbstractDao;
import com.beehyv.nmsreporting.dao.KilkariSelfDeactivatedDao;
import com.beehyv.nmsreporting.model.KilkariSelfDeactivated;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
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
        criteria.add(Restrictions.between("deactivationDate",fromDate,toDate));
        criteria.addOrder(Order.asc("deactivationDate"));
        return (List<KilkariSelfDeactivated>) criteria.list();
    }
}
