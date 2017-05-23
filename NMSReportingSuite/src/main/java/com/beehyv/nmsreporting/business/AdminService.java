package com.beehyv.nmsreporting.business;

import com.beehyv.nmsreporting.model.AnonymousUsers;
import com.beehyv.nmsreporting.model.FrontLineWorkers;
import com.beehyv.nmsreporting.model.KilkariSixWeeksNoAnswer;
import com.beehyv.nmsreporting.model.User;
import org.apache.poi.ss.formula.functions.T;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by beehyv on 19/4/17.
 */
public interface AdminService {

  HashMap startBulkDataImport(User user);

  void getBulkDataImportCSV();

  void getCumulativeCourseCompletionFiles(Date fromDate, Date toDate);

  void getCircleWiseAnonymousFiles(Date toDate);

  void getCumulativeInactiveFiles(Date fromDate, Date toDate);

  void getKilkariSixWeekNoAnswerFiles(Date fromDate, Date toDate);

  void getKilkariLowUsageFiles(Date fromDate, Date toDate);

  void getKilkariSelfDeactvationFiles(Date fromDate, Date toDate);

  void createFiles(String fileName);

  void createFolders(String reportType);

}
