package com.beehyv.nmsreporting.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by beehyv on 11/10/17.
 */
@Entity
@Table(name="kilkari_call_report")
public class KilkariCalls {

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

    @Column(name="content_75_100", columnDefinition = "BIGINT(20)")
    private Long content_75_100;

    @Column(name="content_50_75", columnDefinition = "BIGINT(20)")
    private Long content_50_75;

    @Column(name="content_25_50", columnDefinition = "BIGINT(20)")
    private Long content_25_50;

    @Column(name="content_1_25", columnDefinition = "BIGINT(20)")
    private Long content_1_25;

    @Column(name="calls_attempted", columnDefinition = "BIGINT(20)")
    private Long callsAttempted;

    @Column(name="successful_calls", columnDefinition = "BIGINT(20)")
    private Long successfulCalls;

    @Column(name="billable_minutes", columnDefinition = "BIGINT(20)")
    private Long billableMinutes;

    @Column(name="calls_to_inbox", columnDefinition = "BIGINT(20)")
    private Long callsToInbox;

    public KilkariCalls(Integer id, String locationType, Long locationId, Date date, Long content_75_100, Long content_50_75, Long content_25_50, Long content_1_25, Long callsAttempted, Long successfulCalls, Long billableMinutes, Long callsToInbox) {
        this.id = id;
        this.locationType = locationType;
        this.locationId = locationId;
        this.date = date;
        this.content_75_100 = content_75_100;
        this.content_50_75 = content_50_75;
        this.content_25_50 = content_25_50;
        this.content_1_25 = content_1_25;
        this.callsAttempted = callsAttempted;
        this.successfulCalls = successfulCalls;
        this.billableMinutes = billableMinutes;
        this.callsToInbox = callsToInbox;
    }

    public KilkariCalls(){

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

    public Long getContent_75_100() {
        return content_75_100;
    }

    public void setContent_75_100(Long content_75_100) {
        this.content_75_100 = content_75_100;
    }

    public Long getContent_50_75() {
        return content_50_75;
    }

    public void setContent_50_75(Long content_50_75) {
        this.content_50_75 = content_50_75;
    }

    public Long getContent_25_50() {
        return content_25_50;
    }

    public void setContent_25_50(Long content_25_50) {
        this.content_25_50 = content_25_50;
    }

    public Long getContent_1_25() {
        return content_1_25;
    }

    public void setContent_1_25(Long content_1_25) {
        this.content_1_25 = content_1_25;
    }

    public Long getCallsAttempted() {
        return callsAttempted;
    }

    public void setCallsAttempted(Long callsAttempted) {
        this.callsAttempted = callsAttempted;
    }

    public Long getSuccessfulCalls() {
        return successfulCalls;
    }

    public void setSuccessfulCalls(Long successfulCalls) {
        this.successfulCalls = successfulCalls;
    }

    public Long getBillableMinutes() {
        return billableMinutes;
    }

    public void setBillableMinutes(Long billableMinutes) {
        this.billableMinutes = billableMinutes;
    }

    public Long getCallsToInbox() {
        return callsToInbox;
    }

    public void setCallsToInbox(Long callsToInbox) {
        this.callsToInbox = callsToInbox;
    }
}
