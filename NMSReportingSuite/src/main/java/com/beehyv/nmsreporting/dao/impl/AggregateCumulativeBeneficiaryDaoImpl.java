package com.beehyv.nmsreporting.dao.impl;

import com.beehyv.nmsreporting.dao.AbstractDao;
import com.beehyv.nmsreporting.dao.AggregateCumulativeBeneficiaryDao;
import com.beehyv.nmsreporting.model.AggregateCumulativeBeneficiary;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

/**
 * Created by beehyv on 9/10/17.
 */
@Repository("aggregateCumulativeBeneficiaryDao")
public class AggregateCumulativeBeneficiaryDaoImpl extends AbstractDao<Integer,AggregateCumulativeBeneficiary> implements AggregateCumulativeBeneficiaryDao {

    @Override
    public AggregateCumulativeBeneficiary getCumulativeBeneficiary(Long locationId, String locationType, Date toDate,String periodType){


        Criteria criteria = createEntityCriteria().addOrder(Order.asc("locationId"));
        criteria.add(Restrictions.and(
                Restrictions.eq("locationId",locationId.longValue()),
                Restrictions.eq("locationType",locationType),
                Restrictions.eq("periodType",periodType),
                Restrictions.eq("date",toDate)
        ));
        List<AggregateCumulativeBeneficiary> result = criteria.list();
        if(result.size() < 1){
            Long a = (long)0;
            AggregateCumulativeBeneficiary aggregateCumulativeBeneficiary = new AggregateCumulativeBeneficiary(0,locationType,locationId,toDate,a,a,a,a,a,a,a,"",a);
            return aggregateCumulativeBeneficiary;
        }
        AggregateCumulativeBeneficiary aggregateCumulativeBeneficiary = result.get(0);
        aggregateCumulativeBeneficiary.setJoinedSubscription(aggregateCumulativeBeneficiary.getJoinedSubscription() == null ? 0 : aggregateCumulativeBeneficiary.getJoinedSubscription());
        aggregateCumulativeBeneficiary.setChildCompletion(aggregateCumulativeBeneficiary.getChildCompletion() == null ? 0 : aggregateCumulativeBeneficiary.getChildCompletion());
        aggregateCumulativeBeneficiary.setMotherCompletion(aggregateCumulativeBeneficiary.getMotherCompletion() == null ? 0 : aggregateCumulativeBeneficiary.getMotherCompletion());
        aggregateCumulativeBeneficiary.setLowListenership(aggregateCumulativeBeneficiary.getLowListenership() == null ? 0 : aggregateCumulativeBeneficiary.getLowListenership());
        aggregateCumulativeBeneficiary.setSelfDeactivated(aggregateCumulativeBeneficiary.getSelfDeactivated() == null ? 0 : aggregateCumulativeBeneficiary.getSelfDeactivated());
        aggregateCumulativeBeneficiary.setNotAnswering(aggregateCumulativeBeneficiary.getNotAnswering() == null ? 0 : aggregateCumulativeBeneficiary.getNotAnswering());
        aggregateCumulativeBeneficiary.setSystemDeactivation(aggregateCumulativeBeneficiary.getSystemDeactivation() == null ? 0 : aggregateCumulativeBeneficiary.getSystemDeactivation());
        aggregateCumulativeBeneficiary.setSubscriptionsRejected(aggregateCumulativeBeneficiary.getSubscriptionsRejected() == null ? 0 : aggregateCumulativeBeneficiary.getSubscriptionsRejected());

        return aggregateCumulativeBeneficiary;
    }

    public Long getCumulativeJoinedSubscription(Long locationId, String locationType, Date toDate) {
        String hql;
        Query query;

        if (locationId == 0 && "State".equalsIgnoreCase(locationType)) {
            hql = "SELECT COALESCE(SUM(CAST(joinedSubscription AS long)), 0) FROM AggregateCumulativeBeneficiary WHERE locationType = :locationType AND date <= :toDate AND periodType = 'WEEK'";
            query = getSession().createQuery(hql);
            query.setParameter("locationType", "State");
        } else {
            hql = "SELECT COALESCE(SUM(CAST(joinedSubscription AS long)), 0) FROM AggregateCumulativeBeneficiary WHERE locationId = :locationId AND locationType = :locationType AND date <= :toDate AND periodType = 'WEEK'";
            query = getSession().createQuery(hql);
            query.setParameter("locationId", locationId);
            query.setParameter("locationType", locationType);
        }

        query.setParameter("toDate", toDate);

        Long result = (Long) query.uniqueResult();
        return result != null ? result : 0L;
    }




