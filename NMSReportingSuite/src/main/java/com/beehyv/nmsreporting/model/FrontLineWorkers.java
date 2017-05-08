package com.beehyv.nmsreporting.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by beehyv on 3/5/17.
 */
@Entity
@Table(name="front_line_worker")
public class FrontLineWorkers {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="flw_id")
    private Integer flwId;

    @Column(name="flw_name")
    private String fullName;

    @Column(name="flw_msisdn")
    private String mobileNumber;

    @Column(name="external_flw_id")
    private String externalFlwId;

    @Column(name="flw_designation")
    private String designation;

    @Column(name="language")
    private String language;

    @Column(name="flw_status")
    private String jobStatus;

    @Column(name="state_id")
    private Integer state;

    @Column(name="district_id")
    private Integer district;

    @Column(name="taluka_id")
    private Integer taluka;

    @Column(name="village_id")
    private Integer village;

    @Column(name="block_id")
    private Integer block;

    @Column(name="facility_id")
    private Integer facility;

    @Column(name="subfacility_id")
    private Integer subfacility;

    @Column(name="creation_Date")
    private Date creationDate;

    @Column(name="lastModified_Date")
    private Date lastModifiedDate;

    public Integer getFlwId() {
        return flwId;
    }

    public void setFlwId(Integer flwId) {
        this.flwId = flwId;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getExternalFlwId() {
        return externalFlwId;
    }

    public void setExternalFlwId(String externalFlwId) {
        this.externalFlwId = externalFlwId;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(String jobStatus) {
        this.jobStatus = jobStatus;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getDistrict() {
        return district;
    }

    public void setDistrict(Integer district) {
        this.district = district;
    }

    public Integer getTaluka() {
        return taluka;
    }

    public void setTaluka(Integer taluka) {
        this.taluka = taluka;
    }

    public Integer getBlock() {
        return block;
    }

    public void setBlock(Integer block) {
        this.block = block;
    }

    public Integer getVillage() {
        return village;
    }

    public void setVillage(Integer village) {
        this.village = village;
    }

    public Integer getFacility() {
        return facility;
    }

    public void setFacility(Integer facility) {
        this.facility = facility;
    }

    public Integer getSubfacility() {
        return subfacility;
    }

    public void setSubfacility(Integer subfacility) {
        this.subfacility = subfacility;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }
}
