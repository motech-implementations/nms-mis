package com.beehyv.nmsreporting.entity;

/**
 * Created by beehyv on 29/9/17.
 */
public class BreadCrumbDto {

    String locationType;
    String locationName;
    Integer locationId;
    boolean status;

    public BreadCrumbDto(String locationType,String locationName, Integer locationId, boolean status) {
        this.locationType = locationType;
        this.locationName = locationName;
        this.locationId = locationId;
        this.status = status;
    }
    public BreadCrumbDto(){

    }
    public String getLocationType() {
        return locationType;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public void setLocationType(String locationType) {
        this.locationType = locationType;
    }

    public Integer getLocationId() {
        return locationId;
    }

    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
