package com.beehyv.nmsreporting.entity;

import java.util.Date;

public class KilkariRepeatListenerMonthWisePercentDto {
    private Integer id;
    private String month;
    private Double moreThanFiveCallsAnsweredPercent;
    private Double fiveCallsAnsweredPercent;
    private Double fourCallsAnsweredPercent;
    private Double threeCallsAnsweredPercent;
    private Double twoCallsAnsweredPercent;
    private Double oneCallAnsweredPercent;
    private Double noCallsAnsweredPercent;
    private Date date;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getMoreThanFiveCallsAnsweredPercent() {
        return moreThanFiveCallsAnsweredPercent;
    }

    public void setMoreThanFiveCallsAnsweredPercent(Double moreThanFiveCallsAnsweredPercent) {
        this.moreThanFiveCallsAnsweredPercent = moreThanFiveCallsAnsweredPercent;
    }

    public Double getFiveCallsAnsweredPercent() {
        return fiveCallsAnsweredPercent;
    }

    public void setFiveCallsAnsweredPercent(Double fiveCallsAnsweredPercent) {
        this.fiveCallsAnsweredPercent = fiveCallsAnsweredPercent;
    }

    public Double getFourCallsAnsweredPercent() {
        return fourCallsAnsweredPercent;
    }

    public void setFourCallsAnsweredPercent(Double fourCallsAnsweredPercent) {
        this.fourCallsAnsweredPercent = fourCallsAnsweredPercent;
    }

    public Double getThreeCallsAnsweredPercent() {
        return threeCallsAnsweredPercent;
    }

    public void setThreeCallsAnsweredPercent(Double threeCallsAnsweredPercent) {
        this.threeCallsAnsweredPercent = threeCallsAnsweredPercent;
    }

    public Double getTwoCallsAnsweredPercent() {
        return twoCallsAnsweredPercent;
    }

    public void setTwoCallsAnsweredPercent(Double twoCallsAnsweredPercent) {
        this.twoCallsAnsweredPercent = twoCallsAnsweredPercent;
    }

    public Double getOneCallAnsweredPercent() {
        return oneCallAnsweredPercent;
    }

    public void setOneCallAnsweredPercent(Double oneCallAnsweredPercent) {
        this.oneCallAnsweredPercent = oneCallAnsweredPercent;
    }

    public Double getNoCallsAnsweredPercent() {
        return noCallsAnsweredPercent;
    }

    public void setNoCallsAnsweredPercent(Double noCallsAnsweredPercent) {
        this.noCallsAnsweredPercent = noCallsAnsweredPercent;
    }
}
