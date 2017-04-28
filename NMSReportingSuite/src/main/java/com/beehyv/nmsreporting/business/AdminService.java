package com.beehyv.nmsreporting.business;

import java.util.HashMap;

/**
 * Created by beehyv on 19/4/17.
 */
public interface AdminService {
  public HashMap startBulkDataImport();

  public void getBulkDataImportCSV();
}
