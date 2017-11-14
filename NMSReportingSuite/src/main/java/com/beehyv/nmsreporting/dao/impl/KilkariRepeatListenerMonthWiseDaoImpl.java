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

    public KilkariRepeatListenerMonthWise getListenerData(Date date){
        Criteria criteria = createEntityCriteria().addOrder(Order.asc("date"));
        criteria.add(Restrictions.eq("date", date));
        List<KilkariRepeatListenerMonthWise> kilkariRepeatListenerMonthWiseList = criteria.list();
        if(kilkariRepeatListenerMonthWiseList.isEmpty()){
            KilkariRepeatListenerMonthWise kilkariRepeatListenerMonthWise = new KilkariRepeatListenerMonthWise(0,date,0,0,0,0,0,0);
            return kilkariRepeatListenerMonthWise;
        }
        KilkariRepeatListenerMonthWise kilkariRepeatListenerMonthWise = kilkariRepeatListenerMonthWiseList.get(0);
        kilkariRepeatListenerMonthWise.setFiveCallsAnswered(kilkariRepeatListenerMonthWise.getFiveCallsAnswered() == null ? 0 : kilkariRepeatListenerMonthWise.getFiveCallsAnswered());
        kilkariRepeatListenerMonthWise.setFourCallsAnswered(kilkariRepeatListenerMonthWise.getFourCallsAnswered() == null ? 0 : kilkariRepeatListenerMonthWise.getFourCallsAnswered());
        kilkariRepeatListenerMonthWise.setThreeCallsAnswered(kilkariRepeatListenerMonthWise.getThreeCallsAnswered() == null ? 0 : kilkariRepeatListenerMonthWise.getThreeCallsAnswered());
        kilkariRepeatListenerMonthWise.setTwoCallsAnswered(kilkariRepeatListenerMonthWise.getTwoCallsAnswered() == null ? 0 : kilkariRepeatListenerMonthWise.getTwoCallsAnswered());
        kilkariRepeatListenerMonthWise.setOneCallAnswered(kilkariRepeatListenerMonthWise.getOneCallAnswered() == null ? 0 : kilkariRepeatListenerMonthWise.getOneCallAnswered());
        kilkariRepeatListenerMonthWise.setNoCallsAnswered(kilkariRepeatListenerMonthWise.getNoCallsAnswered() == null ? 0 : kilkariRepeatListenerMonthWise.getNoCallsAnswered());
        return kilkariRepeatListenerMonthWise;
    }

}