    @Override
    public Long getTotalBeneficiariesCalled(Long locationId, String locationType, Date date){

        Long result = (long)0;

        if(locationType.equalsIgnoreCase("State")){
            Query query = getSession().createSQLQuery("select count(DISTINCT bcm.pregnancy_id) from beneficiary_call_measure bcm left join pregnancy p on bcm.pregnancy_id = p.id where p.state_id = :stateId and bcm.modificationDate < :Date and bcm.call_source = 'OBD'")
                    .setParameter("stateId", locationId)
                    .setParameter("Date",date);
            result = (Long) query.uniqueResult();
        }
        if(locationType.equalsIgnoreCase("District")){
            Query query = getSession().createSQLQuery("select count(DISTINCT bcm.pregnancy_id) from beneficiary_call_measure bcm left join pregnancy p on bcm.pregnancy_id = p.id where p.district_id = :districtId and bcm.modificationDate < :Date and bcm.call_source = 'OBD'")
                    .setParameter("districtId", locationId)
                    .setParameter("Date",date);
            result = (Long) query.uniqueResult();
        }
        if(locationType.equalsIgnoreCase("Block")){
            Query query = getSession().createSQLQuery("select count(DISTINCT bcm.pregnancy_id) from beneficiary_call_measure bcm left join pregnancy p on bcm.pregnancy_id = p.id where p.healthBlock_id = :stateId and bcm.modificationDate < :Date and bcm.call_source = 'OBD'")
                    .setParameter("blockId", locationId)
                    .setParameter("Date",date);
            result = (Long) query.uniqueResult();
        }
        if(locationType.equalsIgnoreCase("Subcenter")){
            Query query = getSession().createSQLQuery("select count(DISTINCT bcm.pregnancy_id) from beneficiary_call_measure bcm left join pregnancy p on bcm.pregnancy_id = p.id where p.healthSubFacility_id = :subcenterId and bcm.modificationDate < :Date  and bcm.call_source = 'OBD'")
                    .setParameter("subcenterId", locationId)
                    .setParameter("Date",date);
            result = (Long) query.uniqueResult();
        }
        return result;
    }


    @Override
    public Long getTotalBeneficiariesAnsweredAtleastOnce(Long locationId, String locationType, Date date){

        Long result = (long)0;

        if(locationType.equalsIgnoreCase("State")){
            Query query = getSession().createSQLQuery("select count(DISTINCT bcm.pregnancy_id) from beneficiary_call_measure bcm left join pregnancy p on bcm.pregnancy_id = p.id where p.state_id = :stateId and bcm.modificationDate < :Date and bcm.call_source = 'OBD' and bcm.call_status = 'SUCCESS'")
                    .setParameter("stateId", locationId)
                    .setParameter("Date",date);
            result = (Long) query.uniqueResult();
        }
        if(locationType.equalsIgnoreCase("District")){
            Query query = getSession().createSQLQuery("select count(DISTINCT bcm.pregnancy_id) from beneficiary_call_measure bcm left join pregnancy p on bcm.pregnancy_id = p.id where p.district_id = :districtId and bcm.modificationDate < :Date and bcm.call_source = 'OBD' and bcm.call_status = 'SUCCESS'")
                    .setParameter("districtId", locationId)
                    .setParameter("Date",date);
            result = (Long) query.uniqueResult();
        }
        if(locationType.equalsIgnoreCase("Block")){
            Query query = getSession().createSQLQuery("select count(DISTINCT bcm.pregnancy_id) from beneficiary_call_measure bcm left join pregnancy p on bcm.pregnancy_id = p.id where p.healthBlock_id = :stateId and bcm.modificationDate < :Date and bcm.call_source = 'OBD' and bcm.call_status = 'SUCCESS'")
                    .setParameter("blockId", locationId)
                    .setParameter("Date",date);
            result = (Long) query.uniqueResult();
        }
        if(locationType.equalsIgnoreCase("Subcenter")){
            Query query = getSession().createSQLQuery("select count(DISTINCT bcm.pregnancy_id) from beneficiary_call_measure bcm left join pregnancy p on bcm.pregnancy_id = p.id where p.healthSubFacility_id = :subcenterId and bcm.modificationDate < :Date  and bcm.call_source = 'OBD' and bcm.call_status = 'SUCCESS'")
                    .setParameter("subcenterId", locationId)
                    .setParameter("Date",date);
            result = (Long) query.uniqueResult();
        }
        return result;
    }

