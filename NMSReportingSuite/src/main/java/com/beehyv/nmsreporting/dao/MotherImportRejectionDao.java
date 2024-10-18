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

    Long getUniqueDuplicatePhoneNumberCountForBlock(Date fromDate, Date toDate, Integer blockId);

    Long getUniqueDuplicatePhoneNumberCountForState(Date fromDate, Date toDate, Integer stateId);

    Long getUniqueDuplicatePhoneNumberCountForDistrict(Date fromDate, Date toDate, Integer districtId);

    Long getUniqueDuplicatePhoneNumberCountForHealthFacility(Date fromDate, Date toDate, Integer healthFacilityId);

    Long getTotalIneligibleCountByState(Date fromDate, Date toDate, Integer stateId);

    Long getTotalIneligibleCountByDistrict(Date fromDate, Date toDate, Integer districtId);

    Long getTotalIneligibleCountByBlock(Date fromDate, Date toDate, Integer blockId);

    Long getTotalIneligibleCountByHealthFacility(Date fromDate, Date toDate, Integer healthFacilityId);

}
