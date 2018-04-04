package com.beehyv.nmsreporting.entity;


import java.util.Date;

/**
 * Created by himanshu on 06/10/17.
 */

public class KilkariRepeatListenerMonthWiseDto {

    private Integer id;
    private String month;
    private Integer moreThanFiveCallsAnswered;
    private Integer fiveCallsAnswered;
    private Integer fourCallsAnswered;
    private Integer threeCallsAnswered;
    private Integer twoCallsAnswered;
    private Integer oneCallAnswered;
    private Integer noCallsAnswered;
    private Integer total;
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

    public Integer getMoreThanFiveCallsAnswered() {
        return moreThanFiveCallsAnswered;
    }

    public void setMoreThanFiveCallsAnswered(Integer moreThanFiveCallsAnswered) {
        this.moreThanFiveCallsAnswered = moreThanFiveCallsAnswered;
    }

    public Integer getFiveCallsAnswered() {
        return fiveCallsAnswered;
    }

    public void setFiveCallsAnswered(Integer fiveCallsAnswered) {
        this.fiveCallsAnswered = fiveCallsAnswered;
    }

    public Integer getFourCallsAnswered() {
        return fourCallsAnswered;
    }

    public void setFourCallsAnswered(Integer fourCallsAnswered) {
        this.fourCallsAnswered = fourCallsAnswered;
    }

    public Integer getThreeCallsAnswered() {
        return threeCallsAnswered;
    }

    public void setThreeCallsAnswered(Integer threeCallsAnswered) {
        this.threeCallsAnswered = threeCallsAnswered;
    }

    public Integer getTwoCallsAnswered() {
        return twoCallsAnswered;
    }

    public void setTwoCallsAnswered(Integer twoCallsAnswered) {
        this.twoCallsAnswered = twoCallsAnswered;
    }

    public Integer getOneCallAnswered() {
        return oneCallAnswered;
    }

    public void setOneCallAnswered(Integer oneCallAnswered) {
        this.oneCallAnswered = oneCallAnswered;
    }

    public Integer getNoCallsAnswered() {
        return noCallsAnswered;
    }

    public void setNoCallsAnswered(Integer noCallsAnswered) {
        this.noCallsAnswered = noCallsAnswered;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
