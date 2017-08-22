package com.beehyv.nmsreporting.dao;

import java.util.Date;

public interface ChildImportRejectionDao {

    Long getCountOfRejectedChildRecords(Date toDate, Integer districtId);

}
