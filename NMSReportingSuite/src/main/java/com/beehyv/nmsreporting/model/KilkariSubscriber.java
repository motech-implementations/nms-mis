package com.beehyv.nmsreporting.model;


import javax.persistence.*;
import java.util.Date;

/**
 * Created by himanshu on 06/10/17.
 */
@Entity
@Table(name="agg_kilkari_subscriber")

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

    @Column(name="total_records_received_MCTS_RCH", columnDefinition = "INT(11)")
    private Integer totalRecordsReceived_MCTS_RCH;

    @Column(name="eligible_for_subscriptions", columnDefinition = "INT(11)")
    private Integer eligibleForSubscriptions;

    @Column(name="total_subscriptions_rejected", columnDefinition = "INT(11)")
    private Integer totalSubscriptionsRejected;

    @Column(name="total_subscriptions_accepted", columnDefinition = "INT(11)")
    private Integer totalSubscriptionsAccepted;

    @Column(name="total_subscriptions_completed", columnDefinition = "INT(11)")
    private Integer totalSubscriptionsCompleted;


    public KilkariSubscriber(Integer id, String locationType, Long locationId, Date date, Integer totalSubscriptions, Integer totalRecordsReceived_MCTS_RCH, Integer eligibleForSubscriptions, Integer totalSubscriptionsRejected, Integer totalSubscriptionsAccepted, Integer totalSubscriptionsCompleted) {
        this.id = id;
        this.locationType = locationType;
        this.locationId = locationId;
        this.date = date;
        this.totalSubscriptions = totalSubscriptions;
        this.totalRecordsReceived_MCTS_RCH = totalRecordsReceived_MCTS_RCH;
        this.eligibleForSubscriptions = eligibleForSubscriptions;
        this.totalSubscriptionsRejected = totalSubscriptionsRejected;
        this.totalSubscriptionsAccepted = totalSubscriptionsAccepted;
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

    public Integer getTotalRecordsReceived_MCTS_RCH() {
        return totalRecordsReceived_MCTS_RCH;
    }

    public void setTotalRecordsReceived_MCTS_RCH(Integer totalRecordsReceived_MCTS_RCH) {
        this.totalRecordsReceived_MCTS_RCH = totalRecordsReceived_MCTS_RCH;
    }

    public Integer getEligibleForSubscriptions() {
        return eligibleForSubscriptions;
    }

    public void setEligibleForSubscriptions(Integer eligibleForSubscriptions) {
        this.eligibleForSubscriptions = eligibleForSubscriptions;
    }

    public Integer getTotalSubscriptionsRejected() {
        return totalSubscriptionsRejected;
    }

    public void setTotalSubscriptionsRejected(Integer totalSubscriptionsRejected) {
        this.totalSubscriptionsRejected = totalSubscriptionsRejected;
    }

    public Integer getTotalSubscriptionsAccepted() {
        return totalSubscriptionsAccepted;
    }

    public void setTotalSubscriptionsAccepted(Integer totalSubscriptionsAccepted) {
        this.totalSubscriptionsAccepted = totalSubscriptionsAccepted;
    }

    public Integer getTotalSubscriptionsCompleted() {
        return totalSubscriptionsCompleted;
    }

    public void setTotalSubscriptionsCompleted(Integer totalSubscriptionsCompleted) {
        this.totalSubscriptionsCompleted = totalSubscriptionsCompleted;
    }
}
