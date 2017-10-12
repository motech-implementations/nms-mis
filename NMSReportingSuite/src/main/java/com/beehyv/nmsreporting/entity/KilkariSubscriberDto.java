package com.beehyv.nmsreporting.entity;

/**
 * Created by himanshu on 06/10/17.
 */
public class KilkariSubscriberDto {

    private int id;
    private Long locationId;
    private String locationType;
    private String locationName;
    private  Integer totalSubscriptions;
    private Integer totalBeneficiaryRecordsReceived;
    private Integer totalBeneficiaryRecordsEligible;
    private Integer totalBeneficiaryRecordsAccepted;
    private Integer totalBeneficiaryRecordsRejected;
    private Integer totalSubscriptionsCompleted;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
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

    public Integer getTotalSubscriptions() {
        return totalSubscriptions;
    }

    public void setTotalSubscriptions(Integer totalSubscriptions) {
        this.totalSubscriptions = totalSubscriptions;
    }

    public Integer getTotalBeneficiaryRecordsReceived() {
        return totalBeneficiaryRecordsReceived;
    }

    public void setTotalBeneficiaryRecordsReceived(Integer totalBeneficiaryRecordsReceived) {
        this.totalBeneficiaryRecordsReceived = totalBeneficiaryRecordsReceived;
    }

    public Integer getTotalBeneficiaryRecordsEligible() {
        return totalBeneficiaryRecordsEligible;
    }

    public void setTotalBeneficiaryRecordsEligible(Integer totalBeneficiaryRecordsEligible) {
        this.totalBeneficiaryRecordsEligible = totalBeneficiaryRecordsEligible;
    }

    public Integer getTotalBeneficiaryRecordsAccepted() {
        return totalBeneficiaryRecordsAccepted;
    }

    public void setTotalBeneficiaryRecordsAccepted(Integer totalBeneficiaryRecordsAccepted) {
        this.totalBeneficiaryRecordsAccepted = totalBeneficiaryRecordsAccepted;
    }

    public Integer getTotalBeneficiaryRecordsRejected() {
        return totalBeneficiaryRecordsRejected;
    }

    public void setTotalBeneficiaryRecordsRejected(Integer totalBeneficiaryRecordsRejected) {
        this.totalBeneficiaryRecordsRejected = totalBeneficiaryRecordsRejected;
    }

    public Integer getTotalSubscriptionsCompleted() {
        return totalSubscriptionsCompleted;
    }

    public void setTotalSubscriptionsCompleted(Integer totalSubscriptionsCompleted) {
        this.totalSubscriptionsCompleted = totalSubscriptionsCompleted;
    }
}
