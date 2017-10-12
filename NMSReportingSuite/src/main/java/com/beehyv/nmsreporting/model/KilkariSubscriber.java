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

    @Column(name="date", columnDefinition = "DATETIME")
    private Date date;

    @Column(name="total_subscriptions", columnDefinition = "INT(11)")
    private Integer totalSubscriptions;

    @Column(name="total_beneficiary_records_received", columnDefinition = "INT(11)")
    private Integer totalBeneficiaryRecordsReceived;

    @Column(name="total_beneficiary_records_eligible", columnDefinition = "INT(11)")
    private Integer totalBeneficiaryRecordsEligible;

    @Column(name="total_beneficiary_records_accepted", columnDefinition = "INT(11)")
    private Integer totalBeneficiaryRecordsAccepted;

    @Column(name="total_beneficiary_records_rejected", columnDefinition = "INT(11)")
    private Integer totalBeneficiaryRecordsRejected;

    @Column(name="total_subscriptions_completed", columnDefinition = "INT(11)")
    private Integer totalSubscriptionsCompleted;


    public KilkariSubscriber(Integer id, String locationType, Long locationId, Date date, Integer totalSubscriptions, Integer totalBeneficiaryRecordsReceived, Integer totalBeneficiaryRecordsEligible, Integer totalBeneficiaryRecordsAccepted, Integer totalBeneficiaryRecordsRejected, Integer totalSubscriptionsCompleted){
        this.id = id;
        this.locationType = locationType;
        this.locationId = locationId;
        this.date = date;
        this.totalSubscriptions = totalSubscriptions;
        this.totalBeneficiaryRecordsReceived = totalBeneficiaryRecordsReceived;
        this.totalBeneficiaryRecordsEligible = totalBeneficiaryRecordsEligible;
        this.totalBeneficiaryRecordsAccepted = totalBeneficiaryRecordsAccepted;
        this.totalBeneficiaryRecordsRejected = totalBeneficiaryRecordsRejected;
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
