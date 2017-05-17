package com.beehyv.nmsreporting.business;

import com.beehyv.nmsreporting.model.User;
import org.apache.poi.ss.formula.functions.T;

import java.util.HashMap;

/**
 * Created by beehyv on 19/4/17.
 */
public interface AdminService {

  public HashMap startBulkDataImport(User user);

  public void getBulkDataImportCSV();

  public void getCumulativeCourseCompletionCSV(String State,String District,String Block);

  public void getCumulativeCourseCompletionCSV1(Integer LocationId,String Parent);

}
