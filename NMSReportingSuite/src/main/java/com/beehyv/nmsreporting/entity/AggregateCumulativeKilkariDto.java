package com.beehyv.nmsreporting.entity;

/**
 * Created by beehyv on 3/10/17.
 */
public class AggregateCumulativeKilkariDto {

    int id;
    String locationType;
    String locationName;
    Integer locationId;
    Integer uniqueBeneficiaries;
    Integer successfulCalls;
    Integer billableMinutes;
    Integer averageDuration;

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

    public Integer getLocationId() {
        return locationId;
    }

    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
    }

    public Integer getUniqueBeneficiaries() {
        return uniqueBeneficiaries;
    }

    public void setUniqueBeneficiaries(Integer uniqueBeneficiaries) {
        this.uniqueBeneficiaries = uniqueBeneficiaries;
    }

    public Integer getSuccessfulCalls() {
        return successfulCalls;
    }

    public void setSuccessfulCalls(Integer successfulCalls) {
        this.successfulCalls = successfulCalls;
    }

    public Integer getBillableMinutes() {
        return billableMinutes;
    }

    public void setBillableMinutes(Integer billableMinutes) {
        this.billableMinutes = billableMinutes;
    }

    public Integer getAverageDuration() {
        return averageDuration;
    }

    public void setAverageDuration(Integer averageDuration) {
        this.averageDuration = averageDuration;
    }
}
