package com.beehyv.nmsreporting.entity;

public class MAPerformanceCountsDto {

    Long accessedAtleastOnce;
    Integer ashasFailed;
    Long accessedNotOnce;
    Long ashasRefresherCourse;
    Long ashasActivatedInBetween;
    Long ashasDeactivatedInBetween;
    Long ashasCompletedInGivenTime;

    public Long getAccessedAtleastOnce() {
        return accessedAtleastOnce;
    }

    public void setAccessedAtleastOnce(Long accessedAtleastOnce) {
        this.accessedAtleastOnce = accessedAtleastOnce;
    }

    public Integer getAshasFailed() {
        return ashasFailed;
    }

    public void setAshasFailed(Integer ashasFailed) {
        this.ashasFailed = ashasFailed;
    }

    public Long getAccessedNotOnce() {
        return accessedNotOnce;
    }

    public void setAccessedNotOnce(Long accessedNotOnce) {
        this.accessedNotOnce = accessedNotOnce;
    }

    public Long getAshasRefresherCourse() {
        return ashasRefresherCourse;
    }

    public void setAshasRefresherCourse(Long ashasRefresherCourse) {
        this.ashasRefresherCourse = ashasRefresherCourse;
    }

    public Long getAshasActivatedInBetween() {
        return ashasActivatedInBetween;
    }

    public void setAshasActivatedInBetween(Long ashasActivatedInBetween) {
        this.ashasActivatedInBetween = ashasActivatedInBetween;
    }

    public Long getAshasDeactivatedInBetween() {
        return ashasDeactivatedInBetween;
    }

    public void setAshasDeactivatedInBetween(Long ashasDeactivatedInBetween) {
        this.ashasDeactivatedInBetween = ashasDeactivatedInBetween;
    }

    public Long getAshasCompletedInGivenTime() {
        return ashasCompletedInGivenTime;
    }

    public void setAshasCompletedInGivenTime(Long ashasCompletedInGivenTime) {
        this.ashasCompletedInGivenTime = ashasCompletedInGivenTime;
    }

}
