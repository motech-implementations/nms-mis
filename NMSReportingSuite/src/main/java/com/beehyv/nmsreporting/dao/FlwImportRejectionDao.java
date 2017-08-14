package com.beehyv.nmsreporting.dao;

import com.beehyv.nmsreporting.model.FlwImportRejection;

import java.util.Date;
import java.util.List;

public interface FlwImportRejectionDao {

    List<FlwImportRejection> getAllRejectedFlwImportRecords(Date toDate);
}
