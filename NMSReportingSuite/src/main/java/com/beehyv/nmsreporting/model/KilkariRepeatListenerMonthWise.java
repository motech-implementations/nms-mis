package com.beehyv.nmsreporting.model;

import com.beehyv.nmsreporting.entity.KilkariRepeatListenerMonthWiseDto;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by himanshu on 06/10/17.
 */

@Entity
@Table(name="agg_kilkari_repeat_listener_month_wise")
public class KilkariRepeatListenerMonthWise {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id", columnDefinition = "INT(11)")
    private Integer id;

    @Column(name="location_type", columnDefinition = "VARCHAR(45)")
    private String locationType;

    @Column(name="location_id", columnDefinition = "BIGINT(20)")
    private Long locationId;

    @Column(name="date", columnDefinition = "DATE")
    private Date date;

    @Column(name="greater_than_5", columnDefinition = "INT(11)")
    private Integer moreThanFiveCallsAnswered;

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

    public KilkariRepeatListenerMonthWise(){

    }

    public KilkariRepeatListenerMonthWise(Integer id, Date date, Integer fiveCallsAnswered, Integer fourCallsAnswered, Integer threeCallsAnswered, Integer twoCallsAnswered, Integer oneCallAnswered, Integer noCallsAnswered, Integer moreThanFiveCallsAnswered){
        this.id = id;
        this.date = date;
        this.fiveCallsAnswered = fiveCallsAnswered;
        this.fourCallsAnswered = fourCallsAnswered;
        this.threeCallsAnswered = threeCallsAnswered;
        this.twoCallsAnswered = twoCallsAnswered;
        this.oneCallAnswered = oneCallAnswered;
        this.noCallsAnswered = noCallsAnswered;
        this.moreThanFiveCallsAnswered = moreThanFiveCallsAnswered;
    }

    public Integer getId() {
        return id;
    }

    public String getLocationType() {
        return locationType;
    }

    public void setLocationType(String locationType) {
        this.locationType = locationType;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
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

    public Integer getMoreThanFiveCallsAnswered() {
        return moreThanFiveCallsAnswered;
    }

    public void setMoreThanFiveCallsAnswered(Integer moreThanFiveCallsAnswered) {
        this.moreThanFiveCallsAnswered = moreThanFiveCallsAnswered;
    }
}
