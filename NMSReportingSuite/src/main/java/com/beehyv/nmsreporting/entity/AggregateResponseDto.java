package com.beehyv.nmsreporting.entity;

import java.util.List;
import java.util.Map;

/**
 * Created by beehyv on 28/9/17.
 */
public class AggregateResponseDto {

    Map<String,String> breadCrumbData;
    Object tableData;

    public Map<String, String> getBreadCrumbData() {
        return breadCrumbData;
    }

    public void setBreadCrumbData(Map<String, String> breadCrumbData) {
        this.breadCrumbData = breadCrumbData;
    }

    public Object getTableData() {
        return tableData;
    }

    public void setTableData(Object tableData) {
        this.tableData = tableData;
    }
}
