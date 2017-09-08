package com.beehyv.nmsreporting.dao;

import com.beehyv.nmsreporting.model.MotherImportRejection;

import java.util.Date;
import java.util.List;

public interface MotherImportRejectionDao {

    List<MotherImportRejection> getAllRejectedMotherImportRecords(Date fromDate, Date toDate);

    List<MotherImportRejection> getAllRejectedMotherImportRecordsWithStateId(Date fromDate, Date toDate, Integer stateId);

    List<MotherImportRejection> getAllRejectedMotherImportRecordsWithDistrictId(Date fromDate, Date toDate, Integer districtId);

    List<MotherImportRejection> getAllRejectedMotherImportRecordsWithBlockId(Date fromDate, Date toDate, Integer blockId);

    Long getCountOFRejectedMotherImportRecordsWithDistrictId(Date fromDate, Date toDate, Integer districtId);

}
