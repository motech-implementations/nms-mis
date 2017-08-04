package com.beehyv.nmsreporting.model;


import javax.persistence.*;
import java.util.Date;


/**
 * Created by beehyv on 13/7/17.
 */
@Entity
@Table(name="child_import_rejection")
public class MotherImportRejection {

    @Id
    @Column(name="id")
    private Long id;

    @Column(name = "state_id")
    private Long stateId;

    @Column(name = "district_id")
    private Long districtId;

    @Column(name = "district_name")
    private String districtName;

    @Column(name = "phc_id")
    private Long phcId;

    @Column(name = "phc_name")
    private String phcName;

    @Column(name = "subcentre_id")
    private Long subcentreId;

    @Column(name = "taluka_id")
    private String talukaId;

    @Column(name = "taluka_name")
    private String talukaName;

    @Column(name = "health_block_id")
    private Long healthBlockId;

    @Column(name = "health_block_name")
    private String healthBlockName;

    @Column(name = "subcentre_name")
    private String subcentreName;

    @Column(name = "village_id")
    private Long villageId;

    @Column(name = "village_name")
    private String villageName;

    @Column(name = "yr")
    private Integer yr;

    @Column(name = "gp_village")
    private String gPVillage;

    @Column(name = "address")
    private String address;

    @Column(name = "id_no")
    private String idNo;

    @Column(name = "name")
    private String name;

    @Column(name = "husband_name")
    private String husbandName;

    @Column(name = "phone_number_of_whom")
    private String phoneNumberWhom;

    @Column(name = "whom_phone_number")
    private String whomPhoneNumber;

    @Column(name = "birth_date")
    private String birthDate;

    @Column(name = "jsy_benificiary")
    private String jSYBeneficiary;

    @Column(name = "caste")
    private String caste;

    @Column(name = "subcenter_name1")
    private String subcenterName1;

    @Column(name = "anm_name")
    private String aNMName;

    @Column(name = "anm_phone")
    private String aNMPhone;

    @Column(name = "asha_name")
    private String ashaName;

    @Column(name = "asha_phone")
    private String ashaPhone;

    @Column(name = "delivery_link_facility")
    private String deliveryLnkFacility;

    @Column(name = "facility_name")
    private String facilityName;

    @Column(name = "lmp_date")
    private String lmpDate;

    @Column(name = "anc1_date")
    private String aNC1Date;

    @Column(name = "anc2_date")
    private String aNC2Date;

    @Column(name = "anc3_date")
    private String aNC3Date;

    @Column(name = "anc4_date")
    private String aNC4Date;

    @Column(name = "tt1_date")
    private String tT1Date;

    @Column(name = "tt2_date")
    private String tT2Date;

    @Column(name = "tt_booster_date")
    private String tTBoosterDate;

    @Column(name = "ifa100_given_date")
    private String iFA100GivenDate;

    @Column(name = "anemia")
    private String anemia;

    @Column(name = "anc_complication")
    private String aNCComplication;

    @Column(name = "rti_sti")
    private String rTISTI;

    @Column(name = "dly_date")
    private String dlyDate;

    @Column(name = "dly_pace_home_type")
    private String dlyPlaceHomeType;

    @Column(name = "dly_public_place")
    private String dlyPlacePublic;

    @Column(name = "dly_place_private")
    private String dlyPlacePrivate;

    @Column(name = "dly_type")
    private String dlyType;

    @Column(name = "dly_complication")
    private String dlyComplication;

    @Column(name = "discharge_date")
    private String dischargeDate;

    @Column(name = "jsy_paid_date")
    private String jSYPaidDate;

    @Column(name = "abortion")
    private String abortion;

    @Column(name = "pnc_home_visit")
    private String pNCHomeVisit;

    @Column(name = "pnc_complication")
    private String pNCComplication;

    @Column(name = "ppc_method")
    private String pPCMethod;

    @Column(name = "pnc_checkup")
    private String pNCCheckup;

    @Column(name = "outcome_nos")
    private Integer outcomeNos;

    @Column(name = "child1_name")
    private String child1Name;

    @Column(name = "child1_sex")
    private String child1Sex;

    @Column(name = "child1_wt")
    private Double child1Wt;

    @Column(name = "child1_brest_feeding")
    private String child1Brestfeeding;

    @Column(name = "child2_name")
    private String child2Name;

    @Column(name = "child2_sex")
    private String child2Sex;

    @Column(name = "child2_wt")
    private Double child2Wt;

    @Column(name = "child2_brest_feeding")
    private String child2Brestfeeding;

    @Column(name = "child3_name")
    private String child3Name;

    @Column(name = "child3_sex")
    private String child3Sex;

    @Column(name = "child3_wt")
    private Double child3Wt;

