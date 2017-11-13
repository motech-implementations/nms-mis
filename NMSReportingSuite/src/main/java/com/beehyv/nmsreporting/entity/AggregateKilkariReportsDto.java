package com.beehyv.nmsreporting.entity;

/**
 * Created by himanshu on 06/10/17.
 */

import java.util.List;

public class AggregateKilkariReportsDto {

    private List<BreadCrumbDto> breadCrumbData;
    private Object tableData;

    public List<BreadCrumbDto> getBreadCrumbData() {
        return breadCrumbData;
    }

    public void setBreadCrumbData(List<BreadCrumbDto> breadCrumbData) {
        this.breadCrumbData = breadCrumbData;
    }

    public Object getTableData() {
        return tableData;
    }

    public void setTableData(Object tableData) {
        this.tableData = tableData;
    }
}
