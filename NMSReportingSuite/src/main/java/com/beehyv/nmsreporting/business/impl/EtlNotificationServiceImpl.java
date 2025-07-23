package com.beehyv.nmsreporting.business.impl;

import com.beehyv.nmsreporting.business.EtlNotificationService;
import com.beehyv.nmsreporting.dao.EtlInfoDAO;
import com.beehyv.nmsreporting.dao.NoticeDao;
import com.beehyv.nmsreporting.dao.ScheduledReportTrackerDao;
import com.beehyv.nmsreporting.enums.EtlJobForNotifications;
import com.beehyv.nmsreporting.enums.ReportType;
import com.beehyv.nmsreporting.model.EtlInfoTable;
import com.beehyv.nmsreporting.model.Notice;
import com.beehyv.nmsreporting.model.ScheduledReportTracker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

import static com.beehyv.nmsreporting.utils.Global.isAutoGenerate;

@Service("etlNotificationService")
public class EtlNotificationServiceImpl implements EtlNotificationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AggregateKilkariReportsServiceImpl.class);

    @Autowired
    private EtlInfoDAO etlInfoDAO;

    @Autowired
    private ScheduledReportTrackerDao scheduledReportTrackerDao;

    @Autowired
    private NoticeDao noticeDao;

    @Autowired
    private JmsTemplate jmsTemplate;

    public final String getNoticeTypeAsMonthlyReports = "MONTHLY_REPORTS";
    public final String getNoticeTypeAsQuarterReports = "QUARTERLY_NOTICE";
    public final String getNoticeTypeAsYearlyReports = "YEARLY_NOTICE";
    public final String getNoticeTypeAsFinancialYearReports = "FINANCIAL_YEAR_NOTICE";

    @Override
    @Transactional
    public void dailyNotifications() {
        Date currentDate = new Date();
        List<Notice> notices = new ArrayList<>();

        List<String> dailyETLJobs = new ArrayList<String>();
        List<String> monthlyReports = new ArrayList<String>();
        List<String> quarterlyReports = new ArrayList<>();
        List<String> yearlyReports = new ArrayList<>();
        List<String> financialYearReports = new ArrayList<>();

        categorizeEtlJobs(dailyETLJobs, monthlyReports, quarterlyReports, yearlyReports, financialYearReports);

        LOGGER.info("ETL Jobs to be searched: {}", dailyETLJobs);
        LOGGER.info("Date sent: {}", currentDate);

        List<EtlInfoTable> etlInfo =  fetchEtlInfo(dailyETLJobs, currentDate);
        List<String> jobsForTomorrow = Arrays.asList(" KilkariCallReport", " KilkariSubscriber");
        dailyETLJobs.addAll(jobsForTomorrow);

        LOGGER.info(etlInfo == null || etlInfo.isEmpty() ? "No ETL jobs were executed successfully." : "Etl that were successful :" + etlInfo.get(0).getEtlJob());

        List<String> executedJobs = null;
        if (etlInfo != null) {
            executedJobs = extractExecutedJobs(etlInfo);
        }

        Set<String> dailyJobsSet = new HashSet<String>(dailyETLJobs);
        Set<String> executedJobSet = new HashSet<String>(executedJobs);

        dailyJobsSet.removeAll(executedJobSet);
        List<String> failedEtlJobs = new ArrayList<String>(dailyJobsSet);

        LOGGER.info("Failed ETL Jobs: {}", failedEtlJobs);

        addFailedEtlNotifications(notices, failedEtlJobs);
        addSuccessfulEtlNotifications(notices, failedEtlJobs);

        LOGGER.info("Searching in ScheduledReport table for Month : {}_{}", currentDate.getMonth(), currentDate.getYear());

        List<ScheduledReportTracker> monthlyGeneratedReports = fetchMonthlyGeneratedReports(currentDate);

        List<String> reportsTracker = getStrings(currentDate, monthlyGeneratedReports);
        addReportNotifications(notices, reportsTracker, "LINE-LIST");

        addGeneratedReportNotifications(notices, monthlyReports, getNoticeTypeAsMonthlyReports);
        addGeneratedReportNotifications(notices, quarterlyReports, getNoticeTypeAsQuarterReports);
        addGeneratedReportNotifications(notices, yearlyReports, getNoticeTypeAsYearlyReports);
        addGeneratedReportNotifications(notices, financialYearReports, getNoticeTypeAsFinancialYearReports);

        noticeDao.saveAll(notices);
    }

    private void categorizeEtlJobs(List<String> dailyETLJobs, List<String> monthlyReports, List<String> quarterlyReports, List<String> yearlyReports, List<String> financialYearReports) {
        for (EtlJobForNotifications job : EtlJobForNotifications.values()) {
            switch (job.getSchedule()) {
                case "MONTHLY":
                    monthlyReports.add(job.getJobName());
                    break;
                case "QUARTERLY":
                    quarterlyReports.add(job.getJobName());
                    break;
                case "YEARLY":
                    yearlyReports.add(job.getJobName());
                    break;
                case "FINANCIALYEAR":
                    financialYearReports.add(job.getJobName());
                    break;
                default:
                    dailyETLJobs.add(job.getJobName());
                    break;
            }
        }
    }

    private List<EtlInfoTable> fetchEtlInfo(List<String> dailyETLJobs, Date date) {
        try {
            return etlInfoDAO.fetchETLInfoByJobNames(dailyETLJobs, date);
        } catch (Exception e) {
            LOGGER.error("Exception while fetching ETL info: ", e);
            return Collections.emptyList();
        }
    }

    private List<String> extractExecutedJobs(List<EtlInfoTable> etlInfo) {
        List<String> executedJobs = new ArrayList<>();
        for (EtlInfoTable etlInfoTable : etlInfo) {
            executedJobs.add(etlInfoTable.getEtlJob());
        }
        return executedJobs;
    }

    private void addFailedEtlNotifications(List<Notice> notices, List<String> failedEtlJobs) {
        if (!failedEtlJobs.isEmpty()) {
            if(failedEtlJobs.contains(" KilkariSubscriber")){
                notices.add(new Notice("ETL", new Date(), getDailyReportGenerationFailureMessage(ReportType.kilkariSubscriber.getReportName(), "KILKARI"), "FAILED"));
            }

            if (failedEtlJobs.contains(" KilkariCallReport")){
                notices.add(new Notice("ETL", new Date(), getDailyReportGenerationFailureMessage(ReportType.kilkariCumulative.getReportName(), "KILKARI"), "FAILED"));
                notices.add(new Notice("ETL", new Date(), getDailyReportGenerationFailureMessage(ReportType.kilkariCalls.getReportName(), "KILKARI"), "FAILED"));
            }

            if (failedEtlJobs.contains(" Mobile Academy Aggregate Reports")){
                notices.add(new Notice("ETL", new Date(), getDailyReportGenerationFailureMessage(ReportType.maPerformance.getReportName(), "MA"), "FAILED"));
                notices.add(new Notice("ETL", new Date(), getDailyReportGenerationFailureMessage(ReportType.maSubscriber.getReportName(), "MA"), "FAILED"));
                notices.add(new Notice("ETL", new Date(), getDailyReportGenerationFailureMessage(ReportType.maCumulative.getReportName(), "MA"), "FAILED"));
            }

            if (failedEtlJobs.contains(" MA") || failedEtlJobs.contains(" MA call detail") || failedEtlJobs.contains(" MA call content") ||
                    failedEtlJobs.contains(" MA course completion") || failedEtlJobs.contains(" FLW Rejections")){

                String maNotifications= "MA report generation failed due to Data copy issue (ETL failure).";
                Notice etlNotice = new Notice("ETL", new Date(), maNotifications, "FAILED");
                notices.add(etlNotice);
            }
            if (failedEtlJobs.contains(" Beneficiary") || failedEtlJobs.contains(" BeneficiaryTracker") || failedEtlJobs.contains(" Subscription") ||
                    failedEtlJobs.contains(" Beneficiary Call Measure Inbox") || failedEtlJobs.contains(" Mother Rejections") ||
                    failedEtlJobs.contains(" Child Rejections") || failedEtlJobs.contains(" Kilkari Subscriptions update") ||
                    failedEtlJobs.contains(" Reactivated Beneficiaries") || failedEtlJobs.contains(" nms imi cdrs")){
                String etlNotification = "Kilkari report generation failed due to Data copy issue (ETL failure).";
                Notice etlNotice = new Notice("ETL", new Date(), etlNotification, "FAILED");
                notices.add(etlNotice);
            }
        }
    }

    private String getDailyReportGenerationFailureMessage(String reportName, String type) {
        return  reportName + " report failed due to Data copy issue (ETL failure).";
    }

    private void addSuccessfulEtlNotifications(List<Notice> notices, List<String> failedEtlJobs) {
        if (failedEtlJobs.isEmpty()) {
            java.sql.Date yesterdayDate = getYesterdayDate();
            if (!noticeDao.findByNoticeTypeAndDate("ETL", yesterdayDate, "FAILED").isEmpty()) {
                String successMessage = "DATA Copied successfully : All reports are available now";
                java.sql.Date todayDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());
                if (noticeDao.findByNoticeTypeAndDate("ETL", todayDate, "PASSED").isEmpty()) {
                    Notice successNotice = new Notice("ETL", new Date(), successMessage, "PASSED");
                    notices.add(successNotice);
                }
            }
        }
    }

    private List<ScheduledReportTracker> fetchMonthlyGeneratedReports(Date date) {
        try {
            return scheduledReportTrackerDao.findByForMonth(date.getMonth() + "_" + (date.getYear() % 100));
        } catch (Exception e) {
            LOGGER.error("Exception while fetching generated reports for the month: ", e);
            return Collections.emptyList();
        }
    }

    private void addReportNotifications(List<Notice> notices, List<String> reports, String reportType) {
        if (!reports.isEmpty()) {
            for (String report : reports) {
                List<Notice> noticesForReport = noticeDao.findByNoticeTypeAndMessageAndYear(reportType, Calendar.getInstance().get(Calendar.YEAR), report);
                if (noticesForReport.isEmpty()) {
                    Notice reportNotice = new Notice(reportType, new Date(), report, null);
                    notices.add(reportNotice);
                }
            }
        }
    }

    private void addGeneratedReportNotifications(List<Notice> notices,  List<String> reports, String reportType) {
        List<String> generatedReports = finalNoticeReportsInfo(reports, reportType);
        addReportNotifications(notices, generatedReports, reportType);
    }

    @Override
    @Transactional
    public boolean scheduledNotification() {
        if (isAutoGenerate()) {
            try {
                jmsTemplate.convertAndSend("etl-notification", "PROCESS_DAILY_NOTIFICATIONS");
                LOGGER.info("Successfully sent PROCESS_DAILY_NOTIFICATIONS to etl-notification queue.");
            } catch (Exception e) {
                LOGGER.error("Error occurred while sending message to etl-notification queue: ", e);
                return false;
            }
            return true;
        }
        LOGGER.info("Auto-generation is disabled. Skipping scheduled notification.");
        return false;
    }


    private java.sql.Date getYesterdayDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        return new java.sql.Date(calendar.getTime().getTime());
    }

    private List<String> getStrings(Date date, List<ScheduledReportTracker> generatedReportsForMonth) {
        List <String> reportsTracker = new ArrayList<>();
        Set<String> maReports = new HashSet<>();
        maReports.add(ReportType.maCourse.getReportName().trim());
        maReports.add(ReportType.maAnonymous.getReportName().trim());
        maReports.add(ReportType.maInactive.getReportName().trim());

        if(generatedReportsForMonth != null){
        for (ScheduledReportTracker scheduledReportTracker : generatedReportsForMonth){
            String reportType = maReports.contains(scheduledReportTracker.getReportName().trim()) ? "MA" : "Kilkari";
            String notificationLine = reportType + " Monthly line-list " + scheduledReportTracker.getReportName() + " for the " + getMonthName(date) + " generated" ;
            reportsTracker.add(notificationLine);
        }
        }
        return reportsTracker;
    }

    private String getMonthName(Date date){
        String[] monthNames = new String[] {
                "January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"
        };
// Get full month name
        return monthNames[date.getMonth()-1];
    }

    public String getQuarterName(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int month = calendar.get(Calendar.MONTH);

        String quarter;
        if (month <= Calendar.MARCH) {
            quarter = "Q1";
        } else if (month <= Calendar.JUNE) {
            quarter = "Q2";
        } else if (month <= Calendar.SEPTEMBER) {
            quarter = "Q3";
        } else {
            quarter = "Q4";
        }

        return quarter;
    }

    private List<String> finalNoticeReportsInfo(List<String> reports, String noticeType){
        Date date = new Date();
        List<String> reportGenerated = new ArrayList<String>();
        List<EtlInfoTable> etlInfo = fetchEtlInfoByNoticeType(reports, noticeType);

        if (etlInfo.isEmpty()) {
            LOGGER.info("{} Reports not generated yet", getReportTypeDescription(noticeType));
            return reportGenerated;
        }

        LOGGER.info("{} Reports successfully generated: {}", getReportTypeDescription(noticeType), etlInfo);

        for (EtlInfoTable etlInfoTable : etlInfo) {
            reportGenerated.add(generateReportMessage(etlInfoTable, noticeType, date));
        }
        return reportGenerated;
    }

    private List<EtlInfoTable> fetchEtlInfoByNoticeType(List<String> reports, String noticeType) {
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH) + 1;

        switch (noticeType) {
            case getNoticeTypeAsMonthlyReports:
                return etlInfoDAO.fetchMonthlyReportsByJobNames(reports, month);
            case getNoticeTypeAsQuarterReports:
                return etlInfoDAO.fetchQuarterlyReportsByJobName(reports);
            case getNoticeTypeAsYearlyReports:
                return etlInfoDAO.fetchYearlyReportsByJobNames(reports);
            case getNoticeTypeAsFinancialYearReports:
                return etlInfoDAO.fetchYearlyReportsByJobNames(reports);
            default:
                return Collections.emptyList();
        }
    }

    private String getReportTypeDescription(String noticeType) {
        switch (noticeType) {
            case getNoticeTypeAsMonthlyReports:
                return "Monthly";
            case getNoticeTypeAsQuarterReports:
                return "Quarterly";
            case getNoticeTypeAsYearlyReports:
                return "Yearly";
            case getNoticeTypeAsFinancialYearReports:
                return "Financial Year";
            default:
                return "Unknown";
        }
    }

    private String generateReportMessage(EtlInfoTable etlInfoTable, String noticeType, Date date) {
        switch (noticeType) {
            case getNoticeTypeAsMonthlyReports:
                return "Kilkari Monthly " + EtlJobForNotifications.getReportNameByJobName(etlInfoTable.getEtlJob()) + " report for " + getMonthName(date) + " generated";
            case getNoticeTypeAsQuarterReports:
                return "Kilkari Quarterly " + EtlJobForNotifications.getReportNameByJobName(etlInfoTable.getEtlJob()) + " report for " + getQuarterName(date) + " generated";
            case getNoticeTypeAsYearlyReports:
                return "Kilkari Calendar Year " + EtlJobForNotifications.getReportNameByJobName(etlInfoTable.getEtlJob()) + " report generated";
            case getNoticeTypeAsFinancialYearReports:
                return "Kilkari Financial Year " + EtlJobForNotifications.getReportNameByJobName(etlInfoTable.getEtlJob()) + " report generated";
            default:
                return "Unknown report type";
        }
    }
        
}
