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

    maCourse("MA_Cumulative_Course_Completion", "Cumulative Course Completion", "M"),
    maAnonymous("MA_Anonymous_Users", "Circle wise Anonymous Users", "M"),
    maInactive("MA_Cumulative_Inactive_Users", "Cumulative Inactive Users", "M"),
    lowUsage("Kilkari_Low_Usage", "Listen to < 25% this month", "K"),
    selfDeactivated("Kilkari_Self_Deactivated", "Self Deactivations", "K"),
    sixWeeks("Kilkari_Six_Weeks_No_Answer", "Deactivated for not answering", "K"),
    flwRejected("MA_Flw_Import_Rejects", "Flw Rejected Records", "M"),
    childRejected("Kilkari_Child_Import_Rejects", "Child Rejected Records", "K"),
    motherRejected("Kilkari_Mother_Import_Rejects", "Mother Rejected Records", "K"),
    lowListenership("Kilkari_Low_Listenership_Deactivation", "Deactivated for Low Listenership", "K");

    private String reportType;
    private String reportName;
    private String serviceType;

    ReportType(String reportType, String reportName, String serviceType) {
        this.reportType = reportType;
        this.reportName = reportName;
        this.serviceType = serviceType;
    }

    public static ReportType getType(String test) {
        for (ReportType type : ReportType.values()) {
            if (type.reportType.equalsIgnoreCase(test)) {
                return type;
            }
        }
        return null;
    }

    public String getReportType() {
        return reportType;
    }

    public String getReportName() {
        return reportName;
    }

    public String getServiceType() {
        return serviceType;
    }


}
