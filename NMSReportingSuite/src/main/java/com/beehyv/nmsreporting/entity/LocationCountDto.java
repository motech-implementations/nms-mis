package com.beehyv.nmsreporting.entity;

public class LocationCountDto {
    private Long locationId;
    private String locationName;
    private int completedCount;

    private int startedCount;

    public String getLocationType() {
        return locationType;
    }

    public void setLocationType(String locationType) {
        this.locationType = locationType;
    }

    private String locationType;

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public int getCompletedCount() {
        return completedCount;
    }

    public void setCompletedCount(int completedCount) {
        this.completedCount = completedCount;
    }

    public int getStartedCount() {
        return startedCount;
    }

    public void setStartedCount(int startedCount) {
        this.startedCount = startedCount;
    }
}

