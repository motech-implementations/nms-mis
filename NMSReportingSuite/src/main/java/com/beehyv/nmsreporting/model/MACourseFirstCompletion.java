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

    @Column(name="flw_msisdn", columnDefinition = "BIGINT(20)")
    private Long msisdn;

    @Column(name="state_id", columnDefinition = "TINYINT(4)")
    private Integer stateId;

    @Column(name="district_id", columnDefinition = "SMALLINT(6)")
    private Integer districtId;

    @Column(name="taluka_id", columnDefinition = "SMALLINT(6)")
    private Integer talukaId;

    @Column(name="village_id", columnDefinition = "BIGINT(20)")
    private Integer villageId;

    @Column(name="block_id", columnDefinition = "BIGINT(20)")
    private Integer blockId;

    @Column(name="healthfacility_id", columnDefinition = "BIGINT(20)")
    private Integer healthFacilityId;

    @Column(name="healthsubfacility_id", columnDefinition = "BIGINT(20)")
    private Integer healthSubFacilityId;

    @Column(name="flw_name", columnDefinition = "VARCHAR(255)")
    private String fullName;

    @Column(name="external_flw_id", columnDefinition = "VARCHAR(255)")
    private Long externalFlwId;

    @Column(name="job_status", columnDefinition = "VARCHAR(255)")
    private String jobStatus;

    @Column(name="creationdate", columnDefinition = "DATE")
    private Date creationDate;

    @Column(name="first_completion", columnDefinition = "DATE")
    private Date firstCompletionDate;

    @Column(name="sent_notification")
    private Boolean sentNotification;

    @Column(name="for_month", columnDefinition = "VARCHAR(45)")
    private String forMonth;

    @Column(name="last_delivery_status")
    private String lastDeliveryStatus;

    @Column(name="modification_date")
    private Date lastModifiedDate;

    private String encryptedOTP;

    private long normalisedOTPEpoch;

    public String getLastDeliveryStatus() {
        return lastDeliveryStatus;
    }

    public void setLastDeliveryStatus(String lastDeliveryStatus) {
        this.lastDeliveryStatus = lastDeliveryStatus;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }


    public String getEncryptedOTP() {
        return encryptedOTP;
    }

    public void setEncryptedOTP(String encryptedOTP) {
        this.encryptedOTP = encryptedOTP;
    }

    public long getNormalisedOTPEpoch() {
        return normalisedOTPEpoch;
    }

    public void setNormalisedOTPEpoch(long normalisedOTPEpoch) {
        this.normalisedOTPEpoch = normalisedOTPEpoch;
    }

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

    public Integer getVillageId() {
        return villageId;
    }

    public void setVillageId(Integer villageId) {
        this.villageId = villageId;
    }

    public Integer getBlockId() {
        return blockId;
    }

    public void setBlockId(Integer blockId) {
        this.blockId = blockId;
    }

    public Integer getHealthFacilityId() {
        return healthFacilityId;
    }

    public void setHealthFacilityId(Integer healthFacilityId) {
        this.healthFacilityId = healthFacilityId;
    }

    public Integer getHealthSubFacilityId() {
        return healthSubFacilityId;
    }

    public void setHealthSubFacilityId(Integer healthSubFacilityId) {
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

    public Date getFirstCompletionDate() {
        return firstCompletionDate;
    }

    public void setFirstCompletionDate(Date firstCompletionDate) {
        this.firstCompletionDate = firstCompletionDate;
    }

    public Boolean getSentNotification() {
        return sentNotification;
    }

    public void setSentNotification(Boolean sentNotification) {
        this.sentNotification = sentNotification;
    }

    public String getForMonth() {
        return forMonth;
    }

    public void setForMonth(String forMonth) {
        this.forMonth = forMonth;
    }

    @Override
    public String toString() {
        return "MACourseFirstCompletion{" +
                "Id=" + Id +
                ", flwId=" + flwId +
                ", msisdn=" + msisdn +
                ", stateId=" + stateId +
                ", districtId=" + districtId +
                ", talukaId=" + talukaId +
                ", villageId=" + villageId +
                ", blockId=" + blockId +
                ", healthFacilityId=" + healthFacilityId +
                ", healthSubFacilityId=" + healthSubFacilityId +
                ", fullName='" + fullName + '\'' +
                ", externalFlwId=" + externalFlwId +
                ", jobStatus='" + jobStatus + '\'' +
                ", creationDate=" + creationDate +
                ", firstCompletionDate=" + firstCompletionDate +
                ", sentNotification=" + sentNotification +
                ", forMonth='" + forMonth + '\'' +
                ", lastDeliveryStatus='" + lastDeliveryStatus + '\'' +
                ", lastModifiedDate=" + lastModifiedDate +
                ", encryptedOTP='" + encryptedOTP + '\'' +
                ", normalisedOTPEpoch=" + normalisedOTPEpoch +
                '}';
    }
}
