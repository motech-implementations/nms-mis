package com.beehyv.nmsreporting.entity;

/**
 * Created by beehyv on 9/10/17.
 */
public class KilkariAggregateBeneficiariesDto {

    private Integer id;
    private String locationType;
    private String locationName;
    private Long locationId;
    private Long beneficiariesCalled;
    private Long answeredAtleastOneCall;
    private Long selfDeactivated;
    private Long notAnswering;
    private Long lowListenership;
    private Long systemDeactivation;
    private Long motherCompletion;
    private Long childCompletion;
    private Long calledInbox;
    private Long joinedSubscription;

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

    public Long getBeneficiariesCalled() {
        return beneficiariesCalled;
    }

    public void setBeneficiariesCalled(Long beneficiariesCalled) {
        this.beneficiariesCalled = beneficiariesCalled;
    }

    public Long getAnsweredAtleastOneCall() {
        return answeredAtleastOneCall;
    }

    public void setAnsweredAtleastOneCall(Long answeredAtleastOneCall) {
        this.answeredAtleastOneCall = answeredAtleastOneCall;
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

    public Long getCalledInbox() {
        return calledInbox;
    }

    public void setCalledInbox(Long calledInbox) {
        this.calledInbox = calledInbox;
    }

    public Long getJoinedSubscription() {
        return joinedSubscription;
    }

    public void setJoinedSubscription(Long joinedSubscription) {
        this.joinedSubscription = joinedSubscription;
    }
}
