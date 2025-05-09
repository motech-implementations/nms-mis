package com.beehyv.nmsreporting.business.impl;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TargetFileNotification {

    @JsonProperty("filename")
    private String fileName;

    @JsonProperty("checksum")
    private String checksum;

    @JsonProperty("recordscount")
    private Integer recordsCount;


    public TargetFileNotification() { }

    public TargetFileNotification(String fileName, String checksum, Integer recordsCount) {
        this.fileName = fileName;
        this.checksum = checksum;
        this.recordsCount = recordsCount;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getChecksum() {
        return checksum;
    }

    public void setChecksum(String checksum) {
        this.checksum = checksum;
    }

    public Integer getRecordsCount() {
        return recordsCount;
    }

    public void setRecordsCount(Integer recordsCount) {
        this.recordsCount = recordsCount;
    }

    @Override
    public String toString() {
        return "TargetFileNotification{" +
                "fileName='" + fileName + '\'' +
                ", checksum='" + checksum + '\'' +
                ", recordsCount=" + recordsCount +
                '}';
    }
}
