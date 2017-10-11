package com.beehyv.nmsreporting.entity;

/**
 * Created by beehyv on 11/10/17.
 */
public class KilkariCallReportDto {

    String locationType;
    String locationName;
    Long locationId;
    Long callsAttempted;
    Long content_75_100;
    Long content_50_75;
    Long content_25_50;
    Long content_1_25;
    Long successfulCalls;
    Long billableMinutes;
    Long callsToInbox;
    Integer avgDuration;

    public String getLocationType() {
        return locationType;
    }

    public void setLocationType(String locationType) {
        this.locationType = locationType;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public Long getCallsAttempted() {
        return callsAttempted;
    }

    public void setCallsAttempted(Long callsAttempted) {
        this.callsAttempted = callsAttempted;
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

    public Integer getAvgDuration() {
        return avgDuration;
    }

    public void setAvgDuration(Integer avgDuration) {
        this.avgDuration = avgDuration;
    }
}
