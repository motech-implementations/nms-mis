package com.beehyv.nmsreporting.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by beehyv on 16/5/17.
 */
@Entity
@Table(name="ma_anonymous_users")
public class AnonymousUsers {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id", columnDefinition = "INT(11)")
    private Integer anonymousId;

    @Column(name="week_id", columnDefinition = "SMALLINT(6)")
    private Integer weekId;

    @Column(name="circle_id", columnDefinition = "TINYINT(4)")
    private Integer circleId;

    @Column(name="msisdn", columnDefinition = "BIGINT(20)")
    private Long msisdn;

    @Column(name="last_called_date", columnDefinition = "DATE")
    private Date lastCalledDate;

    @Column(name="last_called_time", columnDefinition = "DATETIME")
    private Timestamp deactivationTime;

    @Column(name="last_modified", columnDefinition = "DATETIME")
    private Timestamp lastModified;

    public Integer getAnonymousId() {
        return anonymousId;
    }

    public void setAnonymousId(Integer anonymousId) {
        this.anonymousId = anonymousId;
    }

    public Integer getWeekId() {
        return weekId;
    }

    public void setWeekId(Integer weekId) {
        this.weekId = weekId;
    }

    public Integer getCircleId() {
        return circleId;
    }

    public void setCircleId(Integer circleId) {
        this.circleId = circleId;
    }

    public Long getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(Long msisdn) {
        this.msisdn = msisdn;
    }

    public Date getLastCalledDate() {
        return lastCalledDate;
    }

    public void setLastCalledDate(Date lastCalledDate) {
        this.lastCalledDate = lastCalledDate;
    }

    public Timestamp getDeactivationTime() {
        return deactivationTime;
    }

    public void setDeactivationTime(Timestamp deactivationTime) {
        this.deactivationTime = deactivationTime;
    }

    public Timestamp getLastModified() {
        return lastModified;
    }

    public void setLastModified(Timestamp lastModified) {
        this.lastModified = lastModified;
    }
}
