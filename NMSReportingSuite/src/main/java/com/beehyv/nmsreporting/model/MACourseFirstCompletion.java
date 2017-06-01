package com.beehyv.nmsreporting.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by beehyv on 31/5/17.
 */
@Entity
@Table(name="ma_course_completion_first")
public class MACourseFirstCompletion {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private Integer Id;

    @Column(name="flw_id", columnDefinition = "BIGINT(20)")
    private Long flwId;

    @Column(name="`flw_msisdn", columnDefinition = "BIGINT(20)")
    private Long msisdn;

    @Column(name="state_id", columnDefinition = "TINYINT(4)")
    private Integer stateId;

    @Column(name="district_id", columnDefinition = "SMALLINT(6)")
    private Integer districtId;

    @Column(name="taluka_id", columnDefinition = "SMALLINT(6)")
    private Integer talukaId;

    @Column(name="`village_id", columnDefinition = "BIGINT(20)")
    private Long villageId;

    @Column(name="`block_id", columnDefinition = "BIGINT(20)")
    private Long blockId;

    @Column(name="healthfacility_id", columnDefinition = "BIGINT(20)")
    private Long healthFacilityId;

    @Column(name="healthsubfacility_id", columnDefinition = "BIGINT(20)")
    private Long healthSubFacilityId;

    @Column(name="flw_name", columnDefinition = "VARCHAR(255)")
    private String fullName;

    @Column(name="external_flw_id", columnDefinition = "BIGINT(20)")
    private Long externalFlwId;

    @Column(name="job_status", columnDefinition = "VARCHAR(255)")
    private String jobStatus;

    @Column(name="creation_date", columnDefinition = "DATE")
    private Date creationDate;

    @Column(name="last_modified", columnDefinition = "DATE")
    private Date lastModifiedDate;

    @Column(name="sent_notification")
    private Boolean senrNotification;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public Long getFlwId() {
        return flwId;
    }

    public void setFlwId(Long flwId) {
        this.flwId = flwId;
    }

    public Long getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(Long msisdn) {
        this.msisdn = msisdn;
    }

    public Integer getStateId() {
        return stateId;
    }

    public void setStateId(Integer stateId) {
        this.stateId = stateId;
    }

    public Integer getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Integer districtId) {
        this.districtId = districtId;
    }

    public Integer getTalukaId() {
        return talukaId;
    }

    public void setTalukaId(Integer talukaId) {
        this.talukaId = talukaId;
    }

    public Long getVillageId() {
        return villageId;
    }

    public void setVillageId(Long villageId) {
        this.villageId = villageId;
    }

    public Long getBlockId() {
        return blockId;
    }

    public void setBlockId(Long blockId) {
        this.blockId = blockId;
    }

    public Long getHealthFacilityId() {
        return healthFacilityId;
    }

    public void setHealthFacilityId(Long healthFacilityId) {
        this.healthFacilityId = healthFacilityId;
    }

    public Long getHealthSubFacilityId() {
        return healthSubFacilityId;
    }

    public void setHealthSubFacilityId(Long healthSubFacilityId) {
        this.healthSubFacilityId = healthSubFacilityId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Long getExternalFlwId() {
        return externalFlwId;
    }

    public void setExternalFlwId(Long externalFlwId) {
        this.externalFlwId = externalFlwId;
    }

    public String getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(String jobStatus) {
        this.jobStatus = jobStatus;
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

    public Boolean getSenrNotification() {
        return senrNotification;
    }

    public void setSenrNotification(Boolean senrNotification) {
        this.senrNotification = senrNotification;
    }
}
