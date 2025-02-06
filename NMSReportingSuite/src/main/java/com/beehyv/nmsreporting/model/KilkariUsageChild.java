package com.beehyv.nmsreporting.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by beehyv on 27/01/25.
 */
@Entity
@Table(name="agg_kilkari_usage_child")
public class KilkariUsageChild {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id", columnDefinition = "INT(11)")
    private Integer id;

    @Column(name="location_type", columnDefinition = "VARCHAR(45)")
    private String locationType;

    @Column(name="location_id", columnDefinition = "BIGINT(20)")
    private Long locationId;

    @Column(name="date", columnDefinition = "DATE")
    private Date date;

    @Column(name="content_listened_more_than_75_per", columnDefinition = "BIGINT(20)")
    private Long calls_75_100;

    @Column(name="content_listened_50_to_75_per", columnDefinition = "BIGINT(20)")
    private Long calls_50_75;

    @Column(name="content_listened_25_to_50_per", columnDefinition = "BIGINT(20)")
    private Long calls_25_50;

    @Column(name="content_listened_1_to_25_per", columnDefinition = "BIGINT(20)")
    private Long calls_1_25;

    @Column(name="called_inbox",columnDefinition = "BIGINT(20)")
    private Long calledInbox;

    @Column(name="answered_atleast_one_call", columnDefinition = "BIGINT(20)")
    private Long answeredAtleastOneCall;

    @Column(name="total_beneficiaries_called", columnDefinition = "BIGINT(20)")
    private Long totalBeneficiariesCalled;

    @Column(name="period_type", columnDefinition = "VARCHAR(45)")
    private String periodType;


    public KilkariUsageChild(Integer id, String locationType, Long locationId, Date date, Long calls_75_100, Long calls_50_75, Long calls_25_50, Long calls_1_25, Long calledInbox, Long answeredAtleastOneCall, Long totalBeneficiariesCalled, String periodType) {
        this.id = id;
        this.locationType = locationType;
        this.locationId = locationId;
        this.date = date;
        this.calls_75_100 = calls_75_100;
        this.calls_50_75 = calls_50_75;
        this.calls_25_50 = calls_25_50;
        this.calls_1_25 = calls_1_25;
        this.calledInbox = calledInbox;
        this.answeredAtleastOneCall = answeredAtleastOneCall;
        this.totalBeneficiariesCalled = totalBeneficiariesCalled;
        this.periodType = periodType;
    }

    public KilkariUsageChild(){

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLocationType() {
        return locationType;
    }

    public void setLocationType(String locationType) {
        this.locationType = locationType;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getCalls_75_100() {
        return calls_75_100;
    }

    public void setCalls_75_100(Long calls_75_100) {
        this.calls_75_100 = calls_75_100;
    }

    public Long getCalls_50_75() {
        return calls_50_75;
    }

    public void setCalls_50_75(Long calls_50_75) {
        this.calls_50_75 = calls_50_75;
    }

    public Long getCalls_25_50() {
        return calls_25_50;
    }

    public void setCalls_25_50(Long calls_25_50) {
        this.calls_25_50 = calls_25_50;
    }

    public Long getCalls_1_25() {
        return calls_1_25;
    }

    public void setCalls_1_25(Long calls_1_25) {
        this.calls_1_25 = calls_1_25;
    }

    public Long getCalledInbox() {
        return calledInbox;
    }

    public void setCalledInbox(Long calledInbox) {
        this.calledInbox = calledInbox;
    }

    public Long getAnsweredAtleastOneCall(){
        return answeredAtleastOneCall;
    }

    public void setAnsweredAtleastOneCall(Long answeredAtleastOneCall) {
        this.answeredAtleastOneCall = answeredAtleastOneCall;
    }

    public Long getTotalBeneficiariesCalled() {
        return totalBeneficiariesCalled;
    }

    public void setTotalBeneficiariesCalled(Long totalBeneficiariesCalled) {
        this.totalBeneficiariesCalled = totalBeneficiariesCalled;
    }

    public String getPeriodType() {
        return periodType;
    }

    public void setPeriodType(String periodType) {
        this.periodType = periodType;
    }
}
