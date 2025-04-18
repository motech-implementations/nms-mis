package com.beehyv.nmsreporting.model;

import javassist.bytecode.annotation.LongMemberValue;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by beehyv on 9/10/17.
 */
@Entity
@Table(name="agg_aggregate_beneficiaries")
public class AggregateCumulativeBeneficiary {

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

    @Column(name="self_deactivated",columnDefinition = "BIGINT(20)")
    private Long selfDeactivated;

    @Column(name="no_answer_deactivation",columnDefinition = "BIGINT(20))")
    private Long notAnswering;

    @Column(name="low_listener_deactivation",columnDefinition = "BIGINT(20)")
    private Long lowListenership;

    @Column(name="system_deactivation",columnDefinition = "BIGINT(20)")
    private Long systemDeactivation;

    @Column(name="mother_completed",columnDefinition = "BIGINT(20)")
    private Long motherCompletion;

    @Column(name="child_completed",columnDefinition = "BIGINT(20)")
    private Long childCompletion;

    @Column(name="joined_subscription",columnDefinition = "BIGINT(20)")
    private Long joinedSubscription;

    @Column(name="Mother_subscriptions_joined",columnDefinition = "BIGINT(20)")
    private Long motherSubscriptionJoined;

    @Column(name="Child_subscriptions_joined",columnDefinition = "BIGINT(20)")
    private Long childSubscriptionJoined;

    @Column(name="period_type", columnDefinition = "VARCHAR(45)")
    private String periodType;

    @Column(name="subscriptions_rejected",columnDefinition = "BIGINT(20)")
    private Long subscriptionsRejected;

    public AggregateCumulativeBeneficiary(Integer id, String locationType, Long locationId, Date date, Long selfDeactivated, Long notAnswering, Long lowListenership, Long systemDeactivation, Long motherCompletion, Long childCompletion, Long joinedSubscription, Long motherSubscriptionJoined, Long childSubscriptionJoined, String periodType, Long subscriptionsRejected ) {
        this.id = id;
        this.locationType = locationType;
        this.locationId = locationId;
        this.date = date;
        this.selfDeactivated = selfDeactivated;
        this.notAnswering = notAnswering;
        this.lowListenership = lowListenership;
        this.systemDeactivation = systemDeactivation;
        this.motherCompletion = motherCompletion;
        this.childCompletion = childCompletion;
        this.joinedSubscription = joinedSubscription;
        this.motherSubscriptionJoined = motherSubscriptionJoined;
        this.childSubscriptionJoined = childSubscriptionJoined;
        this.periodType = periodType;
        this.subscriptionsRejected = subscriptionsRejected;
    }

    public AggregateCumulativeBeneficiary(){

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

    public Long getSelfDeactivated() {
        return selfDeactivated;
    }

    public void setSelfDeactivated(Long selfDeactivated) {
        this.selfDeactivated = selfDeactivated;
    }

    public Long getNotAnswering() {
        return notAnswering;
    }

    public void setNotAnswering(Long notAnswering) {
        this.notAnswering = notAnswering;
    }

    public Long getLowListenership() {
        return lowListenership;
    }

    public void setLowListenership(Long lowListenership) {
        this.lowListenership = lowListenership;
    }

    public Long getSystemDeactivation() {
        return systemDeactivation;
    }

    public void setSystemDeactivation(Long systemDeactivation) {
        this.systemDeactivation = systemDeactivation;
    }

    public Long getMotherCompletion() {
        return motherCompletion;
    }

    public void setMotherCompletion(Long motherCompletion) {
        this.motherCompletion = motherCompletion;
    }

    public Long getChildCompletion() {
        return childCompletion;
    }

    public void setChildCompletion(Long childCompletion) {
        this.childCompletion = childCompletion;
    }

    public Long getJoinedSubscription() {
        return joinedSubscription;
    }

    public void setJoinedSubscription(Long joinedSubscription) {
        this.joinedSubscription = joinedSubscription;
    }

    public Long getMotherSubscriptionJoined() {
        return motherSubscriptionJoined;
    }

    public void setMotherSubscriptionJoined(Long motherSubscriptionJoined) {
        this.motherSubscriptionJoined = motherSubscriptionJoined;
    }

    public Long getChildSubscriptionJoined() {
        return childSubscriptionJoined;
    }

    public void setChildSubscriptionJoined(Long childSubscriptionJoined) {
        this.childSubscriptionJoined = childSubscriptionJoined;
    }

    public String getPeriodType() {
        return periodType;
    }

    public void setPeriodType(String periodType) {
        this.periodType = periodType;
    }

    public Long getSubscriptionsRejected() {
        return subscriptionsRejected;
    }

    public void setSubscriptionsRejected(Long subscriptionsRejected) {
        this.subscriptionsRejected = subscriptionsRejected;
    }
}
