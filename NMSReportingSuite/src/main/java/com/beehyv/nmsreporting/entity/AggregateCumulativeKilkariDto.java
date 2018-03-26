package com.beehyv.nmsreporting.entity;

/**
 * Created by beehyv on 3/10/17.
 */
public class AggregateCumulativeKilkariDto{

    private int id;
    private String locationType;
    private String locationName;
    private Long locationId;
    private Long uniqueBeneficiaries;
    private Long successfulCalls;
    private Double billableMinutes;
    private Float averageDuration;
    private boolean link = false;

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

    public Long getUniqueBeneficiaries() {
        return uniqueBeneficiaries;
    }

    public void setUniqueBeneficiaries(Long uniqueBeneficiaries) {
        this.uniqueBeneficiaries = uniqueBeneficiaries;
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

    public Float getAverageDuration() {
        return averageDuration;
    }

    public void setAverageDuration(Float averageDuration) {
        this.averageDuration = averageDuration;
    }

    public boolean isLink() {
        return link;
    }

    public void setLink(boolean link) {
        this.link = link;
    }
}
