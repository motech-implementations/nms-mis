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
                "SUM(a.subscriptions_pending) AS subscriptions_pending , " +
                "SUM(a.subscriptions_completed) AS subscriptions_completed , " +
                "SUM(a.subscriptions_rejected) AS subscriptions_rejected " +

                "FROM (SELECT b.healthBlock_id , " +
                "COUNT(distinct(subscription_id) ) AS total_subscriptions , " +
                "COUNT(Case when s.subscription_status = 'ACTIVE' THEN s.subscription_id END) AS subscriptions_active, " +
                "COUNT(Case when s.subscription_status = 'HOLD' THEN s.subscription_id END) AS subscriptions_on_hold, " +
                "COUNT(Case when s.subscription_status = 'DEACTIVATED' THEN s.subscription_id END) AS subscriptions_deactivated, " +
                "COUNT(Case when s.subscription_status = 'PENDING_ACTIVATION' THEN s.subscription_id END) AS subscriptions_pending, " +
                "COUNT(Case when s.subscription_status = 'COMPLETED' THEN s.subscription_id END) AS subscriptions_completed, " +
                "0 AS subscriptions_rejected "+
                "FROM Beneficiary b INNER JOIN ( SELECT beneficiary_id, MAX(subscription_id) as max_id " +
                "FROM subscriptions s  GROUP BY beneficiary_id ) max_ids ON b.id = max_ids.beneficiary_id " +
                "INNER JOIN subscriptions s ON max_ids.max_id = s.subscription_id " +
                "WHERE registrationDate >= :fromDate AND registrationDate < :toDate " +
                "AND b.district_id = :districtId GROUP BY b.healthBlock_id "+

                "UNION ALL " +
                "SELECT health_block_id , COUNT(*) AS total_subscriptions , 0 AS subscriptions_active, 0 AS subscriptions_on_hold, 0 AS subscriptions_deactivated ," +
                "0 AS subscriptions_pending , 0 AS subscriptions_completed, COUNT(*) AS subscriptions_rejected FROM mother_import_rejection " +
                "WHERE registration_date >= :fromDate AND registration_date < :toDate AND district_id = :districtId " +
                "GROUP BY health_block_id " +

                "UNION ALL " +
                "SELECT health_block_id , COUNT(*) AS total_subscriptions , 0 AS subscriptions_active, 0 AS subscriptions_on_hold, 0 AS subscriptions_deactivated ," +
                "0 AS subscriptions_pending , 0 AS subscriptions_completed , COUNT(*) AS subscriptions_rejected FROM child_import_rejection " +
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
                        ((BigDecimal) row[7]).intValue()
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

        String query_string = "SELECT a.health_block_id, SUM(a.duplicate_subscribers) AS duplicate_subscribers FROM " +
                "(SELECT mir.health_block_id , COUNT(distinct(mir.registration_no) ) AS duplicate_subscribers " +
                "FROM mother_import_rejection mir INNER JOIN Beneficiary b ON b.rch_id = mir.registration_no " +
                "WHERE (registration_date >= :fromDate AND registration_date < :toDate) AND mir.district_id = :districtId " +
                "GROUP BY mir.health_block_id  " +
                "UNION ALL " +
                "SELECT cir.health_block_id , COUNT(distinct(cir.registration_no)) AS duplicate_subscribers " +
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
                kilkariSubscriberRegistrationDateRejectedCountDtos.add(dto);
            }
            if(!isNullInserted){
                KilkariSubscriberRegistrationDateRejectedCountDto dto = new KilkariSubscriberRegistrationDateRejectedCountDto(0,0);
                kilkariSubscriberRegistrationDateRejectedCountDtos.add(dto);
            }
            List<Block> blocks = blockDao.getBlocksOfDistrict(districtId);
            LOGGER.info("Operation = Adding HealthBlocks with 0 count, status = IN_PROGRESS ");
            for(Block block : blocks){
                if(!set.contains(block.getBlockId().longValue() )){
                    KilkariSubscriberRegistrationDateRejectedCountDto dto = new KilkariSubscriberRegistrationDateRejectedCountDto();
                    dto.setLocationId(block.getBlockId());
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
