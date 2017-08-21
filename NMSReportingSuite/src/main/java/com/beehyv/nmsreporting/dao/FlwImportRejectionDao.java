package com.beehyv.nmsreporting.dao;

import com.beehyv.nmsreporting.model.FlwImportRejection;

import java.util.Date;
import java.util.List;

public interface FlwImportRejectionDao {

    List<FlwImportRejection> getAllRejectedFlwImportRecords(Date toDate);

    List<FlwImportRejection> getAllRejectedFlwImportRecordsWithStateId(Date toDate, Integer stateId);

    List<FlwImportRejection> getAllRejectedFlwImportRecordsWithDistrictId(Date toDate, Integer districtId);

    List<FlwImportRejection> getAllRejectedFlwImportRecordsWithBlockId(Date toDate, Integer blockId);

    Long getCountOfFlwRejectedRecordsForDistrict(Date toDate, Integer districtId);


}
