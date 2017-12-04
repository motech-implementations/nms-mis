package com.beehyv.nmsreporting.model;


import javax.persistence.*;
import java.util.Date;

/**
 * Created by himanshu on 06/10/17.
 */
@Entity
@Table(name="kilkari_subscriber_counts")

public class KilkariSubscriber {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id", columnDefinition = "INT(11)")
    private Integer id;


    @Column(name="location_type", columnDefinition = "VARCHAR(45)")
    private String locationType;

    @Column(name="location_id", columnDefinition = "BIGINT(20)")
    private Long locationId;

    @Column(name="date", columnDefinition = "DATE")
    private Date date;

    @Column(name="total_subscriptions", columnDefinition = "INT(11)")
    private Integer totalSubscriptions;

    @Column(name="total_beneficiary_records_rejected", columnDefinition = "INT(11)")
    private Integer totalBeneficiaryRecordsRejected;

    @Column(name="records_deactivated", columnDefinition = "INT(11)")
    private Integer recordsDeactivated;

    @Column(name="records_rejected_but_eligible", columnDefinition = "INT(11)")
    private Integer recordsRejectedButEligible;

    @Column(name="total_subscriptions_completed", columnDefinition = "INT(11)")
    private Integer totalSubscriptionsCompleted;


    public KilkariSubscriber(Integer id, String locationType, Long locationId, Date date, Integer totalSubscriptions, Integer totalBeneficiaryRecordsRejected, Integer recordsDeactivated, Integer recordsRejectedButEligible, Integer totalSubscriptionsCompleted){
        this.id = id;
        this.locationType = locationType;
        this.locationId = locationId;
        this.date = date;
        this.totalSubscriptions = totalSubscriptions;
        this.totalBeneficiaryRecordsRejected = totalBeneficiaryRecordsRejected;
        this.recordsDeactivated = recordsDeactivated;
        this.recordsRejectedButEligible = recordsRejectedButEligible;
        this.totalSubscriptionsCompleted = totalSubscriptionsCompleted;
    }

    public KilkariSubscriber(){

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLocationType() {
        return locationType;
    }

    public void setLocationType(String locationType) {
        this.locationType = locationType;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getTotalSubscriptions() {
        return totalSubscriptions;
    }

    public void setTotalSubscriptions(Integer totalSubscriptions) {
        this.totalSubscriptions = totalSubscriptions;
    }

    public Integer getTotalBeneficiaryRecordsRejected() {
        return totalBeneficiaryRecordsRejected;
    }

    public void setTotalBeneficiaryRecordsRejected(Integer totalBeneficiaryRecordsRejected) {
        this.totalBeneficiaryRecordsRejected = totalBeneficiaryRecordsRejected;
    }

    public Integer getRecordsDeactivated() {
        return recordsDeactivated;
    }

    public void setRecordsDeactivated(Integer recordsDeactivated) {
        this.recordsDeactivated = recordsDeactivated;
    }

    public Integer getRecordsRejectedButEligible() {
        return recordsRejectedButEligible;
    }

    public void setRecordsRejectedButEligible(Integer recordsRejectedButEligible) {
        this.recordsRejectedButEligible = recordsRejectedButEligible;
    }

    public Integer getTotalSubscriptionsCompleted() {
        return totalSubscriptionsCompleted;
    }

    public void setTotalSubscriptionsCompleted(Integer totalSubscriptionsCompleted) {
        this.totalSubscriptionsCompleted = totalSubscriptionsCompleted;
    }
}
