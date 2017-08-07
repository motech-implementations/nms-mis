package com.beehyv.nmsreporting.dao;

import com.beehyv.nmsreporting.model.MotherImportRejection;

import java.util.Date;
import java.util.List;

public interface MotherImportRejectionDao {

    List<MotherImportRejection> getAllRejectedMotherImportRecords(Date toDate);
}
