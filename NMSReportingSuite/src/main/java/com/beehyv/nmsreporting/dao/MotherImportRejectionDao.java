package com.beehyv.nmsreporting.dao;

import com.beehyv.nmsreporting.model.MotherImportRejection;

import java.util.Date;
import java.util.List;

public interface MotherImportRejectionDao {

    List<MotherImportRejection> getAllRejectedMotherImportRecords(Date toDate);

    List<MotherImportRejection> getAllRejectedMotherImportRecordsWithStateId(Date toDate, Integer stateId);

    List<MotherImportRejection> getAllRejectedMotherImportRecordsWithDistrictId(Date toDate, Integer districtId);

    List<MotherImportRejection> getAllRejectedMotherImportRecordsWithBlockId(Date toDate, Integer blockId);

    Long getCountOFRejectedMotherImportRecordsWithDistrictId(Date toDate, Integer districtId);

}
