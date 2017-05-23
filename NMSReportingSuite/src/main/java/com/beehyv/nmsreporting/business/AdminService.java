package com.beehyv.nmsreporting.business;

import com.beehyv.nmsreporting.model.FrontLineWorkers;
import com.beehyv.nmsreporting.model.User;
import org.apache.poi.ss.formula.functions.T;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by beehyv on 19/4/17.
 */
public interface AdminService {

  public HashMap startBulkDataImport(User user);

  public void getBulkDataImportCSV();

  public void getCumulativeCourseCompletionCSV(List<FrontLineWorkers> successFulcandidates, String rootPath,String place);

  public void getCumulativeCourseCompletionFiles(Date fromDate, Date toDate);

}
