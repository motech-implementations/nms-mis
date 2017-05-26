package com.beehyv.nmsreporting.business;

import com.beehyv.nmsreporting.entity.ReportRequest;
import com.beehyv.nmsreporting.model.StateCircle;

import java.util.List;

/**
 * Created by beehyv on 25/5/17.
 */
public interface ReportService {

    public List<String> getReportPathName(ReportRequest reportRequest);

    public List<StateCircle> getCirclesByState(Integer stateId);

}
