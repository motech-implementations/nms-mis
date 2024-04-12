package com.beehyv.nmsreporting.dao.impl;

import com.beehyv.nmsreporting.dao.AbstractDao;
import com.beehyv.nmsreporting.dao.BeneficiaryWithRegistrationDateDistrictDao;
import com.beehyv.nmsreporting.dao.DistrictDao;
import com.beehyv.nmsreporting.dao.StateDao;
import com.beehyv.nmsreporting.entity.KilkariSubscriberRegistrationDateDto;
import com.beehyv.nmsreporting.entity.KilkariSubscriberRegistrationDateRejectedCountDto;
import com.beehyv.nmsreporting.model.ChildImportRejection;
import com.beehyv.nmsreporting.model.District;
import com.beehyv.nmsreporting.model.State;
import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

@Repository("beneficiaryWithRegistrationDateDistrictDao")
@Transactional
public class BeneficiaryWithRegistrationDateDistrictDaoImpl extends AbstractDao<Integer  , ChildImportRejection> implements BeneficiaryWithRegistrationDateDistrictDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(BeneficiaryWithRegistrationDateDistrictDaoImpl.class);

    @Autowired
    private DistrictDao districtDao;
    @Override
    public List<KilkariSubscriberRegistrationDateDto> allCountOffReports(Integer stateId, Date fromDate, Date toDate) {

        LOGGER.info("Operation = allCountOffReports, status = IN_PROGRESS , stateId = {} ,  fromDate = {} , toDate = {}" ,  stateId, fromDate , toDate  );

        String query_string = "SELECT a.district_id AS locationId , " +
                "SUM(a.total_subscriptions) AS total_subscriptions , " +
                "SUM(a.subscriptions_active) AS subscriptions_active , " +
                "SUM(a.subscriptions_on_hold) AS subscriptions_on_hold , " +
                "SUM(a.subscriptions_deactivated) AS subscriptions_deactivated , " +
                "SUM(a.subscriptions_pending) AS subscriptions_pending , " +
                "SUM(a.subscriptions_completed) AS subscriptions_completed , " +
                "SUM(a.subscriptions_rejected) AS subscriptions_rejected " +

                "FROM (SELECT b.district_id , " +
                "COUNT(distinct(subscription_id) ) AS total_subscriptions , " +
                "COUNT(CASE WHEN s.subscription_status = 'ACTIVE' THEN s.subscription_id END) AS subscriptions_active , " +
                "COUNT(CASE WHEN s.subscription_status = 'HOLD' THEN s.subscription_id END) AS subscriptions_on_hold , " +
                "COUNT(CASE WHEN s.subscription_status = 'DEACTIVATED' THEN s.subscription_id END) AS subscriptions_deactivated , " +
                "COUNT(CASE WHEN s.subscription_status = 'PENDING_ACTIVATION' THEN s.subscription_id END) AS subscriptions_pending , " +
                "COUNT(CASE WHEN s.subscription_status = 'COMPLETED' THEN s.subscription_id END) AS subscriptions_completed , " +
                "0 AS subscriptions_rejected "+
                "FROM Beneficiary b INNER JOIN ( SELECT beneficiary_id, MAX(subscription_id) as max_id " +
                "FROM subscriptions s  group by beneficiary_id ) max_ids ON b.id = max_ids.beneficiary_id  " +
                "INNER JOIN subscriptions s ON max_ids.max_id = s.subscription_id " +
                "WHERE registrationDate >= :fromDate AND registrationDate < :toDate " +
                "AND b.state_id = :stateId GROUP BY b.district_id "+

                "UNION ALL " +
                "SELECT district_id , COUNT(*) AS total_subscriptions , 0 AS subscriptions_active, 0 AS subscriptions_on_hold, 0 AS subscriptions_deactivated ," +
                " 0 AS subscriptions_pending , 0 AS subscriptions_completed , COUNT(*) AS subscriptions_rejected FROM mother_import_rejection " +
                "WHERE registration_date >= :fromDate AND registration_date < :toDate AND state_id = :stateId " +
                "GROUP BY district_id " +

                "UNION ALL " +
                "SELECT district_id , COUNT(*) AS total_subscriptions , 0 AS subscriptions_active, 0 AS subscriptions_on_hold, 0 AS subscriptions_deactivated ," +
                " 0 AS subscriptions_pending , 0 AS subscriptions_completed , COUNT(*) AS subscriptions_rejected FROM child_import_rejection " +
                "WHERE registration_date >= :fromDate AND registration_date < :toDate AND state_id = :stateId " +
                "GROUP BY district_id) AS a GROUP BY a.district_id" ;


        Query query = getSession().createSQLQuery(query_string)
                .setParameter("fromDate",fromDate)
                .setParameter("toDate",toDate)
                .setParameter("stateId",stateId);


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
                    dto.setLocationType("No District Count");
                }
                else{
                    dto.setLocationId(((BigInteger) row[0]).longValue());
                    dto.setLocationType("DISTRICT");
                }
                kilkariSubscriberRegistrationDateDtoList.add(dto);
            }
        }
        catch (Exception e){
            LOGGER.info("An exception occurred: {}", e.getMessage(), e);
        }
        LOGGER.info("Operation = allCountOffReports, status = COMPLETED ");
        return kilkariSubscriberRegistrationDateDtoList;
   }


    @Override
    public List<KilkariSubscriberRegistrationDateRejectedCountDto> duplicateRejectedSubscriberCount(Integer stateId, Date fromDate, Date toDate) {

        LOGGER.info("Operation = duplicateRejectedSubscriberCount, status = IN_PROGRESS , stateId = {} ,  fromDate = {} , toDate = {}" ,  stateId, fromDate , toDate  );

        String query_string = "SELECT a.district_id , SUM(a.duplicate_subscribers) AS duplicate_subscribers " +
                "FROM (SELECT mir.district_id , COUNT(distinct(mir.registration_no) ) AS duplicate_subscribers " +
                "FROM mother_import_rejection mir INNER JOIN Beneficiary b ON b.rch_id = mir.registration_no " +
                "WHERE (registration_date >= :fromDate AND registration_date < :toDate) AND mir.state_id = :stateId " +
                "GROUP BY mir.district_id  " +
                "UNION ALL " +
                "SELECT cir.district_id , COUNT(distinct(cir.registration_no) ) AS duplicate_subscribers " +
                "FROM child_import_rejection cir INNER JOIN Beneficiary b ON b.rch_id = cir.registration_no " +
                "WHERE (registration_date >= :fromDate AND registration_date < :toDate) AND cir.state_id = :stateId " +
                "GROUP BY cir.district_id ) AS a GROUP BY a.district_id ";

        Query query = getSession().createSQLQuery(query_string)
                .setParameter("stateId" , stateId)
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
                else{
                    set.add(((BigInteger) row[0]).longValue());
                    dto.setLocationId( ((BigInteger) row[0]).longValue() );
                }
                dto.setSubscriberCount( ((BigDecimal) row[1]).intValue() );
                kilkariSubscriberRegistrationDateRejectedCountDtos.add(dto);
            }
            List<District> districts =  districtDao.getDistrictsOfState(stateId);
            LOGGER.info("Operation = Adding District with 0 count, status = IN_PROGRESS ");
            for(District district : districts){
                if(!set.contains(district.getDistrictId().longValue())){
                    KilkariSubscriberRegistrationDateRejectedCountDto dto = new KilkariSubscriberRegistrationDateRejectedCountDto();
                    dto.setLocationId(district.getDistrictId());
                    dto.setSubscriberCount(0);
                    kilkariSubscriberRegistrationDateRejectedCountDtos.add(dto);
                }
            }
        }
        catch (Exception e) {
            LOGGER.info("An exception occurred: {}", e.getMessage(), e);
        }
        LOGGER.info("Operation = duplicateRejectedSubscriberCount, status = COMPLETED");
        return kilkariSubscriberRegistrationDateRejectedCountDtos;

    }

}
