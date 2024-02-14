package com.beehyv.nmsreporting.dao.impl;

import com.beehyv.nmsreporting.dao.AbstractDao;
import com.beehyv.nmsreporting.dao.BeneficiaryWithRegistrationDateSubCentreDao;
import com.beehyv.nmsreporting.dao.HealthFacilityDao;
import com.beehyv.nmsreporting.dao.HealthSubFacilityDao;
import com.beehyv.nmsreporting.entity.KilkariSubscriberRegistrationDateDto;
import com.beehyv.nmsreporting.entity.KilkariSubscriberRegistrationDateRejectedCountDto;
import com.beehyv.nmsreporting.model.ChildImportRejection;
import com.beehyv.nmsreporting.model.HealthSubFacility;
import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;


@Repository("beneficiaryWithRegistrationDateSubCentreDao")
@Transactional
public class BeneficiaryWithRegistrationDateSubCentreDaoImpl extends AbstractDao<Integer  , ChildImportRejection> implements BeneficiaryWithRegistrationDateSubCentreDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(BeneficiaryWithRegistrationDateSubCentreDaoImpl.class);

    @Autowired
    private HealthSubFacilityDao healthSubFacilityDao;
    @Override
    public List<KilkariSubscriberRegistrationDateDto> allCountOffReports(Integer blockId, Date fromDate, Date toDate) {

        LOGGER.info("Operation = allCountOffReports, status = IN_PROGRESS , blockId = {} ,  fromDate = {} , toDate = {}" ,  blockId, fromDate , toDate  );

        String query_string = "SELECT a.healthSubFacility_id AS locationId , " +
                "SUM(a.total_subscriptions) AS total_subscriptions , " +
                "SUM(a.subscriptions_active) AS subscriptions_active , " +
                "SUM(a.subscriptions_on_hold) AS subscriptions_on_hold , " +
                "SUM(a.subscriptions_deactivated) AS subscriptions_deactivated , " +
                "SUM(a.subscriptions_pending) AS subscriptions_pending , " +
                "SUM(a.subscriptions_completed) AS subscriptions_completed , " +
                "SUM(a.subscriptions_rejected) AS subscriptions_rejected " +

                "FROM (SELECT b.healthSubFacility_id , " +
                "COUNT(DISTINCT(subscription_id) ) AS total_subscriptions , " +
                "COUNT(CASE WHEN s.subscription_status = 'ACTIVE' THEN s.subscription_id END) AS subscriptions_active , " +
                "COUNT(CASE WHEN s.subscription_status = 'HOLD' THEN s.subscription_id END) AS subscriptions_on_hold , " +
                "COUNT(CASE WHEN s.subscription_status = 'DEACTIVATED' THEN s.subscription_id END) AS subscriptions_deactivated , " +
                "COUNT(CASE WHEN s.subscription_status = 'PENDING_ACTIVATION' THEN s.subscription_id END) AS subscriptions_pending , " +
                "COUNT(CASE WHEN s.subscription_status = 'COMPLETED' THEN s.subscription_id END) AS subscriptions_completed , " +
                "0 AS subscriptions_rejected "+
                "FROM subscriptions s INNER JOIN Beneficiary b ON s.beneficiary_id = b.id LEFT JOIN beneficiary_tracker bt " +
                "ON s.beneficiaryTracking_id = bt.id WHERE (registrationDate BETWEEN :fromDate AND :toDate)" +
                "AND b.healthBlock_id = :blockId GROUP BY b.healthSubFacility_id " +

                "UNION ALL " +
                "SELECT subcentre_id , COUNT(*) AS total_subscriptions , 0 AS subscriptions_active, 0 AS subscriptions_on_hold, 0 AS subscriptions_deactivated ," +
                " 0 AS subscriptions_pending , 0 AS subscriptions_completed , COUNT(*) AS subscriptions_rejected FROM mother_import_rejection " +
                "WHERE registration_date >= :fromDate AND registration_date < :toDate " +
                "AND health_block_id = :blockId GROUP BY subcentre_id " +

                "UNION ALL " +
                "SELECT subcentre_id , COUNT(*) AS total_subscriptions , 0 AS subscriptions_active, 0 AS subscriptions_on_hold, 0 AS subscriptions_deactivated ," +
                " 0 AS subscriptions_pending , 0 AS subscriptions_completed , COUNT(*) AS subscriptions_rejected FROM child_import_rejection " +
                "WHERE registration_date >= :fromDate AND registration_date < :toDate " +
                "AND health_block_id = :blockId GROUP BY subcentre_id) " +
                "AS a GROUP BY a.healthSubFacility_id";


        Query query = getSession().createSQLQuery(query_string)
                .setParameter("fromDate",fromDate)
                .setParameter("toDate",toDate)
                .setParameter("blockId",blockId);

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
                    dto.setLocationType("No SubCenter Count");
                }
                else{
                    dto.setLocationId(((BigInteger) row[0]).longValue());
                    dto.setLocationType("SUBCENTER");
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
    public List<KilkariSubscriberRegistrationDateRejectedCountDto> duplicateRejectedSubscriberCount(Integer blockId, Date fromDate, Date toDate) {

        LOGGER.info("Operation = duplicateRejectedSubscriberCount, status = IN_PROGRESS , blockId = {} ,  fromDate = {} , toDate = {}" ,  blockId, fromDate , toDate  );


        String query_string = "SELECT a.subcentre_id , SUM(a.duplicate_subscribers) AS duplicate_subscribers " +
                "FROM (SELECT mir.subcentre_id , COUNT(distinct(mir.registration_no) ) AS duplicate_subscribers " +
                "FROM mother_import_rejection mir INNER JOIN Beneficiary b ON b.rch_id = mir.registration_no " +
                "WHERE (registration_date >= :fromDate AND registration_date < :toDate) AND mir.health_block_id = :blockId " +
                "GROUP BY mir.subcentre_id  " +
                "UNION ALL " +
                "SELECT cir.subcentre_id , COUNT(distinct(cir.registration_no)) AS duplicate_subscribers " +
                "FROM child_import_rejection cir INNER JOIN Beneficiary b ON b.rch_id = cir.registration_no " +
                "WHERE (registration_date >= :fromDate AND registration_date < :toDate) AND cir.health_block_id = :blockId " +
                "GROUP BY cir.phc_id ) AS a GROUP BY a.subcentre_id ";

        Query query = getSession().createSQLQuery(query_string)
                .setParameter("blockId" , blockId)
                .setParameter("fromDate" , fromDate)
                .setParameter("toDate" , toDate);

        List<KilkariSubscriberRegistrationDateRejectedCountDto> kilkariSubscriberRegistrationDateRejectedCountDtos = new ArrayList<>();

        List<Object[]> result = new ArrayList<>();

        try {
            LOGGER.info("Query - {} " , query_string);
            result = query.list();
            Set<Long> set = new HashSet<>();
            boolean isNullInserted = false;
            for(Object[] row : result) {
                KilkariSubscriberRegistrationDateRejectedCountDto dto = new KilkariSubscriberRegistrationDateRejectedCountDto();
                if (row[0] == null) {
                    isNullInserted = true;
                    dto.setLocationId(0L);
                }
                else{
                    set.add(((BigInteger) row[0]).longValue());
                    dto.setLocationId( ((BigInteger) row[0]).longValue() );
                }
                dto.setSubscriberCount( ((BigDecimal) row[1]).intValue() );
                kilkariSubscriberRegistrationDateRejectedCountDtos.add(dto);
            }

            if(!isNullInserted){
                KilkariSubscriberRegistrationDateRejectedCountDto dto = new KilkariSubscriberRegistrationDateRejectedCountDto(0,0);
                kilkariSubscriberRegistrationDateRejectedCountDtos.add(dto);
            }
            List<HealthSubFacility> healthSubFacilityList = healthSubFacilityDao.getSubcentersOfBlock(blockId);
            LOGGER.info("Operation = Adding healthSubFacilityList with 0 count, status = IN_PROGRESS ");
            for(HealthSubFacility healthSubFacility : healthSubFacilityList){
                if(!set.contains(healthSubFacility.getHealthSubFacilityId().longValue() )){
                    KilkariSubscriberRegistrationDateRejectedCountDto dto = new KilkariSubscriberRegistrationDateRejectedCountDto();
                    dto.setLocationId( healthSubFacility.getHealthSubFacilityId() );
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
