package com.beehyv.nmsreporting.dao.impl;

import com.beehyv.nmsreporting.dao.AbstractDao;
import com.beehyv.nmsreporting.dao.ReportTypeDao;
import com.beehyv.nmsreporting.enums.ReportType;
import org.springframework.stereotype.Repository;

/**
 * Created by beehyv on 26/5/17.
 */
@Repository("reportTypeDao")
public class ReportTypeDaoImpl extends AbstractDao<String, ReportType> implements ReportTypeDao {

    @Override
    public ReportType getReportTypeByName(String reportName) {
        return ReportType.valueOf(reportName);
    }
}
