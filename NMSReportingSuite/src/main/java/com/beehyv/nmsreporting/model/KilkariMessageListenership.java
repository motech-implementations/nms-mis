package com.beehyv.nmsreporting.model;


import javax.persistence.*;
import java.util.Date;

/**
 * Created by himanshu on 06/10/17.
 */
@Entity
@Table(name="agg_kilkari_message_listenership")

public class KilkariMessageListenership {

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

    @Column(name="answered_atleast_one_call", columnDefinition = "BIGINT(20)")
    private Long answeredAtleastOneCall;

    @Column(name="answered_more_than_75_per", columnDefinition = "BIGINT(20)")
    private Long answeredMoreThan75Per;

    @Column(name="answered_50_to_75_per", columnDefinition = "BIGINT(20)")
    private Long answered50To75Per;

    @Column(name="answered_25_to_50_per", columnDefinition = "BIGINT(20)")
    private Long answered25To50Per;

    @Column(name="answered_1_to_25_per", columnDefinition = "BIGINT(20)")
    private Long answered1To25Per;

    @Column(name="answered_no_calls", columnDefinition = "BIGINT(20)")
    private Long answeredNoCalls;

    @Column(name="total_beneficiaries_called", columnDefinition = "BIGINT(20)")
    private Long totalBeneficiariesCalled;


    public KilkariMessageListenership(Integer id, String locationType, Long locationId, Date date, Long answeredAtleastOneCall, Long answered1To25Per, Long answered25To50Per, Long answered50To75Per, Long answeredMoreThan75Per, Long answeredNoCalls, Long totalBeneficiariesCalled){
        this.id = id;
        this.locationId = locationId;
        this.locationType = locationType;
        this.date = date;
        this.answeredAtleastOneCall = answeredAtleastOneCall;
        this.answered1To25Per = answered1To25Per;
        this.answered25To50Per = answered25To50Per;
        this.answered50To75Per = answered50To75Per;
        this.answeredMoreThan75Per = answeredMoreThan75Per;
        this.answeredNoCalls = answeredNoCalls;
        this.totalBeneficiariesCalled = totalBeneficiariesCalled;
    }

    public KilkariMessageListenership(){

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getAnsweredAtleastOneCall() {
        return answeredAtleastOneCall;
    }

    public void setAnsweredAtleastOneCall(Long answeredAtleastOneCall) {
        this.answeredAtleastOneCall = answeredAtleastOneCall;
    }

    public Long getAnsweredMoreThan75Per() {
        return answeredMoreThan75Per;
    }

    public void setAnsweredMoreThan75Per(Long answeredMoreThan75Per) {
        this.answeredMoreThan75Per = answeredMoreThan75Per;
    }

    public Long getAnswered50To75Per() {
        return answered50To75Per;
    }

    public void setAnswered50To75Per(Long answered50To75Per) {
        this.answered50To75Per = answered50To75Per;
    }

    public Long getAnswered25To50Per() {
        return answered25To50Per;
    }

    public void setAnswered25To50Per(Long answered25To50Per) {
        this.answered25To50Per = answered25To50Per;
    }

    public Long getAnswered1To25Per() {
        return answered1To25Per;
    }

    public void setAnswered1To25Per(Long answered1To25Per) {
        this.answered1To25Per = answered1To25Per;
    }

    public Long getAnsweredNoCalls() {
        return answeredNoCalls;
    }

    public void setAnsweredNoCalls(Long answeredNoCalls) {
        this.answeredNoCalls = answeredNoCalls;
    }

    public Long getTotalBeneficiariesCalled() {
        return totalBeneficiariesCalled;
    }

    public void setTotalBeneficiariesCalled(Long totalBeneficiariesCalled) {
        this.totalBeneficiariesCalled = totalBeneficiariesCalled;
    }
}
