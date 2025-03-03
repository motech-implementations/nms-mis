package com.beehyv.nmsreporting.entity;

import java.io.Serializable;
import java.util.Date;


public class ReportMessage implements Serializable {
    private static final long serialVersionUID = 1L;
    private String reportType;
    private Date fromDate;
    private Date toDate;
    private boolean isWeekly;

    public ReportMessage() {}

    public ReportMessage(String reportType, Date fromDate, Date toDate, boolean isWeekly) {
        this.reportType = reportType;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.isWeekly = isWeekly;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public boolean isWeekly() {
        return isWeekly;
    }

    public void setWeekly(boolean weekly) {
        isWeekly = weekly;
    }
}
