package com.beehyv.nmsreporting.entity;

import java.util.Date;

/**
 * Created by beehyv on 19/9/17.
 */
public class AggregateCumulativeMADto {
    int id;
    String locationType;
    String locationName;
    Long locationId;
    Integer ashasRegistered;
    Integer ashasStarted;
    Integer ashasNotStarted;
    Integer ashasCompleted;
    Integer ashasFailed;
    Integer ashasRejected;
    Integer recordsReceived;
    float notStartedpercentage;
    float completedPercentage;
    float failedpercentage;

    public Integer getRecordsReceived() {
        return recordsReceived;
    }

    public void setRecordsReceived(Integer recordsReceived) {
        this.recordsReceived = recordsReceived;
    }

    public Integer getAshasRejected() {
        return ashasRejected;
    }

    public void setAshasRejected(Integer ashasRejected) {
        this.ashasRejected = ashasRejected;
    }

    public float getNotStartedpercentage() {
        return notStartedpercentage;
    }

    public void setNotStartedpercentage(float notStartedpercentage) {
        this.notStartedpercentage = notStartedpercentage;
    }

    public float getCompletedPercentage() {
        return completedPercentage;
    }

    public void setCompletedPercentage(float completedPercentage) {
        this.completedPercentage = completedPercentage;
    }

    public float getFailedpercentage() {
        return failedpercentage;
    }

    public void setFailedpercentage(float failedpercentage) {
        this.failedpercentage = failedpercentage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public Integer getAshasRegistered() {
        return ashasRegistered;
    }

    public void setAshasRegistered(Integer ashasRegistered) {
        this.ashasRegistered = ashasRegistered;
    }

    public Integer getAshasStarted() {
        return ashasStarted;
    }

    public void setAshasStarted(Integer ashasStarted) {
        this.ashasStarted = ashasStarted;
    }

    public Integer getAshasNotStarted() {
        return ashasNotStarted;
    }

    public void setAshasNotStarted(Integer ashasNotStarted) {
        this.ashasNotStarted = ashasNotStarted;
    }

    public Integer getAshasCompleted() {
        return ashasCompleted;
    }

    public void setAshasCompleted(Integer ashasCompleted) {
        this.ashasCompleted = ashasCompleted;
    }

    public Integer getAshasFailed() {
        return ashasFailed;
    }

    public void setAshasFailed(Integer ashasFailed) {
        this.ashasFailed = ashasFailed;
    }
}
