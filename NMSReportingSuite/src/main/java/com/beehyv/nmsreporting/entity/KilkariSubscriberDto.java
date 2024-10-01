package com.beehyv.nmsreporting.entity;

import javax.persistence.criteria.CriteriaBuilder;

/**
 * Created by himanshu on 06/10/17.
 */
public class KilkariSubscriberDto {

    private int id;
    private Long locationId;
    private String locationType;
    private String locationName;
    private Integer totalSubscriptionsStart;
    private Integer totalBeneficiaryRecordsReceived;
    private Integer totalBeneficiaryRecordsEligible;
    private Integer totalBeneficiaryRecordsAccepted;
    private Integer totalRecordsRejected;
    private Integer totalSubscriptionsCompleted;
    private Integer totalSubscriptionsEnd;
    private boolean link = false;

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

    public Integer getTotalSubscriptionsStart() {
        return totalSubscriptionsStart;
    }

    public void setTotalSubscriptionsStart(Integer totalSubscriptionsStart) {
        this.totalSubscriptionsStart = totalSubscriptionsStart;
    }

    public Integer getTotalSubscriptionsEnd() {
        return totalSubscriptionsEnd;
    }

    public void setTotalSubscriptionsEnd(Integer totalSubscriptionsEnd) {
        this.totalSubscriptionsEnd = totalSubscriptionsEnd;
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

    public Integer getTotalRecordsRejected() {
        return totalRecordsRejected;
    }

    public void setTotalRecordsRejected(Integer totalRecordsRejected) {
        this.totalRecordsRejected = totalRecordsRejected;
    }

    public Integer getTotalSubscriptionsCompleted() {
        return totalSubscriptionsCompleted;
    }

    public void setTotalSubscriptionsCompleted(Integer totalSubscriptionsCompleted) {
        this.totalSubscriptionsCompleted = totalSubscriptionsCompleted;
    }

    public boolean isLink() {
        return link;
    }

    public void setLink(boolean link) {
        this.link = link;
    }
}
