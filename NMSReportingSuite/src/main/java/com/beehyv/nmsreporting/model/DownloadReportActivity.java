package com.beehyv.nmsreporting.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="download_report_activity")
public class DownloadReportActivity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private long id;

    @Column(name="user_id")
    private Integer userId;

    @Column(name="username")
    private String userName;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getReportName() {
        return reportName;
    }

    public Date getDownloadTime() {
        return downloadTime;
    }

    public void setDownloadTime(Date downloadTime) {
        this.downloadTime = downloadTime;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    @Column(name="report_name")
    private String reportName;

    @Column(name="download_time")
    private Date downloadTime;
}
