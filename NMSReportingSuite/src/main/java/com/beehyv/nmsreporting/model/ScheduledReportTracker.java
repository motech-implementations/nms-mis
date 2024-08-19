package com.beehyv.nmsreporting.model;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "ScheduledReportTracker")
public class ScheduledReportTracker {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "date")
    private Date creationDate;

    public ScheduledReportTracker() {
    }

    @Column
    private String reportName;

    @Column
    private String forMonth;

    public int getId() {
        return id;
    }

    public ScheduledReportTracker(String reportName, Date creationDate, String forMonth) {
        this.reportName = reportName;
        this.creationDate = creationDate;
        this.forMonth = forMonth;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public String getForMonth() {
        return forMonth;
    }

    public void setForMonth(String forMonth) {
        this.forMonth = forMonth;
    }
}
