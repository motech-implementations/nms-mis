package com.beehyv.nmsreporting.dao.impl;

import com.beehyv.nmsreporting.dao.BeneficiaryWithRegistrationDateStateDao;
import com.beehyv.nmsreporting.dao.StateDao;
import com.beehyv.nmsreporting.entity.KilkariSubscriberRegistrationDateDto;
import com.beehyv.nmsreporting.entity.KilkariSubscriberRegistrationDateRejectedCountDto;
import com.beehyv.nmsreporting.model.KilkariSubscriber;
import com.beehyv.nmsreporting.model.State;
import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.beehyv.nmsreporting.dao.AbstractDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

@Repository("beneficiaryWithRegistrationDateStateDao")
@Transactional
public class BeneficiaryWithRegistrationDateStateDaoImpl extends AbstractDao<Integer , KilkariSubscriber> implements BeneficiaryWithRegistrationDateStateDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(BeneficiaryWithRegistrationDateStateDaoImpl.class);

    @Autowired
    private StateDao stateDao;
    @Override
    public List<KilkariSubscriberRegistrationDateDto> allCountOffReports(Date fromDate, Date toDate) {

        LOGGER.info("Operation = allCountOffReports, status = IN_PROGRESS , fromDate = {} , toDate = {}" , fromDate , toDate  );

        String query_string = "SELECT a.state_id AS locationId , " +
                "SUM(a.total_subscriptions) AS total_subscriptions , " +
                "SUM(a.subscriptions_active) AS subscriptions_active, " +
                "SUM(a.subscriptions_on_hold) AS subscriptions_on_hold, " +
                "SUM(a.subscriptions_deactivated) AS subscriptions_deactivated , " +
                "SUM(a.subscriptions_pending) AS subscriptions_pending , " +
                "SUM(a.subscriptions_completed) AS subscriptions_completed , " +
                "SUM(a.subscriptions_rejected) AS subscriptions_rejected " +

                "FROM (SELECT b.state_id , " +
                "COUNT(distinct(subscription_id) ) AS total_subscriptions , " +
                "COUNT(CASE WHEN s.subscription_status = 'ACTIVE' THEN s.subscription_id END) AS subscriptions_active , " +
                "COUNT(CASE WHEN s.subscription_status = 'HOLD' THEN s.subscription_id END) AS subscriptions_on_hold , " +
                "COUNT(CASE WHEN s.subscription_status = 'DEACTIVATED' THEN s.subscription_id END) AS subscriptions_deactivated , " +
                "COUNT(CASE WHEN s.subscription_status = 'PENDING_ACTIVATION' THEN s.subscription_id END) AS subscriptions_pending , " +
                "COUNT(CASE WHEN s.subscription_status = 'COMPLETED' THEN s.subscription_id END) AS subscriptions_completed ," +
                "0 AS subscriptions_rejected "+
                "FROM Beneficiary b INNER JOIN ( SELECT beneficiary_id, MAX(subscription_id) as max_id " +
                "FROM subscriptions s  group by beneficiary_id ) max_ids ON b.id = max_ids.beneficiary_id  " +
                "INNER JOIN subscriptions s ON max_ids.max_id = s.subscription_id " +
                "WHERE registrationDate >= :fromDate AND registrationDate < :toDate " +
                "GROUP BY b.state_id "+

                "UNION ALL " +
                "SELECT state_id , COUNT(*) AS total_subscriptions , 0 AS subscriptions_active, 0 AS subscriptions_on_hold, 0 AS subscriptions_deactivated ," +
                "0 AS subscriptions_pending , 0 AS subscriptions_completed , COUNT(*) AS subscriptions_rejected FROM mother_import_rejection " +
                "WHERE registration_date >= :fromDate AND registration_date < :toDate  " +
                "GROUP BY state_id " +

                "UNION ALL " +
                "SELECT state_id , COUNT(*) AS total_subscriptions , 0 AS subscriptions_active, 0 AS subscriptions_on_hold, 0 AS subscriptions_deactivated ," +
                "0 AS subscriptions_pending , 0 AS subscriptions_completed , COUNT(*) AS subscriptions_rejected FROM child_import_rejection " +
                "WHERE registration_date >= :fromDate AND registration_date < :toDate " +
                "GROUP BY state_id )" +
                "AS a GROUP BY a.state_id ";

        Query query =  getSession().createSQLQuery(query_string)
                .setParameter("fromDate",fromDate)
                .setParameter("toDate",toDate);

        List<KilkariSubscriberRegistrationDateDto> kilkariSubscriberRegistrationDateDtoList = new ArrayList<>();

        List<Object[]> result = new ArrayList<>();
        try {
            LOGGER.info("Query - {} " , query_string);
            result = query.list();

            for(Object[] row : result){
                KilkariSubscriberRegistrationDateDto dto = new KilkariSubscriberRegistrationDateDto(
                        ((BigDecimal) row[1]).intValue(), ((BigDecimal) row[2]).intValue(),
                        ((BigDecimal) row[3]).intValue(), ((BigDecimal) row[4]).intValue(),
                        ((BigDecimal) row[5]).intValue(), ((BigDecimal) row[6]).intValue(),
                        ((BigDecimal) row[7]).intValue()
                );
                if(row[0] == null){
                    dto.setLocationId(0L);
                    dto.setLocationType("No State Count");
                }
                else{
                    dto.setLocationId(((BigInteger) row[0]).longValue());
                    dto.setLocationType("STATE");
                }
                kilkariSubscriberRegistrationDateDtoList.add(dto);
            }
        }
        catch (Exception e){
            LOGGER.info("An exception occurred: {}", e.getMessage(), e);
        }
        LOGGER.info("Operation = allCountOffReports, status = COMPLETED" );
        return kilkariSubscriberRegistrationDateDtoList;
    }


    @Override
    public List<KilkariSubscriberRegistrationDateRejectedCountDto> duplicateRejectedSubscriberCount(Date fromDate, Date toDate) {

        LOGGER.info("Operation = duplicateRejectedSubscriberCount, status = IN_PROGRESS , fromDate = {} , toDate = {}" , fromDate , toDate  );

        String query_string = "SELECT a.state_id , SUM(a.duplicate_subscribers) AS duplicate_subscribers FROM " +
                "(SELECT mir.state_id , COUNT(distinct(mir.registration_no) ) AS duplicate_subscribers " +
                "FROM mother_import_rejection mir INNER JOIN Beneficiary b ON b.rch_id = mir.registration_no " +
                "WHERE (registration_date >= :fromDate AND registration_date < :toDate ) GROUP BY mir.state_id  " +
                "UNION ALL " +
                "SELECT cir.state_id , COUNT(distinct(cir.registration_no) )  AS duplicate_subscribers " +
                "FROM child_import_rejection cir INNER JOIN Beneficiary b ON b.rch_id = cir.registration_no " +
                "WHERE (registration_date >= :fromDate AND registration_date < :toDate) " +
                "GROUP BY cir.state_id ) AS a GROUP BY a.state_id ";

        Query query = getSession().createSQLQuery(query_string)
                .setParameter("fromDate" , fromDate)
                .setParameter("toDate" , toDate);

        List<KilkariSubscriberRegistrationDateRejectedCountDto> kilkariSubscriberRegistrationDateRejectedCountDtos = new ArrayList<>();

        List<Object[]> result = new ArrayList<>();

        try {
            LOGGER.info("Query - {} " , query_string);
            result = query.list();
            Set<Long> set = new HashSet<>();
            for(Object[] row : result) {
                KilkariSubscriberRegistrationDateRejectedCountDto dto = new KilkariSubscriberRegistrationDateRejectedCountDto();
                if (row[0] == null) {
                    dto.setLocationId(0L);
                }
                else {
                    set.add(((BigInteger) row[0]).longValue());
                    dto.setLocationId( ((BigInteger) row[0]).longValue() );
                }
                dto.setSubscriberCount( ((BigDecimal) row[1]).intValue() );
                kilkariSubscriberRegistrationDateRejectedCountDtos.add(dto);
            }
            List<State> states = stateDao.getAllStates();
            LOGGER.info("Operation = Adding State with 0 count, status = IN_PROGRESS ");
            for(State state : states){
                if(!set.contains(state.getStateId().longValue() )){
                    KilkariSubscriberRegistrationDateRejectedCountDto dto = new KilkariSubscriberRegistrationDateRejectedCountDto();
                    dto.setLocationId(state.getStateId());
                    dto.setSubscriberCount(0);
                    kilkariSubscriberRegistrationDateRejectedCountDtos.add(dto);
                }
            }
        }
        catch (Exception e){
            LOGGER.info("An exception occurred: {}", e.getMessage(), e);
        }
        LOGGER.info("Operation = duplicateRejectedSubscriberCount, status = COMPLETED" );
        return kilkariSubscriberRegistrationDateRejectedCountDtos;
    }

}