package com.beehyv.nmsreporting.entity;

public class AshaPerformanceDto {
     Long ashaStarted;
     Long ashaCompleted;

    public AshaPerformanceDto(Long ashaStarted, Long ashaCompleted) {
        this.ashaStarted = ashaStarted;
        this.ashaCompleted = ashaCompleted;
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
}
