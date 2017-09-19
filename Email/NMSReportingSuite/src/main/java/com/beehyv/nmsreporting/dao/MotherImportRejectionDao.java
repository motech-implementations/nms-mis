package com.beehyv.nmsreporting.dao;

import java.util.Date;

public interface MotherImportRejectionDao {

    Long getCountOFRejectedMotherImportRecordsWithDistrictId(Date fromDate, Date toDate, Integer districtId);

}
