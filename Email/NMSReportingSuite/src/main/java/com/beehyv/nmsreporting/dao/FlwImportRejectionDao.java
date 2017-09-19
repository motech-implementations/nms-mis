package com.beehyv.nmsreporting.dao;

import java.util.Date;

public interface FlwImportRejectionDao {

    Long getCountOfFlwRejectedRecordsForDistrict(Date fromDate, Date toDate, Integer districtId);


}
