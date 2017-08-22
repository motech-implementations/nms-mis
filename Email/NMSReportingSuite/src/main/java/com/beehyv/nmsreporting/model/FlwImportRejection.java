package com.beehyv.nmsreporting.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by beehyv on 3/8/17.
 */
@Entity
@Table(name = "flw_import_rejection")
public class FlwImportRejection {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "state_id")
    private Integer stateId;

    @Column(name = "district_id")
    private Integer districtId;

    @Column(name = "district_name")
    private String districtName;

    @Column(name = "taluka_id")
    private String talukaId;

    @Column(name = "taluka_name")
    private String talukaName;

    @Column(name = "health_block_id")
    private Integer healthBlockId;

    @Column(name = "health_block_name")
    private String healthBlockName;

    @Column(name = "phc_id")
    private Long phcId;

    @Column(name = "phc_name")
    private String phcName;

    @Column(name = "subcentre_id")
    private Long subcentreId;

    @Column(name = "subcentre_name")
    private String subcentreName;

    @Column(name = "village_id")
    private Long villageId;

    @Column(name = "village_name")
    private String villageName;

    @Column(name = "flw_id")
    private Long flwId;

    @Column(name = "msisdn")
    private String msisdn;

    @Column(name = "gf_name")
    private String gfName;

    @Column(name = "gf_status")
    private String gfStatus;

    @Column(name = "exec_date")
    private String execDate;

    @Column(name = "reg_date")
    private String regDate;

    @Column(name = "sex")
    private String sex;

    @Column(name = "type")
    private String type;

    @Column(name = "sms_reply")
    private String smsReply;

    @Column(name = "aadhar_no")
    private Integer aadharNo;

    @Column(name = "created_on")
    private String createdOn;

    @Column(name = "update_on")
    private String updatedOn;

    @Column(name = "bank_id")
    private Integer bankId;

    @Column(name = "branch_name")
    private String branchName;

    @Column(name = "ifsc_id_code")
    private String ifscIdCode;

    @Column(name = "bank_name")
    private String bankName;

    @Column(name = "account_number")
    private String accountNumber;

    @Column(name = "is_aadhar_linked")
    private Boolean isAadharLinked;

    @Column(name = "verify_date")
    private String verifyDate;

    @Column(name = "verifier_name")
    private String verifierName;

    @Column(name = "verified_id")
    private Integer verifierId;

    @Column(name = "call_ans")
    private Boolean callAns;

    @Column(name = "is_phone_correct")
    private Boolean isPhoneNoCorrect;

    @Column(name = "no_call_reason")
    private Integer noCallReason;

    @Column(name = "no_phone_reason")
    private Integer noPhoneReason;

    @Column(name = "verifier_remarks")
    private String verifierRemarks;

    @Column(name = "gf_address")
    private String gfAddress;

    @Column(name = "husband_name")
    private String husbandName;

    @Column(name = "accepted")
    private Boolean accepted;

    @Column(name = "rejection_reason")
    private String rejectionReason;

    @Column(name = "source")
    private String source;

    @Column(name = "action")
    private String action;

    @Column(name = "creation_date")
    private Date creationDate;

    @Column(name = "modification_date")
    private Date modificationDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public String getTalukaId() {
        return talukaId;
    }

    public void setTalukaId(String talukaId) {
        this.talukaId = talukaId;
    }

    public String getTalukaName() {
        return talukaName;
    }

    public void setTalukaName(String talukaName) {
        this.talukaName = talukaName;
    }

    public Integer getHealthBlockId() {
        return healthBlockId;
    }

    public void setHealthBlockId(Integer healthBlockId) {
        this.healthBlockId = healthBlockId;
    }

    public String getHealthBlockName() {
        return healthBlockName;
    }

    public void setHealthBlockName(String healthBlockName) {
        this.healthBlockName = healthBlockName;
    }

    public Long getPhcId() {
        return phcId;
    }

    public void setPhcId(Long phcId) {
        this.phcId = phcId;
    }

    public String getPhcName() {
        return phcName;
    }

    public void setPhcName(String phcName) {
        this.phcName = phcName;
    }

    public Long getSubcentreId() {
        return subcentreId;
    }

    public void setSubcentreId(Long subcentreId) {
        this.subcentreId = subcentreId;
    }

    public String getSubcentreName() {
        return subcentreName;
    }

    public void setSubcentreName(String subcentreName) {
        this.subcentreName = subcentreName;
    }

    public Long getVillageId() {
        return villageId;
    }

    public void setVillageId(Long villageId) {
        this.villageId = villageId;
    }

    public String getVillageName() {
        return villageName;
    }

    public void setVillageName(String villageName) {
        this.villageName = villageName;
    }

    public Long getFlwId() {
        return flwId;
    }

    public void setFlwId(Long flwId) {
        this.flwId = flwId;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getGfName() {
        return gfName;
    }

    public void setGfName(String gfName) {
        this.gfName = gfName;
    }

    public String getGfStatus() {
        return gfStatus;
    }

    public void setGfStatus(String gfStatus) {
        this.gfStatus = gfStatus;
    }

    public String getExecDate() {
        return execDate;
    }

    public void setExecDate(String execDate) {
        this.execDate = execDate;
    }

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSmsReply() {
        return smsReply;
    }

    public void setSmsReply(String smsReply) {
        this.smsReply = smsReply;
    }

    public Integer getAadharNo() {
        return aadharNo;
    }

    public void setAadharNo(Integer aadharNo) {
        this.aadharNo = aadharNo;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public String getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(String updatedOn) {
        this.updatedOn = updatedOn;
    }

    public Integer getBankId() {
        return bankId;
    }

    public void setBankId(Integer bankId) {
        this.bankId = bankId;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getIfscIdCode() {
        return ifscIdCode;
    }

    public void setIfscIdCode(String ifscIdCode) {
        this.ifscIdCode = ifscIdCode;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Boolean getAadharLinked() {
        return isAadharLinked;
    }

    public void setAadharLinked(Boolean aadharLinked) {
        isAadharLinked = aadharLinked;
    }

    public String getVerifyDate() {
        return verifyDate;
    }

    public void setVerifyDate(String verifyDate) {
        this.verifyDate = verifyDate;
    }

    public String getVerifierName() {
        return verifierName;
    }

    public void setVerifierName(String verifierName) {
        this.verifierName = verifierName;
    }

    public Integer getVerifierId() {
        return verifierId;
    }

    public void setVerifierId(Integer verifierId) {
        this.verifierId = verifierId;
    }

    public Boolean getCallAns() {
        return callAns;
    }

    public void setCallAns(Boolean callAns) {
        this.callAns = callAns;
    }

    public Boolean getPhoneNoCorrect() {
        return isPhoneNoCorrect;
    }

    public void setPhoneNoCorrect(Boolean phoneNoCorrect) {
        isPhoneNoCorrect = phoneNoCorrect;
    }

    public Integer getNoCallReason() {
        return noCallReason;
    }

    public void setNoCallReason(Integer noCallReason) {
        this.noCallReason = noCallReason;
    }

    public Integer getNoPhoneReason() {
        return noPhoneReason;
    }

    public void setNoPhoneReason(Integer noPhoneReason) {
        this.noPhoneReason = noPhoneReason;
    }

    public String getVerifierRemarks() {
        return verifierRemarks;
    }

    public void setVerifierRemarks(String verifierRemarks) {
        this.verifierRemarks = verifierRemarks;
    }

    public String getGfAddress() {
        return gfAddress;
    }

    public void setGfAddress(String gfAddress) {
        this.gfAddress = gfAddress;
    }

    public String getHusbandName() {
        return husbandName;
    }

    public void setHusbandName(String husbandName) {
        this.husbandName = husbandName;
    }

    public Boolean getAccepted() {
        return accepted;
    }

    public void setAccepted(Boolean accepted) {
        this.accepted = accepted;
    }

    public String getRejectionReason() {
        return rejectionReason;
    }

    public void setRejectionReason(String rejectionReason) {
        this.rejectionReason = rejectionReason;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(Date modificationDate) {
        this.modificationDate = modificationDate;
    }
}

