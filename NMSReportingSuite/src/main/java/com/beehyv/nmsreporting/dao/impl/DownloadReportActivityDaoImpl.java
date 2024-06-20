package com.beehyv.nmsreporting.dao.impl;

import com.beehyv.nmsreporting.dao.AbstractDao;
import com.beehyv.nmsreporting.dao.DownloadReportActivityDao;
import com.beehyv.nmsreporting.model.DownloadReportActivity;
import com.beehyv.nmsreporting.model.User;
import org.springframework.stereotype.Repository;

@Repository("downloadReportActivityDao")
public class DownloadReportActivityDaoImpl extends AbstractDao<Long, DownloadReportActivity> implements DownloadReportActivityDao {

    @Override
    public void saveDownloadReportActivity(DownloadReportActivity activity) {
        persist(activity);
    }

}
