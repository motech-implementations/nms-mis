package com.beehyv.nmsreporting.entity;

/**
 * Created by beehyv on 13/10/17.
 */
public class MessageMatrixResponseDto {

    MessageMatrixDto motherData;
    MessageMatrixDto childData;

    public MessageMatrixDto getMotherData() {
        return motherData;
    }

    public void setMotherData(MessageMatrixDto motherData) {
        this.motherData = motherData;
    }

    public MessageMatrixDto getChildData() {
        return childData;
    }

    public void setChildData(MessageMatrixDto childData) {
        this.childData = childData;
    }
}
