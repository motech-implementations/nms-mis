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

    maCourse("CumulativeCourseCompletion"),
    maAnonymous("AnonymousUsers"),
    maInactive("CumulativeInactiveUsers"),
    lowUsage("KilkariLowUsage"),
    selfDeactivated("KilkariSelfDeactivated"),
    sixWeeks("KilkariSixWeeksNoAnswer");

    private String reportType;

    public String getReportType() {
        return reportType;
    }

    ReportType(String reportType) {
        this.reportType = reportType;
    }



}
