package com.beehyv.nmsreporting.dao.impl;

/**
 * Created by himanshu on 06/10/17.
 */

import com.beehyv.nmsreporting.dao.AbstractDao;
import com.beehyv.nmsreporting.dao.BeneficiaryCallMeasureDao;
import com.beehyv.nmsreporting.dao.KilkariMessageListenershipReportDao;
import com.beehyv.nmsreporting.dao.KilkariThematicContentReportDao;
import com.beehyv.nmsreporting.model.BeneficiaryCallMeasure;
import com.beehyv.nmsreporting.model.KilkariMessageListenership;
import com.beehyv.nmsreporting.model.KilkariThematicContent;
import com.beehyv.nmsreporting.model.State;
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

@Repository("kilkariMessageListenershipDao")
public class KilkariMessageListenershipDaoImpl extends AbstractDao<Integer,KilkariMessageListenership> implements KilkariMessageListenershipReportDao {
    
    @Override
    public KilkariMessageListenership getListenerData(Integer locationId, String locationType, Date date){
        Criteria criteria = createEntityCriteria().addOrder(Order.asc("locationId"));
        criteria.add(Restrictions.and(
                Restrictions.eq("locationId",locationId.longValue()),
                Restrictions.eq("locationType",locationType),
                Restrictions.eq("date",date)
        ));
        List<KilkariMessageListenership> result = criteria.list();
        if(result.isEmpty()){
            Long a = (long)0;
            KilkariMessageListenership kilkariMessageListenership = new KilkariMessageListenership(0,locationType,locationId.longValue(),date,a,a,a,a,a,a,a);
            return kilkariMessageListenership;
        }
        KilkariMessageListenership kilkariMessageListenership =  result.get(0);
        kilkariMessageListenership.setTotalBeneficiariesCalled(kilkariMessageListenership.getTotalBeneficiariesCalled() == null ? 0 : kilkariMessageListenership.getTotalBeneficiariesCalled());
        kilkariMessageListenership.setAnsweredNoCalls(kilkariMessageListenership.getAnsweredNoCalls() == null ? 0 : kilkariMessageListenership.getAnsweredNoCalls());
        kilkariMessageListenership.setAnsweredMoreThan75Per(kilkariMessageListenership.getAnsweredMoreThan75Per() == null ? 0 : kilkariMessageListenership.getAnsweredMoreThan75Per());
        kilkariMessageListenership.setAnswered50To75Per(kilkariMessageListenership.getAnswered50To75Per() == null ? 0 : kilkariMessageListenership.getAnswered50To75Per());
        kilkariMessageListenership.setAnswered25To50Per(kilkariMessageListenership.getAnswered25To50Per() == null ? 0 : kilkariMessageListenership.getAnswered25To50Per());
        kilkariMessageListenership.setAnswered1To25Per(kilkariMessageListenership.getAnswered1To25Per() == null ? 0 : kilkariMessageListenership.getAnswered1To25Per());
        kilkariMessageListenership.setAnsweredAtleastOneCall(kilkariMessageListenership.getAnsweredAtleastOneCall() == null ? 0 : kilkariMessageListenership.getAnsweredAtleastOneCall());
        return kilkariMessageListenership;
    }
}
