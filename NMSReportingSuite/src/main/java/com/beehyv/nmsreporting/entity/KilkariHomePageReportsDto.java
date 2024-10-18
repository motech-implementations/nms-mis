package com.beehyv.nmsreporting.entity;

public class KilkariHomePageReportsDto {


    private String locationType;
    private String locationName;
    private Long locationId;
    private String dateRange;

    private int beneficiariesJoinedTillLastMonth;

    private int beneficiariesAnsweredAtLeastOneCall;

    private int totalDeactivations;

    private int mostHeardCallWeekNo;
    private int leastHeardCallWeekNo;

    private Double avgDurationOfCalls;

    private int duplicatePhoneNumberCount;
    private int totalIneligible;



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

    public String getDateRange() {
        return dateRange;
    }

    public void setDateRange(String dateRange) {
        this.dateRange = dateRange;
    }

    public int getBeneficiariesJoinedTillLastMonth() {
        return beneficiariesJoinedTillLastMonth;
    }

    public void setBeneficiariesJoinedTillLastMonth(int beneficiariesJoinedTillLastMonth) {
        this.beneficiariesJoinedTillLastMonth = beneficiariesJoinedTillLastMonth;
    }

    public int getBeneficiariesAnsweredAtLeastOneCall() {
        return this.beneficiariesAnsweredAtLeastOneCall;
    }

    public void setBeneficiariesAnsweredAtLeastOneCall(int beneficiariesAnsweredAtLeastOneCall) {
        this.beneficiariesAnsweredAtLeastOneCall = beneficiariesAnsweredAtLeastOneCall;
    }

    public int getTotalDeactivations() {
        return totalDeactivations;
    }

    public void setTotalDeactivations(int totalDeactivations) {
        this.totalDeactivations = totalDeactivations;
    }

    public int getMostHeardCallWeekNo() {
        return mostHeardCallWeekNo;
    }

    public void setMostHeardCallWeekNo(int mostHeardCallWeekNo) {
        this.mostHeardCallWeekNo = mostHeardCallWeekNo;
    }

    public int getLeastHeardCallWeekNo() {
        return leastHeardCallWeekNo;
    }

    public void setLeastHeardCallWeekNo(int leastHeardCallWeekNo) {
        this.leastHeardCallWeekNo = leastHeardCallWeekNo;
    }

    public double getAvgDurationOfCalls() {
        return avgDurationOfCalls;
    }

    public void setAvgDurationOfCalls(double avgDurationOfCalls) {
        this.avgDurationOfCalls = avgDurationOfCalls;
    }

    public int getDuplicatePhoneNumberCount() {
        return duplicatePhoneNumberCount;
    }

    public void setDuplicatePhoneNumberCount(int duplicatePhoneNumberCount) {
        this.duplicatePhoneNumberCount = duplicatePhoneNumberCount;
    }

    public int getTotalIneligible() {
        return totalIneligible;
    }

    public void setTotalIneligible(int totalIneligible) {
        this.totalIneligible = totalIneligible;
    }
}
