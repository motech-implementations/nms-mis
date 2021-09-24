package com.beehyv.nmsreporting.entity;

public class MAPerformanceCountsDto {

    Long accessedAtleastOnce;
    Integer ashasFailed;
    Long accessedNotOnce;
    Long ashasRefresherCourse;
    Long ashasDeactivated;
    Long ashasActivated;

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

    public Long getAshasDeactivated() {
        return ashasDeactivated;
    }

    public void setAshasDeactivated(Long ashasDeactivated) {
        this.ashasDeactivated = ashasDeactivated;
    }

    public Long getAshasActivated() {
        return ashasActivated;
    }

    public void setAshasActivated(Long ashasActivated) {
        this.ashasActivated = ashasActivated;
    }
}
