package com.beehyv.nmsreporting.model;

import com.beehyv.nmsreporting.enums.MessageType;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "beneficiary_sms_notifications")
public class AshaBeneficiaryMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "flw_id", nullable = false)
    private Long ashaId;

    @Column(name = "flw_contact_number", nullable = false)
    private String ashaContactNumber;

    @Column(name = "beneficiary_id", nullable = false)
    private Long beneficiaryId;

    @Column(name = "beneficiary_name", nullable = false)
    private String beneficiaryName;

    @Column(name = "beneficiary_rch_id", nullable = false)
    private String beneficiaryRchId;

    @Column(name = "beneficiary_contact_number", nullable = false)
    private String beneficiaryContactNumber;

    @Column(name = "state_name")
    private String stateName;


    @Column(name = "language_code")
    private String language_code;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private MessageType type;

    @Column(name = "date", nullable = false)
    private Date date;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAshaId() {
        return ashaId;
    }

    public void setAshaId(Long ashaId) {
        this.ashaId = ashaId;
    }

    public String getAshaContactNumber() {
        return ashaContactNumber;
    }

    public void setAshaContactNumber(String ashaContactNumber) {
        this.ashaContactNumber = ashaContactNumber;
    }

    public Long getBeneficiaryId() {
        return beneficiaryId;
    }

    public void setBeneficiaryId(Long beneficiaryId) {
        this.beneficiaryId = beneficiaryId;
    }

    public String getBeneficiaryRchId() {
        return beneficiaryRchId;
    }

    public void setBeneficiaryRchId(String beneficiaryRchId) {
        this.beneficiaryRchId = beneficiaryRchId;
    }

    public String getBeneficiaryContactNumber() {
        return beneficiaryContactNumber;
    }

    public void setBeneficiaryContactNumber(String beneficiaryContactNumber) {
        this.beneficiaryContactNumber = beneficiaryContactNumber;
    }


    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getBeneficiaryName() {
        return beneficiaryName;
    }

    public void setBeneficiaryName(String beneficiaryName) {
        this.beneficiaryName = beneficiaryName;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getLanguage_code() {
        return language_code;
    }

    public void setLanguage_code(String language_code) {
        this.language_code = language_code;
    }
}
