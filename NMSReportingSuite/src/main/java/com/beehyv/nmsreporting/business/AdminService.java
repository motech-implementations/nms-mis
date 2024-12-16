package com.beehyv.nmsreporting.business;

import com.beehyv.nmsreporting.entity.ReportRequest;
import com.beehyv.nmsreporting.model.User;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by beehyv on 19/4/17.
 */
public interface AdminService {

  HashMap startBulkDataImport(User user);

  void getBulkDataImportCSV();

  void getCumulativeCourseCompletionFiles( Date toDate);

  void modifyCumulativeCourseCompletionFiles(Date toDate, Integer stateIdRequest);

  void getCircleWiseAnonymousFiles(Date fromDate,Date toDate);

  void getCumulativeInactiveFiles(Date toDate);

  void modifyCumulativeInactiveFiles(Date toDate, Integer stateIdRequest);

  void getKilkariSixWeekNoAnswerFiles(Date fromDate, Date toDate);

  void getKilkariLowListenershipDeactivationFiles(Date fromDate, Date toDate);

  void getKilkariLowUsageFiles(Date fromDate, Date toDate);

  void processKilkariLowUsageFiles(Date fromDate, Date toDate);

  void getKilkariSelfDeactivationFiles(Date fromDate, Date toDate);

  void createFiles(String reportType);

  void createFolders(String reportType);

  void createSpecificReport(ReportRequest reportRequest);

  void createChildImportRejectedFiles(int relativeMonth, boolean isWeekly);

  void createMotherImportRejectedFiles(int relativeMonth, boolean isWeekly);

  void createFlwImportRejectedFiles(Date toDate);

  void modifySpecificReport(ReportRequest reportRequest);

  void updateCDRDetailsInDatabase() throws IOException;

}
