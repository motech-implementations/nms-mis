package com.beehyv.nmsreporting.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by himanshu on 06/10/17.
 */

@Entity
@Table(name="kilkari_repeat_listener_month_wise")
public class KilkariRepeatListenerMonthWise {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id", columnDefinition = "INT(11)")
    private Integer id;

    @Column(name="date", columnDefinition = "DATETIME")
    private Date date;

    @Column(name="5_calls_answered", columnDefinition = "INT(11)")
    private Integer fiveCallsAnswered;

    @Column(name="4_calls_answered", columnDefinition = "INT(11)")
    private Integer fourCallsAnswered;

    @Column(name="3_calls_answered", columnDefinition = "INT(11)")
    private Integer threeCallsAnswered;

    @Column(name="2_calls_answered", columnDefinition = "INT(11)")
    private Integer twoCallsAnswered;

    @Column(name="1_calls_answered", columnDefinition = "INT(11)")
    private Integer oneCallAnswered;

    @Column(name="0_calls_answered", columnDefinition = "INT(11)")
    private Integer noCallsAnswered;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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
}
