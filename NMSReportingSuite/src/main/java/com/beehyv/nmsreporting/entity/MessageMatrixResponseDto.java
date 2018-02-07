package com.beehyv.nmsreporting.entity;

import java.util.List;

/**
 * Created by beehyv on 13/10/17.
 */
public class MessageMatrixResponseDto {

    private List<BreadCrumbDto> breadCrumbData;
    private List<MessageMatrixDto> motherData;
    private List<MessageMatrixDto> childData;

    public List<BreadCrumbDto> getBreadCrumbData() {
        return breadCrumbData;
    }

    public void setBreadCrumbData(List<BreadCrumbDto> breadCrumbData) {
        this.breadCrumbData = breadCrumbData;
    }

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
