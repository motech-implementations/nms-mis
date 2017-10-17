package com.beehyv.nmsreporting.entity;


/**
 * Created by himanshu on 06/10/17.
 */


public class KilkariMessageListenershipReportDto {

    private int id;
    private Integer locationId;
    private String locationType;
    private String locationName;
    private Integer totalBeneficiariesCalled;
    private Integer beneficiariesAnsweredAtleastOnce;
    private Integer beneficiariesAnsweredMoreThan75;
    private Integer beneficiariesAnswered50To75;
    private Integer beneficiariesAnswered25To50;
    private Integer beneficiariesAnswered1To25;
    private Integer beneficiariesAnsweredNoCalls;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getLocationId() {
        return locationId;
    }

    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
    }

    public String getLocationType() {
        return locationType;
    }

    public void setLocationType(String locationType) {
        this.locationType = locationType;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public Integer getTotalBeneficiariesCalled() {
        return totalBeneficiariesCalled;
    }

    public void setTotalBeneficiariesCalled(Integer totalBeneficiariesCalled) {
        this.totalBeneficiariesCalled = totalBeneficiariesCalled;
    }

    public Integer getBeneficiariesAnsweredAtleastOnce() {
        return beneficiariesAnsweredAtleastOnce;
    }

    public void setBeneficiariesAnsweredAtleastOnce(Integer beneficiariesAnsweredAtleastOnce) {
        this.beneficiariesAnsweredAtleastOnce = beneficiariesAnsweredAtleastOnce;
    }

    public Integer getBeneficiariesAnsweredMoreThan75() {
        return beneficiariesAnsweredMoreThan75;
    }

    public void setBeneficiariesAnsweredMoreThan75(Integer beneficiariesAnsweredMoreThan75) {
        this.beneficiariesAnsweredMoreThan75 = beneficiariesAnsweredMoreThan75;
    }

    public Integer getBeneficiariesAnswered50To75() {
        return beneficiariesAnswered50To75;
    }

    public void setBeneficiariesAnswered50To75(Integer beneficiariesAnswered50To75) {
        this.beneficiariesAnswered50To75 = beneficiariesAnswered50To75;
    }

    public Integer getBeneficiariesAnswered25To50() {
        return beneficiariesAnswered25To50;
    }

    public void setBeneficiariesAnswered25To50(Integer beneficiariesAnswered25To50) {
        this.beneficiariesAnswered25To50 = beneficiariesAnswered25To50;
    }

    public Integer getBeneficiariesAnswered1To25() {
        return beneficiariesAnswered1To25;
    }

    public void setBeneficiariesAnswered1To25(Integer beneficiariesAnswered1To25) {
        this.beneficiariesAnswered1To25 = beneficiariesAnswered1To25;
    }

    public Integer getBeneficiariesAnsweredNoCalls() {
        return beneficiariesAnsweredNoCalls;
    }

    public void setBeneficiariesAnsweredNoCalls(Integer beneficiariesAnsweredNoCalls) {
        this.beneficiariesAnsweredNoCalls = beneficiariesAnsweredNoCalls;
    }
}
