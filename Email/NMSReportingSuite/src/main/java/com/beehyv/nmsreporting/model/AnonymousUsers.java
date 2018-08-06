package com.beehyv.nmsreporting.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by beehyv on 16/5/17.
 */
@Entity
@Table(name="ma_anonymous_users_monthly")
public class AnonymousUsers {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id", columnDefinition = "INT(11)")
    private Integer anonymousId;

    @Column(name="circle_name", columnDefinition = "VARCHAR(45)")
    private String circleName;

    @Column(name="callingNumber", columnDefinition = "BIGINT(20)")
    private Long msisdn;

    @Column(name="LastCalledDate", columnDefinition = "DATE")
    private Date lastCalledDate;

    @Column(name="for_month", columnDefinition = "VARCHAR(45)")
    private String forMonth;

    public Integer getAnonymousId() {
        return anonymousId;
    }

    public void setAnonymousId(Integer anonymousId) {
        this.anonymousId = anonymousId;
    }

    public String getCircleName() {
        return circleName;
    }

    public void setCircleName(String circleName) {
        this.circleName = circleName;
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

    public String getForMonth() {
        return forMonth;
    }

    public void setForMonth(String forMonth) {
        this.forMonth = forMonth;
    }
}
