package com.beehyv.nmsreporting.dao.impl;

import com.beehyv.nmsreporting.dao.AbstractDao;
import com.beehyv.nmsreporting.dao.MotherImportRejectionDao;
import com.beehyv.nmsreporting.model.MotherImportRejection;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository("motherImportRejectionDao")
public class MotherImportRejectionDaoImpl extends AbstractDao<Long, MotherImportRejection> implements MotherImportRejectionDao {
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
    public Long getUniqueDuplicatePhoneNumberCountForState(Date fromDate, Date toDate, Integer stateId) {
        String sql = "SELECT COUNT(DISTINCT mobile_no) AS total_unique_duplicate_count " +
                "FROM (" +
                "    SELECT mobile_no " +
                "    FROM child_import_rejection " +
                "    WHERE rejection_reason = 'DUPLICATE_MOBILE_NUMBER_IN_DATASET' " +
                "      AND mobile_no IS NOT NULL " +
                "      AND modification_date BETWEEN :fromDate AND :toDate " +
                "      AND state_id = :stateId " +
                "      AND accepted = false " +
                "    UNION " +
                "    SELECT mobile_no " +
                "    FROM mother_import_rejection " +
                "    WHERE rejection_reason = 'DUPLICATE_MOBILE_NUMBER_IN_DATASET' " +
                "      AND mobile_no IS NOT NULL " +
                "      AND modification_date BETWEEN :fromDate AND :toDate " +
                "      AND state_id = :stateId " +
                "      AND accepted = false " +
                ") AS combined_duplicates";

        Query query = getSession().createSQLQuery(sql);
        query.setParameter("fromDate", fromDate);
        query.setParameter("toDate", toDate);
        query.setParameter("stateId", stateId);

        Object result = query.uniqueResult();
        return result != null ? ((Number) result).longValue() : 0L;
    }

    @Override
    public Long getUniqueDuplicatePhoneNumberCountForDistrict(Date fromDate, Date toDate, Integer districtId) {
        String sql = "SELECT COUNT(DISTINCT mobile_no) AS total_unique_duplicate_count " +
                "FROM (" +
                "    SELECT mobile_no " +
                "    FROM child_import_rejection " +
                "    WHERE rejection_reason = 'DUPLICATE_MOBILE_NUMBER_IN_DATASET' " +
                "      AND mobile_no IS NOT NULL " +
                "      AND modification_date BETWEEN :fromDate AND :toDate " +
                "      AND district_id = :districtId " +
                "      AND accepted = false " +
                "    UNION " +
                "    SELECT mobile_no " +
                "    FROM mother_import_rejection " +
                "    WHERE rejection_reason = 'DUPLICATE_MOBILE_NUMBER_IN_DATASET' " +
                "      AND mobile_no IS NOT NULL " +
                "      AND modification_date BETWEEN :fromDate AND :toDate " +
                "      AND district_id = :districtId " +
                "      AND accepted = false " +
                ") AS combined_duplicates";

        Query query = getSession().createSQLQuery(sql);
        query.setParameter("fromDate", fromDate);
        query.setParameter("toDate", toDate);
        query.setParameter("districtId", districtId);

        Object result = query.uniqueResult();
        return result != null ? ((Number) result).longValue() : 0L;
    }

    @Override
    public Long getUniqueDuplicatePhoneNumberCountForBlock(Date fromDate, Date toDate, Integer blockId) {
        String sql = "SELECT COUNT(DISTINCT mobile_no) AS total_unique_duplicate_count " +
                "FROM (" +
                "    SELECT mobile_no " +
                "    FROM child_import_rejection " +
                "    WHERE rejection_reason = 'DUPLICATE_MOBILE_NUMBER_IN_DATASET' " +
                "      AND mobile_no IS NOT NULL " +
                "      AND modification_date BETWEEN :fromDate AND :toDate " +
                "      AND health_block_id = :blockId " +
                "      AND accepted = false " +
                "    UNION " +
                "    SELECT mobile_no " +
                "    FROM mother_import_rejection " +
                "    WHERE rejection_reason = 'DUPLICATE_MOBILE_NUMBER_IN_DATASET' " +
                "      AND mobile_no IS NOT NULL " +
                "      AND modification_date BETWEEN :fromDate AND :toDate " +
                "      AND health_block_id = :blockId " +
                "      AND accepted = false " +
                ") AS combined_duplicates";

        Query query = getSession().createSQLQuery(sql);
        query.setParameter("fromDate", fromDate);
        query.setParameter("toDate", toDate);
        query.setParameter("blockId", blockId);

        Object result = query.uniqueResult();
        return result != null ? ((Number) result).longValue() : 0L;
    }

    @Override
    public Long getUniqueDuplicatePhoneNumberCountForHealthFacility(Date fromDate, Date toDate, Integer healthFacilityId) {
        String sql = "SELECT COUNT(DISTINCT mobile_no) AS total_unique_duplicate_count " +
                "FROM (" +
                "    SELECT mobile_no " +
                "    FROM child_import_rejection " +
                "    WHERE rejection_reason = 'DUPLICATE_MOBILE_NUMBER_IN_DATASET' " +
                "      AND mobile_no IS NOT NULL " +
                "      AND modification_date BETWEEN :fromDate AND :toDate " +
                "      AND phc_id = :healthFacilityId " +
                "      AND accepted = false " +
                "    UNION " +
                "    SELECT mobile_no " +
                "    FROM mother_import_rejection " +
                "    WHERE rejection_reason = 'DUPLICATE_MOBILE_NUMBER_IN_DATASET' " +
                "      AND mobile_no IS NOT NULL " +
                "      AND modification_date BETWEEN :fromDate AND :toDate " +
                "      AND phc_id = :healthFacilityId " +
                "      AND accepted = false " +
                ") AS combined_duplicates";

        Query query = getSession().createSQLQuery(sql);
        query.setParameter("fromDate", fromDate);
        query.setParameter("toDate", toDate);
        query.setParameter("healthFacilityId", healthFacilityId);

        Object result = query.uniqueResult();
        return result != null ? ((Number) result).longValue() : 0L;
    }


