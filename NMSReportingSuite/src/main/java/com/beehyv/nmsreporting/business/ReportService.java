package com.beehyv.nmsreporting.business;

import com.beehyv.nmsreporting.entity.ReportRequest;

import java.util.List;

/**
 * Created by beehyv on 25/5/17.
 */
public interface ReportService {

    public List<String> getReportPathName(ReportRequest reportRequest);

}
