package com.beehyv.nmsreporting.entity;

public class AshaPerformanceDto {
     Long ashaStarted;
     Long ashaCompleted;
     Long ashaRegistered;

    public AshaPerformanceDto(Long ashaStarted, Long ashaCompleted, Long ashaRegistered) {
        this.ashaStarted = ashaStarted;
        this.ashaCompleted = ashaCompleted;
        this.ashaRegistered = ashaRegistered;
    }

    public Long getAshaStarted() {
        return ashaStarted;
    }

    public Long getAshaCompleted() {
        return ashaCompleted;
    }

    public void setAshaStarted(Long ashaStarted) {
        this.ashaStarted = ashaStarted;
    }

    public void setAshaCompleted(Long ashaCompleted) {
        this.ashaCompleted = ashaCompleted;
    }

    public Long getAshaRegistered() {
        return ashaRegistered;
    }

    public void setAshaRegistered(Long ashaRegistered) {
        this.ashaRegistered = ashaRegistered;
    }
}