    @Column(name = "child3_brest_feeding")
    private String child3Brestfeeding;

    @Column(name = "child4_name")
    private String child4Name;

    @Column(name = "child4_sex")
    private String child4Sex;

    @Column(name = "child4_wt")
    private Double child4Wt;

    @Column(name = "child4_brest_feeding")
    private String child4Brestfeeding;

    @Column(name = "age")
    private Integer age;

    @Column(name = "mthr_reg_date")
    private String mTHRREGDATE;

    @Column(name = "last_update_date")
    private String lastUpdateDate;

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "anm_id")
    private Integer aNMID;

    @Column(name = "asha_id")
    private Integer aSHAID;

    @Column(name = "call_ans")
    private Boolean callAns;

    @Column(name = "no_call_reason")
    private Integer noCallReason;

    @Column(name = "no_phone_reason")
    private Integer noPhoneReason;

    @Column(name = "created_by")
    private Integer createdBy;

    @Column(name = "updated_by")
    private Integer updatedBy;

    @Column(name = "aadhar_no")
    private Integer aadharNo;

    @Column(name = "bpl_apl")
    private Integer bPLAPL;

    @Column(name = "eid")
    private Integer eID;

    @Column(name = "eid_time")
    private String eIDTime;

    @Column(name = "entry_type")
    private Integer entryType;

    @Column(name = "registration_no")
    private String registrationNo;

    @Column(name = "case_no")
    private Long caseNo;

    @Column(name = "mobile_no")
    private String mobileNo;

    @Column(name = "abortion_type")
    private String abortionType;

    @Column(name = "delivery_out_comes")
    private String deliveryOutcomes;

    @Column(name = "exec_date")
    private String execDate;

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

    public Long getStateId() {
        return stateId;
    }

    public void setStateId(Long stateId) {
        this.stateId = stateId;
    }

    public Long getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Long districtId) {
        this.districtId = districtId;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
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

    public Long getHealthBlockId() {
        return healthBlockId;
    }

    public void setHealthBlockId(Long healthBlockId) {
        this.healthBlockId = healthBlockId;
    }

    public String getHealthBlockName() {
        return healthBlockName;
    }

    public void setHealthBlockName(String healthBlockName) {
        this.healthBlockName = healthBlockName;
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

    public Integer getYr() {
        return yr;
    }

    public void setYr(Integer yr) {
        this.yr = yr;
    }

    public String getgPVillage() {
        return gPVillage;
    }

    public void setgPVillage(String gPVillage) {
        this.gPVillage = gPVillage;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHusbandName() {
        return husbandName;
    }

    public void setHusbandName(String husbandName) {
        this.husbandName = husbandName;
    }

    public String getPhoneNumberWhom() {
        return phoneNumberWhom;
    }

    public void setPhoneNumberWhom(String phoneNumberWhom) {
        this.phoneNumberWhom = phoneNumberWhom;
    }

    public String getWhomPhoneNumber() {
        return whomPhoneNumber;
    }

    public void setWhomPhoneNumber(String whomPhoneNumber) {
        this.whomPhoneNumber = whomPhoneNumber;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getjSYBeneficiary() {
        return jSYBeneficiary;
    }

    public void setjSYBeneficiary(String jSYBeneficiary) {
        this.jSYBeneficiary = jSYBeneficiary;
    }

    public String getCaste() {
        return caste;
    }

    public void setCaste(String caste) {
        this.caste = caste;
    }

    public String getSubcenterName1() {
        return subcenterName1;
    }

    public void setSubcenterName1(String subcenterName1) {
        this.subcenterName1 = subcenterName1;
    }

    public String getaNMName() {
        return aNMName;
    }

    public void setaNMName(String aNMName) {
        this.aNMName = aNMName;
    }

    public String getaNMPhone() {
        return aNMPhone;
    }

    public void setaNMPhone(String aNMPhone) {
        this.aNMPhone = aNMPhone;
    }

    public String getAshaName() {
        return ashaName;
    }

    public void setAshaName(String ashaName) {
        this.ashaName = ashaName;
    }

    public String getAshaPhone() {
        return ashaPhone;
    }

    public void setAshaPhone(String ashaPhone) {
        this.ashaPhone = ashaPhone;
    }

    public String getDeliveryLnkFacility() {
        return deliveryLnkFacility;
    }

    public void setDeliveryLnkFacility(String deliveryLnkFacility) {
        this.deliveryLnkFacility = deliveryLnkFacility;
    }

    public String getFacilityName() {
        return facilityName;
    }

    public void setFacilityName(String facilityName) {
        this.facilityName = facilityName;
    }

    public String getLmpDate() {
        return lmpDate;
    }

    public void setLmpDate(String lmpDate) {
        this.lmpDate = lmpDate;
    }

    public String getaNC1Date() {
        return aNC1Date;
    }

    public void setaNC1Date(String aNC1Date) {
        this.aNC1Date = aNC1Date;
    }

    public String getaNC2Date() {
        return aNC2Date;
    }

    public void setaNC2Date(String aNC2Date) {
        this.aNC2Date = aNC2Date;
    }

    public String getaNC3Date() {
        return aNC3Date;
    }

    public void setaNC3Date(String aNC3Date) {
        this.aNC3Date = aNC3Date;
    }

    public String getaNC4Date() {
        return aNC4Date;
    }

    public void setaNC4Date(String aNC4Date) {
        this.aNC4Date = aNC4Date;
    }

    public String gettT1Date() {
        return tT1Date;
    }

    public void settT1Date(String tT1Date) {
        this.tT1Date = tT1Date;
    }

    public String gettT2Date() {
        return tT2Date;
    }

    public void settT2Date(String tT2Date) {
        this.tT2Date = tT2Date;
    }

    public String gettTBoosterDate() {
        return tTBoosterDate;
    }

    public void settTBoosterDate(String tTBoosterDate) {
        this.tTBoosterDate = tTBoosterDate;
    }

    public String getiFA100GivenDate() {
        return iFA100GivenDate;
    }

    public void setiFA100GivenDate(String iFA100GivenDate) {
        this.iFA100GivenDate = iFA100GivenDate;
    }

    public String getAnemia() {
        return anemia;
    }

    public void setAnemia(String anemia) {
        this.anemia = anemia;
    }

    public String getaNCComplication() {
        return aNCComplication;
    }

    public void setaNCComplication(String aNCComplication) {
        this.aNCComplication = aNCComplication;
    }

    public String getrTISTI() {
        return rTISTI;
    }

    public void setrTISTI(String rTISTI) {
        this.rTISTI = rTISTI;
    }

    public String getDlyDate() {
        return dlyDate;
    }

    public void setDlyDate(String dlyDate) {
        this.dlyDate = dlyDate;
    }

    public String getDlyPlaceHomeType() {
        return dlyPlaceHomeType;
    }

    public void setDlyPlaceHomeType(String dlyPlaceHomeType) {
        this.dlyPlaceHomeType = dlyPlaceHomeType;
    }

    public String getDlyPlacePublic() {
        return dlyPlacePublic;
    }

    public void setDlyPlacePublic(String dlyPlacePublic) {
        this.dlyPlacePublic = dlyPlacePublic;
    }

    public String getDlyPlacePrivate() {
        return dlyPlacePrivate;
    }

    public void setDlyPlacePrivate(String dlyPlacePrivate) {
        this.dlyPlacePrivate = dlyPlacePrivate;
    }

    public String getDlyType() {
        return dlyType;
    }

    public void setDlyType(String dlyType) {
        this.dlyType = dlyType;
    }

    public String getDlyComplication() {
        return dlyComplication;
    }

    public void setDlyComplication(String dlyComplication) {
        this.dlyComplication = dlyComplication;
    }

    public String getDischargeDate() {
        return dischargeDate;
    }

    public void setDischargeDate(String dischargeDate) {
        this.dischargeDate = dischargeDate;
    }

    public String getjSYPaidDate() {
        return jSYPaidDate;
    }

    public void setjSYPaidDate(String jSYPaidDate) {
        this.jSYPaidDate = jSYPaidDate;
    }

    public String getAbortion() {
        return abortion;
    }

    public void setAbortion(String abortion) {
        this.abortion = abortion;
    }

    public String getpNCHomeVisit() {
        return pNCHomeVisit;
    }

    public void setpNCHomeVisit(String pNCHomeVisit) {
        this.pNCHomeVisit = pNCHomeVisit;
    }

    public String getpNCComplication() {
        return pNCComplication;
    }

    public void setpNCComplication(String pNCComplication) {
        this.pNCComplication = pNCComplication;
    }

    public String getpPCMethod() {
        return pPCMethod;
    }

    public void setpPCMethod(String pPCMethod) {
        this.pPCMethod = pPCMethod;
    }

    public String getpNCCheckup() {
        return pNCCheckup;
    }

    public void setpNCCheckup(String pNCCheckup) {
        this.pNCCheckup = pNCCheckup;
    }

    public Integer getOutcomeNos() {
        return outcomeNos;
    }

    public void setOutcomeNos(Integer outcomeNos) {
        this.outcomeNos = outcomeNos;
    }

    public String getChild1Name() {
        return child1Name;
    }

    public void setChild1Name(String child1Name) {
        this.child1Name = child1Name;
    }

    public String getChild1Sex() {
        return child1Sex;
    }

    public void setChild1Sex(String child1Sex) {
        this.child1Sex = child1Sex;
    }

    public Double getChild1Wt() {
        return child1Wt;
    }

    public void setChild1Wt(Double child1Wt) {
        this.child1Wt = child1Wt;
    }

    public String getChild1Brestfeeding() {
        return child1Brestfeeding;
    }

    public void setChild1Brestfeeding(String child1Brestfeeding) {
        this.child1Brestfeeding = child1Brestfeeding;
    }

    public String getChild2Name() {
        return child2Name;
    }

    public void setChild2Name(String child2Name) {
        this.child2Name = child2Name;
    }

    public String getChild2Sex() {
        return child2Sex;
    }

    public void setChild2Sex(String child2Sex) {
        this.child2Sex = child2Sex;
    }

    public Double getChild2Wt() {
        return child2Wt;
    }

    public void setChild2Wt(Double child2Wt) {
        this.child2Wt = child2Wt;
    }

    public String getChild2Brestfeeding() {
        return child2Brestfeeding;
    }

    public void setChild2Brestfeeding(String child2Brestfeeding) {
        this.child2Brestfeeding = child2Brestfeeding;
    }

    public String getChild3Name() {
        return child3Name;
    }

    public void setChild3Name(String child3Name) {
        this.child3Name = child3Name;
    }

    public String getChild3Sex() {
        return child3Sex;
    }

    public void setChild3Sex(String child3Sex) {
        this.child3Sex = child3Sex;
    }

    public Double getChild3Wt() {
        return child3Wt;
    }

    public void setChild3Wt(Double child3Wt) {
        this.child3Wt = child3Wt;
    }

    public String getChild3Brestfeeding() {
        return child3Brestfeeding;
    }

    public void setChild3Brestfeeding(String child3Brestfeeding) {
        this.child3Brestfeeding = child3Brestfeeding;
    }

    public String getChild4Name() {
        return child4Name;
    }

    public void setChild4Name(String child4Name) {
        this.child4Name = child4Name;
    }

    public String getChild4Sex() {
        return child4Sex;
    }

    public void setChild4Sex(String child4Sex) {
        this.child4Sex = child4Sex;
    }

    public Double getChild4Wt() {
        return child4Wt;
    }

    public void setChild4Wt(Double child4Wt) {
        this.child4Wt = child4Wt;
    }

    public String getChild4Brestfeeding() {
        return child4Brestfeeding;
    }

    public void setChild4Brestfeeding(String child4Brestfeeding) {
        this.child4Brestfeeding = child4Brestfeeding;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getmTHRREGDATE() {
        return mTHRREGDATE;
    }

    public void setmTHRREGDATE(String mTHRREGDATE) {
        this.mTHRREGDATE = mTHRREGDATE;
    }

    public String getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(String lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Integer getaNMID() {
        return aNMID;
    }

    public void setaNMID(Integer aNMID) {
        this.aNMID = aNMID;
    }

    public Integer getaSHAID() {
        return aSHAID;
    }

    public void setaSHAID(Integer aSHAID) {
        this.aSHAID = aSHAID;
    }

    public Boolean getCallAns() {
        return callAns;
    }

    public void setCallAns(Boolean callAns) {
        this.callAns = callAns;
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

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public Integer getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Integer updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Integer getAadharNo() {
        return aadharNo;
    }

    public void setAadharNo(Integer aadharNo) {
        this.aadharNo = aadharNo;
    }

    public Integer getbPLAPL() {
        return bPLAPL;
    }

    public void setbPLAPL(Integer bPLAPL) {
        this.bPLAPL = bPLAPL;
    }

    public Integer geteID() {
        return eID;
    }

    public void seteID(Integer eID) {
        this.eID = eID;
    }

    public String geteIDTime() {
        return eIDTime;
    }

    public void seteIDTime(String eIDTime) {
        this.eIDTime = eIDTime;
    }

    public Integer getEntryType() {
        return entryType;
    }

    public void setEntryType(Integer entryType) {
        this.entryType = entryType;
    }

    public String getRegistrationNo() {
        return registrationNo;
    }

    public void setRegistrationNo(String registrationNo) {
        this.registrationNo = registrationNo;
    }

    public Long getCaseNo() {
        return caseNo;
    }

    public void setCaseNo(Long caseNo) {
        this.caseNo = caseNo;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getAbortionType() {
        return abortionType;
    }

    public void setAbortionType(String abortionType) {
        this.abortionType = abortionType;
    }

    public String getDeliveryOutcomes() {
        return deliveryOutcomes;
    }

    public void setDeliveryOutcomes(String deliveryOutcomes) {
        this.deliveryOutcomes = deliveryOutcomes;
    }

    public String getExecDate() {
        return execDate;
    }

    public void setExecDate(String execDate) {
        this.execDate = execDate;
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
