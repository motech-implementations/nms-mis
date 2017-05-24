package com.beehyv.nmsreporting.enums;

/**
 * Created by beehyv on 24/5/17.
 */
public enum ReportType {

    maCourse("maCourse"),
    maAnonymous("maAnonymous"),
    maInactive("maInactive"),
    lowUsage("lowUsage"),
    selfDeactivated("selfDeactivated"),
    sixWeeks("sixWeeks");

    private String reportType;

    public String getReportType() {
        return reportType;
    }

    ReportType(String reportType) {
        this.reportType = reportType;
    }



}
