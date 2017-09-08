package com.beehyv.nmsreporting.dao;

import com.beehyv.nmsreporting.model.ChildImportRejection;

import java.util.Date;
import java.util.List;

public interface ChildImportRejectionDao {

    List<ChildImportRejection> getRejectedChildRecords(Date fromDate, Date toDate);

    List<ChildImportRejection> getRejectedChildRecordsWithStateId(Date fromDate, Date toDate, Integer stateId);

    List<ChildImportRejection> getRejectedChildRecordsWithDistrictId(Date fromDate, Date toDate, Integer districtId);

    List<ChildImportRejection> getRejectedChildRecordsWithBlockId(Date fromDate, Date toDate, Integer blockId);

    Long getCountOfRejectedChildRecords(Date fromDate, Date toDate, Integer districtId);


}
