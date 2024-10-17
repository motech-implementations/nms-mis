package com.beehyv.nmsreporting.dao.impl;

/**
 * Created by himanshu on 06/10/17.
 */

import com.beehyv.nmsreporting.dao.AbstractDao;
import com.beehyv.nmsreporting.dao.KilkariMessageListenershipReportDao;
import com.beehyv.nmsreporting.dao.KilkariThematicContentReportDao;
import com.beehyv.nmsreporting.model.KilkariMessageListenership;
import com.beehyv.nmsreporting.model.KilkariThematicContent;
import com.beehyv.nmsreporting.model.State;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.TemporalType;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

@Repository("kilkariMessageListenershipDao")
public class KilkariMessageListenershipDaoImpl extends AbstractDao<Integer,KilkariMessageListenership> implements KilkariMessageListenershipReportDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(KilkariMessageListenershipDaoImpl.class);

    @Override
    public KilkariMessageListenership getListenerData(Integer locationId, String locationType, Date date,String periodType){
        Criteria criteria = createEntityCriteria().addOrder(Order.asc("locationId"));
        criteria.add(Restrictions.and(
                Restrictions.eq("locationId",locationId.longValue()),
                Restrictions.eq("locationType",locationType),
                Restrictions.eq("periodType",periodType),
                Restrictions.eq("date",date)
        ));
        List<KilkariMessageListenership> result = criteria.list();
        if(result.isEmpty()){
            Long a = (long)0;
            KilkariMessageListenership kilkariMessageListenership = new KilkariMessageListenership(0,locationType,locationId.longValue(),date,a,a,a,a,a,a,a,"");
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



    @Override
    public Long getTotalAnsweredAtLeastOneCall(Integer locationId, String locationType, Date fromDate, Date toDate, String periodType) {
        String sql = "SELECT SUM(answered_atleast_one_call) " +
                "FROM agg_kilkari_message_listenership " +
                "WHERE location_id = :locationId " +
                "AND location_type = :locationType " +
                "AND period_type = :periodType " +
                "AND date BETWEEN :fromDate AND :toDate";

        LOGGER.info("Query - {} " , sql);
        SQLQuery query = getSession().createSQLQuery(sql);
        query.setParameter("locationId", locationId.longValue());
        query.setParameter("locationType", locationType);
        query.setParameter("periodType", periodType);
        query.setParameter("fromDate", fromDate);
        query.setParameter("toDate", toDate);

        Object result = query.uniqueResult();
        LOGGER.info("Operation = TotalAnsweredAtLeastOneCall, status = COMPLETED" );
        LOGGER.info("result:{}", result != null ? ((Number) result).longValue() : 0);
        return result != null ? ((Number) result).longValue() : 0L;
    }
}
