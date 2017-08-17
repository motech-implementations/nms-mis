package com.beehyv.nmsreporting.business;

import com.beehyv.nmsreporting.entity.ReportRequest;
import com.beehyv.nmsreporting.model.User;

import java.util.Date;
import java.util.HashMap;

/**
 * Created by beehyv on 19/4/17.
 */
public interface AdminService {

  HashMap startBulkDataImport(User user);

  void getBulkDataImportCSV();

  void getCumulativeCourseCompletionFiles( Date toDate);

  void getCircleWiseAnonymousFiles(Date fromDate,Date toDate);

  void getCumulativeInactiveFiles(Date toDate);

  void getKilkariSixWeekNoAnswerFiles(Date fromDate, Date toDate);

  void getKilkariLowListenershipDeactivationFiles(Date fromDate, Date toDate);

  void getKilkariLowUsageFiles(Date fromDate, Date toDate);

  void getKilkariSelfDeactivationFiles(Date fromDate, Date toDate);

  void createFiles(String reportType);

  void createFolders(String reportType);

  void createSpecificReport(ReportRequest reportRequest);

  void createChildImportRejectedFiles(Date toDate);

  void createMotherImportRejectedFiles(Date toDate);

  void createFlwImportRejectedFiles(Date toDate);

}
