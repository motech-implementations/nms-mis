package com.beehyv.nmsreporting.enums;

/**
 * Created by beehyv on 24/5/17.
 */
public enum ReportType {

//    maCourse("CumulativeCourseCompletion"),
//    maAnonymous("Circle wise Anonymous Users"),
//    maInactive("maInactive"),
//    lowUsage("KillowUsage"),
//    selfDeactivated("selfDeactivated"),
//    sixWeeks("sixWeeks");
    messageMatrix("Kilkari_message_matrix","Kilkari Message Matrix","K","Kilkari Message Matrix Report"),
    kilkariCalls("Kilkari_Call","Kilkari Call","K","Kilkari Call Report"),
    listeningMatrix("Kilkari_listening_matrix","Kilkari Listening Matrix","K","Kilkari Listening Matrix Report"),
    beneficiary("Kilkari_Beneficiary","Kilkari Beneficiary","K","Kilkari Aggregate Beneficiary Report"),
    usage("Kilkari_usage","Kilkari Usage","K","Kilkari Usage Report"),
    beneficiaryCompletion("Kilkari_Beneficiary_Completion","Kilkari Beneficiary Completion","K","Kilkari Beneficiary Completion Report"),
    kilkariCumulative("Kilkari_Cumulative_Summary","Kilkari Cumulative Summary","K","Kilkari Cumulative Summery Aggregate Report"),
    maPerformance("MA_Performance","MA Performance","M","MA Performance Aggregate Report"),
    maSubscriber("MA_Subscriber","MA Subscriber","M","MA Subscriber Aggregate Report"),
    maCumulative("MA_Cumulative_Summary","MA Cumulative Summary","M","MA Cumulative Summery Aggregate Report"),
    maCourse("MA_Cumulative_Course_Completion", "Cumulative Course Completion", "M","MA Completion Line-Listing Report"),
    maAnonymous("MA_Anonymous_Users", "Circle wise Anonymous Users", "M", "MA Circle Wise Anonymous Line-Listing Report"),
    maInactive("MA_Cumulative_Inactive_Users", "Cumulative Inactive Users", "M", "MA Inactive Users Line-Listing Report"),
    lowUsage("Kilkari_Low_Usage", "Listen to < 25% this month", "K","Kilkari Deactivations for For Low Listening Report"),
    selfDeactivated("Kilkari_Self_Deactivated", "Self Deactivations", "K","Kilkari Self Deactivation Report"),
    sixWeeks("Kilkari_Six_Weeks_No_Answer", "Deactivated for not answering", "K","Kilkari Deactivations for not answering Report"),
    flwRejected("MA_Asha_Import_Rejects","Asha Rejected Records", "M", "MA Rejected Line-Listing Report"),
    childRejected("Kilkari_Child_Import_Rejects","Child Rejected Records", "K","Kilkari Rejected Child Line Listing Report"),
    motherRejected("Kilkari_Mother_Import_Rejects","Mother Rejected Records", "K" ,"Kilkari Rejected Mother Line Listing Report"),
    lowListenership("Kilkari_Low_Listenership_Deactivation", "Deactivated for Low Listenership","K",
            "Kilkari Deactivations for For Low Listening Report"),
    kilkariSubscriber("Kilkari_Subscriber","Kilkari Subscriber","K","Kilkari Subscriber Report");

    private String reportType;
    private String reportName;
    private String serviceType;
    private String reportHeader;

    public String getReportType() {
        return reportType;
    }
    public String getReportName(){
        return reportName;
    }
    public String getServiceType(){
        return serviceType;
    }
    public String getReportHeader() {
        return reportHeader;
    }

    ReportType(String reportType, String reportName, String serviceType, String reportHeader) {
        this.reportType = reportType;
        this.reportName = reportName;
        this.serviceType = serviceType;
        this.reportHeader=reportHeader;
    }

    public static ReportType getType(String test){
        for (ReportType type: ReportType.values()) {
            if(type.reportType.equalsIgnoreCase(test)){
                return type;
            }
        }
        return null;
    }


}
