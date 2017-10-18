package com.beehyv.nmsreporting.model;
/**
 * Created by himanshu on 06/10/17.
 */

import javax.persistence.*;


@Entity
@Table(name="beneficiary_call_measure")
public class BeneficiaryCallMeasure {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id", columnDefinition = "INT(11)")
    private Integer id;

    @Column(name="beneficiary_id", columnDefinition = "BIGINT(20)")
    private Long beneficiaryId;

    @Column(name="subscription_id", columnDefinition = "BIGINT(20)")
    private Long subscriptionId;

    @Column(name="mother_beneficiary", columnDefinition = "TINYINT(4)")
    private Integer motherBeneficiary;

    @Column(name="msisdn", columnDefinition = "BIGINT(20)")
    private Long msisdn;

    @Column(name="operator_id", columnDefinition = "VARCHAR(45)")
    private String operatorId;

    @Column(name="campaign_id", columnDefinition = "VARCHAR(45)")
    private String campiagnId;

    @Column(name="state_id", columnDefinition = "INT(11)")
    private Integer stateId;

    @Column(name="call_status", columnDefinition = "VARCHAR(45)")
    private String callStatus;

    @Column(name="duration", columnDefinition = "VARCHAR(45)")
    private String duration;

    @Column(name="percentage_listened", columnDefinition = "VARCHAR(45)")
    private String percentageListened;

    @Column(name="call_source", columnDefinition = "VARCHAR(45)")
    private String callSource;

    @Column(name="subscription_status", columnDefinition = "VARCHAR(45)")
    private String subscriptionStatus;

    @Column(name="duration_in_pulse", columnDefinition = "INT(11)")
    private Integer durationInPulse;

    @Column(name="call_start_time", columnDefinition = "TIME")
    private Integer callStartTime;

    @Column(name="call_end_time", columnDefinition = "TIME")
    private Integer callEndTime;

    @Column(name="attempt_number", columnDefinition = "INT(11)")
    private Integer attemptNumber;

    @Column(name="subscription_start_date", columnDefinition = "DATETIME")
    private Integer subscriptionStartDate;

    @Column(name="message_duration", columnDefinition = "INT(11)")
    private Integer messageDuration;

    @Column(name="call_duration", columnDefinition = "INT(11)")
    private Integer callDuration;

    @Column(name="requestId", columnDefinition = "VARCHAR(45)")
    private String requestId;

    @Column(name="modification_date", columnDefinition = "DATETIME")
    private Integer modificationDate;

    @Column(name="subscriptionId", columnDefinition = "VARCHAR(45)")
    private String subscription_Id;

    @Column(name="pregnancy_id", columnDefinition = "BIGINT(20)")
    private Long pregnancyId;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getBeneficiaryId() {
        return beneficiaryId;
    }

    public void setBeneficiaryId(Long beneficiaryId) {
        this.beneficiaryId = beneficiaryId;
    }

    public Long getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(Long subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public Integer getMotherBeneficiary() {
        return motherBeneficiary;
    }

    public void setMotherBeneficiary(Integer motherBeneficiary) {
        this.motherBeneficiary = motherBeneficiary;
    }

    public Long getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(Long msisdn) {
        this.msisdn = msisdn;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public String getCampiagnId() {
        return campiagnId;
    }

    public void setCampiagnId(String campiagnId) {
        this.campiagnId = campiagnId;
    }

    public Integer getStateId() {
        return stateId;
    }

    public void setStateId(Integer stateId) {
        this.stateId = stateId;
    }

    public String getCallStatus() {
        return callStatus;
    }

    public void setCallStatus(String callStatus) {
        this.callStatus = callStatus;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getPercentageListened() {
        return percentageListened;
    }

    public void setPercentageListened(String percentageListened) {
        this.percentageListened = percentageListened;
    }

    public String getCallSource() {
        return callSource;
    }

    public void setCallSource(String callSource) {
        this.callSource = callSource;
    }

    public String getSubscriptionStatus() {
        return subscriptionStatus;
    }

    public void setSubscriptionStatus(String subscriptionStatus) {
        this.subscriptionStatus = subscriptionStatus;
    }

    public Integer getDurationInPulse() {
        return durationInPulse;
    }

    public void setDurationInPulse(Integer durationInPulse) {
        this.durationInPulse = durationInPulse;
    }

    public Integer getCallStartTime() {
        return callStartTime;
    }

    public void setCallStartTime(Integer callStartTime) {
        this.callStartTime = callStartTime;
    }

    public Integer getCallEndTime() {
        return callEndTime;
    }

    public void setCallEndTime(Integer callEndTime) {
        this.callEndTime = callEndTime;
    }

    public Integer getAttemptNumber() {
        return attemptNumber;
    }

    public void setAttemptNumber(Integer attemptNumber) {
        this.attemptNumber = attemptNumber;
    }

    public Integer getSubscriptionStartDate() {
        return subscriptionStartDate;
    }

    public void setSubscriptionStartDate(Integer subscriptionStartDate) {
        this.subscriptionStartDate = subscriptionStartDate;
    }

    public Integer getMessageDuration() {
        return messageDuration;
    }

    public void setMessageDuration(Integer messageDuration) {
        this.messageDuration = messageDuration;
    }

    public Integer getCallDuration() {
        return callDuration;
    }

    public void setCallDuration(Integer callDuration) {
        this.callDuration = callDuration;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public Integer getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(Integer modificationDate) {
        this.modificationDate = modificationDate;
    }

    public String getSubscription_Id() {
        return subscription_Id;
    }

    public void setSubscription_Id(String subscription_Id) {
        this.subscription_Id = subscription_Id;
    }

    public Long getPregnancyId() {
        return pregnancyId;
    }

    public void setPregnancyId(Long pregnancyId) {
        this.pregnancyId = pregnancyId;
    }
}
