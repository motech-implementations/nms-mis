package com.beehyv.nmsreporting.entity;

/**
 * Created by beehyv on 29/9/17.
 */
public class BreadCrumbDto {

    String locationType;
    String locationName;
    Integer locationId;
    String status;

    public BreadCrumbDto(String locationType,String locationName, Integer locationId, String status) {
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
