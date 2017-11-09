package com.beehyv.nmsreporting.entity;


/**
 * Created by himanshu on 06/10/17.
 */


public class KilkariMessageListenershipReportDto {

    private int id;
    private Long locationId;
    private String locationType;
    private String locationName;
    private Long totalBeneficiariesCalled;
    private Long beneficiariesAnsweredAtleastOnce;
    private Long beneficiariesAnsweredMoreThan75;
    private Long beneficiariesAnswered50To75;
    private Long beneficiariesAnswered25To50;
    private Long beneficiariesAnswered1To25;
    private Long beneficiariesAnsweredNoCalls;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
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

    public Long getTotalBeneficiariesCalled() {
        return totalBeneficiariesCalled;
    }

    public void setTotalBeneficiariesCalled(Long totalBeneficiariesCalled) {
        this.totalBeneficiariesCalled = totalBeneficiariesCalled;
    }

    public Long getBeneficiariesAnsweredAtleastOnce() {
        return beneficiariesAnsweredAtleastOnce;
    }

    public void setBeneficiariesAnsweredAtleastOnce(Long beneficiariesAnsweredAtleastOnce) {
        this.beneficiariesAnsweredAtleastOnce = beneficiariesAnsweredAtleastOnce;
    }

    public Long getBeneficiariesAnsweredMoreThan75() {
        return beneficiariesAnsweredMoreThan75;
    }

    public void setBeneficiariesAnsweredMoreThan75(Long beneficiariesAnsweredMoreThan75) {
        this.beneficiariesAnsweredMoreThan75 = beneficiariesAnsweredMoreThan75;
    }

    public Long getBeneficiariesAnswered50To75() {
        return beneficiariesAnswered50To75;
    }

    public void setBeneficiariesAnswered50To75(Long beneficiariesAnswered50To75) {
        this.beneficiariesAnswered50To75 = beneficiariesAnswered50To75;
    }

    public Long getBeneficiariesAnswered25To50() {
        return beneficiariesAnswered25To50;
    }

    public void setBeneficiariesAnswered25To50(Long beneficiariesAnswered25To50) {
        this.beneficiariesAnswered25To50 = beneficiariesAnswered25To50;
    }

    public Long getBeneficiariesAnswered1To25() {
        return beneficiariesAnswered1To25;
    }

    public void setBeneficiariesAnswered1To25(Long beneficiariesAnswered1To25) {
        this.beneficiariesAnswered1To25 = beneficiariesAnswered1To25;
    }

    public Long getBeneficiariesAnsweredNoCalls() {
        return beneficiariesAnsweredNoCalls;
    }

    public void setBeneficiariesAnsweredNoCalls(Long beneficiariesAnsweredNoCalls) {
        this.beneficiariesAnsweredNoCalls = beneficiariesAnsweredNoCalls;
    }
}
