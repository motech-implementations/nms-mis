package com.beehyv.nmsreporting.entity;

import com.beehyv.nmsreporting.enums.MessageType;

import java.util.Date;

public class AshaBeneficiaryMessageDto {


    private Long ashaId;
    private String ashaContactNumber;
    private Long beneficiaryId;
    private String beneficiaryRchId;
    private String beneficiaryContactNumber;
    private MessageType type;
    private Date date;

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
}
