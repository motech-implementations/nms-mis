package com.beehyv.nmsreporting.entity;

public class KilkariSubscriberRegistrationDateListDto {
    private int id;
    private Long locationId;
    private String locationType;
    private String locationName;
    private Integer totalBeneficiaryWithActiveStatus;
    private Integer totalBeneficiaryWithOnHoldStatus;
    private Integer totalBeneficiaryWithPendingStatus;
//    private Integer totalRecordsRejected;
    private Integer totalBeneficiaryWithDeactivatedStatus;
    private Integer totalSubscriberCount;
    private Integer totalRejectedSubscriberCount;
    private Integer totalBeneficiaryWithCompletedStatus;


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

    public Integer getTotalBeneficiaryWithActiveStatus() {
        return totalBeneficiaryWithActiveStatus;
    }

    public void setTotalBeneficiaryWithActiveStatus(Integer totalBeneficiaryWithActiveStatus) {
        this.totalBeneficiaryWithActiveStatus = totalBeneficiaryWithActiveStatus;
    }

    public Integer getTotalBeneficiaryWithOnHoldStatus() {
        return totalBeneficiaryWithOnHoldStatus;
    }

    public void setTotalBeneficiaryWithOnHoldStatus(Integer totalBeneficiaryWithOnHoldStatus) {
        this.totalBeneficiaryWithOnHoldStatus = totalBeneficiaryWithOnHoldStatus;
    }

    public Integer getTotalBeneficiaryWithPendingStatus() {
        return totalBeneficiaryWithPendingStatus;
    }

    public void setTotalBeneficiaryWithPendingStatus(Integer totalBeneficiaryWithPendingStatus) {
        this.totalBeneficiaryWithPendingStatus = totalBeneficiaryWithPendingStatus;
    }

    public Integer getTotalBeneficiaryWithDeactivatedStatus() {
        return totalBeneficiaryWithDeactivatedStatus;
    }

    public void setTotalBeneficiaryWithDeactivatedStatus(Integer totalBeneficiaryWithDeactivatedStatus) {
        this.totalBeneficiaryWithDeactivatedStatus = totalBeneficiaryWithDeactivatedStatus;
    }

    public Integer getTotalSubscriberCount() {
        return totalSubscriberCount;
    }

    public void setTotalSubscriberCount(Integer totalSubscriberCount) {
        this.totalSubscriberCount = totalSubscriberCount;
    }

    public Integer getTotalRejectedSubscriberCount() {
        return totalRejectedSubscriberCount;
    }

    public void setTotalRejectedSubscriberCount(Integer totalRejectedSubscriberCount) {
        this.totalRejectedSubscriberCount = totalRejectedSubscriberCount;
    }

    public Integer getTotalBeneficiaryWithCompletedStatus() {
        return totalBeneficiaryWithCompletedStatus;
    }

    public void setTotalBeneficiaryWithCompletedStatus(Integer totalBeneficiaryWithCompletedStatus) {
        this.totalBeneficiaryWithCompletedStatus = totalBeneficiaryWithCompletedStatus;
    }
}
