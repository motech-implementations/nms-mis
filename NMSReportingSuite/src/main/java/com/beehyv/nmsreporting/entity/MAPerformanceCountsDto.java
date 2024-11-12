package com.beehyv.nmsreporting.entity;

public class MAPerformanceCountsDto {

    Long accessedAtleastOnce;
    Integer ashasFailed;
    Long accessedNotOnce;
    Long ashasRefresherCourse;
    Long ashasActivatedInBetween;
    Long ashasDeactivatedInBetween;
    Long ashasCompletedInGivenTime;
    Long ashaDeactivatedStartedCourseInBetweenCount;
    Long ashaDeactivatedCompletedCourseInBetweenCount;
    Long ashasStarted;
    Long ashasCompleted;

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

    public Long getAshaDeactivatedStartedCourseInBetweenCount() {
        return ashaDeactivatedStartedCourseInBetweenCount;
    }

    public void setAshaDeactivatedStartedCourseInBetweenCount(Long ashaDeactivatedStartedCourseInBetweenCount) {
        this.ashaDeactivatedStartedCourseInBetweenCount = ashaDeactivatedStartedCourseInBetweenCount;
    }

    public Long getAshaDeactivatedCompletedCourseInBetweenCount() {
        return ashaDeactivatedCompletedCourseInBetweenCount;
    }

    public void setAshaDeactivatedCompletedCourseInBetweenCount(Long ashaDeactivatedCompletedCourseInBetweenCount) {
        this.ashaDeactivatedCompletedCourseInBetweenCount = ashaDeactivatedCompletedCourseInBetweenCount;
    }

    public Long getAshasStarted() {
        return ashasStarted;
    }

    public void setAshasStarted(Long ashasStarted) {
        this.ashasStarted = ashasStarted;
    }

    public Long getAshasCompleted() {
        return ashasCompleted;
    }

    public void setAshasCompleted(Long ashasCompleted) {
        this.ashasCompleted = ashasCompleted;
    }
}
