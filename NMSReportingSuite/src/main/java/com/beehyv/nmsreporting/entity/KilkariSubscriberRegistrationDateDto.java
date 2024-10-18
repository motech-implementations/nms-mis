package com.beehyv.nmsreporting.entity;

public class KilkariSubscriberRegistrationDateDto {

    private int id;
    private Long locationId;
    private String locationType;
    private Integer totalSubscriptions;
    private Integer totalBeneficiaryWithActiveStatus;
    private Integer totalBeneficiaryWithOnHoldStatus;
    private Integer totalBeneficiaryWithPendingStatus;
    private Integer totalRecordsRejected;
    private Integer totalBeneficiaryWithDeactivatedStatus;
    private Integer totalBeneficiaryWithDeactivatedStatus_LIVE_BIRTH;
    private Integer totalSubscriptionsCompletedStatus;
    private boolean link = false;

    public KilkariSubscriberRegistrationDateDto() {
    }

    public KilkariSubscriberRegistrationDateDto(Long locationId, Integer totalBeneficiaryWithActiveStatus , Integer totalBeneficiaryWithOnHoldStatus ,  Integer totalBeneficiaryWithDeactivatedStatus , Integer totalBeneficiaryWithPendingStatus , Integer totalRecordsRejected) {
        this.locationId = locationId;
        this.totalBeneficiaryWithActiveStatus = totalBeneficiaryWithActiveStatus;
        this.totalBeneficiaryWithOnHoldStatus = totalBeneficiaryWithOnHoldStatus;
        this.totalBeneficiaryWithDeactivatedStatus = totalBeneficiaryWithDeactivatedStatus;
        this.totalBeneficiaryWithPendingStatus = totalBeneficiaryWithPendingStatus;
        this.totalRecordsRejected = totalRecordsRejected;
    }

    public KilkariSubscriberRegistrationDateDto(Integer totalSubscriptions, Integer totalBeneficiaryWithActiveStatus, Integer totalBeneficiaryWithOnHoldStatus, Integer totalBeneficiaryWithDeactivatedStatus, Integer totalBeneficiaryWithPendingStatus, Integer totalSubscriptionsCompletedStatus, Integer totalRecordsRejected ) {
        this.totalSubscriptions = totalSubscriptions;
        this.totalBeneficiaryWithActiveStatus = totalBeneficiaryWithActiveStatus;
        this.totalBeneficiaryWithOnHoldStatus = totalBeneficiaryWithOnHoldStatus;
        this.totalBeneficiaryWithDeactivatedStatus = totalBeneficiaryWithDeactivatedStatus;
        this.totalBeneficiaryWithPendingStatus = totalBeneficiaryWithPendingStatus;
        this.totalSubscriptionsCompletedStatus = totalSubscriptionsCompletedStatus;
        this.totalRecordsRejected = totalRecordsRejected;
    }

    public KilkariSubscriberRegistrationDateDto(Integer totalSubscriptions, Integer totalBeneficiaryWithActiveStatus, Integer totalBeneficiaryWithOnHoldStatus, Integer totalBeneficiaryWithDeactivatedStatus, Integer totalBeneficiaryWithDeactivatedStatus_LIVE_BIRTH,Integer totalBeneficiaryWithPendingStatus, Integer totalSubscriptionsCompletedStatus, Integer totalRecordsRejected ) {
        this.totalSubscriptions = totalSubscriptions;
        this.totalBeneficiaryWithActiveStatus = totalBeneficiaryWithActiveStatus;
        this.totalBeneficiaryWithOnHoldStatus = totalBeneficiaryWithOnHoldStatus;
        this.totalBeneficiaryWithDeactivatedStatus = totalBeneficiaryWithDeactivatedStatus;
        this.totalBeneficiaryWithDeactivatedStatus_LIVE_BIRTH = totalBeneficiaryWithDeactivatedStatus_LIVE_BIRTH;
        this.totalBeneficiaryWithPendingStatus = totalBeneficiaryWithPendingStatus;
        this.totalSubscriptionsCompletedStatus = totalSubscriptionsCompletedStatus;
        this.totalRecordsRejected = totalRecordsRejected;
    }


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

    public Integer getTotalSubscriptions() {
        return totalSubscriptions;
    }

    public void setTotalSubscriptions(Integer totalSubscriptions) {
        this.totalSubscriptions = totalSubscriptions;
    }

    public Integer getTotalBeneficiaryWithActiveStatus() {
        return totalBeneficiaryWithActiveStatus;
    }

    public void setTotalBeneficiaryRecordsReceived(Integer totalBeneficiaryRecordsReceived) {
        this.totalBeneficiaryWithActiveStatus = totalBeneficiaryRecordsReceived;
    }

    public Integer getTotalBeneficiaryWithHoldSubscriptionStatus() {
        return totalBeneficiaryWithOnHoldStatus;
    }

    public void setTotalBeneficiaryWithHoldSubscriptionStatus(Integer totalBeneficiaryWithHoldSubscriptionStatus) {
        this.totalBeneficiaryWithOnHoldStatus = totalBeneficiaryWithHoldSubscriptionStatus;
    }

    public Integer getTotalBeneficiaryWithPendingStatus() {
        return totalBeneficiaryWithPendingStatus;
    }

    public void setTotalBeneficiaryWithPendingStatus(Integer totalBeneficiaryWithPendingStatus) {
        this.totalBeneficiaryWithPendingStatus = totalBeneficiaryWithPendingStatus;
    }

    public Integer getTotalRecordsRejected() {
        return totalRecordsRejected;
    }

    public void setTotalRecordsRejected(Integer totalRecordsRejected) {
        this.totalRecordsRejected = totalRecordsRejected;
    }

    public Integer getTotalBeneficiaryWithDeactivatedStatus() {
        return totalBeneficiaryWithDeactivatedStatus;
    }

    public void setTotalBeneficiaryWithDeactivatedStatus(Integer totalBeneficiaryWithDeactivatedStatus) {
        this.totalBeneficiaryWithDeactivatedStatus = totalBeneficiaryWithDeactivatedStatus;
    }

    public Integer getTotalBeneficiaryWithDeactivatedStatus_LIVE_BIRTH() {
        return totalBeneficiaryWithDeactivatedStatus_LIVE_BIRTH;
    }

    public void setTotalBeneficiaryWithDeactivatedStatus_LIVE_BIRTH(Integer totalBeneficiaryWithDeactivatedStatus_LIVE_BIRTH){
        this.totalBeneficiaryWithDeactivatedStatus_LIVE_BIRTH = totalBeneficiaryWithDeactivatedStatus_LIVE_BIRTH;
    }
    public Integer getTotalSubscriptionsCompletedStatus() {
        return totalSubscriptionsCompletedStatus;
    }

    public void setTotalSubscriptionsCompletedStatus(Integer totalSubscriptionsCompletedStatus) {
        this.totalSubscriptionsCompletedStatus = totalSubscriptionsCompletedStatus;
    }

    public boolean isLink() {
        return link;
    }

    public void setLink(boolean link) {
        this.link = link;
    }
}
