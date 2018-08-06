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
    messageMatrix("Kilkari_Message_Matrix","Kilkari Message Matrix","KILKARI","Kilkari Message Matrix Report"),
    kilkariCalls("Kilkari_Call","Kilkari Call","KILKARI","Kilkari Call Report"),
    listeningMatrix("Kilkari_Listening_Matrix","Kilkari Listening Matrix","KILKARI","Kilkari Listening Matrix Report"),
    beneficiary("Kilkari_Aggregate_Beneficiaries","Kilkari Aggregate Beneficiaries","KILKARI","Kilkari Aggregate Beneficiaries Report"),
    usage("Kilkari_Usage","Kilkari Usage","KILKARI","Kilkari Usage Report"),
    beneficiaryCompletion("Kilkari_Beneficiary_Completion","Kilkari Beneficiary Completion","KILKARI","Kilkari Beneficiary Completion Report"),
    kilkariCumulative("Kilkari_Cumulative_Summary","Kilkari Cumulative Summary","KILKARI","Kilkari Cumulative Summery Aggregate Report"),
    maPerformance("MA_Performance","MA Performance","MOBILE_ACADEMY","MA Performance Aggregate Report"),
    maSubscriber("MA_Subscriber","MA Subscriber","MOBILE_ACADEMY","MA Subscriber Aggregate Report"),
    maCumulative("MA_Cumulative_Summary","MA Cumulative Summary","MOBILE_ACADEMY","MA Cumulative Summery Aggregate Report"),
    maCourse("MA_Cumulative_Course_Completion", "Cumulative Course Completion", "MOBILE_ACADEMY","MA Completion Line-Listing Report"),
    maAnonymous("MA_Anonymous_Users", "Circle wise Anonymous Users", "MOBILE_ACADEMY", "MA Circle Wise Anonymous Line-Listing Report"),
    maInactive("MA_Cumulative_Inactive_Users", "Cumulative Inactive Users", "MOBILE_ACADEMY", "MA Inactive Users Line-Listing Report"),
    lowUsage("Kilkari_Low_Usage", "Listen to < 25% this month", "KILKARI","Kilkari Low Listenership Line Listing Report"),
    selfDeactivated("Kilkari_Self_Deactivated", "Self Deactivations", "KILKARI","Kilkari Self–deactivation line listing Report"),
    sixWeeks("Kilkari_Six_Weeks_No_Answer", "Deactivated for not answering", "KILKARI","Kilkari Deactivations for not answering Report"),
    flwRejected("MA_Asha_Import_Rejects","Asha Rejected Records", "MOBILE_ACADEMY", "MA Rejected Line-Listing Report"),
    childRejected("Kilkari_Child_Import_Rejects","Child Rejected Records", "KILKARI","Kilkari Rejected Child Line Listing Report"),
    motherRejected("Kilkari_Mother_Import_Rejects","Mother Rejected Records", "KILKARI" ,"Kilkari Rejected Mother Line Listing Report"),
    lowListenership("Kilkari_Low_Listenership_Deactivation", "Deactivated for Low Listenership","KILKARI",
            "Kilkari Deactivations for For Low Listening Report"),
    kilkariSubscriber("Kilkari_Subscriber","Kilkari Subscriber","KILKARI","Kilkari Subscriber Report"),
    kilkariMessageListenership("Kilkari_Message_Listenership","Kilkari Message Listenership","KILKARI","Kilkari Message Listenership Report"),
    kilkariThematicContent("Kilkari_Thematic_Content","Kilkari Thematic Content","KILKARI","Kilkari Thematic Content Report"),
    kilkariRepeatListenerMonthWise("Kilkari_Repeat_Listener_Month_Wise","Kilkari Repeat Listener","KILKARI","Kilkari_Repeat_Listener_Month_Wise Report");

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
