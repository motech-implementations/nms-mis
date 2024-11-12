package com.beehyv.nmsreporting.dao.impl;

import com.beehyv.nmsreporting.dao.AbstractDao;
import com.beehyv.nmsreporting.dao.BeneficiaryWithRegistrationDateBlockDao;
import com.beehyv.nmsreporting.dao.BlockDao;
import com.beehyv.nmsreporting.entity.*;
import com.beehyv.nmsreporting.model.Block;
import com.beehyv.nmsreporting.model.ChildImportRejection;
import com.beehyv.nmsreporting.model.District;
import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

@Repository("beneficiaryWithRegistrationDateBlockDao")
@Transactional
public class BeneficiaryWithRegistrationDateBlockDaoImpl extends AbstractDao<Integer  , ChildImportRejection> implements BeneficiaryWithRegistrationDateBlockDao {

    @Autowired
    private BlockDao blockDao;

    private static final Logger LOGGER = LoggerFactory.getLogger(BeneficiaryWithRegistrationDateBlockDaoImpl.class);

    public List<KilkariSubscriberRegistrationDateDto> allCountOffReports(Integer districtId , Date fromDate , Date toDate){

        LOGGER.info("Operation = allCountOffReports, status = IN_PROGRESS , districtId = {} ,  fromDate = {} , toDate = {}" ,  districtId, fromDate , toDate  );

        String query_string = "SELECT a.healthBlock_id , " +
                "SUM(a.total_subscriptions) AS total_subscriptions ," +
                "SUM(a.subscriptions_active) AS subscriptions_active , " +
                "SUM(a.subscriptions_on_hold) AS subscriptions_on_hold , " +
                "SUM(a.subscriptions_deactivated) AS subscriptions_deactivated , " +
                "SUM(a.subscriptions_deactivated_LIVE_BIRTH) AS subscriptions_deactivated_LIVE_BIRTH , " +
                "SUM(a.subscriptions_pending) AS subscriptions_pending , " +
                "SUM(a.subscriptions_completed) AS subscriptions_completed , " +
                "SUM(a.subscriptions_rejected) AS subscriptions_rejected , " +
                "SUM(a.Subscriptions_Received_for_PW) AS Subscriptions_Received_for_PW , " +
                "SUM(a.Subscriptions_Received_for_Child) AS Subscriptions_Received_for_Child, " +
                "SUM(a.Subscriptions_Ineligible) AS Subscriptions_Ineligible " +

                "FROM (SELECT b.healthBlock_id , " +
                "COUNT(distinct(subscription_id) ) AS total_subscriptions , " +
                "COUNT(Case when s.subscription_status = 'ACTIVE' THEN s.subscription_id END) AS subscriptions_active, " +
                "COUNT(Case when s.subscription_status = 'HOLD' THEN s.subscription_id END) AS subscriptions_on_hold, " +
                "COUNT(CASE WHEN s.subscription_status = 'DEACTIVATED' AND s.deactivation_reason != 'LIVE_BIRTH' THEN s.subscription_id END) AS subscriptions_deactivated , " +
                "COUNT(CASE WHEN s.subscription_status = 'DEACTIVATED' AND s.deactivation_reason = 'LIVE_BIRTH' THEN s.subscription_id END) AS subscriptions_deactivated_LIVE_BIRTH , " +
                "COUNT(Case when s.subscription_status = 'PENDING_ACTIVATION' THEN s.subscription_id END) AS subscriptions_pending, " +
                "COUNT(Case when s.subscription_status = 'COMPLETED' THEN s.subscription_id END) AS subscriptions_completed, " +
                "0 AS subscriptions_rejected , "+
                "COUNT(DISTINCT CASE WHEN s.subscriptionPack_id = 1 AND DATEDIFF(registrationDate, s.start_date) BETWEEN -90 AND 168  THEN s.subscription_id ELSE NULL END) AS Subscriptions_Received_for_PW , "+
                "COUNT(DISTINCT CASE WHEN s.subscriptionPack_id = 2 OR DATEDIFF(registrationDate, s.start_date) BETWEEN 169 AND 504 THEN s.subscription_id ELSE NULL END) AS Subscriptions_Received_for_Child , "+
                "COUNT(DISTINCT CASE WHEN s.subscriptionPack_id = 1 AND DATEDIFF(registrationDate, s.start_date) < -90 THEN s.subscription_id ELSE NULL END) AS Subscriptions_Ineligible "+
                "FROM Beneficiary b INNER JOIN ( SELECT beneficiary_id, MAX(subscription_id) as max_id " +
                "FROM subscriptions s  GROUP BY beneficiary_id ) max_ids ON b.id = max_ids.beneficiary_id " +
                "INNER JOIN subscriptions s ON max_ids.max_id = s.subscription_id " +
                "WHERE registrationDate >= :fromDate AND registrationDate < :toDate " +
                "AND b.district_id = :districtId GROUP BY b.healthBlock_id "+

                "UNION ALL " +
                "SELECT health_block_id , COUNT(*) AS total_subscriptions , 0 AS subscriptions_active, 0 AS subscriptions_on_hold, 0 AS subscriptions_deactivated ," +
                " 0 AS subscriptions_deactivated_LIVE_BIRTH , 0 AS subscriptions_pending , 0 AS subscriptions_completed, COUNT(*) AS subscriptions_rejected , " +
                "COUNT(CASE WHEN DATEDIFF(registration_date," +
                "        CASE WHEN LENGTH(lmp_date) = 29 THEN STR_TO_DATE(SUBSTRING_INDEX(lmp_date, '.', 1), '%Y-%m-%dT%H:%i:%s') END) + 90 BETWEEN -90 AND 168 THEN 1" +
                "        WHEN LENGTH(lmp_date) < 5 OR lmp_date IS NULL THEN 1 ELSE NULL END) AS Subscriptions_Received_for_PW , "+
                "COUNT(CASE WHEN DATEDIFF(registration_date," +
                "        CASE WHEN LENGTH(lmp_date) = 29 THEN STR_TO_DATE(SUBSTRING_INDEX(lmp_date, '.', 1), '%Y-%m-%dT%H:%i:%s') END) + 90 >= 169 THEN 1 ELSE NULL " +
                "        END) AS Subscriptions_Received_for_Child , "+
                "COUNT(CASE WHEN DATEDIFF(registration_date," +
                "        CASE WHEN LENGTH(lmp_date) = 29 THEN STR_TO_DATE(SUBSTRING_INDEX(lmp_date, '.', 1), '%Y-%m-%dT%H:%i:%s') END) + 90 < -90 THEN 1 ELSE NULL " +
                "        END) AS Subscriptions_Ineligible "+
                "FROM mother_import_rejection " +
                "WHERE registration_date >= :fromDate AND registration_date < :toDate AND district_id = :districtId " +
                "GROUP BY health_block_id " +

                "UNION ALL " +
                "SELECT health_block_id , COUNT(*) AS total_subscriptions , 0 AS subscriptions_active, 0 AS subscriptions_on_hold, 0 AS subscriptions_deactivated ," +
                " 0 AS subscriptions_deactivated_LIVE_BIRTH , 0 AS subscriptions_pending , 0 AS subscriptions_completed , COUNT(*) AS subscriptions_rejected , " +
                "0 AS Subscriptions_Received_for_PW, COUNT(*) AS Subscriptions_Received_for_Child, 0 AS Subscriptions_Ineligible FROM child_import_rejection " +
                "WHERE registration_date >= :fromDate AND registration_date < :toDate AND district_id = :districtId " +
                "GROUP BY health_block_id ) AS a GROUP BY a.healthBlock_id " ;


        Query query = getSession().createSQLQuery(query_string)
                .setParameter("fromDate",fromDate)
                .setParameter("toDate",toDate)
                .setParameter("districtId",districtId);

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
                        ((BigDecimal) row[7]).intValue(), ((BigDecimal) row[8]).intValue(),
                        ((BigDecimal) row[9]).intValue(), ((BigDecimal) row[10]).intValue(),
                        ((BigDecimal) row[11]).intValue()
                );
                if(row[0] == null){
                    dto.setLocationId(0L);
                    dto.setLocationType("No BLOCK Count");
                }
                else{
                    dto.setLocationId(((BigInteger) row[0]).longValue());
                    dto.setLocationType("BLOCK");
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
    public List<KilkariSubscriberRegistrationDateRejectedCountDto> duplicateRejectedSubscriberCount(Integer districtId, Date fromDate, Date toDate) {

        LOGGER.info("Operation = duplicateRejectedSubscriberCount, status = IN_PROGRESS , districtId = {} ,  fromDate = {} , toDate = {}" ,  districtId, fromDate , toDate  );

        String query_string = "SELECT a.health_block_id, SUM(a.duplicate_subscribers) AS duplicate_subscribers , " +
                "SUM(a.duplicate_subscribers_PW) AS duplicate_subscribers_PW , SUM(a.duplicate_subscribers_Child) AS duplicate_subscribers_Child , " +
                "SUM(a.duplicate_subscribers_Ineligible) AS duplicate_subscribers_Ineligible " +
                "FROM (SELECT mir.health_block_id, " +
                "       COUNT(DISTINCT mir.registration_no) AS duplicate_subscribers, " +
                "       COUNT(DISTINCT CASE WHEN date_diff + 90 BETWEEN -90 AND 168 THEN mir.registration_no ELSE NULL END) AS duplicate_subscribers_PW, " +
                "       COUNT(DISTINCT CASE WHEN date_diff + 90 >= 169 THEN mir.registration_no ELSE NULL END) AS duplicate_subscribers_Child, " +
                "       COUNT(DISTINCT CASE WHEN date_diff + 90 < -90 THEN mir.registration_no ELSE NULL END) AS duplicate_subscribers_Ineligible " +
                "FROM ( " +
                "    SELECT mir.*, " +
                "           DATEDIFF(mir.registration_date, STR_TO_DATE(SUBSTRING_INDEX(mir.lmp_date, '.', 1), '%Y-%m-%dT%H:%i:%s')) AS date_diff " +
                "    FROM mother_import_rejection mir " +
                "    INNER JOIN ( " +
                "        SELECT MAX(id) AS id " +
                "        FROM mother_import_rejection " +
                "        GROUP BY registration_no " +
                "    ) mir_latest ON mir.id = mir_latest.id " +
                "    WHERE mir.district_id = :districtId " +
                "      AND (mir.registration_date >= :fromDate AND mir.registration_date < :toDate) " +
                ") mir " +
                "INNER JOIN Beneficiary b ON b.rch_id = mir.registration_no " +
                "GROUP BY mir.health_block_id " +
                "UNION ALL " +
                "SELECT cir.health_block_id , COUNT(DISTINCT cir.registration_no) AS duplicate_subscribers , " +
                "0 AS duplicate_subscribers_PW, COUNT(DISTINCT cir.registration_no) AS duplicate_subscribers_Child , 0 AS duplicate_subscribers_Ineligible "+
                "FROM child_import_rejection cir INNER JOIN Beneficiary b ON b.rch_id = cir.registration_no " +
                "WHERE (registration_date >= :fromDate AND registration_date < :toDate) AND cir.district_id = :districtId " +
                "GROUP BY cir.health_block_id ) AS a GROUP BY a.health_block_id ";

        Query query = getSession().createSQLQuery(query_string)
                .setParameter("districtId" , districtId)
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
                dto.setSubscriberCount_PW( ((BigDecimal) row[2]).intValue() );
                dto.setSubscriberCount_Child( ((BigDecimal) row[3]).intValue() );
                dto.setSubscriberCount_Ineligible( ((BigDecimal) row[4]).intValue() );
                kilkariSubscriberRegistrationDateRejectedCountDtos.add(dto);
            }
            if(!isNullInserted){
                KilkariSubscriberRegistrationDateRejectedCountDto dto = new KilkariSubscriberRegistrationDateRejectedCountDto(0,0,0,0,0);
                kilkariSubscriberRegistrationDateRejectedCountDtos.add(dto);
            }
            List<Block> blocks = blockDao.getBlocksOfDistrict(districtId);
            LOGGER.info("Operation = Adding HealthBlocks with 0 count, status = IN_PROGRESS ");
            for(Block block : blocks){
                if(!set.contains(block.getBlockId().longValue() )){
                    KilkariSubscriberRegistrationDateRejectedCountDto dto = new KilkariSubscriberRegistrationDateRejectedCountDto();
                    dto.setLocationId(block.getBlockId());
                    dto.setSubscriberCount(0);
                    dto.setSubscriberCount_PW(0);
                    dto.setSubscriberCount_Child(0);
                    dto.setSubscriberCount_Ineligible(0);
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
