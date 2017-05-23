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
    @Column(name="id", columnDefinition = "BIGINT(20)")
    private Integer flwId;

    @Column(name="flw_name", columnDefinition = "VARCHAR(255)")
    private String fullName;

    @Column(name="flw_msisdn", columnDefinition = "BIGINT(10)")
    private String mobileNumber;

    @Column(name="external_flw_id", columnDefinition = "BIGINT(20)")
    private String externalFlwId;

    @Column(name="flw_designation", columnDefinition = "BOOLEAN")
    private String designation;

    @Column(name="language", columnDefinition = "TINYINT(4)")
    private String language;

    @Column(name="flw_status", columnDefinition = "TINYINT(4)")
    private String status;

    @ManyToOne
    @JoinColumn(name="state_id", columnDefinition = "TINYINT")
    private State state;

    @ManyToOne
    @JoinColumn(name="district_id", columnDefinition = "SMALLINT(6)")
    private District district;

    @ManyToOne
    @JoinColumn(name="taluka_id", columnDefinition = "SMALLINT(6)")
    private Taluka taluka;

    @Column(name="village_id", columnDefinition = "INT(11)")
    private Integer village;

    @ManyToOne
    @JoinColumn(name="block_id", columnDefinition = "INT(11)")
    private Block block;

    @Column(name="facility_id", columnDefinition = "INT(11)")
    private Integer facility;

    @Column(name="subfacility_id", columnDefinition = "INT(11)")
    private Integer subfacility;

    @Column(name="creation_date", columnDefinition = "DATE")
    private Date creationDate;

    @Column(name="last_modified", columnDefinition = "DATE")
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public District getDistrict() {
        return district;
    }

    public void setDistrict(District district) {
        this.district = district;
    }

    public Taluka getTaluka() {
        return taluka;
    }

    public void setTaluka(Taluka taluka) {
        this.taluka = taluka;
    }

    public Block getBlock() {
        return block;
    }

    public void setBlock(Block block) {
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
