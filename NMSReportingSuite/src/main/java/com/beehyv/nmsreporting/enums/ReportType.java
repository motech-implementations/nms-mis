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

    maCourse("CumulativeCourseCompletion", "Cumulative Completion Report", "M"),
    maAnonymous("AnonymousUsers", "Circle wise Anonymous Reports", "M"),
    maInactive("CumulativeInactiveUsers", "Cumulative Inactive Users", "M"),
    lowUsage("KilkariLowUsage", "Listen to < 25% this month", "K"),
    selfDeactivated("KilkariSelfDeactivated", "Deactivation for not answering", "K"),
    sixWeeks("KilkariSixWeeksNoAnswer", "Self Deactivations", "K");

    private String reportType;
    private String reportName;
    private String serviceType;

    public String getReportType() {
        return reportType;
    }
    public String getReportName(){
        return reportName;
    }
    public String getServiceType(){
        return serviceType;
    }

    ReportType(String reportType, String reportName, String serviceType) {
        this.reportType = reportType;
        this.reportName = reportName;
        this.serviceType = serviceType;
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
