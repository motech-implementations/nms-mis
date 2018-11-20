package com.beehyv.nmsreporting.entity;

/**
 * Created by beehyv on 4/6/17.
 */
public class Report {
    private String name;

    private String reportEnum;

    private String simpleName;

    private String icon;

    private String service;

    private boolean showItem;

    public Report(String name, String reportEnum, String simpleName, String icon, String service, boolean showItem){
        this.name = name;
        this.reportEnum = reportEnum;
        this.simpleName = simpleName;
        this.icon = icon;
        this.service = service;
        this.showItem = showItem;
    }

    public String getName() {
        return name;
    }

    public String getSimpleName() {
        return simpleName;
    }

    public void setSimpleName(String simpleName) {
        this.simpleName = simpleName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReportEnum() {
        return reportEnum;
    }

    public void setReportEnum(String reportEnum) {
        this.reportEnum = reportEnum;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public boolean isShowItem() {
        return showItem;
    }

    public void setShowItem(boolean showItem) {
        this.showItem = showItem;
    }
}
