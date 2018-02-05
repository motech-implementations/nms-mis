package com.beehyv.nmsreporting.entity;

import java.util.Date;

/**
 * Created by beehyv on 21/9/17.
 */
public class MAPerformanceDto {

    int id;
    String locationType;
    String locationName;
    Long locationId;
    Integer ashasStarted;
    Long ashasNotAccessed;
    Long ashasAccessed;
    Integer ashasCompleted;
    Integer ashasFailed;
    boolean link = false;
//    Integer ashasRejected;
//    Integer recordsReceived;
//    Integer registeredNotCompletedStart;
//    Integer registeredNotCompletedend;


    public Long getAshasNotAccessed() {
        return ashasNotAccessed;
    }

    public void setAshasNotAccessed(Long ashasNotAccessed) {
        this.ashasNotAccessed = ashasNotAccessed;
    }

    public Long getAshasAccessed() {
        return ashasAccessed;
    }

    public void setAshasAccessed(Long ashasAccessed) {
        this.ashasAccessed = ashasAccessed;
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

    public Integer getAshasStarted() {
        return ashasStarted;
    }

    public void setAshasStarted(Integer ashasStarted) {
        this.ashasStarted = ashasStarted;
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

    public boolean isLink() {
        return link;
    }

    public void setLink(boolean link) {
        this.link = link;
    }
}
