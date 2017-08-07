package com.beehyv.nmsreporting.dao;

import com.beehyv.nmsreporting.model.ChildImportRejection;

import java.util.Date;
import java.util.List;

public interface ChildImportRejectionDao {

    List<ChildImportRejection> getRejectedChildRecords( Date toDate);
}