    public Long getTotalIneligibleCountByState(Date fromDate, Date toDate, Integer stateId) {
        String sql = "SELECT COALESCE(SUM(ineligible_for_subscriptions), 0) AS total_ineligible_count " +
                "FROM (" +
                "    SELECT COUNT(CASE WHEN rejection_reason = 'INVALID_LMP_DATE' THEN id END) AS ineligible_for_subscriptions " +
                "    FROM mother_import_rejection " +
                "    WHERE modification_date BETWEEN :fromDate AND :toDate " +
                "      AND state_id = :stateId " +
                "      AND accepted = false " +
                "    UNION ALL " +
                "    SELECT COUNT(CASE WHEN rejection_reason = 'INVALID_DOB' THEN id END) AS ineligible_for_subscriptions " +
                "    FROM child_import_rejection " +
                "    WHERE modification_date BETWEEN :fromDate AND :toDate " +
                "      AND state_id = :stateId " +
                "      AND accepted = false " +
                ") AS combined_ineligible";


            Query query = getSession().createSQLQuery(sql);
            query.setParameter("fromDate", fromDate);
            query.setParameter("toDate", toDate);
            query.setParameter("stateId", stateId);

            Object result = query.uniqueResult();
            return result != null ? ((Number) result).longValue() : 0L;

    }

    public Long getTotalIneligibleCountByDistrict(Date fromDate, Date toDate, Integer districtId) {
        String sql = "SELECT COALESCE(SUM(ineligible_for_subscriptions), 0) AS total_ineligible_count " +
                "FROM (" +
                "    SELECT COUNT(CASE WHEN rejection_reason = 'INVALID_LMP_DATE' THEN id END) AS ineligible_for_subscriptions " +
                "    FROM mother_import_rejection " +
                "    WHERE modification_date BETWEEN :fromDate AND :toDate " +
                "      AND district_id = :districtId " +
                "      AND accepted = false " +
                "    UNION ALL " +
                "    SELECT COUNT(CASE WHEN rejection_reason = 'INVALID_DOB' THEN id END) AS ineligible_for_subscriptions " +
                "    FROM child_import_rejection " +
                "    WHERE modification_date BETWEEN :fromDate AND :toDate " +
                "      AND district_id = :districtId " +
                "      AND accepted = false " +
                ") AS combined_ineligible";


        Query query = getSession().createSQLQuery(sql);
        query.setParameter("fromDate", fromDate);
        query.setParameter("toDate", toDate);
        query.setParameter("districtId", districtId);

        Object result = query.uniqueResult();
        return result != null ? ((Number) result).longValue() : 0L;

    }

    public Long getTotalIneligibleCountByBlock(Date fromDate, Date toDate, Integer blockId) {
        String sql = "SELECT COALESCE(SUM(ineligible_for_subscriptions), 0) AS total_ineligible_count " +
                "FROM (" +
                "    SELECT COUNT(CASE WHEN rejection_reason = 'INVALID_LMP_DATE' THEN id END) AS ineligible_for_subscriptions " +
                "    FROM mother_import_rejection " +
                "    WHERE modification_date BETWEEN :fromDate AND :toDate " +
                "      AND health_block_id = :blockId " +
                "      AND accepted = false " +
                "    UNION ALL " +
                "    SELECT COUNT(CASE WHEN rejection_reason = 'INVALID_DOB' THEN id END) AS ineligible_for_subscriptions " +
                "    FROM child_import_rejection " +
                "    WHERE modification_date BETWEEN :fromDate AND :toDate " +
                "      AND health_block_id = :blockId " +
                "      AND accepted = false " +
                ") AS combined_ineligible";


        Query query = getSession().createSQLQuery(sql);
        query.setParameter("fromDate", fromDate);
        query.setParameter("toDate", toDate);
        query.setParameter("blockId", blockId);

        Object result = query.uniqueResult();
        return result != null ? ((Number) result).longValue() : 0L;

    }

    public Long getTotalIneligibleCountByHealthFacility(Date fromDate, Date toDate, Integer healthFacilityId) {
        String sql = "SELECT COALESCE(SUM(ineligible_for_subscriptions), 0) AS total_ineligible_count " +
                "FROM (" +
                "    SELECT COUNT(CASE WHEN rejection_reason = 'INVALID_LMP_DATE' THEN id END) AS ineligible_for_subscriptions " +
                "    FROM mother_import_rejection " +
                "    WHERE modification_date BETWEEN :fromDate AND :toDate " +
                "      AND phc_id = :healthFacilityId " +
                "      AND accepted = false " +
                "    UNION ALL " +
                "    SELECT COUNT(CASE WHEN rejection_reason = 'INVALID_DOB' THEN id END) AS ineligible_for_subscriptions " +
                "    FROM child_import_rejection " +
                "    WHERE modification_date BETWEEN :fromDate AND :toDate " +
                "      AND phc_id = :healthFacilityId " +
                "      AND accepted = false " +
                ") AS combined_ineligible";


        Query query = getSession().createSQLQuery(sql);
        query.setParameter("fromDate", fromDate);
        query.setParameter("toDate", toDate);
        query.setParameter("healthFacilityId", healthFacilityId);

        Object result = query.uniqueResult();
        return result != null ? ((Number) result).longValue() : 0L;

    }
}
