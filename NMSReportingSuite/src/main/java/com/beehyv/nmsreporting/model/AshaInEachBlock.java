package com.beehyv.nmsreporting.model;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;

public class AshaInEachBlock {

    @CsvBindByName(column = "flw id")
    @CsvBindByPosition(position = 0)
    private Long flwId;

    @CsvBindByName(column = "flw_name")
    @CsvBindByPosition(position = 1)
    private String ashaName;

    @CsvBindByName(column = "flw_msisdn")
    @CsvBindByPosition(position = 2)
    private String mobileNumber;

    @CsvBindByName(column = "state_name")
    @CsvBindByPosition(position = 3)
    private String state;

    @CsvBindByName(column = "district_name")
    @CsvBindByPosition(position = 4)
    private String district;

    @CsvBindByName(column = "taluka_name")
    @CsvBindByPosition(position = 5)
    private String taluka;

    @CsvBindByName(column = "village_name")
    @CsvBindByPosition(position = 6)
    private String village;

    @CsvBindByName(column = "block_name")
    @CsvBindByPosition(position = 7)
    private String block;

    @CsvBindByName(column = "healthfacility_name")
    @CsvBindByPosition(position = 8)
    private String healthfacility;

    @CsvBindByName(column = "healthsubfacility_name")
    @CsvBindByPosition(position = 9)
    private String healthsubfacility;

    @CsvBindByName(column = "start date")
    @CsvBindByPosition(position = 10)
    private String startDate;

    @CsvBindByName(column = "completion date")
    @CsvBindByPosition(position = 11)
    private String completionDate;

    /*@CsvBindByName(column = "chapter wise scores")
    @CsvBindByPosition(position = 12)
    private String chapterWiseScore;*/

    @CsvBindByName(column = "total_score")
    @CsvBindByPosition(position = 12)
    private Integer totalScore = 0;

    @CsvBindByName(column = "no of attempts")
    @CsvBindByPosition(position = 13)
    private Long noOfAttempts;

    @CsvBindByName(column = "duration")
    @CsvBindByPosition(position = 14)
    private Double durationInMinutes = 0.00;

    @CsvBindByName(column = "total duration")
    @CsvBindByPosition(position = 15)
    private Double totalDurationInMinutes = 0.00;

    public AshaInEachBlock() {
    }

    public AshaInEachBlock(Long flwId, String startDate, String completionDate, String chapterWiseScore, Long noOfAttempts, Double durationInMinutes , Double totalDurationInMinutes) {
        this.flwId = flwId;
        this.startDate = startDate;
        this.completionDate = completionDate;
        //this.chapterWiseScore = chapterWiseScore;
        this.noOfAttempts = noOfAttempts;
        this.durationInMinutes = durationInMinutes;
        this.totalDurationInMinutes = totalDurationInMinutes;
    }

    public Long getFlwId() {
        return flwId;
    }

    public void setFlwId(Long flwId) {
        this.flwId = flwId;
    }

    public String getAshaName() {
        return ashaName;
    }

    public void setAshaName(String ashaName) {
        this.ashaName = ashaName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getTaluka() {
        return taluka;
    }

    public void setTaluka(String taluka) {
        this.taluka = taluka;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }

    public String getHealthfacility() {
        return healthfacility;
    }

    public void setHealthfacility(String healthfacility) {
        this.healthfacility = healthfacility;
    }

    public String getHealthsubfacility() {
        return healthsubfacility;
    }

    public void setHealthsubfacility(String healthsubfacility) {
        this.healthsubfacility = healthsubfacility;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(String completionDate) {
        this.completionDate = completionDate;
    }

    /*public String getChapterWiseScore() {
        return chapterWiseScore;
    }

    public void setChapterWiseScore(String chapterWiseScore) {
        this.chapterWiseScore = chapterWiseScore;
    }*/

    public Long getNoOfAttempts() {
        return noOfAttempts;
    }

    public void setNoOfAttempts(Long noOfAttempts) {
        this.noOfAttempts = noOfAttempts;
    }

    public Double getDurationInMinutes() {
        return durationInMinutes;
    }

    public void setDurationInMinutes(Double durationInMinutes) {
        this.durationInMinutes = durationInMinutes;
    }

     public Double getTotalDurationInMinutes() {
        return totalDurationInMinutes;
    }

    public void setTotalDurationInMinutes(Double totalDurationInMinutes) {
        this.totalDurationInMinutes = totalDurationInMinutes;
    }

    public void setTotalScore(Integer totalScore) {
        this.totalScore = totalScore;
    }

    public Integer getTotalScore() {
        return totalScore;
    }
}
