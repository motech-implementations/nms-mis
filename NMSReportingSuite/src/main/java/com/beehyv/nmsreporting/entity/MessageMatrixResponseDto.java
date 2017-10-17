package com.beehyv.nmsreporting.entity;

import java.util.List;

/**
 * Created by beehyv on 13/10/17.
 */
public class MessageMatrixResponseDto {

    List<MessageMatrixDto> motherData;
    List<MessageMatrixDto> childData;

    public List<MessageMatrixDto> getMotherData() {
        return motherData;
    }

    public void setMotherData(List<MessageMatrixDto> motherData) {
        this.motherData = motherData;
    }

    public List<MessageMatrixDto> getChildData() {
        return childData;
    }

    public void setChildData(List<MessageMatrixDto> childData) {
        this.childData = childData;
    }
}
