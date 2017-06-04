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

    maCourse("CumulativeCourseCompletion","M"),
    maAnonymous("AnonymousUsers", "M"),
    maInactive("CumulativeInactiveUsers", "M"),
    lowUsage("KilkariLowUsage", "K"),
    selfDeactivated("KilkariSelfDeactivated", "K"),
    sixWeeks("KilkariSixWeeksNoAnswer", "K");

    private String reportType;

    private String serviceType;

    public String getReportType() {
        return reportType;
    }

    public String getServiceType(){ return serviceType;}

    ReportType(String reportType, String serviceType) {
        this.reportType = reportType;
        this.serviceType = serviceType;
    }

    public static ReportType getType(String test){
        for (ReportType type: ReportType.values()) {
            if(type.reportType.equalsIgnoreCase(test)){
                return type;
            };
        }
        return null;
    }


}
