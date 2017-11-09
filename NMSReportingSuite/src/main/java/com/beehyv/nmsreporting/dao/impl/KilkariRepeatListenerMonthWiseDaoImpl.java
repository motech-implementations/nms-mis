package com.beehyv.nmsreporting.dao.impl;

/**
 * Created by himanshu on 06/10/17.
 */

import com.beehyv.nmsreporting.dao.AbstractDao;
import com.beehyv.nmsreporting.dao.KilkariRepeatListenerMonthWiseDao;
import com.beehyv.nmsreporting.model.KilkariRepeatListenerMonthWise;
import com.beehyv.nmsreporting.model.State;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository("kilkariRepeatListenerMonthWiseDao")
public class KilkariRepeatListenerMonthWiseDaoImpl extends AbstractDao<Integer,KilkariRepeatListenerMonthWise> implements KilkariRepeatListenerMonthWiseDao {

    public List<KilkariRepeatListenerMonthWise> getListenerData(Date fromDate, Date toDate){
        Criteria criteria = createEntityCriteria().addOrder(Order.asc("date"));
        criteria.add(Restrictions.between("date", fromDate, toDate));
        List<KilkariRepeatListenerMonthWise> kilkariRepeatListenerMonthWiseList = criteria.list();
        if(kilkariRepeatListenerMonthWiseList.isEmpty()){
            return null;
        }
        return kilkariRepeatListenerMonthWiseList;
    }

}
