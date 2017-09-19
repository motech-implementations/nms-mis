package com.beehyv.nmsreporting.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by beehyv on 13/9/17.
 */
@Entity
@Table(name="aggregate_daily_kilkari_counts")
public class AggregateDailyKilkari {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id", columnDefinition = "INT(11)")
    private Integer id;

    @Column(name="location_type", columnDefinition = "VARCHAR(45)")
    private String locationType;

    @Column(name="location_id", columnDefinition = "BIGINT(20)")
    private Long locationId;

    @Column(name="date", columnDefinition = "DATETIME")
    private Date date;

    @Column(name="records_received", columnDefinition = "INT(11)")
    private Integer recordsReceived;

    @Column(name="records_accepted", columnDefinition = "INT(11)")
    private Integer recordsAccepted; // 5.3.2:5, 5.3.2:7, 5.3.3:12

    @Column(name="records_rejected", columnDefinition = "INT(11)")
    private Integer recordsRejected;

    // recordsCompleted = childCompleted + motherCompleted

    @Column(name="self_deactivated", columnDefinition = "INT(11)")
    private Integer selfDeactivated;

    @Column(name="not_answering_deactivations", columnDefinition = "INT(11)")
    private Integer notAnsweringDeactivations;

    @Column(name="low_listenership_deactivations", columnDefinition = "INT(11)")
    private Integer lowListenershipDeactivations;

    @Column(name="system_deactivations", columnDefinition = "INT(11)")
    private Integer systemDeactivations;

    @Column(name="child_completed", columnDefinition = "INT(11)")
    private Integer childCompleted;

    @Column(name="mother_completed", columnDefinition = "INT(11)")
    private Integer motherCompleted;

    @Column(name="called_inbox", columnDefinition = "BIGINT(20)")
    private Long calledInbox; // 5.3.11:10, 5.3.4:9

    @Column(name="calls_attempted", columnDefinition = "BIGINT(20)")
    private Long callsAttempted;

    @Column(name="calls_successful", columnDefinition = "BIGINT(20)")
    private Long callsSuccessful; // can be skipped: calls_1_25 + calls_25_50 + calls_50_75 + calls_75_100

    @Column(name="billable_minutes", columnDefinition = "BIGINT(20)")
    private Long billableMinutes;

    @Column(name="calls_75_100", columnDefinition = "BIGINT(20)")
    private Long calls_75_100;

    @Column(name="calls_50_75", columnDefinition = "BIGINT(20)")
    private Long calls_50_75;

    @Column(name="calls_25_50", columnDefinition = "BIGINT(20)")
    private Long calls_25_50;

    @Column(name="calls_1_25", columnDefinition = "BIGINT(20)")
    private Long calls_1_25;

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

    public Integer getRecordsReceived() {
        return recordsReceived;
    }

    public void setRecordsReceived(Integer recordsReceived) {
        this.recordsReceived = recordsReceived;
    }

    public Integer getRecordsAccepted() {
        return recordsAccepted;
    }

    public void setRecordsAccepted(Integer recordsAccepted) {
        this.recordsAccepted = recordsAccepted;
    }

    public Integer getRecordsRejected() {
        return recordsRejected;
    }

    public void setRecordsRejected(Integer recordsRejected) {
        this.recordsRejected = recordsRejected;
    }

    public Integer getSelfDeactivated() {
        return selfDeactivated;
    }

    public void setSelfDeactivated(Integer selfDeactivated) {
        this.selfDeactivated = selfDeactivated;
    }

    public Integer getNotAnsweringDeactivations() {
        return notAnsweringDeactivations;
    }

    public void setNotAnsweringDeactivations(Integer notAnsweringDeactivations) {
        this.notAnsweringDeactivations = notAnsweringDeactivations;
    }

    public Integer getLowListenershipDeactivations() {
        return lowListenershipDeactivations;
    }

    public void setLowListenershipDeactivations(Integer lowListenershipDeactivations) {
        this.lowListenershipDeactivations = lowListenershipDeactivations;
    }

    public Integer getSystemDeactivations() {
        return systemDeactivations;
    }

    public void setSystemDeactivations(Integer systemDeactivations) {
        this.systemDeactivations = systemDeactivations;
    }

    public Integer getChildCompleted() {
        return childCompleted;
    }

    public void setChildCompleted(Integer childCompleted) {
        this.childCompleted = childCompleted;
    }

    public Integer getMotherCompleted() {
        return motherCompleted;
    }

    public void setMotherCompleted(Integer motherCompleted) {
        this.motherCompleted = motherCompleted;
    }

    public Long getCalledInbox() {
        return calledInbox;
    }

    public void setCalledInbox(Long calledInbox) {
        this.calledInbox = calledInbox;
    }

    public Long getCallsAttempted() {
        return callsAttempted;
    }

    public void setCallsAttempted(Long callsAttempted) {
        this.callsAttempted = callsAttempted;
    }

    public Long getCallsSuccessful() {
        return callsSuccessful;
    }

    public void setCallsSuccessful(Long callsSuccessful) {
        this.callsSuccessful = callsSuccessful;
    }

    public Long getBillableMinutes() {
        return billableMinutes;
    }

    public void setBillableMinutes(Long billableMinutes) {
        this.billableMinutes = billableMinutes;
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
}
