package com.beehyv.nmsreporting.dao.impl;


import com.beehyv.nmsreporting.dao.*;
import com.beehyv.nmsreporting.model.*;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import javax.persistence.TemporalType;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

@Repository("kilkariMessageListenershipWeekDao")
public class KilkariMessageListenershipWeekDaoImpl extends AbstractDao<Integer,KilkariMessageListenershipWeek> implements KilkariMessageListenershipWeekDao {

    @Override
    public KilkariMessageListenership getListenerData(Integer locationId, String locationType, Date date){
        Criteria criteria = createEntityCriteria().addOrder(Order.asc("locationId"));
        criteria.add(Restrictions.and(
                Restrictions.eq("locationId",locationId.longValue()),
                Restrictions.eq("locationType",locationType),
                Restrictions.eq("date",date)
        ));
        List<KilkariMessageListenershipWeek> result = criteria.list();
        if(result.isEmpty()){
            Long a = (long)0;
            KilkariMessageListenership kilkariMessageListenership = new KilkariMessageListenership(0,locationType,locationId.longValue(),date,a,a,a,a,a,a,a);
            return kilkariMessageListenership;
        }
        KilkariMessageListenershipWeek kilkariMessageListenershipweek =  result.get(0);
        KilkariMessageListenership kilkariMessageListenership = new KilkariMessageListenership() ;

        kilkariMessageListenership.setId(kilkariMessageListenershipweek.getId());
        kilkariMessageListenership.setDate(kilkariMessageListenershipweek.getDate());
        kilkariMessageListenership.setLocationId(kilkariMessageListenershipweek.getLocationId());
        kilkariMessageListenership.setLocationType(kilkariMessageListenershipweek.getLocationType());
        kilkariMessageListenership.setTotalBeneficiariesCalled(kilkariMessageListenershipweek.getTotalBeneficiariesCalled() == null ? 0 : kilkariMessageListenershipweek.getTotalBeneficiariesCalled());
        kilkariMessageListenership.setAnsweredNoCalls(kilkariMessageListenershipweek.getAnsweredNoCalls() == null ? 0 : kilkariMessageListenershipweek.getAnsweredNoCalls());
        kilkariMessageListenership.setAnsweredMoreThan75Per(kilkariMessageListenershipweek.getAnsweredMoreThan75Per() == null ? 0 : kilkariMessageListenershipweek.getAnsweredMoreThan75Per());
        kilkariMessageListenership.setAnswered50To75Per(kilkariMessageListenershipweek.getAnswered50To75Per() == null ? 0 : kilkariMessageListenershipweek.getAnswered50To75Per());
        kilkariMessageListenership.setAnswered25To50Per(kilkariMessageListenershipweek.getAnswered25To50Per() == null ? 0 : kilkariMessageListenershipweek.getAnswered25To50Per());
        kilkariMessageListenership.setAnswered1To25Per(kilkariMessageListenershipweek.getAnswered1To25Per() == null ? 0 : kilkariMessageListenershipweek.getAnswered1To25Per());
        kilkariMessageListenership.setAnsweredAtleastOneCall(kilkariMessageListenershipweek.getAnsweredAtleastOneCall() == null ? 0 : kilkariMessageListenershipweek.getAnsweredAtleastOneCall());
        return kilkariMessageListenership;
    }
}

