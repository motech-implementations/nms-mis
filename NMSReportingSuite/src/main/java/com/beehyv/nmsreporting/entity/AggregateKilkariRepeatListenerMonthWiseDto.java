package com.beehyv.nmsreporting.entity;

import java.util.List;

public class AggregateKilkariRepeatListenerMonthWiseDto {

    private List<BreadCrumbDto> breadCrumbData;
    private Object numberData;
    private Object percentData;

    public List<BreadCrumbDto> getBreadCrumbData() {
        return breadCrumbData;
    }

    public void setBreadCrumbData(List<BreadCrumbDto> breadCrumbData) {
        this.breadCrumbData = breadCrumbData;
    }

    public Object getNumberData() {
        return numberData;
    }

    public void setNumberData(Object numberData) {
        this.numberData = numberData;
    }

    public Object getPercentData() {
        return percentData;
    }

    public void setPercentData(Object percentData) {
        this.percentData = percentData;
    }
}
