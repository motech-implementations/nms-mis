package com.beehyv.nmsreporting.entity;

public class KilkariCallReportWithBeneficiariesDto {
    private String locationType;
    private String locationName;
    private Long locationId;
    private Long callsAttempted;
    private Long content_75_100;
    private Long content_1_25;
    private Long successfulCalls;
    private Double billableMinutes;
    private Long callsToInbox;
    private Float avgDuration;
    private Long beneficiariesCalled;
    private float percentageCalls_75_100;
    private float percentageCalls_1_25;
    private float percentageCallsResponded;
    private boolean link =false;

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

    public Double getBillableMinutes() {
        return billableMinutes;
    }

    public void setBillableMinutes(Double billableMinutes) {
        this.billableMinutes = billableMinutes;
    }

    public Long getCallsToInbox() {
        return callsToInbox;
    }

    public void setCallsToInbox(Long callsToInbox) {
        this.callsToInbox = callsToInbox;
    }

    public Float getAvgDuration() {
        return avgDuration;
    }

    public void setAvgDuration(Float avgDuration) {
        this.avgDuration = avgDuration;
    }

    public Long getBeneficiariesCalled() {
        return beneficiariesCalled;
    }

    public void setBeneficiariesCalled(Long beneficiariesCalled) {
        this.beneficiariesCalled = beneficiariesCalled;
    }

    public float getPercentageCalls_75_100() {
        return percentageCalls_75_100;
    }

    public void setPercentageCalls_75_100(float percentageCalls_75_100) {
        this.percentageCalls_75_100 = percentageCalls_75_100;
    }

    public float getPercentageCalls_1_25() {
        return percentageCalls_1_25;
    }

    public void setPercentageCalls_1_25(float percentageCalls_1_25) {
        this.percentageCalls_1_25 = percentageCalls_1_25;
    }

    public float getPercentageCallsResponded() {
        return percentageCallsResponded;
    }

    public void setPercentageCallsResponded(float percentageCallsResponded) {
        this.percentageCallsResponded = percentageCallsResponded;
    }

    public boolean isLink() {
        return link;
    }

    public void setLink(boolean link) {
        this.link = link;
    }
}
