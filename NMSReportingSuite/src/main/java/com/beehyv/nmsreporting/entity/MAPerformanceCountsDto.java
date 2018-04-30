package com.beehyv.nmsreporting.entity;

public class MAPerformanceCountsDto {

    Long accessedAtleastOnce;
    Integer ashasFailed;
    Long accessedNotOnce;

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
}
