package com.beehyv.nmsreporting.dao;

import java.util.Date;

public interface MotherImportRejectionDao {

    Long getCountOFRejectedMotherImportRecordsWithDistrictId(Date toDate, Integer districtId);

}
