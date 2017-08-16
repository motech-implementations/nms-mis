package com.beehyv.nmsreporting.dao;

import com.beehyv.nmsreporting.model.ChildImportRejection;

import java.util.Date;
import java.util.List;

public interface ChildImportRejectionDao {

    List<ChildImportRejection> getRejectedChildRecords( Date toDate);
    List<ChildImportRejection> getRejectedChildRecordsWithStateId(Date toDate, Integer stateId);
    List<ChildImportRejection> getRejectedChildRecordsWithDistrictId(Date toDate, Integer districtId);
    List<ChildImportRejection> getRejectedChildRecordsWithBlockId(Date toDate, Integer blockId);

}
