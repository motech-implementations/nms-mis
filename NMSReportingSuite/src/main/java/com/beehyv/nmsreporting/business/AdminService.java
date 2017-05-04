package com.beehyv.nmsreporting.business;

import java.util.HashMap;

/**
 * Created by beehyv on 19/4/17.
 */
public interface AdminService {

  public HashMap startBulkDataImport();

  public void getBulkDataImportCSV();

  public void getCumulativeCourseCompletionCSV(String State,String District,String Block);

  public void getCumulativeCourseCompletionCSV1(Integer LocationId);

}
