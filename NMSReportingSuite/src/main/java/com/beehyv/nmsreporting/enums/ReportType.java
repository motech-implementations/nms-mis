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
    messageMatrix("Kilkari_Message_Matrix","Kilkari Message Matrix","KILKARI","Kilkari Message Matrix Report", "Message Matrix"),
    kilkariCalls("Kilkari_Call","Kilkari Call","KILKARI","Kilkari Call Report", "Call Report"),
    kilkariCallsWithBeneficiaries("District-wise Performance of the State for Kilkari","District-wise Performance of the State for Kilkari","KILKARI","District-wise Performance of the State for Kilkari", "District-wise Performance of the State for Kilkari"),
    listeningMatrix("Kilkari_Listening_Matrix","Kilkari Listening Matrix","KILKARI","Kilkari Listening Matrix Report", "Listening Matrix"),
    beneficiary("Kilkari_Aggregate_Beneficiaries","Kilkari Aggregate Beneficiaries","KILKARI","Kilkari Aggregate Beneficiaries Report", "Aggregate Beneficiaries"),
    usage("Kilkari_Usage","Kilkari Usage","KILKARI","Kilkari Usage Report", "Usage Report"),
    beneficiaryCompletion("Kilkari_Beneficiary_Completion","Kilkari Beneficiary Completion","KILKARI","Kilkari Beneficiary Completion Report", "Beneficiary Completion"),
    kilkariCumulative("Kilkari_Cumulative_Summary","Kilkari Cumulative Summary","KILKARI","Kilkari Cumulative Summary Aggregate Report", "Cumulative Summary"),
    maPerformance("MA_Performance","MA Performance","MOBILE_ACADEMY","MA Performance Aggregate Report", "Performance Report"),
    maSubscriber("MA_Subscriber","MA Subscriber","MOBILE_ACADEMY","MA Subscriber Aggregate Report", "Subscriber Report"),
    maCumulative("District-wise Performance of the State for Mobile Academy","District-wise Performance of the State for Mobile Academy","MOBILE_ACADEMY","District-wise Performance of the State for Mobile Academy", "District-wise Performance of the State for Mobile Academy"),
    maCourse("MA_Cumulative_Course_Completion", "Course Completion", "MOBILE_ACADEMY","MA Completion Line-Listing Report", "Course Completion"),
    maAnonymous("MA_Anonymous_Users", "Circle wise Anonymous Users", "MOBILE_ACADEMY", "MA Circle Wise Anonymous Line-Listing Report", "Circle wise Anonymous Users"),
    maInactive("MA_Cumulative_Inactive_Users", "Cumulative Inactive Users", "MOBILE_ACADEMY", "MA Inactive Users Line-Listing Report", "Cumulative Inactive Users"),
    lowUsage("Kilkari_Low_Usage", "Listen to < 25% this month", "KILKARI","Kilkari Low Listenership Line Listing Report", "Listened to < 25% this month"),
    selfDeactivated("Kilkari_Self_Deactivated", "Self Deactivations", "KILKARI","Kilkari Self–deactivation line listing Report", "Self Deactivations"),
    sixWeeks("Kilkari_Six_Weeks_No_Answer", "Deactivated for not answering", "KILKARI","Kilkari Deactivations for not answering Report", "Deactivated for not answering"),
    flwRejected("MA_Asha_Import_Rejects","Asha Rejected Records", "MOBILE_ACADEMY", "MA Rejected Line-Listing Report", "Asha Rejected Records"),
    childRejected("Kilkari_Child_Import_Rejects","Child Rejected Records", "KILKARI","Kilkari Rejected Child Line Listing Report", "Child Rejected Records"),
    motherRejected("Kilkari_Mother_Import_Rejects","Mother Rejected Records", "KILKARI" ,"Kilkari Rejected Mother Line Listing Report", "Mother Rejected Records"),
    lowListenership("Kilkari_Low_Listenership_Deactivation", "Deactivated for Low Listenership","KILKARI",
            "Kilkari Deactivations for For Low Listening Report", "Deactivated for Low Listenership"),
    kilkariSubscriber("Kilkari_Subscriber","Kilkari Subscriber","KILKARI","Kilkari Subscriber Report", "Subscriber Report"),
    kilkariMessageListenership("Kilkari_Message_Listenership","Kilkari Message Listenership","KILKARI","Kilkari Message Listenership Report", "Message Listenership"),
    kilkariThematicContent("Kilkari_Thematic_Content","Kilkari Thematic Content","KILKARI","Kilkari Thematic Content Report", "Thematic Content"),
    kilkariRepeatListenerMonthWise("Kilkari_Repeat_Listener_Month_Wise","Kilkari Repeat Listener","KILKARI","Kilkari_Repeat_Listener_Month_Wise Report", "Repeat Listener");

    private String reportType;
    private String reportName;
    private String serviceType;
    private String reportHeader;
    private String simpleName;

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

    public String getSimpleName() {
        return simpleName;
    }

    ReportType(String reportType, String reportName, String serviceType, String reportHeader, String simpleName) {
        this.reportType = reportType;
        this.reportName = reportName;
        this.serviceType = serviceType;
        this.reportHeader=reportHeader;
        this.simpleName = simpleName;
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
