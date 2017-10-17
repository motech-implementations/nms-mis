package com.beehyv.nmsreporting.dao.impl;

/**
 * Created by himanshu on 06/10/17.
 */

import com.beehyv.nmsreporting.dao.AbstractDao;
import com.beehyv.nmsreporting.dao.BeneficiaryCallMeasureDao;
import com.beehyv.nmsreporting.dao.KilkariMessageListenershipReportDao;
import com.beehyv.nmsreporting.dao.KilkariThematicContentReportDao;
import com.beehyv.nmsreporting.model.BeneficiaryCallMeasure;
import com.beehyv.nmsreporting.model.KilkariThematicContent;
import com.beehyv.nmsreporting.model.State;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import javax.persistence.TemporalType;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

@Repository("kilkariMessageListenershipDao")
public class KilkariMessageListenershipDaoImpl extends AbstractDao<Integer,BeneficiaryCallMeasure> implements KilkariMessageListenershipReportDao {

    @Override
    public List<State> getStateList(){
        Query query = getSession().createSQLQuery("select * from dim_state s where s.serviceType = 'K' or s.serviceType = 'ALL'");
        List<State> result = query.list();
        return result;
    }


    @Override
    public List<Object> getAllBeneficiaryIds(Date startDate, Date endDate, Integer stateId){

        Query query = getSession().createSQLQuery("select DISTINCT bcm.pregnancy_id from beneficiary_call_measure bcm where bcm.modificationDate >= :startDate and bcm.modificationDate <= :endDate and bcm.state_id = :stateId")
                .setParameter("startDate",startDate)
                .setParameter("endDate",endDate)
                .setParameter("stateId",stateId);
        List<Object> result = query.list();
        return result;
    }

    @Override
    public Integer getTotalCallsMadeToABeneficiary(BigInteger beneficiaryId) {
        Query query = getSession().createSQLQuery("select count(DISTINCT bcm.campaign_id) from beneficiary_call_measure bcm where bcm.pregnancy_id = :beneficiaryId")
                .setParameter("beneficiaryId", beneficiaryId);
        BigInteger result = (BigInteger) query.uniqueResult();
        return result.intValue();
    }


    @Override
    public Integer getTotalCallsAnsweredByBeneficiary(BigInteger beneficiaryId) {
        Query query = getSession().createSQLQuery("select count(DISTINCT bcm.campaign_id) from beneficiary_call_measure bcm where bcm.pregnancy_id = :beneficiaryId and bcm.call_status = :call_status")
                .setParameter("beneficiaryId", beneficiaryId)
                .setParameter("call_status","SUCCESS");
        BigInteger result = (BigInteger) query.uniqueResult();
        return result.intValue();
    }
}
