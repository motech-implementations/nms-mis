package com.beehyv.nmsreporting.enums;

public enum EtlJobForNotifications {
    Beneficiary(" Beneficiary", "DAILY", "ETL"),
    BeneficiaryTracker(" BeneficiaryTracker", "DAILY", "ETL"),
    Subscription(" Subscription", "DAILY", "ETL"),
    Beneficiary_Call_Measure_Inbox(" Beneficiary Call Measure Inbox", "DAILY", "ETL"),
    // Kilkari_self_deactivated(" Kilkari self deactivated", "DAILY", "ETL"),
    Kilkari_manual_deactivations(" Kilkari manual deactivations", "DAILY", "ETL"),
    Mother_Rejections(" Mother Rejections", "DAILY", "ETL"),
    Child_Rejections(" Child Rejections", "DAILY", "ETL"),
    Reactivated_Beneficiaries(" Reactivated Beneficiaries", "DAILY", "ETL"),
    nms_imi_cdrs(" nms imi cdrs", "DAILY", "ETL"),
    KilkariCallReport(" KilkariCallReport", "DAILY", "ETL"),
    KilkariSubscriber(" KilkariSubscriber", "DAILY", "ETL"),
    FLW(" FLW", "DAILY", "ETL"),
    MA_call_detail(" MA call detail", "DAILY", "ETL"),
    MA_call_content(" MA call content", "DAILY", "ETL"),
    MA_course_completion(" MA course completion", "DAILY", "ETL"),
    FLW_Sim_Changes(" FLW Sim Changes", "DAILY", "ETL"),
    MA_Anonymous_Users(" MA Anonymous Users", "DAILY", "ETL"),
    FLW_Rejections(" FLW Rejections", "DAILY", "ETL"),
    Mobile_Academy_Aggregate_Reports(" Mobile Academy Aggregate Reports", "DAILY", "ETL"),
    MA_Anonymous_User_Reports(" MAAnonymousUsersReportMonthly", "Circle wise Anonymous Users","MONTHLY", "REPORTS"),
    MA_ASHA_First_Course_Completions(" MA ASHA First Course Completions", "Course Completion", "MONTHLY", "REPORTS"),
    Kilkari_Low_Usage(" Kilkari Low Usage", "Listen to < 25% this month","MONTHLY", "REPORTS"),
    Kilkari_Repeat_Listener(" KilkariRepeatListenerMonthWise", "Kilkari Repeat Listener","MONTHLY", "REPORTS"),
    Kilkari_Aggregate_Beneficiaries_Monthly(" KilkariAggregateBeneficiariesMonthly","Kilkari Aggregate Beneficiaries", "MONTHLY", "REPORTS"),
    Kilkari_Aggregate_Beneficiaries_Quarter(" KilkariAggregateBeneficiariesQuarter","Kilkari Aggregate Beneficiaries", "QUARTERLY", "REPORTS"),
    Kilkari_Aggregate_Beneficiaries_Year(" KilkariAggregateBeneficiariesYear","Kilkari Aggregate Beneficiaries", "YEARLY", "REPORTS"),
    Kilkari_Aggregate_Beneficiaries_Financial_Year(" KilkariAggregateBeneficiariesFinancialYear","Kilkari Aggregate Beneficiaries", "FINANCIALYEAR", "REPORTS"),
    Kilkari_Beneficiary_Completion_Monthly(" KilkariBeneficiaryCompletionMonthly", "Kilkari Beneficiary Completion","MONTHLY", "REPORTS"),
    Kilkari_Beneficiary_Completion_Quarter(" KilkariBeneficiaryCompletionQuarter", "Kilkari Beneficiary Completion","QUARTERLY", "REPORTS"),
    Kilkari_Beneficiary_Completion_Year(" KilkariBeneficiaryCompletionYear", "Kilkari Beneficiary Completion","YEARLY", "REPORTS"),
    Kilkari_Beneficiary_Completion_FinancialYear(" KilkariBeneficiaryCompletionFinancialYear", "Kilkari Beneficiary Completion","FINANCIALYEAR", "REPORTS"),
    Kilkari_Listening_Matrix_Monthly(" KilkariListeningMatrixMonthly", "Kilkari Listening Matrix","MONTHLY", "REPORTS"),
    Kilkari_Listening_Matrix_Quarter(" KilkariListeningMatrixReportQuarter", "Kilkari Listening Matrix","QUARTERLY", "REPORTS"),
    Kilkari_Listening_Matrix_Year(" KilkariListeningMatrixReportYear", "Kilkari Listening Matrix","YEARLY", "REPORTS"),
    Kilkari_Listening_Matrix_FinancialYear(" KilkariListeningMatrixReportFinancialYear","Kilkari Listening Matrix", "FINANCIALYEAR", "REPORTS"),
    Kilkari_Message_Listenership_Monthly(" KilkariMessageListenershipMonthly", "Kilkari Message Listenership","MONTHLY", "REPORTS"),
    Kilkari_Message_Listenership_Quarter(" KilkariMessageListenershipReportQuarter", "Kilkari Message Listenership","QUARTERLY", "REPORTS"),
    Kilkari_Message_Listenership_Year(" KilkariMessageListenershipReportYear", "Kilkari Message Listenership","YEARLY", "REPORTS"),
    Kilkari_Message_Listenership_FinancialYear(" KilkariMessageListenershipReportFinancialYear", "Kilkari Message Listenership","FINANCIALYEAR", "REPORTS"),
    Kilkari_Message_Matrix_Monthly(" KilkariMessageMatrixMonthly", "Kilkari Message Matrix","MONTHLY", "REPORTS"),
    Kilkari_Message_Matrix_Quarter(" KilkariMessageMatrixReportQuarter", "Kilkari Message Matrix","QUARTERLY", "REPORTS"),
    Kilkari_Message_Matrix_Year(" KilkariMessageMatrixReportYear", "Kilkari Message Matrix","YEARLY", "REPORTS"),
    Kilkari_Message_Matrix_FinancialYear(" KilkariMessageMatrixReportFinancialYear", "Kilkari Message Matrix","FINANCIALYEAR", "REPORTS"),
    Kilkari_Usage_Report_Monthly(" KilkariUsageReportMonthly", "Kilkari Usage","MONTHLY", "REPORTS"),
    Kilkari_Usage_Report_Quarter(" KilkariUsageReportQuarter", "Kilkari Usage","QUARTERLY", "REPORTS"),
    Kilkari_Usage_Report_Year(" KilkariUsageReportYear", "Kilkari Usage","YEARLY", "REPORTS"),
    Kilkari_Usage_Report_FinancialYear(" KilkariUsageReportFinancialYear", "Kilkari Usage","FINANCIALYEAR", "REPORTS"),
    Kilkari_Usage_Mother_Report_Monthly(" KilkariUsageMotherReportMonthly", "Kilkari Usage Mother","MONTHLY", "REPORTS"),
    Kilkari_Usage_Mother_Report_Quarter(" KilkariUsageMotherReportQuarter", "Kilkari Usage Mother","QUARTERLY", "REPORTS"),
    Kilkari_Usage_Mother_Report_Year(" KilkariUsageMotherReportYear", "Kilkari Usage Mother","YEARLY", "REPORTS"),
    Kilkari_Usage_Mother_Report_FinancialYear(" KilkariUsageMotherReportFinancialYear", "Kilkari Usage Mother","FINANCIALYEAR", "REPORTS"),
    Kilkari_Usage_Child_Report_Monthly(" KilkariUsageChildReportMonthly", "Kilkari Usage Child","MONTHLY", "REPORTS"),
    Kilkari_Usage_Child_Report_Quarter(" KilkariUsageChildReportQuarter", "Kilkari Usage Child","QUARTERLY", "REPORTS"),
    Kilkari_Usage_Child_Report_Year(" KilkariUsageChildReportYear", "Kilkari Usage Child","YEARLY", "REPORTS"),
    Kilkari_Usage_Child_Report_FinancialYear(" KilkariUsageChildReportFinancialYear", "Kilkari Usage Child","FINANCIALYEAR", "REPORTS"),
    Kilkari_Thematic_Content_Monthly(" KilkariThematicContentMonthly", "Kilkari Thematic Content","MONTHLY", "REPORTS"),
    Kilkari_Thematic_Content_Quarter(" KilkariThematicContentQuarter", "Kilkari Thematic Content","QUARTERLY", "REPORTS"),
    Kilkari_Thematic_Content_Year(" KilkariThematicContentYear", "Kilkari Thematic Content","YEARLY", "REPORTS"),
    Kilkari_Thematic_Content_FinancialYear(" KilkariThematicContentFinancialYear", "Kilkari Thematic Content","FINANCIALYEAR", "REPORTS");

    private final String jobName;
    private final String schedule;
    private final String jobType;
    private final String reportName;

    public String getJobName() {
        return jobName;
    }

    EtlJobForNotifications(String jobName, String reportName, String schedule, String jobType) {
        this.jobName = jobName;
        this.reportName = reportName;
        this.schedule = schedule;
        this.jobType = jobType;
    }

    public String getSchedule() {return schedule;}

    public String getJobType() {return jobType;}

    public String getReportName() {return reportName;}

    EtlJobForNotifications(String jobName, String schedule, String jobType) {
        this.jobName = jobName;
        this.schedule = schedule;
        this.jobType = jobType;
        this.reportName = null;
    }
    public static String getReportNameByJobName(String jobName) {
        for (EtlJobForNotifications etlJobForNotifications : EtlJobForNotifications.values()) {
            if (etlJobForNotifications.getJobName().equals(jobName)) {
                return etlJobForNotifications.getReportName();
            }
        }
        return null;  // Return null if no match is found
    }
}
