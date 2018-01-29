package com.beehyv.nmsreporting.dao;

import com.beehyv.nmsreporting.model.FrontLineWorkers;

import java.util.Date;
import java.util.List;

/**
 * Created by beehyv on 23/5/17.
 */
public interface FrontLineWorkersDao {
    List<FrontLineWorkers> getInactiveFrontLineWorkers(Date toDate);

    List<FrontLineWorkers> getInactiveFrontLineWorkersWithStateId(Date toDate, Integer stateId);

    List<FrontLineWorkers> getInactiveFrontLineWorkersWithDistrictId(Date toDate, Integer districtId);

    List<FrontLineWorkers> getInactiveFrontLineWorkersWithBlockId(Date toDate, Integer blockId);

    Long getCountOfInactiveFrontLineWorkersForGivenDistrict(Date toDate, Integer districtId);

    public FrontLineWorkers getINactiveFrontLineWorkerByExternalFlwID(Date toDate, String Ext_Flw_Id);

    public FrontLineWorkers getINactiveFrontLineWorkerByExternalFlwIDAndStateId(Date toDate, String Ext_Flw_Id, Integer stateId);

    public List<FrontLineWorkers> getAllFrontLineWorkers(Date toDate, Integer stateId);

}
