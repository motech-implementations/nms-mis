package com.beehyv.nmsreporting.dao;

import com.beehyv.nmsreporting.model.MotherImportRejection;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface MotherImportRejectionDao {

    List<MotherImportRejection> getAllRejectedMotherImportRecords(Date fromDate, Date toDate);

    List<MotherImportRejection> getAllRejectedMotherImportRecordsWithStateId(Date fromDate, Date toDate, Integer stateId);

    List<MotherImportRejection> getAllRejectedMotherImportRecordsWithDistrictId(Date fromDate, Date toDate, Integer districtId);

    List<MotherImportRejection> getAllRejectedMotherImportRecordsWithBlockId(Date fromDate, Date toDate, Integer blockId);

    Long getCountOFRejectedMotherImportRecordsWithDistrictId(Date fromDate, Date toDate, Integer districtId);

    Map<Integer, Long> getUniqueDuplicatePhoneNumberCountForBlock(Date fromDate, Date toDate, List<Integer> blockIds);

    Map<Integer, Long> getUniqueDuplicatePhoneNumberCountForState(Date fromDate, Date toDate, List<Integer> stateIds);

    Map<Integer, Long> getUniqueDuplicatePhoneNumberCountForDistrict(Date fromDate, Date toDate, List<Integer> districtIds);

    Map<Integer, Long> getUniqueDuplicatePhoneNumberCountForHealthFacility(Date fromDate, Date toDate, List<Integer> healthFacilityIds);

    Map<Integer, Long> getTotalIneligibleCountByState(Date fromDate, Date toDate, List<Integer> stateIds);

    Map<Integer, Long> getTotalIneligibleCountByDistrict(Date fromDate, Date toDate, List<Integer> districtIds);

    Map<Integer, Long> getTotalIneligibleCountByBlock(Date fromDate, Date toDate, List<Integer> blockIds);

    Map<Integer, Long> getTotalIneligibleCountByHealthFacility(Date fromDate, Date toDate, List<Integer> healthFacilityIds);

}
