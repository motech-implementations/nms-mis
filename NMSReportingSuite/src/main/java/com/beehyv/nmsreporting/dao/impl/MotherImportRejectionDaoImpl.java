package com.beehyv.nmsreporting.dao.impl;

import com.beehyv.nmsreporting.dao.AbstractDao;
import com.beehyv.nmsreporting.dao.MotherImportRejectionDao;
import com.beehyv.nmsreporting.model.MotherImportRejection;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("motherImportRejectionDao")
public class MotherImportRejectionDaoImpl extends AbstractDao<Long, MotherImportRejection> implements MotherImportRejectionDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(MotherImportRejectionDaoImpl.class);

    @Override
    public List<MotherImportRejection> getAllRejectedMotherImportRecords(Date fromDate, Date toDate) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.lt("modificationDate", toDate))
                .add(Restrictions.ge("modificationDate", fromDate))
                .add(Restrictions.eq("accepted", false))
                .add(Restrictions.disjunction()
                        .add(Restrictions.eq("rejectionReason", "DUPLICATE_MOBILE_NUMBER_IN_DATASET"))
                        .add(Restrictions.eq("rejectionReason", "MOBILE_NUMBER_ALREADY_SUBSCRIBED"))
                        .add(Restrictions.eq("rejectionReason", "MOBILE_NUMBER_EMPTY_OR_WRONG_FORMAT"))
                        .add(Restrictions.eq("rejectionReason", "INVALID_CASE_NO"))
                );

        return criteria.list();
    }

    @Override
    public List<MotherImportRejection> getAllRejectedMotherImportRecordsWithStateId(Date fromDate, Date toDate, Integer stateId) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.lt("modificationDate", toDate))
                .add(Restrictions.ge("modificationDate", fromDate))
                .add(Restrictions.eq("accepted", false))
                .add(Restrictions.eq("stateId", stateId))
                .add(Restrictions.disjunction()
                        .add(Restrictions.eq("rejectionReason", "DUPLICATE_MOBILE_NUMBER_IN_DATASET"))
                        .add(Restrictions.eq("rejectionReason", "MOBILE_NUMBER_ALREADY_SUBSCRIBED"))
                        .add(Restrictions.eq("rejectionReason", "MOBILE_NUMBER_EMPTY_OR_WRONG_FORMAT"))
                        .add(Restrictions.eq("rejectionReason", "INVALID_CASE_NO"))
                );

        return criteria.list();
    }

    @Override
    public List<MotherImportRejection> getAllRejectedMotherImportRecordsWithDistrictId(Date fromDate, Date toDate, Integer districtId) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.lt("modificationDate", toDate))
                .add(Restrictions.ge("modificationDate", fromDate))
                .add(Restrictions.eq("accepted", false))
                .add(Restrictions.eq("districtId", districtId))
                .add(Restrictions.disjunction()
                        .add(Restrictions.eq("rejectionReason", "DUPLICATE_MOBILE_NUMBER_IN_DATASET"))
                        .add(Restrictions.eq("rejectionReason", "MOBILE_NUMBER_ALREADY_SUBSCRIBED"))
                        .add(Restrictions.eq("rejectionReason", "MOBILE_NUMBER_EMPTY_OR_WRONG_FORMAT"))
                        .add(Restrictions.eq("rejectionReason", "INVALID_CASE_NO"))
                );

        return criteria.list();
    }

    @Override
    public List<MotherImportRejection> getAllRejectedMotherImportRecordsWithBlockId(Date fromDate, Date toDate, Integer blockId) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.lt("modificationDate", toDate))
                .add(Restrictions.ge("modificationDate", fromDate))
                .add(Restrictions.eq("accepted", false))
                .add(Restrictions.eq("healthBlockId", blockId))
                .add(Restrictions.disjunction()
                        .add(Restrictions.eq("rejectionReason", "DUPLICATE_MOBILE_NUMBER_IN_DATASET"))
                        .add(Restrictions.eq("rejectionReason", "MOBILE_NUMBER_ALREADY_SUBSCRIBED"))
                        .add(Restrictions.eq("rejectionReason", "MOBILE_NUMBER_EMPTY_OR_WRONG_FORMAT"))
                        .add(Restrictions.eq("rejectionReason", "INVALID_CASE_NO"))
                );

        return criteria.list();
    }

    @Override
    public Long getCountOFRejectedMotherImportRecordsWithDistrictId(Date fromDate, Date toDate, Integer districtId) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.lt("modificationDate", toDate))
                .add(Restrictions.ge("modificationDate", fromDate))
                .add(Restrictions.eq("accepted", false))
                .add(Restrictions.eq("districtId", districtId))
                .add(Restrictions.disjunction()
                        .add(Restrictions.eq("rejectionReason", "DUPLICATE_MOBILE_NUMBER_IN_DATASET"))
                        .add(Restrictions.eq("rejectionReason", "MOBILE_NUMBER_ALREADY_SUBSCRIBED"))
                        .add(Restrictions.eq("rejectionReason", "MOBILE_NUMBER_EMPTY_OR_WRONG_FORMAT"))
                        .add(Restrictions.eq("rejectionReason", "INVALID_CASE_NO")))
                .setProjection(Projections.rowCount());

        return (Long) criteria.uniqueResult();

    }

    @Override
    public Map<Integer, Long> getUniqueDuplicatePhoneNumberCountForState(Date fromDate, Date toDate, List<Integer> stateIds) {
        if (stateIds == null || stateIds.isEmpty()) {
            throw new IllegalArgumentException("State IDs cannot be empty.");
        }

        StringBuilder locationIdPlaceholders = new StringBuilder();
        for (int i = 0; i < stateIds.size(); i++) {
            locationIdPlaceholders.append(stateIds.get(i).toString());
            if (i < stateIds.size() - 1) {
                locationIdPlaceholders.append(",");
            }
        }

        String sql = "SELECT state_id, COUNT(DISTINCT mobile_no) AS total_unique_duplicate_count " +
                "FROM (" +
                "    SELECT state_id, mobile_no " +
                "    FROM child_import_rejection " +
                "    WHERE rejection_reason = 'DUPLICATE_MOBILE_NUMBER_IN_DATASET' " +
                "      AND mobile_no IS NOT NULL " +
                "      AND modification_date BETWEEN :fromDate AND :toDate " +
                "      AND state_id IN (" + locationIdPlaceholders.toString() + ") " +
                "      AND accepted = false " +
                "    UNION " +
                "    SELECT state_id, mobile_no " +
                "    FROM mother_import_rejection " +
                "    WHERE rejection_reason = 'DUPLICATE_MOBILE_NUMBER_IN_DATASET' " +
                "      AND mobile_no IS NOT NULL " +
                "      AND modification_date BETWEEN :fromDate AND :toDate " +
                "      AND state_id IN (" + locationIdPlaceholders.toString() + ") " +
                "      AND accepted = false " +
                ") AS combined_duplicates " +
                "GROUP BY state_id";

        Query query = getSession().createSQLQuery(sql)
                .setParameter("fromDate", fromDate)
                .setParameter("toDate", toDate);



        List<Object[]> results = query.list();
        Map<Integer, Long> resultMap = new HashMap<>();
        for (Object[] row : results) {
            Integer stateId = ((BigInteger) row[0]).intValue();
            Long uniqueDuplicatePhoneNumberCountForState = row[1] != null ? ((Number) row[1]).longValue() : 0L;
            resultMap.put(stateId, uniqueDuplicatePhoneNumberCountForState);
        }

        LOGGER.info("Operation = UniqueDuplicatePhoneNumberCountForState, status = COMPLETED" );
        LOGGER.info("Result map: {}", resultMap);
        return resultMap;
    }

    @Override
    public Map<Integer, Long> getUniqueDuplicatePhoneNumberCountForDistrict(Date fromDate, Date toDate, List<Integer> districtIds) {
        StringBuilder locationIdPlaceholders = new StringBuilder();
        for (int i = 0; i < districtIds.size(); i++) {
            locationIdPlaceholders.append(districtIds.get(i).toString());
            if (i < districtIds.size() - 1) {
                locationIdPlaceholders.append(",");
            }
        }
        String sql = "SELECT district_id, COUNT(DISTINCT mobile_no) AS total_unique_duplicate_count " +
                "FROM (" +
                "    SELECT district_id, mobile_no " +
                "    FROM child_import_rejection " +
                "    WHERE rejection_reason = 'DUPLICATE_MOBILE_NUMBER_IN_DATASET' " +
                "      AND mobile_no IS NOT NULL " +
                "      AND modification_date BETWEEN :fromDate AND :toDate " +
                "      AND district_id IN (" + locationIdPlaceholders.toString() + ") " +
                "      AND accepted = false " +
                "    UNION " +
                "    SELECT district_id, mobile_no " +
                "    FROM mother_import_rejection " +
                "    WHERE rejection_reason = 'DUPLICATE_MOBILE_NUMBER_IN_DATASET' " +
                "      AND mobile_no IS NOT NULL " +
                "      AND modification_date BETWEEN :fromDate AND :toDate " +
                "      AND district_id IN (" + locationIdPlaceholders.toString() + ") " +
                "      AND accepted = false " +
                ") AS combined_duplicates GROUP BY district_id ";

        LOGGER.info("Query - {} " , sql);
        Query query = getSession().createSQLQuery(sql);
        query.setParameter("fromDate", fromDate);
        query.setParameter("toDate", toDate);

        List<Object[]> results = query.list();
        Map<Integer, Long> resultMap = new HashMap<>();
        for (Object[] row : results) {
            Integer locationId = ((Number) row[0]).intValue();
            Long uniqueDuplicatePhoneNumberCountForDistrict = row[1] != null ? ((Number) row[1]).longValue() : 0L;
            resultMap.put(locationId, uniqueDuplicatePhoneNumberCountForDistrict);
        }
        LOGGER.info("Operation = UniqueDuplicatePhoneNumberCountForDistrict, status = COMPLETED" );
        LOGGER.info("Result map: {}", resultMap);
        return resultMap;
    }

    @Override
    public Map<Integer, Long> getUniqueDuplicatePhoneNumberCountForBlock(Date fromDate, Date toDate, List<Integer> blockIds) {
        StringBuilder locationIdPlaceholders = new StringBuilder();
        for (int i = 0; i < blockIds.size(); i++) {
            locationIdPlaceholders.append(blockIds.get(i).toString());
            if (i < blockIds.size() - 1) {
                locationIdPlaceholders.append(",");
            }
        }
        String sql = "SELECT health_block_id, COUNT(DISTINCT mobile_no) AS total_unique_duplicate_count " +
                "FROM (" +
                "    SELECT health_block_id, mobile_no " +
                "    FROM child_import_rejection " +
                "    WHERE rejection_reason = 'DUPLICATE_MOBILE_NUMBER_IN_DATASET' " +
                "      AND mobile_no IS NOT NULL " +
                "      AND modification_date BETWEEN :fromDate AND :toDate " +
                "      AND health_block_id IN (" + locationIdPlaceholders.toString() + ") " +
                "      AND accepted = false " +
                "    UNION " +
                "    SELECT health_block_id, mobile_no " +
                "    FROM mother_import_rejection " +
                "    WHERE rejection_reason = 'DUPLICATE_MOBILE_NUMBER_IN_DATASET' " +
                "      AND mobile_no IS NOT NULL " +
                "      AND modification_date BETWEEN :fromDate AND :toDate " +
                "      AND health_block_id IN (" + locationIdPlaceholders.toString() + ") " +
                "      AND accepted = false " +
                ") AS combined_duplicates GROUP BY health_block_id ";

        LOGGER.info("Query - {} " , sql);
        Query query = getSession().createSQLQuery(sql);
        query.setParameter("fromDate", fromDate);
        query.setParameter("toDate", toDate);

        List<Object[]> results = query.list();
        Map<Integer, Long> resultMap = new HashMap<>();
        for (Object[] row : results) {
            Integer locationId = ((Number) row[0]).intValue();
            Long uniqueDuplicatePhoneNumberCountForBlock = row[1] != null ? ((Number) row[1]).longValue() : 0L;
            resultMap.put(locationId, uniqueDuplicatePhoneNumberCountForBlock);
        }
        LOGGER.info("Operation = UniqueDuplicatePhoneNumberCountForBlock, status = COMPLETED" );
        LOGGER.info("Result map: {}", resultMap);
        return resultMap;
    }

    @Override
    public Map<Integer, Long> getUniqueDuplicatePhoneNumberCountForHealthFacility(Date fromDate, Date toDate, List<Integer> healthFacilityIds) {

        StringBuilder locationIdPlaceholders = new StringBuilder();
        for (int i = 0; i < healthFacilityIds.size(); i++) {
            locationIdPlaceholders.append(healthFacilityIds.get(i).toString());
            if (i < healthFacilityIds.size() - 1) {
                locationIdPlaceholders.append(",");
            }
        }
        String sql = "SELECT phc_id, COUNT(DISTINCT mobile_no) AS total_unique_duplicate_count " +
                "FROM (" +
                "    SELECT phc_id, mobile_no " +
                "    FROM child_import_rejection " +
                "    WHERE rejection_reason = 'DUPLICATE_MOBILE_NUMBER_IN_DATASET' " +
                "      AND mobile_no IS NOT NULL " +
                "      AND modification_date BETWEEN :fromDate AND :toDate " +
                "      AND phc_id IN (" + locationIdPlaceholders.toString() + ") " +
                "      AND accepted = false " +
                "    UNION " +
                "    SELECT phc_id, mobile_no " +
                "    FROM mother_import_rejection " +
                "    WHERE rejection_reason = 'DUPLICATE_MOBILE_NUMBER_IN_DATASET' " +
                "      AND mobile_no IS NOT NULL " +
                "      AND modification_date BETWEEN :fromDate AND :toDate " +
                "      AND phc_id IN (" + locationIdPlaceholders.toString() + ") " +
                "      AND accepted = false " +
                ") AS combined_duplicates GROUP BY phc_id ";

        LOGGER.info("Query - {} " , sql);
        Query query = getSession().createSQLQuery(sql);
        query.setParameter("fromDate", fromDate);
        query.setParameter("toDate", toDate);

        List<Object[]> results = query.list();
        Map<Integer, Long> resultMap = new HashMap<>();
        for (Object[] row : results) {
            Integer locationId = ((Number) row[0]).intValue();
            Long uniqueDuplicatePhoneNumberCountForBlock = row[1] != null ? ((Number) row[1]).longValue() : 0L;
            resultMap.put(locationId, uniqueDuplicatePhoneNumberCountForBlock);
        }
        LOGGER.info("Operation = UniqueDuplicatePhoneNumberCountForHealthFacility, status = COMPLETED" );
        LOGGER.info("Result map: {}", resultMap);
        return resultMap;
    }


    public Map<Integer, Long> getTotalIneligibleCountByState(Date fromDate, Date toDate, List<Integer> stateIds) {

        StringBuilder locationIdPlaceholders = new StringBuilder();
        for (int i = 0; i < stateIds.size(); i++) {
            locationIdPlaceholders.append(stateIds.get(i).toString());
            if (i < stateIds.size() - 1) {
                locationIdPlaceholders.append(",");
            }
        }
        String sql = "SELECT state_id, COALESCE(SUM(ineligible_for_subscriptions), 0) AS total_ineligible_count " +
                "FROM (" +
                "    SELECT state_id, COUNT(CASE WHEN rejection_reason = 'INVALID_LMP_DATE' THEN id END) AS ineligible_for_subscriptions " +
                "    FROM mother_import_rejection " +
                "    WHERE modification_date BETWEEN :fromDate AND :toDate " +
                "      AND state_id IN (" + locationIdPlaceholders.toString() + ") " +
                "      AND accepted = false " +
                "      GROUP BY state_id " +
                "    UNION ALL " +
                "    SELECT state_id, COUNT(CASE WHEN rejection_reason = 'INVALID_DOB' THEN id END) AS ineligible_for_subscriptions " +
                "    FROM child_import_rejection " +
                "    WHERE modification_date BETWEEN :fromDate AND :toDate " +
                "      AND state_id IN (" + locationIdPlaceholders.toString() + ") " +
                "      AND accepted = false " +
                "      GROUP BY state_id " +
                ") AS combined_ineligible GROUP BY state_id ";


        LOGGER.info("Query - {} " , sql);
        Query query = getSession().createSQLQuery(sql);
            query.setParameter("fromDate", fromDate);
            query.setParameter("toDate", toDate);

        List<Object[]> results = query.list();
        Map<Integer, Long> resultMap = new HashMap<>();
        for (Object[] row : results) {
            Integer locationId = ((Number) row[0]).intValue();
            Long totalIneligibleCountByState = row[1] != null ? ((Number) row[1]).longValue() : 0L;
            resultMap.put(locationId, totalIneligibleCountByState);
        }
        LOGGER.info("Operation = TotalIneligibleCountByState, status = COMPLETED" );
        LOGGER.info("Result map: {}", resultMap);
        return resultMap;

    }

    public Map<Integer, Long> getTotalIneligibleCountByDistrict(Date fromDate, Date toDate, List<Integer> districtIds) {
        StringBuilder locationIdPlaceholders = new StringBuilder();
        for (int i = 0; i < districtIds.size(); i++) {
            locationIdPlaceholders.append(districtIds.get(i).toString());
            if (i < districtIds.size() - 1) {
                locationIdPlaceholders.append(",");
            }
        }
        String sql = "SELECT district_id, COALESCE(SUM(ineligible_for_subscriptions), 0) AS total_ineligible_count " +
                "FROM (" +
                "    SELECT district_id, COUNT(CASE WHEN rejection_reason = 'INVALID_LMP_DATE' THEN id END) AS ineligible_for_subscriptions " +
                "    FROM mother_import_rejection " +
                "    WHERE modification_date BETWEEN :fromDate AND :toDate " +
                "      AND district_id IN (" + locationIdPlaceholders.toString() + ") " +
                "      AND accepted = false " +
                "      GROUP BY district_id " +
                "    UNION ALL " +
                "    SELECT district_id, COUNT(CASE WHEN rejection_reason = 'INVALID_DOB' THEN id END) AS ineligible_for_subscriptions " +
                "    FROM child_import_rejection " +
                "    WHERE modification_date BETWEEN :fromDate AND :toDate " +
                "      AND district_id IN (" + locationIdPlaceholders.toString() + ") " +
                "      AND accepted = false " +
                "      GROUP BY district_id " +
                ") AS combined_ineligible GROUP BY district_id ";

        LOGGER.info("Query - {} " , sql);
        Query query = getSession().createSQLQuery(sql);
        query.setParameter("fromDate", fromDate);
        query.setParameter("toDate", toDate);

        List<Object[]> results = query.list();
        Map<Integer, Long> resultMap = new HashMap<>();
        for (Object[] row : results) {
            Integer locationId = ((Number) row[0]).intValue();
            Long totalIneligibleCountByDistrict = row[1] != null ? ((Number) row[1]).longValue() : 0L;
            resultMap.put(locationId, totalIneligibleCountByDistrict);
        }
        LOGGER.info("Operation = TotalIneligibleCountByDistrict, status = COMPLETED" );
        LOGGER.info("Result map: {}", resultMap);
        return resultMap;

    }

    public Map<Integer, Long> getTotalIneligibleCountByBlock(Date fromDate, Date toDate, List<Integer> blockIds) {

        StringBuilder locationIdPlaceholders = new StringBuilder();
        for (int i = 0; i < blockIds.size(); i++) {
            locationIdPlaceholders.append(blockIds.get(i).toString());
            if (i < blockIds.size() - 1) {
                locationIdPlaceholders.append(",");
            }
        }
        String sql = "SELECT health_block_id, COALESCE(SUM(ineligible_for_subscriptions), 0) AS total_ineligible_count " +
                "FROM (" +
                "    SELECT health_block_id, COUNT(CASE WHEN rejection_reason = 'INVALID_LMP_DATE' THEN id END) AS ineligible_for_subscriptions " +
                "    FROM mother_import_rejection " +
                "    WHERE modification_date BETWEEN :fromDate AND :toDate " +
                "      AND health_block_id IN (" + locationIdPlaceholders.toString() + ") " +
                "      AND accepted = false " +
                "      GROUP BY health_block_id " +
                "    UNION ALL " +
                "    SELECT health_block_id, COUNT(CASE WHEN rejection_reason = 'INVALID_DOB' THEN id END) AS ineligible_for_subscriptions " +
                "    FROM child_import_rejection " +
                "    WHERE modification_date BETWEEN :fromDate AND :toDate " +
                "      AND health_block_id IN (" + locationIdPlaceholders.toString() + ") " +
                "      AND accepted = false " +
                "      GROUP BY health_block_id " +
                ") AS combined_ineligible GROUP BY health_block_id ";


        LOGGER.info("Query - {} " , sql);
        Query query = getSession().createSQLQuery(sql);
        query.setParameter("fromDate", fromDate);
        query.setParameter("toDate", toDate);

        List<Object[]> results = query.list();
        Map<Integer, Long> resultMap = new HashMap<>();
        for (Object[] row : results) {
            Integer locationId = ((Number) row[0]).intValue();
            Long totalIneligibleCountByBlock = row[1] != null ? ((Number) row[1]).longValue() : 0L;
            resultMap.put(locationId, totalIneligibleCountByBlock);
        }
        LOGGER.info("Operation = TotalIneligibleCountByBlock, status = COMPLETED" );
        LOGGER.info("Result map: {}", resultMap);
        return resultMap;

    }

    public Map<Integer, Long> getTotalIneligibleCountByHealthFacility(Date fromDate, Date toDate, List<Integer> healthFacilityIds) {
        StringBuilder locationIdPlaceholders = new StringBuilder();
        for (int i = 0; i < healthFacilityIds.size(); i++) {
            locationIdPlaceholders.append(healthFacilityIds.get(i).toString());
            if (i < healthFacilityIds.size() - 1) {
                locationIdPlaceholders.append(",");
            }
        }
        String sql = "SELECT phc_id, COALESCE(SUM(ineligible_for_subscriptions), 0) AS total_ineligible_count " +
                "FROM (" +
                "    SELECT phc_id, COUNT(CASE WHEN rejection_reason = 'INVALID_LMP_DATE' THEN id END) AS ineligible_for_subscriptions " +
                "    FROM mother_import_rejection " +
                "    WHERE modification_date BETWEEN :fromDate AND :toDate " +
                "      AND phc_id IN (" + locationIdPlaceholders.toString() + ") " +
                "      AND accepted = false " +
                "      GROUP BY phc_id " +
                "    UNION ALL " +
                "    SELECT phc_id, COUNT(CASE WHEN rejection_reason = 'INVALID_DOB' THEN id END) AS ineligible_for_subscriptions " +
                "    FROM child_import_rejection " +
                "    WHERE modification_date BETWEEN :fromDate AND :toDate " +
                "      AND phc_id IN (" + locationIdPlaceholders.toString() + ") " +
                "      AND accepted = false " +
                "      GROUP BY phc_id " +
                ") AS combined_ineligible GROUP BY phc_id ";

        LOGGER.info("Query - {} " , sql);
        Query query = getSession().createSQLQuery(sql);
        query.setParameter("fromDate", fromDate);
        query.setParameter("toDate", toDate);

        List<Object[]> results = query.list();
        Map<Integer, Long> resultMap = new HashMap<>();
        for (Object[] row : results) {
            Integer locationId = ((Number) row[0]).intValue();
            Long totalIneligibleCountByHealthFacility = row[1] != null ? ((Number) row[1]).longValue() : 0L;
            resultMap.put(locationId, totalIneligibleCountByHealthFacility);
        }
        LOGGER.info("Operation = TotalIneligibleCountByHealthFacility, status = COMPLETED" );
        LOGGER.info("Result map: {}", resultMap);
        return resultMap;

    }
}
