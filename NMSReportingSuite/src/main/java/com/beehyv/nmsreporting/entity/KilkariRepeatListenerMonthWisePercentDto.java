package com.beehyv.nmsreporting.entity;

import java.util.Date;

public class KilkariRepeatListenerMonthWisePercentDto {
    private Integer id;
    private Integer month;
    private Float fiveCallsAnsweredPercent;
    private Float fourCallsAnsweredPercent;
    private Float threeCallsAnsweredPercent;
    private Float twoCallsAnsweredPercent;
    private Float oneCallAnsweredPercent;
    private Float noCallsAnsweredPercent;
    private Date date;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Float getFiveCallsAnsweredPercent() {
        return fiveCallsAnsweredPercent;
    }

    public void setFiveCallsAnsweredPercent(Float fiveCallsAnsweredPercent) {
        this.fiveCallsAnsweredPercent = fiveCallsAnsweredPercent;
    }

    public Float getFourCallsAnsweredPercent() {
        return fourCallsAnsweredPercent;
    }

    public void setFourCallsAnsweredPercent(Float fourCallsAnsweredPercent) {
        this.fourCallsAnsweredPercent = fourCallsAnsweredPercent;
    }

    public Float getThreeCallsAnsweredPercent() {
        return threeCallsAnsweredPercent;
    }

    public void setThreeCallsAnsweredPercent(Float threeCallsAnsweredPercent) {
        this.threeCallsAnsweredPercent = threeCallsAnsweredPercent;
    }

    public Float getTwoCallsAnsweredPercent() {
        return twoCallsAnsweredPercent;
    }

    public void setTwoCallsAnsweredPercent(Float twoCallsAnsweredPercent) {
        this.twoCallsAnsweredPercent = twoCallsAnsweredPercent;
    }

    public Float getOneCallAnsweredPercent() {
        return oneCallAnsweredPercent;
    }

    public void setOneCallAnsweredPercent(Float oneCallAnsweredPercent) {
        this.oneCallAnsweredPercent = oneCallAnsweredPercent;
    }

    public Float getNoCallsAnsweredPercent() {
        return noCallsAnsweredPercent;
    }

    public void setNoCallsAnsweredPercent(Float noCallsAnsweredPercent) {
        this.noCallsAnsweredPercent = noCallsAnsweredPercent;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