    @Override
    public Long getCalledKilkariInboxCount(Long locationId, String locationType, Date date){
        Long result = (long)0;

        if(locationType.equalsIgnoreCase("State")){
            Query query = getSession().createSQLQuery("select count(DISTINCT bcm.pregnancy_id) from beneficiary_call_measure bcm left join pregnancy p on bcm.pregnancy_id = p.id where p.state_id = :stateId and bcm.modificationDate < :Date and bcm.call_source = 'INBOX'")
                    .setParameter("stateId", locationId)
                    .setParameter("Date",date);
            result = (Long) query.uniqueResult();
        }
        if(locationType.equalsIgnoreCase("District")){
            Query query = getSession().createSQLQuery("select count(DISTINCT bcm.pregnancy_id) from beneficiary_call_measure bcm left join pregnancy p on bcm.pregnancy_id = p.id where p.district_id = :districtId and bcm.modificationDate < :Date and bcm.call_source = 'INBOX'")
                    .setParameter("districtId", locationId)
                    .setParameter("Date",date);
            result = (Long) query.uniqueResult();
        }
        if(locationType.equalsIgnoreCase("Block")){
            Query query = getSession().createSQLQuery("select count(DISTINCT bcm.pregnancy_id) from beneficiary_call_measure bcm left join pregnancy p on bcm.pregnancy_id = p.id where p.healthBlock_id = :stateId and bcm.modificationDate < :Date and bcm.call_source = 'INBOX'")
                    .setParameter("blockId", locationId)
                    .setParameter("Date",date);
            result = (Long) query.uniqueResult();
        }
        if(locationType.equalsIgnoreCase("Subcenter")){
            Query query = getSession().createSQLQuery("select count(DISTINCT bcm.pregnancy_id) from beneficiary_call_measure bcm left join pregnancy p on bcm.pregnancy_id = p.id where p.healthSubFacility_id = :subcenterId and bcm.modificationDate < :Date  and bcm.call_source = 'INBOX'")
                    .setParameter("subcenterId", locationId)
                    .setParameter("Date",date);
            result = (Long) query.uniqueResult();
        }
        return result;
    }

    @Override
    public Long getJoinedSubscriptionSum(Integer locationId, String locationType, Date fromDate, Date toDate, String periodType) {
        String sql = "SELECT SUM(joined_subscription) " +
                "FROM agg_aggregate_beneficiaries " +
                "WHERE location_id = :locationId " +
                "AND location_type = :locationType " +
                "AND period_type = :periodType " +
                "AND date BETWEEN :fromDate AND :toDate";

        SQLQuery query = getSession().createSQLQuery(sql);
        query.setParameter("locationId", locationId.longValue());
        query.setParameter("locationType", locationType);
        query.setParameter("periodType", periodType);
        query.setParameter("fromDate", fromDate);
        query.setParameter("toDate", toDate);

        Object result = query.uniqueResult();
        return result != null ? ((Number) result).longValue() : 0L;
    }

    @Override
    public Long getJoinedSubscriptionSumTillDate(Integer locationId, String locationType, Date toDate) {
        String sql = "SELECT SUM(joined_subscription) " +
                "FROM agg_aggregate_beneficiaries " +
                "WHERE location_id = :locationId " +
                "AND location_type = :locationType " +
                "AND period_type = :periodType " +
                "AND date <= :toDate " ;

        SQLQuery query = getSession().createSQLQuery(sql);
        query.setParameter("locationId", locationId.longValue());
        query.setParameter("locationType", locationType);
        query.setParameter("periodType", "WEEK");
        query.setParameter("toDate", toDate);

        Object result = query.uniqueResult();
        return result != null ? ((Number) result).longValue() : 0L;
    }


    @Override
    public Long getTotalDeactivationSum(Integer locationId, String locationType, Date fromDate, Date toDate, String periodType) {
        String sql = "SELECT SUM(no_answer_deactivation+low_listener_deactivation+system_deactivation) " +
                "FROM agg_aggregate_beneficiaries " +
                "WHERE location_id = :locationId " +
                "AND location_type = :locationType " +
                "AND period_type = :periodType " +
                "AND date BETWEEN :fromDate AND :toDate";

        SQLQuery query = getSession().createSQLQuery(sql);
        query.setParameter("locationId", locationId.longValue());
        query.setParameter("locationType", locationType);
        query.setParameter("periodType", periodType);
        query.setParameter("fromDate", fromDate);
        query.setParameter("toDate", toDate);

        Object result = query.uniqueResult();
        return result != null ? ((Number) result).longValue() : 0L;
    }

}
