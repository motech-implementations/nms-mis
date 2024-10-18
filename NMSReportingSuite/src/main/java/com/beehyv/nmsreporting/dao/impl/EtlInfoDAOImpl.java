package com.beehyv.nmsreporting.dao.impl;

import com.beehyv.nmsreporting.business.impl.AggregateKilkariReportsServiceImpl;
import com.beehyv.nmsreporting.dao.AbstractDao;
import com.beehyv.nmsreporting.dao.EtlInfoDAO;
import com.beehyv.nmsreporting.entity.KilkariSubscriberRegistrationDateRejectedCountDto;
import com.beehyv.nmsreporting.model.EtlInfoTable;
import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;


import javax.transaction.Transactional;
import java.util.*;

@Repository("etlInfoDAO")
public class EtlInfoDAOImpl extends AbstractDao<Integer, EtlInfoTable> implements EtlInfoDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(AggregateKilkariReportsServiceImpl.class);

    @Override
    @Transactional
    public List<EtlInfoTable> fetchETLInfoByJobNames(List<String> etlJobNames, Date date) {
        if (etlJobNames == null || etlJobNames.isEmpty() || date == null) {
            throw new IllegalArgumentException("etlJobNames and date must not be null or empty");
        }

        List<String> jobsForTomorrow = Arrays.asList(" KilkariCallReport", " KilkariSubscriber");
        etlJobNames.removeAll(jobsForTomorrow);

        String baseQuery = "SELECT * FROM ETL_info_table WHERE etl_job IN (:etlJob) AND DATE(last_etl_time) = :date";
        List<EtlInfoTable> result = new ArrayList<>();

        if (!etlJobNames.isEmpty()) {
            Query queryToday = getSession().createSQLQuery(baseQuery)
                    .addEntity(EtlInfoTable.class)
                    .setParameterList("etlJob", etlJobNames)
                    .setParameter("date", new java.sql.Date(date.getTime()));
            LOGGER.info("Query for today's jobs: " + queryToday.getQueryString());
            result.addAll(queryToday.list());
        }

        // Run query for tomorrow's jobs
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, 1); // Move to tomorrow
        Date tomorrow = calendar.getTime();

        Query queryTomorrow = getSession().createSQLQuery(baseQuery)
                .addEntity(EtlInfoTable.class)
                .setParameterList("etlJob", jobsForTomorrow)
                .setParameter("date", new java.sql.Date(tomorrow.getTime()));
        LOGGER.info("Query for tomorrow's jobs: " + queryTomorrow.getQueryString());
        result.addAll(queryTomorrow.list());

        return result;
    }

    @Override
    public List<EtlInfoTable> fetchMonthlyReportsByJobNames(List<String> etlJobNames, int currentMonth) {
        if (etlJobNames == null || etlJobNames.isEmpty()) {
            throw new IllegalArgumentException("etlJobNames and date must not be null or empty");
        }
        String sqlQuery = "SELECT * FROM ETL_info_table " +
                "WHERE etl_job IN (:etlJob) AND MONTH(last_etl_time) = :month";
        Query query = getSession().createSQLQuery(sqlQuery)
                .addEntity(EtlInfoTable.class)
                .setParameterList("etlJob", etlJobNames)
                .setParameter("month", currentMonth);
        LOGGER.info("Final Query to be executed for Monthly Reports :" +query.getQueryString());

        List<EtlInfoTable> result = query.list();
        return result;
    }

    @Override
    public List<EtlInfoTable> fetchQuarterlyReportsByJobName(List<String> etlJobNames) {
        if (etlJobNames == null || etlJobNames.isEmpty()) {
            throw new IllegalArgumentException("etlJobNames and date must not be null or empty");
        }
        String sqlQuery = "SELECT * FROM ETL_info_table " +
                "WHERE etl_job IN (:etlJobs) AND QUARTER(last_etl_time) = QUARTER(CURDATE()) " +
                "AND YEAR(last_etl_time) = YEAR(CURDATE())";

        Query query = getSession().createSQLQuery(sqlQuery)
                .addEntity(EtlInfoTable.class)
                .setParameterList("etlJobs", etlJobNames);

        List<EtlInfoTable> result = query.list();
        return result;
    }

    @Override
    public List<EtlInfoTable> fetchYearlyReportsByJobNames(List<String> etlJobNames) {
        if (etlJobNames == null || etlJobNames.isEmpty()) {
            throw new IllegalArgumentException("etlJobNames and date must not be null or empty");
        }
        String sqlQuery = "SELECT * FROM ETL_info_table " +
                "WHERE etl_job IN (:etlJobs) AND YEAR(last_etl_time) = YEAR(CURDATE()) ";

        Query query = getSession().createSQLQuery(sqlQuery)
                .addEntity(EtlInfoTable.class)
                .setParameterList("etlJobs", etlJobNames);

        List<EtlInfoTable> result = query.list();
        return result;
    }

}
