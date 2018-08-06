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
            "Kilkari Deactivations for For Low Listening Report");

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
